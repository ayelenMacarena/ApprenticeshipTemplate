package com.tenpines.starter.servicios;


import com.tenpines.starter.modelo.Carrito;
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

    @Autowired
    private EntityManager em;

    @Transactional
    public void registrarVenta(VentaConcretada unaVenta){

        repo.save(unaVenta);
    }
        public void verificarQueNoSeHayaVendidoYaElCarrito(Long carritoId) {
            List<VentaConcretada> unaVenta = getVentaConcretadasParaUnCarrito(carritoId).getResultList();
            if(!unaVenta.isEmpty()){
            throw new RuntimeException(mensajeDeErrorCuandoYaSeFacturoElCarrito());
        }
    }

    private TypedQuery<VentaConcretada> getVentaConcretadasParaUnCarrito(Long carritoId) {
        return em.createQuery("select c from VentaConcretada c where c.carrito.id = :id", VentaConcretada.class).
                        setParameter("id", carritoId);
    }

    private static String mensajeDeErrorCuandoYaSeFacturoElCarrito() {
        return "El carrito que quiere cobrar ya se facturó";
    }

    public List<VentaConcretada> mostrarVentasDeCarritos(List<Carrito> listaDeCarritos) {
        List<VentaConcretada> listaDeVentas = new ArrayList<>();
        for (int i=0; i<listaDeCarritos.size(); i++) {
            Carrito carrito = listaDeCarritos.get(i);
            List<VentaConcretada> venta = getVentaConcretadasParaUnCarrito(carrito.getId()).getResultList();
            if(!venta.isEmpty()){
            listaDeVentas.add(venta.get(0));}

        }

        return listaDeVentas;
    }
}
