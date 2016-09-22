package com.tenpines.starter.web;

import com.tenpines.starter.integracion.SpringTestBase;
import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Cliente;
import com.tenpines.starter.modelo.Libro;
import com.tenpines.starter.modelo.Sesion;
import com.tenpines.starter.servicios.ServicioDeCarritos;
import com.tenpines.starter.servicios.ServicioDeCatalogo;
import com.tenpines.starter.servicios.ServicioDeCliente;
import com.tenpines.starter.servicios.ServicioDeSesion;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestTransversales extends SpringTestBase {

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
}