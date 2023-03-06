package com.practice.accsystem.controller;

import com.practice.accsystem.dto.contractStage.ContractStageGetDto;
import com.practice.accsystem.dto.contractStage.ContractStagePostDto;
import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractStageEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotHasPermissionException;
import com.practice.accsystem.mapper.ContractStageMapper;
import com.practice.accsystem.security.UserDetailsImpl;
import com.practice.accsystem.service.ContractService;
import com.practice.accsystem.service.ContractStageService;
import com.practice.accsystem.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/contracts/{contractId}/stage")
public class ContractStageController {
    private final ContractStageService contractStageService;
    private final ContractService contractService;
    private final UserService userService;
    private final ContractStageMapper contractStageMapper;

    public ContractStageController(ContractStageService contractStageService,
                                   ContractService contractService,
                                   UserService userService,
                                   ContractStageMapper contractStageMapper) {
        this.contractStageService = contractStageService;
        this.contractService = contractService;
        this.userService = userService;
        this.contractStageMapper = contractStageMapper;
    }

    @PreAuthorize("hasAuthority('contractStage:write')")
    @PostMapping
    public ContractStageGetDto createContractStage(@PathVariable Long contractId,
                                                   @Valid @RequestBody ContractStagePostDto contractStagePostDto,
                                                   @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        ContractEntity contract = contractService.findContractById(contractId);

        if (contractService.hasAccessToContract(user, contract)) {
            return contractStageMapper.toDto(
                    contractStageService.createContractStage(contract, contractStageMapper.toEntity(contractStagePostDto))
            );
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not create contractStage for contract with id '%d'", contractId));
        }
    }

    @PreAuthorize("hasAuthority('contractStage:read')")
    @GetMapping("/{contractStageId}")
    public ContractStageGetDto findContractStageById(@PathVariable Long contractId,
                                                     @PathVariable Long contractStageId,
                                                     @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        ContractStageEntity contractStage = contractStageService.findContractStageById(
                contractService.findContractById(contractId),
                contractStageId);

        if (contractStageService.hasAccessToContractStage(user, contractStage)) {
            return contractStageMapper.toDto(contractStage);
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not get access to contractStage with id '%d' for contract with id '%d'", contractStageId, contractId));
        }
    }

    @PreAuthorize("hasAuthority('contractStage:read')")
    @GetMapping
    public Page<ContractStageGetDto> findAllContractStage(@PathVariable Long contractId,
                                                          @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        ContractEntity contract = contractService.findContractById(contractId);

        if (contractService.hasAccessToContract(user, contract)) {
            return contractStageService.findAllContractStageByContract(contract, pageable)
                    .map(contractStageMapper::toDto);
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not get access to contract with id '%d'", contractId));
        }
    }

    @PreAuthorize("hasAuthority('contractStage:write')")
    @PutMapping("/{contractStageId}")
    public ContractStageGetDto updateContractStage(@PathVariable Long contractId,
                                                   @PathVariable Long contractStageId,
                                                   @Valid @RequestBody ContractStagePostDto contractStagePostDto,
                                                   @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        ContractStageEntity contractStage = contractStageService.findContractStageById(
                contractService.findContractById(contractId),
                contractStageId);

        if (contractStageService.hasAccessToContractStage(user, contractStage)) {
            return contractStageMapper.toDto(
                    contractStageService.updateContractStage(contractStage, contractStageMapper.toEntity(contractStagePostDto))
            );
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not update contractStage with id '%d' for contract with id '%d'", contractStageId, contractId));
        }
    }

    @PreAuthorize("hasAuthority('contractStage:write')")
    @DeleteMapping("/{contractStageId}")
    public ContractStageGetDto deleteContractStage(@PathVariable Long contractId,
                                                   @PathVariable Long contractStageId,
                                                   @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        ContractStageEntity contractStage = contractStageService.findContractStageById(
                contractService.findContractById(contractId),
                contractStageId);

        if (contractStageService.hasAccessToContractStage(user, contractStage)) {
            return contractStageMapper.toDto(
                    contractStageService.deleteContractStage(contractStage)
            );
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not delete contractStage with id '%d' for contract with id '%d'", contractStageId, contractId));
        }
    }
}
