package com.tenpines.starter.servicios;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import com.tenpines.starter.modelo.Cliente;
import com.tenpines.starter.repositorios.RepositorioDeClientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pino on 15/09/16.
 */
@Service
public class ServicioDeCliente {


    @Autowired
    private RepositorioDeClientes repo;

    @Transactional
    public void almacenar(Cliente cliente) {
        repo.save(cliente);
    }

    public Cliente buscarElCliente(Long id) {return repo.findOne(id);}


    public boolean loguearCliente(Long Id, String password) {
        return ((buscarElCliente(Id).getPassword()).equals(password));
    }

    public Cliente clienteLogueado(Long id, String password){
        if (loguearCliente(id, password)){
            return buscarElCliente(id);
        }
        throw new RuntimeException(mensajeDeErrorCuandoUnUsuarioOUnaContraseniaEsInvalida());
    }

    public static String mensajeDeErrorCuandoUnUsuarioOUnaContraseniaEsInvalida() {
        return "Su usuario o contrasenia es invalido";
    }
}
