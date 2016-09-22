package com.tenpines.starter.modelo;

import com.tenpines.starter.servicios.ServicioDeCarritos;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

    public class SesionTest {

        private ProveedorDeObjetos proveedor;
        private ServicioDeCarritos servicioDeCarrito = new ServicioDeCarritos();
        private Cajero cajero = new Cajero();

        private Cliente cliente = Cliente.crearCliente("1234");
        private Carrito carrito = servicioDeCarrito.nuevoCarrito();
        private Sesion sesion = Sesion.crearSesion(carrito, cliente);

        @Before
        public void Setup(){

        }



}
