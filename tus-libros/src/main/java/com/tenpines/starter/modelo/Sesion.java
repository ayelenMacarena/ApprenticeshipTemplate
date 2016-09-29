package com.tenpines.starter.modelo;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Entity;

@Entity
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sesion;

    @OneToOne
    private Cliente cliente;

    @OneToOne
    private Carrito carrito;

    @Column
    @Type(type="timestamp")
    private Timestamp ultimoUso;

    public static Sesion crearSesion(Carrito carrito, Cliente unCliente) {
        if(unCliente == null){
            throw new RuntimeException(mensajeDeErrorCuandoQuieroCrearUnCarritoConUsuarioInvalido());
        }
        Sesion sesion = new Sesion();
        sesion.setCliente(unCliente);
        sesion.setCarrito(carrito);
        sesion.setUltimoUso(Timestamp.valueOf(LocalDateTime.now()));
        return sesion;
    }


    public static String mensajeDeErrorCuandoQuieroCrearUnCarritoConUsuarioInvalido() {
        return "Para crear un carrito debe estar logueado con un usuario valido";
    }

    public Long getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(Long id){
        this.id_sesion = id;
    }


    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setUltimoUso(Timestamp ultimoUso) {
        this.ultimoUso = ultimoUso;
    }

    public Timestamp getUltimoUso() {
        return ultimoUso;
    }

    public int treintaMinutosDeExpiracion(){
        return 30;
    }

    public boolean laSesionExpiro() {
        //TODO: ver usar reloj en lo que está trabajando Kevin.
        return diferenciaDeTiempo() > treintaMinutosDeExpiracion();
    }

    private long diferenciaDeTiempo() {
        return Duration.between(this.getUltimoUso().toLocalDateTime(), LocalDateTime.now()).toMinutes();
    }

    public void chequearSesionExpirada() {
        if(this.laSesionExpiro()){
            throw new RuntimeException(mensajeDeErrorSesionExpirada());
        }
    }

    public static String mensajeDeErrorSesionExpirada() {
        return "Su sesión está expirada";
    }



}
