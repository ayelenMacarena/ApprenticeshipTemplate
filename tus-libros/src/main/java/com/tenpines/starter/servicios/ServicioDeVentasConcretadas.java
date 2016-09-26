package com.tenpines.starter.servicios;


import com.tenpines.starter.modelo.VentaConcretada;
import com.tenpines.starter.repositorios.RepositorioDeVentasConcretadas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioDeVentasConcretadas {

    @Autowired
    private RepositorioDeVentasConcretadas repo;

    @Transactional
    public void registrarVenta(VentaConcretada unaVenta){

        repo.save(unaVenta);
    }

}
