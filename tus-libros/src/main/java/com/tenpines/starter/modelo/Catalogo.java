package com.tenpines.starter.modelo;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Catalogo {

    public Catalogo(){
        this.inicializarCatalogo();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    public ArrayList<String> nombreLibro = new ArrayList<String>();


    public void inicializarCatalogo(){
        nombreLibro.add("Guerra de los mundos");
        nombreLibro.add("El perfume");
        nombreLibro.add("Nacidos de la bruma");
    }

    public ArrayList<String> getNombreLibro(){
        return nombreLibro;
    }

    public void setNombreLibro(ArrayList<String> unCatalogo) {
        this.nombreLibro = unCatalogo;
    }

    public Boolean incluye(String unLibro){
        return nombreLibro.contains(unLibro);
    }

    public Long getId(){ return this.id;}

    public void setId(Long id){ this.id = id;}



}

