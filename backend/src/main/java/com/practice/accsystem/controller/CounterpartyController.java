package com.practice.accsystem.controller;

import com.practice.accsystem.dto.counterparty.CounterpartyGetDto;
import com.practice.accsystem.dto.counterparty.CounterpartyPostDto;
import com.practice.accsystem.mapper.CounterpartyMapper;
import com.practice.accsystem.service.CounterpartyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.practice.accsystem.config.OpenApiConfiguration.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/api/counterparties")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class CounterpartyController {
    private final CounterpartyMapper counterpartyMapper;
    private final CounterpartyService counterpartyService;

    public CounterpartyController(CounterpartyMapper counterpartyMapper,
                                  CounterpartyService counterpartyService) {
        this.counterpartyMapper = counterpartyMapper;
        this.counterpartyService = counterpartyService;
    }

    @Operation(summary = "Создать контрагента")
    @PreAuthorize("hasAuthority('counterparty:write:all')")
    @PostMapping
    public CounterpartyGetDto createCounterparty(@Valid @RequestBody CounterpartyPostDto counterpartyPostDto) {
        return counterpartyMapper.toDto(
                counterpartyService.createCounterparty(counterpartyMapper.toEntity(counterpartyPostDto))
        );
    }

    @Operation(summary = "Найти контрагента по ID")
    @PreAuthorize("hasAuthority('counterparty:read:all')")
    @GetMapping("/{counterpartyId}")
    public CounterpartyGetDto findCounterpartyById(@PathVariable Long counterpartyId) {
        return counterpartyMapper.toDto(
                counterpartyService.findCounterpartyById(counterpartyId)
        );
    }

    @Operation(summary = "Найти всех контрагентов")
    @PreAuthorize("hasAuthority('counterparty:read:all')")
    @GetMapping
    public Page<CounterpartyGetDto> findAllCounterparties(@RequestParam(required = false) String title,
                                                          @RequestParam(required = false) String address,
                                                          @RequestParam(required = false) String INN,
                                                          @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return counterpartyService.findAllCounterparties(title, address, INN, pageable).map(counterpartyMapper::toDto);
    }

    @Operation(summary = "Обновить данные контрагента")
    @PreAuthorize("hasAuthority('counterparty:write:all')")
    @PutMapping("/{counterpartyId}")
    public CounterpartyGetDto updateCounterparty(@PathVariable Long counterpartyId,
                                                 @Valid @RequestBody CounterpartyPostDto counterpartyPostDto) {
        return counterpartyMapper.toDto(
                counterpartyService.updateCounterparty(
                        counterpartyService.findCounterpartyById(counterpartyId),
                        counterpartyMapper.toEntity(counterpartyPostDto)
                )
        );
    }

    @Operation(summary = "Удалить контрагента")
    @PreAuthorize("hasAuthority('counterparty:write:all')")
    @DeleteMapping("/{counterpartyId}")
    public CounterpartyGetDto deleteCounterparty(@PathVariable Long counterpartyId) {
        return counterpartyMapper.toDto(
                counterpartyService.deleteCounterparty(
                        counterpartyService.findCounterpartyById(counterpartyId)
                )
        );
    }
}
