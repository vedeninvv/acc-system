package com.practice.accsystem.controller;

import com.practice.accsystem.entity.ContractEntity;
import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.exception.NotHasPermissionException;
import com.practice.accsystem.security.UserDetailsImpl;
import com.practice.accsystem.service.ContractService;
import com.practice.accsystem.service.UserService;
import com.practice.accsystem.service.impl.ExcelServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ExcelServiceImpl excelService;
    private final ContractService contractService;
    private final UserService userService;

    public ReportController(ExcelServiceImpl excelService, ContractService contractService, UserService userService) {
        this.excelService = excelService;
        this.contractService = contractService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('report')")
    @GetMapping("/contracts")
    public ResponseEntity<ByteArrayResource> createContractsReportByUserInPeriodByPlanDeadline(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date periodStart,
                                                                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date periodEnd,
                                                                                               @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ByteArrayResource report = contractService.createContractsReportByUserInPeriodByPlanDeadline(
                userService.findUserById(userDetails.getId()),
                periodStart,
                periodEnd
        );
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"contractsReport.xls\"")
                .body(report);

    }

    @PreAuthorize("hasAuthority('report')")
    @GetMapping("/contracts/{contractId}/stages")
    public ResponseEntity<ByteArrayResource> createContractStageReport(@PathVariable Long contractId,
                                            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AppUserEntity user = userService.findUserById(userDetails.getId());
        ContractEntity contract = contractService.findContractById(contractId);

        if (contractService.hasAccessToContract(user, contract)) {
            ByteArrayResource report = contractService.createContractStageReport(contract);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"contractStagesReport.xls\"")
                    .body(report);
        } else {
            throw new NotHasPermissionException(
                    String.format("Can not get access to contract with id '%d' by user with id '%d'", contractId, user.getId()));
        }
    }
}
