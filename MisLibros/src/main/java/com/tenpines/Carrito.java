package com.tenpines;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@javax.persistence.Entity
@javax.persistence.Table(name="carrito")
public class Carrito {

    private ArrayList<String> items = new ArrayList<String>();
    /** Persistente como clave y valor generado por la base de datos */


    @Id
    @Column("carrito_id")
    private Long id;


    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }


    @OneToMany()
    ArrayList<ItemsPorCarrito> Items ;


    public Boolean estaVacio() {
        return items.size() == 0;
    }

    public void agregarItem(String unItem) {
        this.verificarQueElItemSeaValido(unItem);
        items.add(unItem);
    }

    private void verificarQueElItemSeaValido(String unItem) {
        if (unItem.equals("INVALIDO")){
            throw new RuntimeException(mensajeDeErrorCuandoUnLibroNoEsValido());
        }
    }

    public static String mensajeDeErrorCuandoUnLibroNoEsValido() {
        return "El libro no corresponde a esta editorial";
    }

    public Boolean contiene(String unItem) {
        return items.contains(unItem);
    }

    public Integer contidadDeUnItem(final String unItem) {
        return Math.toIntExact(
                items.stream().filter((item) -> item.equals(unItem)).count());
    }

    public Integer contidadTotalDeItems() {
        return items.size();
    }

    public ArrayList<String> contenido(){
        return items;
    }
}
