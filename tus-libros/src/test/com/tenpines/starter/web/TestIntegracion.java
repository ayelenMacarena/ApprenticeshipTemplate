package com.tenpines.starter.web;

import com.tenpines.starter.integracion.SpringTestBase;
import com.tenpines.starter.modelo.*;
import com.tenpines.starter.servicios.ServicioDeCarritos;
import com.tenpines.starter.servicios.ServicioDeCatalogo;
import com.tenpines.starter.servicios.ServicioDeCliente;
import com.tenpines.starter.servicios.ServicioDeSesion;
import junit.framework.TestCase;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestIntegracion extends SpringTestBase {

    @Autowired
    private ServicioDeCarritos servicioDeCarritos;

    @Autowired
    private ServicioDeCliente servicioDeCliente;

    @Autowired
    private ServicioDeSesion servicioDeSesion;

    @Autowired
    private ServicioDeCatalogo servicioDeCatalogo;

    @Test
    public void hayCarritoCreado(){
        assertThat(servicioDeCarritos.mostrarCarritos()).isNotEmpty();
    }

    @Test
    public void noCrearCarritoSinUsuario(){
        try {
            Cliente cliente = null;
            Sesion sesion = servicioDeSesion.crearCarrito(cliente);
            assertTrue("nunca deberia llegar aca", false);
        } catch (RuntimeException excepcionDeCarritoSinUsuario) {
            AssertionsForClassTypes.assertThat(excepcionDeCarritoSinUsuario.getMessage()).isEqualTo(ServicioDeSesion.mensajeDeErrorCuandoQuieroCrearUnCarritoConUsuarioInvalido());
        }
    }

    @Test
    public void noSePuedeCrearUnCarritoNuevoSiUnUsuarioYPasswordValido() {
        assertFalse(servicioDeCliente.loguearCliente(1L, "1111"));
    }

    @Test
    public void agregarUnItemAUnCarritoYQueLoContenga() throws Exception {
        Cliente cliente = Cliente.crearCliente("1234");
        servicioDeCliente.almacenar(cliente);
        Sesion sesion = servicioDeSesion.crearCarrito(cliente);
        Carrito carrito = sesion.getCarrito();
        Libro libro = servicioDeCatalogo.agregarLibroAlCatalogo("Guerra de los mundos", "123456789", 45);

        servicioDeSesion.agregarLibro(sesion, libro.getId(), 1);

        assertThat((servicioDeSesion.mostrarLibrosDeCarrito(sesion.getId_sesion())).stream().filter(li -> li.equals(libro))).contains(libro);
    }                //TODO CHECKEAR ESTE EQUALS -- CORRESPONDE A *1

    @Test
    public void alQuererAgregarUnItemConUnaSesionExpiradaDebeLanzarUnExcepcionYNoAgregarElItem(){
        Cliente cliente = Cliente.crearCliente("1234");
        servicioDeCliente.almacenar(cliente);
        Sesion sesionExpirada = servicioDeSesion.crearCarrito(cliente);
        Carrito carrito = sesionExpirada.getCarrito();
        Libro libro = servicioDeCatalogo.agregarLibroAlCatalogo("Guerra de los mundos", "123456789", 45);

        LocalDateTime relojDeTest = LocalDateTime.now();
        sesionExpirada.setUltimoUso(Timestamp.valueOf(relojDeTest.minusMinutes(31)));

        try {servicioDeSesion.agregarLibro(sesionExpirada, libro.getId(), 1);
            TestCase.assertTrue("nunca deberia llegar aca", false);
        } catch (RuntimeException excepcionDeSesionExpirada) {
            assertThat(excepcionDeSesionExpirada.getMessage()).isEqualTo(ServicioDeSesion.mensajeDeErrorSesionExpirada());
        }
    }

//    @Test
//    public void cobrarUnCarritoCon1Itemy1Unidad(){
//        carrito.agregarLibro(proveedor.crearLibro(),1);
//        assertThat(cajero.cobrar(carrito)).isEqualTo(45);
//    }
}