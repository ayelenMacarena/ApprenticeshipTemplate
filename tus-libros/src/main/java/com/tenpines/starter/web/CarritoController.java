package com.tenpines.starter.web;

import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Catalogo;
import com.tenpines.starter.modelo.Cliente;
import com.tenpines.starter.servicios.ServicioDeCarritos;
import com.tenpines.starter.servicios.ServicioDeCatalogo;
import com.tenpines.starter.servicios.ServicioDeCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class CarritoController {
    private ServicioDeCarritos servicioCarrito;

    private ServicioDeCliente servicioDeCliente;
    private ServicioDeCatalogo servicioCatalogo;

    Carrito carrito;
    Cliente unCliente;

    @Autowired
    public CarritoController(ServicioDeCarritos servicioDeCarritos, ServicioDeCliente servicioDeClientes, ServicioDeCatalogo servicioDeCatalogo) {
        this.servicioCarrito = servicioDeCarritos;
        this.servicioCatalogo = servicioDeCatalogo;

        this.servicioDeCliente = servicioDeClientes;

    }

    @RequestMapping(Endpoints.HOME)
    String home(Model model) {
        model.addAttribute("carrito", obtenerUnCarrito());
        model.addAttribute("cliente", unCliente);
        model.addAttribute("libros", catalogo());
        return "nuevaCompra";
    }

    @RequestMapping(value = Endpoints.AGREGAR_CARRITO, method = RequestMethod.POST)
    void crearUnCarrito(HttpServletResponse response) throws IOException {
        //TODO: Assert que haya Cliente, si no hay cliente tirar excepcion y no dejar crear carrito.-
        carrito = new Carrito();
        servicioCarrito.almacenar(carrito);
        response.sendRedirect(Endpoints.HOME);

    }

    @RequestMapping(value = Endpoints.AGREGAR_ITEM, method = RequestMethod.POST)
    void agregarUnLibro(@RequestParam(value = "libro") String unLibro, HttpServletResponse response) throws IOException {
        carrito.agregarLibro(unLibro, 1);
        servicioCarrito.almacenar(carrito);
        response.sendRedirect(Endpoints.HOME);
    }


    @RequestMapping(value = Endpoints.LOGUEAR_CLIENTE, method = RequestMethod.POST)
    private void loguearCliente( @RequestParam Map<String,String> requestParams , HttpServletResponse response) throws IOException {
        Long idUsuario =Long.valueOf(requestParams.get("nombre")).longValue();
        String password = requestParams.get("password");
        unCliente = servicioDeCliente.buscarElCliente(idUsuario);
        //TODO: Validar si el usuario y la contraseña es correcta. Si es incorrecta que avise, sino que lo muestre.
//        if (unCliente.getPassword().equals(password)){
//        carrito.setCliente(unCliente);}

        response.sendRedirect(Endpoints.HOME);
    }


    private Carrito obtenerUnCarrito(){
       // return servicioCarrito.buscarElCarrito(carrito.getId()); TODO: Eventualmene lo voy a necesitar.
        return carrito;
    }

    private ArrayList<String> catalogo(){
        return (new Catalogo()).getNombreLibro();
    }
}
