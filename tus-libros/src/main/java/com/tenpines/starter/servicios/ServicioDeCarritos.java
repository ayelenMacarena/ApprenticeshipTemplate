package com.tenpines.starter.servicios;

import  com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Libro;
import com.tenpines.starter.repositorios.RepositorioDeCarritos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class ServicioDeCarritos {

    @Autowired
    private RepositorioDeCarritos repo;

    @Autowired
    private ServicioDeCliente servicioDeCliente;

    @Autowired
    private ServicioDeCatalogo servicioDeCatalogo;

    @Autowired
    private EntityManager em;

    @Transactional
    public void almacenar(Carrito carrito) {
        repo.save(carrito);
    }

    public Carrito buscarElCarrito(Long id) {
        return repo.findOne(id);
    }

    public List<Carrito> mostrarCarritos(){
        return repo.findAll();
    }

    public Carrito nuevoCarrito() {
        Carrito carrito = Carrito.crearCarrito();
        almacenar(carrito);
        return carrito;
    }

    public void agregarLibro(Carrito unCarrito, Long unLibro, Integer cantidad){
        validarQueElLibroPertenezcaALaEditorial(unLibro);
        Libro miLibroAAgregar = servicioDeCatalogo.darLibro(unLibro);
        unCarrito.getItems().removeAll(unCarrito.getItems());
        unCarrito.agregarLibro(miLibroAAgregar,cantidad);
        almacenar(unCarrito);
    }


    private void validarQueElLibroPertenezcaALaEditorial(Long libroId) {
        List<Libro> catalogo = em.createQuery("select libro from Libro libro where libro.id = :id", Libro.class).
                setParameter("id", libroId).getResultList();
        if(catalogo.isEmpty()){
            throw new RuntimeException(mensajeDeErrorCuandoElLibroNoExisteEnLaEditorial());
        }
    }

    private static String mensajeDeErrorCuandoElLibroNoExisteEnLaEditorial() {
        return "El libro que intenta agregar al carrito no corresponde a esta editorial";
    }

    public List<Libro> mostrarLibrosDeCarrito(Long id) {
        Carrito carrito = repo.findOne(id);
        return carrito.getItems();
    }
}
