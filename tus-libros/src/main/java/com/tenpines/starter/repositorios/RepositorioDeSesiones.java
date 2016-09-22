package com.tenpines.starter.repositorios;

import com.tenpines.starter.modelo.Libro;
import com.tenpines.starter.modelo.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Aye on 22/09/16.
 */
@Transactional
public interface RepositorioDeSesiones extends JpaRepository<Sesion, Long> {

}