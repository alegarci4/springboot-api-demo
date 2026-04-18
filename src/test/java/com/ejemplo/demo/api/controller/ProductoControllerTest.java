package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.ProductoResponse;
import com.ejemplo.demo.domain.service.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    @DisplayName("POST /api/v1/productos debe retornar 201 con datos correctos")
    void debeCrearProductoExitoso() throws Exception {
        when(productoService.crear(any()))
                .thenReturn(new ProductoResponse(1L, "Coca Cola", "Refresco de cola",
                        new BigDecimal("1.50"), 1L, "Bebidas", Instant.now(), Instant.now()));

        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Coca Cola\",\"descripcion\":\"Refresco de cola\",\"precio\":1.50,\"categoriaId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Coca Cola"));
    }

    @Test
    @DisplayName("POST /api/v1/productos con precio invalido debe retornar 400")
    void debeRechazarPrecioInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Coca Cola\",\"descripcion\":\"Refresco de cola\",\"precio\":0,\"categoriaId\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("GET /api/v1/productos/{id} con id inexistente debe retornar 404")
    void debeRetornar404CuandoNoExiste() throws Exception {
        when(productoService.obtenerPorId(99L))
                .thenThrow(new EntityNotFoundException("Producto no encontrado con id: 99"));

        mockMvc.perform(get("/api/v1/productos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("NOT_FOUND"));
    }
}