package com.tenpines.starter.api;

import com.tenpines.starter.integracion.RESTTestBase;
import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Cliente;
import com.tenpines.starter.modelo.Libro;
import com.tenpines.starter.servicios.ServicioDeCarritos;
import com.tenpines.starter.servicios.ServicioDeCliente;
import com.tenpines.starter.servicios.ServicioDeSesion;
import com.tenpines.starter.web.Endpoints;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class APITest extends RESTTestBase {

    @MockBean
    private ServicioDeCarritos servicioDeCarritos;

    @MockBean
    private ServicioDeSesion servicioDeSesion;

    @Test
    public void agregarCarritoYQueNoSeaNullElContenidoDeLaTabla() throws Exception {
        Cliente cliente1 = Cliente.crearCliente("1234");
        Carrito carrito1 = Carrito.crearCarrito();

        Mockito.when(servicioDeCarritos.mostrarCarritos()).thenReturn(Arrays.asList(carrito1));

        this.mockClient.perform(get(Endpoints.OBTENER_CARRITO))
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$..id").value(notNullValue()));
    }


    @Test
    public void agregarUnItemAlCarritoYQueEsteEnElCarrito() throws Exception {
        Cliente cliente1 = Cliente.crearCliente("1234");
        Carrito carrito = Carrito.crearCarrito();
        Libro libro = Libro.crearLibro("Hola", "9873459234",20);

        Mockito.when(servicioDeCarritos.mostrarLibrosDeCarrito((long) 1)).thenReturn(Arrays.asList(libro,libro));

        this.mockClient.perform(get(Endpoints.MOSTRAR_ITEMS))
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0]['isbn']").value(libro.getIsbn()));
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
                .andExpect(jsonPath("$..cliente").value(notNullValue()));
    }

    @Test
    public void agregarClienteYMeDevuelvaLaListaConLoMismo() throws Exception {
        Cliente cliente1 = Cliente.crearCliente("1234");

        Mockito.when(servicioDeCliente.mostrarClientes()).thenReturn((Arrays.asList(cliente1)));

        this.mockClient.perform(get(Endpoints.OBTENER_CLIENTE))
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0]['password']").value((cliente1.getPassword())));
    }


    @Test
    public void agregoUnCliente() throws Exception {
        Cliente cliente1 = Cliente.crearCliente("1234");

        Mockito.when(servicioDeCliente.mostrarClientes()).thenReturn((Arrays.asList(cliente1)));

        this.mockClient.perform(get(Endpoints.OBTENER_CLIENTE))
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0]['password']").value((cliente1.getPassword())));
    }




}
