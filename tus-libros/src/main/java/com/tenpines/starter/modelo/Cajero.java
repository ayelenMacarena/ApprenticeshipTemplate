package com.tenpines.starter.modelo;

import java.time.LocalDateTime;

public class Cajero {

    private MerchantProcessor merchantProcessor = new MerchantProcessor();
    private LibroDeVentas libroDeVentas = new LibroDeVentas();

    public Boolean cobrar(Carrito unCarrito, TarjetaDeCredito tarjetaDeCreaditoValida){
        validarSiUnCarritoEstaVacio(unCarrito);
        VentaConcretada venta = VentaConcretada.nuevaVentaConcretada(unCarrito.getItems(), precioTotalCompra(unCarrito), LocalDateTime.now());
        libroDeVentas.registrarVenta(venta);
        return merchantProcessor.efectuarCompra(tarjetaDeCreaditoValida, precioTotalCompra(unCarrito));
    }

    private void validarSiUnCarritoEstaVacio(Carrito unCarrito) {
        if (unCarrito.estaVacio()) {
            throw new RuntimeException(mensajeDeErrorCuandoQuieroCobrarUnCarroVacio());
        }
    }

    private Integer precioTotalCompra(Carrito unCarrito) {

        Integer precioTotal = unCarrito.getItems().stream().mapToInt(Libro::getPrecio).sum();
        return precioTotal;
    }

    public static String mensajeDeErrorCuandoQuieroCobrarUnCarroVacio() {
        return "No se puede cobrar un carro que esta vacio";
    }
}
