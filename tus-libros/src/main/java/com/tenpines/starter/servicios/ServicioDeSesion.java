package com.tenpines.starter.servicios;

import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Cliente;
import com.tenpines.starter.modelo.Libro;
import com.tenpines.starter.modelo.Sesion;
import com.tenpines.starter.repositorios.RepositorioDeSesiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Aye on 22/09/16.
 */
@Service
public class ServicioDeSesion {

    @Autowired
    private ServicioDeCarritos servicioDeCarritos;

    @Autowired
    private ServicioDeCliente servicioDeCliente;

    @Autowired
    private RepositorioDeSesiones repositorio;


    public Carrito crearCarrito(Cliente unCliente) {
        if(unCliente == null){
            throw new RuntimeException(mensajeDeErrorCuandoQuieroCrearUnCarritoConUsuarioInvalido());
        }
        Carrito carrito = servicioDeCarritos.nuevoCarrito();
        Sesion nuevaSesion = Sesion.crearSesion(carrito,unCliente);
        repositorio.save(nuevaSesion);
        return carrito;
    }


    public static String mensajeDeErrorCuandoQuieroCrearUnCarritoConUsuarioInvalido() {
        return "Para crear un carrito debe estar logueado con un usuario valido";
    }

    public List<Carrito> mostrarCarritos() {
        return servicioDeCarritos.mostrarCarritos();
    }

    public List<Libro> mostrarLibrosDeCarrito(Long carritoId) {
        Sesion laSesion = buscarSesionParaElCarrito(carritoId);
        if (laSesion == null){
            throw new RuntimeException(mensajeDeErrorCuandoNoExisteElCarritoQueQuiero());
        }
        chequearSesionExpirada(laSesion);

        actualizarUltimoUsoDeSesion(laSesion);
        return servicioDeCarritos.mostrarLibrosDeCarrito(carritoId);
    }

    private boolean laSesionExpiro(Sesion laSesion) {
        long periodoDeTiempo = Duration.between(laSesion.getUltimoUso().toLocalDateTime(), LocalDateTime.now()).toMinutes();
        return periodoDeTiempo > 30;
    }


    private void actualizarUltimoUsoDeSesion(Sesion sesion) {
        sesion.setUltimoUso(Timestamp.valueOf(LocalDateTime.now()));
        repositorio.save(sesion);
    }

    private String mensajeDeErrorSesionExpirada() {
        return "Su sesión está expirada";
    }

    private String mensajeDeErrorCuandoNoExisteElCarritoQueQuiero() {
        return "No existe el carrito del que quiere ver el contenido";
    }

    private Sesion buscarSesionParaElCarrito(Long carritoId) {
        //TODO: ROMPE ACA; NO PUEDE CASTEAR STREAM A SESION
        Sesion sesion = (Sesion) repositorio.findAll().stream().filter(s -> s.getCarrito().getId() == carritoId);
        return sesion;
    }

    public void agregarLibro(Carrito carrito, Long idLibro, Integer cantidad) {

        Sesion laSesion = buscarSesionParaElCarrito(carrito.getId());
        chequearSesionExpirada(laSesion);
        actualizarUltimoUsoDeSesion(laSesion);
        servicioDeCarritos.agregarLibro(carrito,idLibro,cantidad);
    }

    private void chequearSesionExpirada(Sesion laSesion) {
        if(laSesionExpiro(laSesion)){
            throw new RuntimeException(mensajeDeErrorSesionExpirada());
        }
    }
}
