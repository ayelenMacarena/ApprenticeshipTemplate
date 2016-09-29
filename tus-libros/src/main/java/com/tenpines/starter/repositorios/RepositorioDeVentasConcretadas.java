package com.tenpines.starter.repositorios;


import com.tenpines.starter.modelo.VentaConcretada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
public interface RepositorioDeVentasConcretadas extends JpaRepository<VentaConcretada, Long> {
    //TODO: El EntityManager no tiene que ser interface, tiene que ser una clase a la que pueda invocar.

    public default List<VentaConcretada> getVentasParaElCarrito(Long carritoId, EntityManager em){
        return em.createQuery("select c from VentaConcretada c where c.carrito.id = :id", VentaConcretada.class).
                setParameter("id", carritoId).getResultList();
                }


}
