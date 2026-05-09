package com.ejemplo.demo.domain.service;

import org.springframework.stereotype.Service;

/**
 * Tiene @Service → Spring crea UNA SOLA instancia.
 * El valor persiste entre llamadas porque siempre es el mismo objeto.
 */
@Service
public class EstadoSingletonService {

    private int valor = 0;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void reset() {
        this.valor = 0;
    }
}