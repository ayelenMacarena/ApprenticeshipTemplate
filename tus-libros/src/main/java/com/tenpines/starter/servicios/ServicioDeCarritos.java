package com.tenpines.starter.servicios;

import  com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Cliente;
import com.tenpines.starter.modelo.Libro;
import com.tenpines.starter.repositorios.RepositorioDeCarritos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioDeCarritos {

    @Autowired
    private RepositorioDeCarritos repo;

    @Autowired
    private ServicioDeCliente servicioDeCliente;


    @Autowired
    private ServicioDeCatalogo servicioDeCatalogo;

    @Transactional
    public void almacenar(Carrito carrito) {
        repo.save(carrito);
    }

    public Carrito buscarElCarrito(Long id) {return repo.findOne(id);}

    public List<Carrito> mostrarCarritos(){ return repo.findAll();}

    public Carrito nuevoCarrito() {
        Carrito carrito = Carrito.crearCarrito();
        almacenar(carrito);
        return carrito;
    }

    public void agregarLibro(Carrito unCarrito, Long unLibro, Integer cantidad){
        Libro miLibroAAgregar = servicioDeCatalogo.darLibro(unLibro);
        unCarrito.agregarLibro(miLibroAAgregar,cantidad);
        almacenar(unCarrito);
    }

    public List<Libro> mostrarLibrosDeCarrito(Long id) {
        Carrito carrito = repo.findOne(id);
        return carrito.getItems();
    }

}
