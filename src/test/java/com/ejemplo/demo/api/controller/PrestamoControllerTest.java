package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.domain.service.PrestamoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrestamoController.class)
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoService prestamoService;

    @Test
    @DisplayName("POST /api/v1/simulaciones/prestamo debe retornar 200 con datos correctos")
    void debeSimularPrestamoExitoso() throws Exception {
        when(prestamoService.simularPrestamo(any()))
                .thenReturn(new PrestamoResponse(
                        new BigDecimal("470.73"),
                        new BigDecimal("1297.52"),
                        new BigDecimal("11297.52")));

        mockMvc.perform(post("/api/v1/simulaciones/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"monto\":10000,\"tasaAnual\":12,\"meses\":24}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuotaMensual").value(470.73))
                .andExpect(jsonPath("$.interesTotal").value(1297.52))
                .andExpect(jsonPath("$.totalPagar").value(11297.52));
    }

    @Test
    @DisplayName("POST /api/v1/simulaciones/prestamo con monto 0 debe retornar 400")
    void debeRechazarMontoInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/simulaciones/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"monto\":0,\"tasaAnual\":12,\"meses\":24}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }
}