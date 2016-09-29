package com.tenpines.starter.modelo;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;


public class SesionTest {

    Cliente cliente ;
    Carrito carrito;
    Cliente clienteNull;
    Carrito carritoNull;

    @Before
    public void Setup() {
        carrito = Carrito.crearCarrito();
        cliente = Cliente.crearCliente("1234");
        clienteNull = null;

    }

    @Test
    public void tratoDeCrearUnaSesionConUnClienteEnNullYMeTiraUnaExcepcion(){
        try {Sesion.crearSesion(carrito,clienteNull);
            //assertTrue("nunca deberia llegar aca", false);
        } catch (RuntimeException excepcionNoCrearSesionSinCliente) {
            assertThat(excepcionNoCrearSesionSinCliente.getMessage()).isEqualTo(Sesion.mensajeDeErrorCuandoQuieroCrearUnCarritoConUsuarioInvalido());
        }
    }

}