package com.tenpines.starter.repositorios;

import com.tenpines.starter.modelo.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RepositorioDeSesiones extends JpaRepository<Sesion, Long> {

}