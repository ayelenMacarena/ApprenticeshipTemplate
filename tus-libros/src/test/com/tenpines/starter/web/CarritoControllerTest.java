package com.tenpines.starter.web;

import com.tenpines.starter.integracion.SpringTestBase;
import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Cliente;
import com.tenpines.starter.servicios.ServicioDeCarritos;
import com.tenpines.starter.servicios.ServicioDeCliente;
import com.tenpines.starter.servicios.ServicioDeSesion;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CarritoControllerTest extends SpringTestBase {

    @Autowired
    private ServicioDeCarritos servicioDeCarritos;

    @Autowired
    private ServicioDeCliente servicioDeCliente;

    @Autowired
    private ServicioDeSesion servicioDeSesion;

    @Test
    public void hayCarritoCreado(){
        assertThat(servicioDeCarritos.mostrarCarritos()).isNotEmpty();
    }

    @Test
    public void noCrearCarritoSinUsuario(){
        try {
            Cliente cliente = null;
            Carrito carrito = servicioDeSesion.crearCarrito(cliente);
            assertTrue("nunca deberia llegar aca", false);
        } catch (RuntimeException excepcionDeCarritoSinUsuario) {
            AssertionsForClassTypes.assertThat(excepcionDeCarritoSinUsuario.getMessage()).isEqualTo(ServicioDeSesion.mensajeDeErrorCuandoQuieroCrearUnCarritoConUsuarioInvalido());
        }
    }

    @Test
    public void noSePuedeCrearUnCarritoNuevoSiUnUsuarioYPasswordValido() {
        assertFalse(servicioDeCliente.loguearCliente(1L, "1111"));
    }

}