package com.tenpines.starter.modelo;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class CajeroTest {

    private Cliente cliente = Cliente.crearCliente("1234");

    Carrito carrito ;
    Cajero cajero;
    ProveedorDeObjetos proveedor;
    TarjetaDeCredito tarjetaDeCreaditoValida;

    @Before
    public void Setup(){
        carrito = Carrito.crearCarrito();
        cajero = new Cajero();
        proveedor = new ProveedorDeObjetos();
        tarjetaDeCreaditoValida = proveedor.tarjetaDeCreditoValida();
    }

    @Test
    public void noSePuedeCobrarUnCarroVacio(){
        try {cajero.cobrar(carrito, tarjetaDeCreaditoValida);
            assertTrue("nunca deberia llegar aca", false);
        } catch (RuntimeException excepcionNoCobrarCarrosVacios) {
            assertThat(excepcionNoCobrarCarrosVacios.getMessage()).isEqualTo(Cajero.mensajeDeErrorCuandoQuieroCobrarUnCarroVacio());
        }
    }

    @Test
    public void cobrarUnCarritoConUnaUnidadDeUnMismoItemYCobraEseLibro(){
        carrito.agregarLibro(proveedor.crearLibro(),1);
        assertThat(cajero.cobrar(carrito, tarjetaDeCreaditoValida)).isEqualTo(true);
    }

    @Test
    public void cobrarUnCarritoConDosUnidadesDeUnMismoItemCobraElPrecioPorLaCantidad(){
        carrito.agregarLibro(proveedor.crearLibro(),2);
        assertThat(cajero.cobrar(carrito, tarjetaDeCreaditoValida)).isEqualTo(true);
    }

    @Test
    public void cobrarUnCarritoCon2LibrosDiferentesCobraLaSumaDeLosMismos(){
        carrito.agregarLibro(proveedor.crearLibro(),1);
        carrito.agregarLibro(proveedor.crearOtroLibro(),1);
        assertThat(cajero.cobrar(carrito, tarjetaDeCreaditoValida)).isEqualTo(true);
    }

    @Test
    public void cobrarUnCarritoConDosUnidadesDeUnLibroYUnaUnidadDeOtroCobraLaSumaDeTodo(){
        carrito.agregarLibro(proveedor.crearLibro(),2);
        carrito.agregarLibro(proveedor.crearOtroLibro(),1);
        assertThat(cajero.cobrar(carrito, tarjetaDeCreaditoValida)).isEqualTo(true);
    }

    @Test
    public void cobrarUnCarritoQueTieneUnLibroConUnaTarjetaDeCreditoValida(){
        carrito.agregarLibro(proveedor.crearLibro(),2);
        carrito.agregarLibro(proveedor.crearOtroLibro(),1);

        assertThat(cajero.cobrar(carrito, tarjetaDeCreaditoValida)).isEqualTo(true);
    }



}
