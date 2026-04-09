package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class PrestamoService {

    public PrestamoResponse simularPrestamo(PrestamoRequest request) {
        BigDecimal P = request.monto();
        BigDecimal tasaMensual = request.tasaAnual()
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        int n = request.meses();

        BigDecimal unoPlusR = BigDecimal.ONE.add(tasaMensual);
        BigDecimal unoPlusRn = unoPlusR.pow(n, new MathContext(10));

        BigDecimal numerador = P.multiply(tasaMensual).multiply(unoPlusRn);
        BigDecimal denominador = unoPlusRn.subtract(BigDecimal.ONE);

        if (denominador.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("No se puede calcular la cuota con los datos proporcionados");
        }

        BigDecimal cuotaMensual = numerador.divide(denominador, 2, RoundingMode.HALF_UP);
        BigDecimal totalPagar = cuotaMensual.multiply(BigDecimal.valueOf(n)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal interesTotal = totalPagar.subtract(P).setScale(2, RoundingMode.HALF_UP);

        return new PrestamoResponse(cuotaMensual, interesTotal, totalPagar);
    }
}