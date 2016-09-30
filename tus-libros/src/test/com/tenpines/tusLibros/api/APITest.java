package com.tenpines.tusLibros.api;

import com.tenpines.tusLibros.integracion.RESTTestBase;
import com.tenpines.tusLibros.modelo.Cliente;
import com.tenpines.tusLibros.servicios.ServicioDeCarritos;
import com.tenpines.tusLibros.servicios.ServicioDeCliente;
import com.tenpines.tusLibros.servicios.ServicioDeSesion;
import com.tenpines.tusLibros.web.Endpoints;
import com.tenpines.tusLibros.web.TransferObjects.UsuarioPasswordTO;
import org.hamcrest.Description;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.support.NullValue;
import org.springframework.expression.spel.ast.ValueRef;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class APITest extends RESTTestBase {

    @MockBean
    private ServicioDeCarritos servicioDeCarritos;

    @MockBean
    private ServicioDeSesion servicioDeSesion;


//    @Test
//    public void agregarUnItemAlCarritoYQueEsteEnElCarrito() throws Exception {
//        Cliente cliente1 = Cliente.crearCliente("1234");
//        Carrito carrito = Carrito.crearCarrito();
//        Libro libro = Libro.crearLibro("Hola", "9873459234",20);
//
//        this.mockClient.perform(get(Endpoints.MOSTRAR_ITEMS))
//                .andExpect(content().contentType(JSON_CONTENT_TYPE))
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("$[0]['isbn']").value(libro.getIsbn()));
//    }
//
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

    @Test
    public void listarVentasParaUnCliente() throws Exception {
        UsuarioPasswordTO usuarioPasswordTO = UsuarioPasswordTO.crearUsuarioPasswordTO(1L, "1234");
        this.mockClient.perform(post(Endpoints.LISTAR_VENTAS, usuarioPasswordTO ))
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").value(notNullValue()));

    }





}
