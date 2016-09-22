package com.tenpines.starter.servicios;

import com.tenpines.starter.modelo.Reloj;

public class ServicioDeReloj {

    public Reloj iniciarTimer(){
        return new Reloj();
    }

    public void verificarQueNoHayaPasadoElTiempo(Reloj horaInicial){
        Reloj horaAhora = new Reloj();
        long diffHoraria = horaAhora.horaActual().toEpochDay() - horaInicial.horaActual().toEpochDay();
        if(diffHoraria > 30){

        }
    }


}
