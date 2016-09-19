package com.tenpines.starter.repositorios;


import com.tenpines.starter.modelo.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RepositorioDeCatalogo extends JpaRepository<Catalogo, Long> {

}