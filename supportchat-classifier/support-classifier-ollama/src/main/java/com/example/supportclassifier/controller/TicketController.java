package com.example.supportclassifier.controller;

import com.example.supportclassifier.dto.TicketAnalysis;
import com.example.supportclassifier.dto.TicketRequest;
import com.example.supportclassifier.service.TicketAnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketAnalysisService ticketAnalysisService;

    public TicketController(TicketAnalysisService ticketAnalysisService) {
        this.ticketAnalysisService = ticketAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<TicketAnalysis> analyze(@Valid @RequestBody TicketRequest request) {
        TicketAnalysis result = ticketAnalysisService.analyze(
                request.getCustomerEmail(), request.getMessageBody());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "application", "support-classifier",
                "modelProvider", "Ollama",
                "model", "llama3.2"
        ));
    }
}
