package com.tenpines.starter.repositorios;

import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Sesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
public interface RepositorioDeSesiones extends JpaRepository<Sesion, Long> {

    //TODO: El EntityManager no tiene que ser interface, tiene que ser una clase a la que pueda invocar.

    public default Sesion getSesion(Long carritoId, EntityManager em) {
        return em.createQuery("select c from Sesion c where c.carrito.id = :id", Sesion.class).
                setParameter("id", carritoId).getSingleResult();
    }

    public default List<Carrito> getCarritoDeUsuario(Long idUsuario, EntityManager em){
        List<Carrito> carritos = em.createQuery("select c.carrito from Sesion c where c.cliente.id = :id", Carrito.class).
                setParameter("id", idUsuario).getResultList();
        return carritos;
    }

    public default List<Sesion> getSesionParaCliente(Long idUsuario, EntityManager em){
        return em.createQuery("select u from Sesion u where u.cliente.id = :id", Sesion.class).
                setParameter("id", idUsuario).getResultList();
    }

}