package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.CategoriaResponse;
import com.ejemplo.demo.domain.service.CategoriaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Test
    @DisplayName("POST /api/v1/categorias debe retornar 201 con datos correctos")
    void debeCrearCategoriaExitoso() throws Exception {
        when(categoriaService.crear(any()))
                .thenReturn(new CategoriaResponse(1L, "Bebidas", "Gaseosas y jugos", Instant.now(), Instant.now()));

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Bebidas\",\"descripcion\":\"Gaseosas y jugos\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Bebidas"));
    }

    @Test
    @DisplayName("POST /api/v1/categorias con nombre vacio debe retornar 400")
    void debeRechazarNombreVacio() throws Exception {
        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"\",\"descripcion\":\"Gaseosas y jugos\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("GET /api/v1/categorias/{id} con id inexistente debe retornar 404")
    void debeRetornar404CuandoNoExiste() throws Exception {
        when(categoriaService.obtenerPorId(99L))
                .thenThrow(new EntityNotFoundException("Categoria no encontrada con id: 99"));

        mockMvc.perform(get("/api/v1/categorias/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("NOT_FOUND"));
    }
}