package com.tenpines.starter.repositorios;

import com.tenpines.starter.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pino on 15/09/16.
 */
@Transactional
public interface RepositorioDeClientes extends JpaRepository<Cliente, Long> {
}
