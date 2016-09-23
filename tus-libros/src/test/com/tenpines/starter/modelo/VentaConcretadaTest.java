package com.tenpines.starter.modelo;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VentaConcretadaTest {

    public VentaConcretada venta;
    public Libro libro = Libro.crearLibro("asdasd","1234567678",100);
    public List<Libro> itemsDeLaCompra = new ArrayList<Libro>();
    public LocalDateTime horaYFecha = LocalDateTime.now();


    @Test
    public void unaVentaConcretadaDebeTenerItemsPrecioTotalYFechanYHoraDeLaMisma(){
        itemsDeLaCompra.add(libro);
        VentaConcretada venta = VentaConcretada.nuevaVentaConcretada(itemsDeLaCompra, 70,horaYFecha);

        assertThat(venta.getItemsDeLaVenta()).isNotEmpty();
        assertThat(venta.getFechaYHoraDeVenta()).isEqualTo(horaYFecha);
        assertThat(venta.getPrecioTotal()).isNotNull();
    }
}
