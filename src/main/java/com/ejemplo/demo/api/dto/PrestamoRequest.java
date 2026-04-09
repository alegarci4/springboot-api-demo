package com.ejemplo.demo.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public record PrestamoRequest(

        @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
        BigDecimal monto,

        @DecimalMin(value = "0.01", message = "La tasa anual debe ser mayor a 0")
        BigDecimal tasaAnual,

        @Min(value = 1, message = "El numero de meses debe ser al menos 1")
        @Max(value = 360, message = "El numero de meses no puede superar 360")
        int meses
) {}