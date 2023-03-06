package com.practice.accsystem.controller;

import com.practice.accsystem.dto.counterparty.CounterpartyGetDto;
import com.practice.accsystem.dto.counterparty.CounterpartyPostDto;
import com.practice.accsystem.mapper.CounterpartyMapper;
import com.practice.accsystem.service.CounterpartyService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/counterparties")
public class CounterpartyController {
    private final CounterpartyMapper counterpartyMapper;
    private final CounterpartyService counterpartyService;

    public CounterpartyController(CounterpartyMapper counterpartyMapper,
                                  CounterpartyService counterpartyService) {
        this.counterpartyMapper = counterpartyMapper;
        this.counterpartyService = counterpartyService;
    }

    @PreAuthorize("hasAuthority('counterparty:write:all')")
    @PostMapping
    public CounterpartyGetDto createCounterparty(@Valid @RequestBody CounterpartyPostDto counterpartyPostDto) {
        return counterpartyMapper.toDto(
                counterpartyService.createCounterparty(counterpartyMapper.toEntity(counterpartyPostDto))
        );
    }

    @PreAuthorize("hasAuthority('counterparty:read:all')")
    @GetMapping("/{counterpartyId}")
    public CounterpartyGetDto findCounterpartyById(@PathVariable Long counterpartyId) {
        return counterpartyMapper.toDto(
                counterpartyService.findCounterpartyById(counterpartyId)
        );
    }

    @PreAuthorize("hasAuthority('counterparty:read:all')")
    @GetMapping
    public Page<CounterpartyGetDto> findAllCounterparties(@RequestParam(required = false) String searchStr,
                                                          @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return counterpartyService.findAllCounterparties(searchStr, pageable).map(counterpartyMapper::toDto);
    }

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
