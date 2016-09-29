package com.tenpines.starter.repositorios;

import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.Sesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
public interface RepositorioDeSesiones extends JpaRepository<Sesion, Long> {


    @Query("select c from Sesion c where c.carrito.id = :carritoId")
    Sesion getSesion(Long carritoId);

    @Query("select c.carrito from Sesion c where c.cliente.id = :idUsuario")
    List<Carrito> getCarritoDeUsuario(Long idUsuario);

    @Query("select u from Sesion u where u.cliente.id = :id")
    List<Sesion> getSesionParaCliente(Long id);


}