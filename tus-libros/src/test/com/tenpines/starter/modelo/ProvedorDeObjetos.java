package com.tenpines.starter.modelo;


import com.tenpines.starter.modelo.Carrito;

public class ProvedorDeObjetos {

    public Carrito carritoVacio(){
        return new Carrito();
    }

    public Catalogo crearLibro(){
        Catalogo elPerfume = Catalogo.crearLibro("El perfume", "123456789", 45);
        return elPerfume;
    }

    public Catalogo crearOtroLibro(){
        Catalogo guerraDeLosMundos = Catalogo.crearLibro("Guerra de los mundos", "987654321", 95);
        return guerraDeLosMundos;
    }

    public Catalogo crearLibroInvalido(){
        Catalogo libroInvalido = Catalogo.crearLibro("INVALIDO", "192837465", 100);
        return libroInvalido;
    }



    public Carrito carritoConUnLibro(){
        Carrito unCarrito = new Carrito();
        unCarrito.agregarLibro(this.crearLibro(), 1);
        return unCarrito;
    }
}
