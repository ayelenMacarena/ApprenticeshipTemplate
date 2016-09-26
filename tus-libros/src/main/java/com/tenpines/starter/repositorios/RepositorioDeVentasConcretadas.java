package com.tenpines.starter.repositorios;


import com.tenpines.starter.modelo.VentaConcretada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RepositorioDeVentasConcretadas extends JpaRepository<VentaConcretada, Long> {

}
