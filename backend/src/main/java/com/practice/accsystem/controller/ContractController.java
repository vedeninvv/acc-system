package com.practice.accsystem.controller;

import com.practice.accsystem.dto.contract.ContractGetDto;
import com.practice.accsystem.dto.contract.ContractPostDto;
import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.ContractType;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotHasPermissionException;
import com.practice.accsystem.mapper.ContractMapper;
import com.practice.accsystem.security.UserDetailsImpl;
import com.practice.accsystem.service.ContractService;
import com.practice.accsystem.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

import static com.practice.accsystem.config.OpenApiConfiguration.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/api/contracts")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class ContractController {
    private final ContractService contractService;
    private final UserService userService;
    private final ContractMapper contractMapper;

    public ContractController(ContractService contractService, UserService userService, ContractMapper contractMapper) {
        this.contractService = contractService;
        this.userService = userService;
        this.contractMapper = contractMapper;
    }

    @PreAuthorize("hasAuthority('contract:read:all') or hasAuthority('contract:read:self')")
    @PostMapping
    public ContractGetDto createContract(@Valid @RequestBody ContractPostDto contractPostDto,
                                         @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return contractMapper.toDto(
                contractService.createContract(
                        userService.findUserById(userDetails.getId()),
                        contractMapper.toEntity(contractPostDto)
                )
        );
    }

    @PostAuthorize("hasAuthority('contract:read:all') " +
            "or (hasAuthority('contract:read:self') and returnObject.assignedUserId == #userDetails.id)")
    @GetMapping("/{contractId}")
    public ContractGetDto findContractById(@PathVariable Long contractId,
                                           @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return contractMapper.toDto(
                contractService.findContractById(contractId)
        );
    }

    @PreAuthorize("hasAuthority('contract:read:all') or hasAuthority('contract:read:self')")
    @GetMapping("/managing")
    public Page<ContractGetDto> findAllManagingContracts(@RequestParam(required = false) String title,
                                                         @RequestParam(required = false) ContractType contractType,
                                                         @RequestParam(required = false) BigDecimal minSum,
                                                         @RequestParam(required = false) BigDecimal maxSum,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startPeriod,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endPeriod,
                                                         @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                         @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return contractService.findAllContracts(userDetails.getId(), title, contractType, minSum, maxSum, startPeriod, endPeriod, pageable)
                .map(contractMapper::toDto);
    }

    @PreAuthorize("hasAuthority('contract:read:all')")
    @GetMapping
    public Page<ContractGetDto> findAllContracts(@RequestParam(required = false) Long assignedUserId,
                                                 @RequestParam(required = false) String title,
                                                 @RequestParam(required = false) ContractType contractType,
                                                 @RequestParam(required = false) BigDecimal minSum,
                                                 @RequestParam(required = false) BigDecimal maxSum,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startPeriod,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endPeriod,
                                                 @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return contractService.findAllContracts(assignedUserId, title, contractType, minSum, maxSum, startPeriod, endPeriod, pageable)
                .map(contractMapper::toDto);
    }

    @PreAuthorize("hasAuthority('contract:write:all') or hasAuthority('contract:write:self')")
    @PutMapping("/{contractId}")
    public ContractGetDto updateContract(@PathVariable Long contractId,
                                         @Valid @RequestBody ContractPostDto contractPostDto,
                                         @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        ContractEntity contract = contractService.findContractById(contractId);

        if (contractService.hasAccessToContract(user, contract)) {
            return contractMapper.toDto(
                    contractService.updateContract(contract, contractMapper.toEntity(contractPostDto))
            );
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not update contract with id '%d' by user with id '%d'", contractId, user.getId()));
        }
    }

    @PreAuthorize("hasAuthority('contract:write:all') or hasAuthority('contract:write:self')")
    @DeleteMapping("/{contractId}")
    public ContractGetDto deleteContract(@PathVariable Long contractId,
                                         @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        ContractEntity contract = contractService.findContractById(contractId);

        if (contractService.hasAccessToContract(user, contract)) {
            return contractMapper.toDto(
                    contractService.deleteContract(contract)
            );
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not delete contract with id '%d' by user with id '%d'", contractId, user.getId()));
        }
    }
}
