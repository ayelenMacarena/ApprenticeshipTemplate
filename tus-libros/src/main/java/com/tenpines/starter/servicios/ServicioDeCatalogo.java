package com.tenpines.starter.servicios;

import com.tenpines.starter.modelo.Catalogo;
import com.tenpines.starter.repositorios.RepositorioDeCatalogo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServicioDeCatalogo {

    @Autowired
    private RepositorioDeCatalogo repo;

    public Catalogo darLibro(Long id) {
        return repo.findOne(id);
    }

    public void guardarCatalogo(Catalogo catalogo) {
        repo.save(catalogo);
    }


    public List<Catalogo> mostrarCatalogo(){
        List<Catalogo> repositorio = repo.findAll();
        return repositorio;
    }
}