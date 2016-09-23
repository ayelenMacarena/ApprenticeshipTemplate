package com.tenpines.starter.modelo;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
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
        Sesion sesion = new Sesion();
        sesion.setCliente(unCliente);
        sesion.setCarrito(carrito);
        sesion.setUltimoUso(Timestamp.valueOf(LocalDateTime.now()));
        return sesion;
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

}
