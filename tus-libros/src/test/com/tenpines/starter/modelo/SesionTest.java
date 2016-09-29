package com.tenpines.starter.modelo;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;


public class SesionTest {

    Cliente cliente = Cliente.crearCliente("1234");
    Carrito carrito = Carrito.crearCarrito();
    Cliente clienteNull;
    Carrito carritoNull;
    Reloj reloj = new Reloj(12,30);
    Sesion sesion = Sesion.crearSesion(carrito,cliente,reloj);
    Libro libro = Libro.crearLibro("el principito", "123123123", 45);

    @Test
    public void tratoDeCrearUnaSesionConUnClienteEnNullYMeTiraUnaExcepcion(){
        try {Sesion.crearSesion(carrito,clienteNull,reloj);
        } catch (RuntimeException excepcionNoCrearSesionSinCliente) {
            assertThat(excepcionNoCrearSesionSinCliente.getMessage()).isEqualTo(Sesion.mensajeDeErrorCuandoQuieroCrearUnCarritoConUsuarioInvalido());
        }
    }

    @Test
    public void unaSesionSeVenceAlPasarMasDe30Minutos(){
        reloj.setearHoraYMinutos(10,0);
        Sesion sesion = Sesion.crearSesion(carrito,cliente,reloj);
        sesion.setUltimoUso(reloj.horaActual());
        reloj.setearHoraYMinutos(10,40);

        try {sesion.chequearSesionExpirada();
            assertTrue("nunca deberia llegar aca", false);
        } catch (RuntimeException excepcionSesionExpirada) {
            assertThat(excepcionSesionExpirada.getMessage()).isEqualTo(Sesion.mensajeDeErrorSesionExpirada());
        }
    }

    @Test
    public void unaSesionSeVenceAlPasar30MinutosTodaviaNoExpiro() {
        reloj.setearHoraYMinutos(10, 0);
        Sesion sesion = Sesion.crearSesion(carrito, cliente, reloj);
        sesion.setUltimoUso(reloj.horaActual());
        reloj.setearHoraYMinutos(10, 30);
        assertThat(sesion.laSesionExpiro()).isFalse();
    }

    @Test
    public void noSePuedeAgregarUnLibroAUnCarritoDeUnaSesionExpiro() {
        reloj.setearHoraYMinutos(10,0);
        Sesion sesion = Sesion.crearSesion(carrito,cliente,reloj);
        sesion.setUltimoUso(reloj.horaActual());
        reloj.setearHoraYMinutos(10,40);
        try {sesion.agregarLibroACarrito(libro, 1);
            assertTrue("nunca deberia llegar aca", false);
        } catch (RuntimeException excepcionSesionExpirada) {
            assertThat(excepcionSesionExpirada.getMessage()).isEqualTo(Sesion.mensajeDeErrorSesionExpirada());
        }
    }

    @Test
    public void agregarUnLibroAUnCarritoDeUnaSesionYEsta() {
        reloj.setearHoraYMinutos(10, 0);
        Sesion sesion = Sesion.crearSesion(carrito, cliente, reloj);
        sesion.setUltimoUso(reloj.horaActual());
        reloj.setearHoraYMinutos(10, 30);
        sesion.agregarLibroACarrito(libro, 1);
        assertThat(sesion.getCarrito().getItems()).contains(libro);
    }
}