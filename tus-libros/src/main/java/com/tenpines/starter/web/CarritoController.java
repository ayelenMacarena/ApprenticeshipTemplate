package com.tenpines.starter.web;

import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Cliente;
import com.tenpines.starter.modelo.Libro;
import com.tenpines.starter.modelo.Sesion;
import com.tenpines.starter.servicios.ServicioDeCatalogo;
import com.tenpines.starter.servicios.ServicioDeCliente;
import com.tenpines.starter.servicios.ServicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class CarritoController {

    private ServicioDeCatalogo servicioCatalogo;
    private ServicioDeSesion servicioDeSesion;
    @Autowired
    private ServicioDeCliente servicioDeCliente;


    private Sesion sesion;
    private Cliente unCliente;

    @Autowired
    public CarritoController(ServicioDeSesion servicioDeSesion, ServicioDeCatalogo servicioDeCatalogo) {
        this.servicioCatalogo = servicioDeCatalogo;
        this.servicioDeSesion = servicioDeSesion;
    }

    @RequestMapping(Endpoints.HOME)
    String home(Model model) {
        //model.addAttribute("carrito", obtenerUnCarrito());
        model.addAttribute("cliente", unCliente);
        model.addAttribute("libros", catalogo());
        return "nuevaCompra";
    }

    @RequestMapping(value = Endpoints.AGREGAR_CARRITO, method = RequestMethod.POST)
    void crearUnCarrito(HttpServletResponse response) throws IOException {
        sesion = servicioDeSesion.crearCarrito(unCliente);
        response.sendRedirect(Endpoints.HOME);

    }

    @RequestMapping(value = Endpoints.AGREGAR_ITEM, method = RequestMethod.POST)
    void agregarUnLibro(@RequestParam Map<String,String> requestParams, HttpServletResponse response) throws IOException {
        Long idLibro = Long.valueOf(requestParams.get("libro"));
        Integer cantidad = Integer.valueOf(requestParams.get("cantidad"));
        servicioDeSesion.agregarLibro(sesion,idLibro,cantidad);
        response.sendRedirect(Endpoints.HOME);
    }


    @RequestMapping(value=Endpoints.MOSTRAR_ITEMS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    List<Libro> obtenerLibro(@RequestParam(value = "carrito") Long carritoId){
        //todo_: ver como queryDSL
        return servicioDeSesion.mostrarLibrosDeCarrito(sesion.getId_sesion());
    }

    @RequestMapping(value = Endpoints.LOGUEAR_CLIENTE, method = RequestMethod.POST)
    private void loguearCliente( @RequestParam Map<String,String> requestParams , HttpServletResponse response) throws IOException {
        Long idUsuario = Long.valueOf(requestParams.get("nombre"));
        String password = requestParams.get("password");
        unCliente = servicioDeCliente.clienteLogueado(idUsuario, password);
        response.sendRedirect(Endpoints.HOME);
    }

    @RequestMapping(value=Endpoints.OBTENER_CLIENTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    List<Cliente> obtenerCliente(){
        return servicioDeCliente.mostrarClientes();
    }

    private Carrito obtenerUnCarrito(){
        return sesion.getCarrito();
    }

    @RequestMapping(value=Endpoints.OBTENER_CARRITO, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    void obtenerCarritos(){
        //return servicioDeSesion.mostrarCarritos();
    }

    private Iterable<Libro> catalogo(){
        return servicioCatalogo.mostrarCatalogo();
    }
}
