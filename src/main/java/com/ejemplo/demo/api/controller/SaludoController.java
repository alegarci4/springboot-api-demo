package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.SaludoRequest;
import com.ejemplo.demo.api.dto.SaludoResponse;
import com.ejemplo.demo.domain.service.SaludoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "workshop", description = "Health y saludos del workshop")
@RestController
@RequestMapping("/api/v1")
public class SaludoController {

    private final SaludoService saludoService;

    public SaludoController(SaludoService saludoService) {
        this.saludoService = saludoService;
    }

    @Operation(operationId = "getWorkshopHealth", summary = "Health check del workshop")
    @GetMapping
    public ResponseEntity<Map<String, String>> getWorkshopHealth() {
        return ResponseEntity.ok(Map.of(
                "estado", "ok",
                "mensaje", "Workshop Spring Boot activo"
        ));
    }

    @Operation(operationId = "saludarPorGet", summary = "Saludo por GET con query param")
    @GetMapping("/saludos")
    public ResponseEntity<SaludoResponse> saludarPorGet(
            @RequestParam(defaultValue = "Mundo") String nombre) {
        return ResponseEntity.ok(saludoService.crearSaludo(nombre));
    }

    @Operation(operationId = "saludarPorPost", summary = "Saludo por POST con body validado")
    @PostMapping("/saludos")
    public ResponseEntity<SaludoResponse> saludarPorPost(
            @Valid @RequestBody SaludoRequest saludoRequest) {
        return ResponseEntity.ok(saludoService.crearSaludo(saludoRequest.nombre()));
    }
}