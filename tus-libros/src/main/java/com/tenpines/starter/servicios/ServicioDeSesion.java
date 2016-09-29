package com.tenpines.starter.servicios;

import com.tenpines.starter.modelo.*;
import com.tenpines.starter.repositorios.RepositorioDeSesiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ServicioDeSesion {

    @Autowired
    private ServicioDeCarritos servicioDeCarritos;

    @Autowired
    private ServicioDeCliente servicioDeCliente;

    @Autowired
    private RepositorioDeSesiones repositorio;

    @Autowired
    private ServicioDeVentasConcretadas servicioDeVentasConcretadas;

    @Autowired
    private Reloj reloj;

    public Sesion crearCarrito(Cliente unCliente) {
        Carrito carrito = servicioDeCarritos.nuevoCarrito();
        Sesion nuevaSesion = Sesion.crearSesion(carrito,unCliente, reloj);
        repositorio.save(nuevaSesion);
        return nuevaSesion;
    }

    public List<Libro> mostrarLibrosDeCarrito(Long carritoId) {
        Sesion laSesion = buscarSesionParaElCarrito(carritoId);
        chequearSesionExpiradaYActulizarUltimoUso(laSesion);
        return servicioDeCarritos.mostrarLibrosDeCarrito(laSesion.getCarrito().getId());
    }

    private void actualizarUltimoUsoDeSesion(Sesion sesion) {
        sesion.setUltimoUso(LocalDateTime.now());
        repositorio.save(sesion);
    }

    private static String mensajeDeErrorCuandoNoExisteElCarritoQueQuiero() {
        return "No existe el carrito del que quiere ver el contenido";
    }

    private Sesion buscarSesionParaElCarrito(Long carritoId) {
        try {
            Sesion sesion = repositorio.getSesion(carritoId);
            return sesion;}
        catch (RuntimeException NoExisteElCarrito) {
                throw new RuntimeException(mensajeDeErrorCuandoNoExisteElCarritoQueQuiero());
             }
    }

    public void agregarLibro(Sesion sesion, Long idLibro, Integer cantidad) {
        chequearSesionExpiradaYActulizarUltimoUso(sesion);
        servicioDeCarritos.agregarLibro(sesion.getCarrito(),idLibro,cantidad);
    }

    private void chequearSesionExpiradaYActulizarUltimoUso(Sesion sesion) {
        sesion.chequearSesionExpirada();
        actualizarUltimoUsoDeSesion(sesion);
    }

    public List<Libro> obtenerUnCarrito(Long id){
        Sesion sesion = buscarSesionParaElCarrito(id);
        chequearSesionExpiradaYActulizarUltimoUso(sesion);
        return servicioDeCarritos.mostrarLibrosDeCarrito(id);
    }

    public void cobrarCarrito(Long carritoId, String nombreDeDuenio,Long numeroDeTarjeta, LocalDate fechaDeExpiracion) {
        Sesion sesion = buscarSesionParaElCarrito(carritoId);
        chequearSesionExpiradaYActulizarUltimoUso(sesion);
        Carrito carrito = servicioDeCarritos.buscarElCarrito(carritoId);
        TarjetaDeCredito tarjetaValidada = TarjetaDeCredito.nuevaTarjeta(numeroDeTarjeta, fechaDeExpiracion, nombreDeDuenio);
        servicioDeVentasConcretadas.concretarVenta(carrito, tarjetaValidada);
    }

    public List<VentaConcretada> mostrarVentasParaUnCliente(Long idUsuario, String password) {
        Cliente cliente = servicioDeCliente.clienteLogueado(idUsuario,password);
        List<Carrito> listaDeCarritos = buscarCarritosDelCliente(idUsuario);
        return servicioDeVentasConcretadas.mostrarVentasDeCarritos(listaDeCarritos);
    }

    private List<Carrito> buscarCarritosDelCliente(Long idUsuario) {
        return repositorio.getCarritoDeUsuario(idUsuario);
    }

//      Lo comento porque sé que en algún momento sirvió.
//    private Sesion buscarSesionParaElCliente(Long idUsuario) {
//        try {
//            List<Sesion> sesion = repositorio.getSesionParaCliente(idUsuario, em);
//            return sesion.get(sesion.size()-1);}
//        catch (RuntimeException NoHaySesionParaElUsuarioIngresado) {
//
//            throw new RuntimeException(mensajeDeErrorCuandoNoExisteSesionParaElUsuarioSeleccionado());
//        }
//
//    }
//
//    private static String mensajeDeErrorCuandoNoExisteSesionParaElUsuarioSeleccionado() {
//        return "No existe sesión para el cliente ingresado";
//    }

}

