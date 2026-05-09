package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.domain.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "simulaciones", description = "Simulador de prestamo")
@RestController
@RequestMapping("/api/v1/simulaciones")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @Operation(operationId = "simularPrestamo", summary = "Simular prestamo con cuota fija")
    @PostMapping("/prestamo")
    public ResponseEntity<PrestamoResponse> simularPrestamo(
            @Valid @RequestBody PrestamoRequest prestamoRequest) {
        return ResponseEntity.ok(prestamoService.simularPrestamo(prestamoRequest));
    }
}