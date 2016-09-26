package com.tenpines.starter.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class VentaConcretada implements Serializable, Cloneable{

    public VentaConcretada(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    public Carrito unCarrito;

    //public List<Libro> itemsDeLaVenta;

    @Column
    public Integer precioTotal;

    @Column
    public LocalDateTime fechaYHoraDeVenta;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrito getUnCarrito(){return this.unCarrito;}

    public void setUnCarrito(Carrito unCarrito) {this.unCarrito = unCarrito;}

    public Integer getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }

    public LocalDateTime getFechaYHoraDeVenta() {
        return fechaYHoraDeVenta;
    }

    public void setFechaYHoraDeVenta(LocalDateTime fechaYHoraDeVenta) {
        this.fechaYHoraDeVenta = fechaYHoraDeVenta;
    }

    public static VentaConcretada nuevaVentaConcretada(Carrito unCarrito, Integer precioTotal, LocalDateTime fechaYHoraDeVenta){
        VentaConcretada ventaConcretada = new VentaConcretada();
        ventaConcretada.setUnCarrito(unCarrito);
        ventaConcretada.setPrecioTotal(precioTotal);
        ventaConcretada.setFechaYHoraDeVenta(fechaYHoraDeVenta);
        return ventaConcretada;
    }
}
