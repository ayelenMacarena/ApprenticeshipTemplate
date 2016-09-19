package com.tenpines.starter.modelo;


import com.tenpines.starter.modelo.Cajero;
import com.tenpines.starter.modelo.Carrito;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class CajeroTest {

    private Cliente cliente = Cliente.crearCliente("1234");
    private Carrito carrito = Carrito.crearCarrito(cliente);
    private Cajero cajero = new Cajero();

    @Test
    public void noSePuedeCobrarUnCarroVacio(){
        try {cajero.cobrar(carrito);
            assertTrue("nunca deberia llegar aca", false);
        } catch (RuntimeException excepcionNoCobrarCarrosVacios) {
            assertThat(excepcionNoCobrarCarrosVacios.getMessage()).isEqualTo(Cajero.mensajeDeErrorCuandoQuieroCobrarUnCarroVacio());
        }
    }




}
