package com.tenpines.starter.modelo;


import com.tenpines.starter.modelo.Cajero;
import com.tenpines.starter.modelo.Carrito;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class CajeroTest {

    private Cliente cliente = Cliente.crearCliente("1234");

    private Carrito carrito = Carrito.crearCarrito();
    private Cajero cajero = new Cajero();
    private ProveedorDeObjetos proveedor;

    @Before
    public void Setup(){
        proveedor = new ProveedorDeObjetos();
    }

    @Test
    public void noSePuedeCobrarUnCarroVacio(){
        try {cajero.cobrar(carrito);
            assertTrue("nunca deberia llegar aca", false);
        } catch (RuntimeException excepcionNoCobrarCarrosVacios) {
            assertThat(excepcionNoCobrarCarrosVacios.getMessage()).isEqualTo(Cajero.mensajeDeErrorCuandoQuieroCobrarUnCarroVacio());
        }
    }

    @Test
    public void cobrarUnCarritoCon1Itemy1Unidad(){
        carrito.agregarLibro(proveedor.crearLibro(),1);
        assertThat(cajero.cobrar(carrito)).isEqualTo(45);
    }

    @Test
    public void cobrarUnCarritoCon1Itemy2Unidades(){
        carrito.agregarLibro(proveedor.crearLibro(),2);
        assertThat(cajero.cobrar(carrito)).isEqualTo(90);
    }

    @Test
    public void cobrarUnCarritoCon2Libros(){
        carrito.agregarLibro(proveedor.crearLibro(),1);
        carrito.agregarLibro(proveedor.crearOtroLibro(),1);
        assertThat(cajero.cobrar(carrito)).isEqualTo(140);
    }

    @Test
    public void cobrarUnCarritoCon2DeUnoyOtro(){
        carrito.agregarLibro(proveedor.crearLibro(),2);
        carrito.agregarLibro(proveedor.crearOtroLibro(),1);
        assertThat(cajero.cobrar(carrito)).isEqualTo(185);
    }


}
