package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.response.TrainerSettlementResponse;
import com.enzith.nexgen.service.TrainerSettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/trainer-settlement")
@RequiredArgsConstructor
public class TrainerSettlementController {

    private final TrainerSettlementService trainerSettlementService;

    @GetMapping("/info")
    public ResponseEntity<TrainerSettlementResponse> getTrainerSettlement(
            @RequestParam(value = "trainer_id") Long trainerId,
            @RequestParam(value = "from_date") LocalDate fromDate,
            @RequestParam(value = "to_date") LocalDate toDate) {
        return new ResponseEntity<>(trainerSettlementService.getTrainerSettlement(fromDate, toDate, trainerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TrainerSettlementResponse> trainerSettlement(
            @RequestParam(value = "trainer_id") Long trainerId,
            @RequestParam(value = "from_date") LocalDate fromDate,
            @RequestParam(value = "to_date") LocalDate toDate) {
        return new ResponseEntity<>(trainerSettlementService.trainerSettlement(fromDate, toDate, trainerId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllTrainerSettlement(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        return new ResponseEntity<>(trainerSettlementService.findAllSettlement(firstName, currentPage, pageSize), HttpStatus.OK);
    }
}
