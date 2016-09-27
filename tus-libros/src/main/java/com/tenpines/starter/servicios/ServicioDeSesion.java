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
    private EntityManager em;

    @Autowired
    private ServicioDeVentasConcretadas servicioDeVentasConcretadas;

    public Sesion crearCarrito(Cliente unCliente) {
        if(unCliente == null){
            throw new RuntimeException(mensajeDeErrorCuandoQuieroCrearUnCarritoConUsuarioInvalido());
        }
        Carrito carrito = servicioDeCarritos.nuevoCarrito();
        Sesion nuevaSesion = Sesion.crearSesion(carrito,unCliente);

        repositorio.save(nuevaSesion);
        return nuevaSesion;
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
        return servicioDeCarritos.mostrarLibrosDeCarrito(laSesion.getCarrito().getId());
    }

    private boolean laSesionExpiro(Sesion laSesion) {
        long periodoDeTiempo = Duration.between(laSesion.getUltimoUso().toLocalDateTime(), LocalDateTime.now()).toMinutes();
        return periodoDeTiempo > 30;
    }

    private void actualizarUltimoUsoDeSesion(Sesion sesion) {
        sesion.setUltimoUso(Timestamp.valueOf(LocalDateTime.now()));
        repositorio.save(sesion);
    }


    public static String mensajeDeErrorCuandoNoExisteElCarritoQueQuiero() {
        return "No existe el carrito del que quiere ver el contenido";
    }

    private Sesion buscarSesionParaElCarrito(Long carritoId) {
        try {
        Sesion sesion = em.createQuery("select c from Sesion c where c.carrito.id = :id", Sesion.class).
                setParameter("id", carritoId).getSingleResult();
        return sesion;}
        catch (RuntimeException NoExisteElCarrito) {
                throw new RuntimeException(mensajeDeErrorCuandoNoExisteElCarritoQueQuiero());
             }
    }

    public void agregarLibro(Sesion sesion, Long idLibro, Integer cantidad) {
        chequearSesionExpirada(sesion);

        actualizarUltimoUsoDeSesion(sesion);
        servicioDeCarritos.agregarLibro(sesion.getCarrito(),idLibro,cantidad);
    }

    private void chequearSesionExpirada(Sesion laSesion) {
        if(laSesionExpiro(laSesion)){
            throw new RuntimeException(mensajeDeErrorSesionExpirada());
        }
    }

    public List<Libro> obtenerUnCarrito(Long id){
        Sesion sesion = buscarSesionParaElCarrito(id);
        chequearSesionExpirada(sesion);
        return servicioDeCarritos.mostrarLibrosDeCarrito(id);
    }

    public static String mensajeDeErrorSesionExpirada() {
        return "Su sesión está expirada";
    }

    public void cobrarCarrito(Long carritoId, String nombreDeDuenio,Long numeroDeTarjeta, LocalDate fechaDeExpiracion) {
        Sesion sesion = buscarSesionParaElCarrito(carritoId);
        chequearSesionExpirada(sesion);
        Carrito carrito = servicioDeCarritos.buscarElCarrito(carritoId);
        TarjetaDeCredito tarjetaValidada = TarjetaDeCredito.nuevaTarjeta(numeroDeTarjeta, fechaDeExpiracion, nombreDeDuenio);
        verificarQueNoSeCobroElCarrito(carritoId);

        Cajero cajero = new Cajero();

        VentaConcretada ventaConcretada = cajero.cobrar(carrito, tarjetaValidada);
        servicioDeVentasConcretadas.registrarVenta(ventaConcretada);
    }

    private void verificarQueNoSeCobroElCarrito(Long carritoId) {
        List<VentaConcretada> unaVenta = em.createQuery("select c from VentaConcretada c where c.carrito.id = :id", VentaConcretada.class).
                    setParameter("id", carritoId).getResultList();
        if(!unaVenta.isEmpty()){
            throw new RuntimeException(mensajeDeErrorCuandoYaSeFacturoElCarrito());
        }
    }

    public List<VentaConcretada> mostrarVentasParaUnCliente(Long idUsuario, String password) {
        Cliente cliente = servicioDeCliente.clienteLogueado(idUsuario,password);
        List<Carrito> listaDeCarritos = buscarCarritosDelCliente(idUsuario);
        return servicioDeVentasConcretadas.mostrarVentasDeCarritos(listaDeCarritos);
    }

    private String mensajeDeErrorCuandoYaSeFacturoElCarrito() {
        return "El carrito que quiere cobrar ya se facturó";
    }

    private List<Carrito> buscarCarritosDelCliente(Long idUsuario) {
        List<Carrito> carritos = em.createQuery("select c.carrito from Sesion c where c.cliente.id = :id", Carrito.class).
                setParameter("id", idUsuario).getResultList();
        return carritos;
    }


    private Sesion buscarSesionParaElCliente(Long idUsuario) {
        try {
            List<Sesion> sesion = em.createQuery("select u from Sesion u where u.cliente.id = :id", Sesion.class).
                    setParameter("id", idUsuario).getResultList();
            return sesion.get(sesion.size()-1);}
        catch (RuntimeException NoHaySesionParaElUsuarioIngresado) {

            throw new RuntimeException(mensajeDeErrorCuandoNoExisteSesionParaElUsuarioSeleccionado());
        }

    }

    private static String mensajeDeErrorCuandoNoExisteSesionParaElUsuarioSeleccionado() {
        return "No existe sesión para el cliente ingresado";
    }

}

