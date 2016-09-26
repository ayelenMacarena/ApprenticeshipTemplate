package com.tenpines.starter.servicios;

import com.tenpines.starter.modelo.Cajero;
import com.tenpines.starter.modelo.Carrito;
import com.tenpines.starter.modelo.TarjetaDeCredito;
import com.tenpines.starter.modelo.VentaConcretada;
import org.springframework.stereotype.Service;

@Service
public class ServicioDeCajero {

    private Cajero cajero = new Cajero();

    public VentaConcretada cobrarUnaCompra(Carrito unCarrito, TarjetaDeCredito tarjetaValidada){
        //TODO: Validar que el carrito ya no haya sido cobrado.
         return cajero.cobrar(unCarrito, tarjetaValidada);
    }

}
