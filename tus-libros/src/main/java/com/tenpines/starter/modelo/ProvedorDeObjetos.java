package com.tenpines.starter.modelo;


import com.tenpines.starter.modelo.Carrito;

public class ProvedorDeObjetos {

    public Carrito carritoVacio(){
        return new Carrito();
    }

    public Carrito carritoConUnLibro(){
        Carrito unCarrito = new Carrito();
        unCarrito.agregarItem("Libro 1");
        return unCarrito;
    }
}