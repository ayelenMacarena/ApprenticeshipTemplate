package com.tenpines;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Aye on 08/09/16.
 */
public class RepositorioTest {

        Repositorio repositorio;
        Cosa unaCosa = new Cosa(1);

    @Before
        public void setUp() {
            repositorio = new Repositorio();

        }

        @Test
        public void testAddInRepositorio(){
            assertThat(repositorio.insert(unaCosa)).isEqualTo(unaCosa.getId());
        }
    }

