package com.ejemplo.demo.domain.service;

/**
 * SIN @Service → no es un bean de Spring.
 * Cada vez que se llama con "new" se crea una instancia nueva
 * y el valor siempre empieza en 0.
 */
public class EstadoManualService {

    private int valor = 0;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}