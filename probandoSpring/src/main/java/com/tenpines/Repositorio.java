package com.tenpines;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aye on 08/09/16.
 */
public class Repositorio {
    private List<Cosa> listaDeCosas;

    public Repositorio() {
       ArrayList<Cosa> listaDeCosas = new ArrayList<Cosa>();
    }

    public Integer insert(Cosa unaCosa){
        listaDeCosas.add(unaCosa);
        return unaCosa.getId();

    }

    public Cosa delete(Cosa unaCosa){
        listaDeCosas.remove(unaCosa);
        return unaCosa;
    }

    public List<Cosa> show(){
        return listaDeCosas;
    }



}
