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

    @OneToMany(fetch = FetchType.EAGER)
    public List<Libro> itemsDeLaVenta;

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

    public List<Libro> getItemsDeLaVenta() {
        return itemsDeLaVenta;
    }

    public void setItemsDeLaVenta(List<Libro> itemsDeLaVenta) {
        this.itemsDeLaVenta = itemsDeLaVenta;
    }

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

    public static VentaConcretada nuevaVentaConcretada(List<Libro> ItemsDeLaVenta, Integer precioTotal, LocalDateTime fechaYHoraDeVenta){
        VentaConcretada ventaConcretada = new VentaConcretada();
        ventaConcretada.setItemsDeLaVenta(ItemsDeLaVenta);
        ventaConcretada.setPrecioTotal(precioTotal);
        ventaConcretada.setFechaYHoraDeVenta(fechaYHoraDeVenta);
        return ventaConcretada;
    }
}
