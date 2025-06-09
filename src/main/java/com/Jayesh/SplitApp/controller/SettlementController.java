package com.Jayesh.SplitApp.controller;


import com.Jayesh.SplitApp.dto.BalanceResponse;
import com.Jayesh.SplitApp.dto.SettlementsResponse;
import com.Jayesh.SplitApp.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SettlementController {

    @Autowired
    private final SettlementService settlementService;

    @GetMapping("/balances")
    public ResponseEntity<List<BalanceResponse>> getBalances() {
        return ResponseEntity.ok(settlementService.calculateBalances());
    }

    @GetMapping("/settlements")
    public ResponseEntity<List<SettlementsResponse>> getSettlements() {
        return ResponseEntity.ok(settlementService.getOptimizedSettlements());

    }


}
