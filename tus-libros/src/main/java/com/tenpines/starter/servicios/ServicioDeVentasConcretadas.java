package com.tenpines.starter.servicios;


import com.tenpines.starter.modelo.Cajero;
import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.TarjetaDeCredito;
import com.tenpines.starter.modelo.VentaConcretada;
import com.tenpines.starter.repositorios.RepositorioDeVentasConcretadas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioDeVentasConcretadas {


    @Autowired
    private RepositorioDeVentasConcretadas repo;

    @Transactional
    public void registrarVenta(VentaConcretada unaVenta){

        repo.save(unaVenta);
    }


    private static String mensajeDeErrorCuandoYaSeFacturoElCarrito() {
        return "El carrito que quiere cobrar ya se facturó";
    }

    public List<VentaConcretada> mostrarVentasDeCarritos(List<Carrito> listaDeCarritos) {
        List<VentaConcretada> listaDeVentas = new ArrayList<>();
        for (int i=0; i<listaDeCarritos.size(); i++) {
            Carrito carrito = listaDeCarritos.get(i);
            List<VentaConcretada> venta = repo.getVentasParaElCarrito(carrito.getId());
            if(!venta.isEmpty()){
            listaDeVentas.add(venta.get(0));}

        }

        return listaDeVentas;
    }

    public void verificarQueNoSeHayaVendido(Long carritoId) {
        List<VentaConcretada> unaVenta = repo.getVentasParaElCarrito(carritoId);
        if (!unaVenta.isEmpty()) {
            throw new RuntimeException(mensajeDeErrorCuandoYaSeFacturoElCarrito());

        }
    }

    public void concretarVenta(Carrito carrito, TarjetaDeCredito tarjetaValidada){
        verificarQueNoSeHayaVendido(carrito.getId());
        Cajero cajero = new Cajero();
        VentaConcretada ventaConcretada = cajero.cobrar(carrito, tarjetaValidada);
        registrarVenta(ventaConcretada);

    }
}
