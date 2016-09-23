package com.tenpines.starter.repositorios;

import com.tenpines.starter.modelo.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RepositorioDeSesiones extends JpaRepository<Sesion, Long>{

    List<Sesion> findByCarritoId(Long carritoId);

}