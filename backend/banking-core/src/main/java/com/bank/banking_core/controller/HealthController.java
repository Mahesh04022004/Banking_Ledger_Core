package com.bank.banking_core.controller;


import com.bank.banking_core.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<ApiResponse<String>> health() {

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "Application is running successfully.",
                "UP"
        );

        return ResponseEntity.ok(response);
    }
}
