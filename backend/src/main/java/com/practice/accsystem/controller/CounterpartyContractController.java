package com.practice.accsystem.controller;

import com.practice.accsystem.dto.counterpartyContract.CounterpartyContractGetDto;
import com.practice.accsystem.dto.counterpartyContract.CounterpartyContractPostDto;
import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.CounterpartyContractEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotHasPermissionException;
import com.practice.accsystem.mapper.CounterpartyContractMapper;
import com.practice.accsystem.security.UserDetailsImpl;
import com.practice.accsystem.service.ContractService;
import com.practice.accsystem.service.CounterpartyContractService;
import com.practice.accsystem.service.CounterpartyService;
import com.practice.accsystem.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

import static com.practice.accsystem.config.OpenApiConfiguration.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/api/contracts/{contractId}/counterparty-contracts")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class CounterpartyContractController {
    private final CounterpartyContractService counterpartyContractService;
    private final ContractService contractService;
    private final CounterpartyService counterpartyService;
    private final UserService userService;
    private final CounterpartyContractMapper counterpartyContractMapper;

    public CounterpartyContractController(CounterpartyContractService counterpartyContractService,
                                          ContractService contractService,
                                          CounterpartyService counterpartyService,
                                          UserService userService,
                                          CounterpartyContractMapper counterpartyContractMapper) {
        this.counterpartyContractService = counterpartyContractService;
        this.contractService = contractService;
        this.counterpartyService = counterpartyService;
        this.userService = userService;
        this.counterpartyContractMapper = counterpartyContractMapper;
    }

    @PreAuthorize("hasAuthority('counterpartyContract:write')")
    @PostMapping
    public CounterpartyContractGetDto createCounterpartyContract(@PathVariable Long contractId,
                                                                 @Valid @RequestBody CounterpartyContractPostDto counterpartyContractPostDto,
                                                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        ContractEntity contract = contractService.findContractById(contractId);

        if (contractService.hasAccessToContract(user, contract)) {
            return counterpartyContractMapper.toDto(
                    counterpartyContractService.createCounterpartyContract(
                            contract,
                            counterpartyService.findCounterpartyById(counterpartyContractPostDto.getCounterpartyId()),
                            counterpartyContractMapper.toEntity(counterpartyContractPostDto)
                    )
            );
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not create counterpartyContract for contract with id '%d'", contract.getId()));
        }
    }

    @PreAuthorize("hasAuthority('counterpartyContract:read')")
    @GetMapping("/{counterpartyContractId}")
    public CounterpartyContractGetDto findCounterpartyContractById(@PathVariable Long contractId,
                                                                   @PathVariable Long counterpartyContractId,
                                                                   @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CounterpartyContractEntity counterpartyContract = counterpartyContractService.findCounterpartyContractById(
                contractService.findContractById(contractId),
                counterpartyContractId
        );
        AppUserEntity user = userService.findUserById(userDetails.getId());

        if (counterpartyContractService.hasAccessToCounterpartyContract(user, counterpartyContract)) {
            return counterpartyContractMapper.toDto(counterpartyContract);
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not get counterpartyContract with id '%d' by user with id '%d'",
                            counterpartyContractId, userDetails.getId()));
        }
    }

    @PreAuthorize("hasAuthority('counterpartyContract:read')")
    @GetMapping
    public Page<CounterpartyContractGetDto> findAllCounterpartyContracts(@PathVariable Long contractId,
                                                                         @RequestParam(required = false) Long counterpartyId,
                                                                         @RequestParam(required = false) String title,
                                                                         @RequestParam(required = false) BigDecimal minSum,
                                                                         @RequestParam(required = false) BigDecimal maxSum,
                                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startPeriod,
                                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endPeriod,
                                                                         @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                         @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ContractEntity contract = contractService.findContractById(contractId);
        AppUserEntity user = userService.findUserById(userDetails.getId());

        if (contractService.hasAccessToContract(user, contract)) {
            return counterpartyContractService.findAllCounterpartyContractsByContract(contract, counterpartyId, title,
                            minSum, maxSum, startPeriod, endPeriod, pageable)
                    .map(counterpartyContractMapper::toDto);
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not get counterpartyContracts by contract with id '%d'", contractId));
        }
    }

    @PreAuthorize("hasAuthority('counterpartyContract:write')")
    @PutMapping("/{counterpartyContractId}")
    public CounterpartyContractGetDto updateCounterpartyContract(@PathVariable Long contractId,
                                                                 @PathVariable Long counterpartyContractId,
                                                                 @Valid @RequestBody CounterpartyContractPostDto counterpartyContractPostDto,
                                                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CounterpartyContractEntity counterpartyContract = counterpartyContractService.findCounterpartyContractById(
                contractService.findContractById(contractId),
                counterpartyContractId
        );
        AppUserEntity user = userService.findUserById(userDetails.getId());

        if (counterpartyContractService.hasAccessToCounterpartyContract(user, counterpartyContract)) {
            return counterpartyContractMapper.toDto(
                    counterpartyContractService.updateCounterpartyContract(
                            counterpartyContract,
                            counterpartyContractMapper.toEntity(counterpartyContractPostDto)
                    )
            );
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not update counterpartyContract with id '%d' by user with id '%d'",
                            counterpartyContractId, userDetails.getId()));
        }
    }

    @PreAuthorize("hasAuthority('counterpartyContract:write')")
    @DeleteMapping("/{counterpartyContractId}")
    public CounterpartyContractGetDto deleteCounterpartyContract(@PathVariable Long contractId,
                                                                 @PathVariable Long counterpartyContractId,
                                                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        CounterpartyContractEntity counterpartyContract = counterpartyContractService.findCounterpartyContractById(
                contractService.findContractById(contractId),
                counterpartyContractId
        );

        if (counterpartyContractService.hasAccessToCounterpartyContract(user, counterpartyContract)) {
            return counterpartyContractMapper.toDto(
                    counterpartyContractService.deleteCounterpartyContract(counterpartyContract)
            );
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not delete counterpartyContract with id '%d' by user with id '%d'",
                            counterpartyContractId, userDetails.getId()));
        }
    }
}
