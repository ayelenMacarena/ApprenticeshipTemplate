package com.tenpines.starter.api;

import com.tenpines.starter.integracion.RESTTestBase;
import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Cliente;
import com.tenpines.starter.servicios.ServicioDeCarritos;
import com.tenpines.starter.servicios.ServicioDeCliente;
import com.tenpines.starter.web.Endpoints;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Aye on 20/09/16.
 */
public class APITest extends RESTTestBase {

    @MockBean
    private ServicioDeCarritos servicioDeCarritos;

    @Test
    public void agregarCarritoYQueNoSeaNullElContenidoDeLaTabla() throws Exception {
        Cliente cliente1 = Cliente.crearCliente("1234");
        Carrito carrito1 = Carrito.crearCarrito(cliente1);

        Mockito.when(servicioDeCarritos.mostrarCarritos()).thenReturn(Arrays.asList(carrito1));

        this.mockClient.perform(get(Endpoints.OBTENER_CARRITO))
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$..carritos").value(notNullValue()));

    }

    @Test
        public void agregar1CarritoYQueDevuelvaElJson() throws Exception {
        Cliente cliente1 = Cliente.crearCliente("1234");
        Carrito carrito1 = Carrito.crearCarrito(cliente1);

        Mockito.when(servicioDeCarritos.mostrarCarritos()).thenReturn(Arrays.asList(carrito1));

        this.mockClient.perform(get(Endpoints.OBTENER_CARRITO))
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$..carritos").value(contains(carrito1)));

        Mockito.verify(servicioDeCarritos, Mockito.times(1)).mostrarCarritos();
        Mockito.verifyNoMoreInteractions(servicioDeCarritos);
    }

    @MockBean
    private ServicioDeCliente servicioDeCliente;

    @Test
    public void agregarClienteYQueLaTablaNoSeaNull() throws Exception {
        Cliente cliente1 = Cliente.crearCliente("1234");


        Mockito.when(servicioDeCliente.mostrarClientes()).thenReturn((Arrays.asList(cliente1)));

        this.mockClient.perform(get(Endpoints.OBTENER_CLIENTE))
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$..clientes").value(notNullValue()));


    }



}
