package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.EstadoResponse;
import com.ejemplo.demo.domain.service.EstadoManualService;
import com.ejemplo.demo.domain.service.EstadoSingletonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "demo-estado", description = "Demo singleton vs instancia manual")
@RestController
@RequestMapping("/api/v1/demo/estado")
public class DemoEstadoController {

    private final EstadoSingletonService singletonService;

    public DemoEstadoController(EstadoSingletonService singletonService) {
        this.singletonService = singletonService;
    }

    @Operation(operationId = "actualizarSingleton", summary = "Actualizar valor singleton")
    @PostMapping("/singleton/{valor}")
    public ResponseEntity<EstadoResponse> actualizarSingleton(@PathVariable int valor) {
        singletonService.setValor(valor);
        return ResponseEntity.ok(new EstadoResponse("singleton", singletonService.getValor()));
    }

    @Operation(operationId = "obtenerSingleton", summary = "Obtener valor singleton")
    @GetMapping("/singleton")
    public ResponseEntity<EstadoResponse> obtenerSingleton() {
        return ResponseEntity.ok(new EstadoResponse("singleton", singletonService.getValor()));
    }

    @Operation(operationId = "reiniciarSingleton", summary = "Reiniciar singleton a 0")
    @PostMapping("/singleton/reset")
    public ResponseEntity<EstadoResponse> reiniciarSingleton() {
        singletonService.reset();
        return ResponseEntity.ok(new EstadoResponse("singleton", singletonService.getValor()));
    }

    @Operation(operationId = "actualizarManual", summary = "Actualizar valor manual")
    @PostMapping("/manual/{valor}")
    public ResponseEntity<EstadoResponse> actualizarManual(@PathVariable int valor) {
        EstadoManualService manual = new EstadoManualService();
        manual.setValor(valor);
        return ResponseEntity.ok(new EstadoResponse("manual", manual.getValor()));
    }

    @Operation(operationId = "obtenerManual", summary = "Obtener valor manual (siempre 0)")
    @GetMapping("/manual")
    public ResponseEntity<EstadoResponse> obtenerManual() {
        EstadoManualService manual = new EstadoManualService();
        return ResponseEntity.ok(new EstadoResponse("manual", manual.getValor()));
    }
}