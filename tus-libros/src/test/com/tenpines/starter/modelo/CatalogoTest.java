package com.tenpines.starter.modelo;


import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CatalogoTest {

    Catalogo catalogo = Catalogo.crearLibro("El perfume", "123456789", 45);

    @Test
    public void alCrearUnCatalogoSuNombreEsIgualAlDeSuCreacion(){
        assertThat(catalogo.getNombreLibro()).isEqualTo("El perfume");
    }

    @Test
    public void alCrearUnLibroDebeTenerContenerNombreIsbnYPrecio(){
        assertThat(catalogo.getNombreLibro()).isEqualTo("El perfume");
        assertThat(catalogo.getIsbn()).isEqualTo("123456789");
        assertThat(catalogo.getPrecio()).isEqualTo(45);
    }
}
