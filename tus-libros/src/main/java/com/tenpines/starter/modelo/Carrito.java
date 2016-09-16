package com.tenpines.starter.modelo;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Carrito {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private ArrayList<String> items = new ArrayList<String>();

    @OneToOne
    /*@JoinColumn(name="id_cliente")*/
    private Cliente cliente;


    public Carrito(){

       // this.cliente = new Cliente();
    }

    //PERSISTENCIA

    private void setId(Long unId) {
        this.id = unId;
    }

    public Long getId() {
        return id;
    }

    public void setItems(ArrayList<String> unItem) {
        this.items = unItem;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    // METODOS
    @Column
    private static Catalogo catalogo = new Catalogo();



    public Boolean estaVacio() {
        return items.size() == 0;
    }

    public void agregarLibro(String unItem, Integer cantidad) {
        this.verificarQueElItemSeaValido(unItem);
        this. verificarQueLaCantidadSeaValida(cantidad);
        for (int i=0; i<cantidad; i++) {
            items.add(unItem);
        }
    }

    private void verificarQueElItemSeaValido(String unItem) {
        if (!catalogo.incluye(unItem)){
            throw new RuntimeException(mensajeDeErrorCuandoUnLibroNoEsValido());
        }
    }

    private void verificarQueLaCantidadSeaValida(Integer cantidad) {
        if (cantidad <= 0){
            throw new RuntimeException(mensajeDeErrorCuandoAgregoLibrosUnaCantidadNegativa());
        }
    }

    public static String mensajeDeErrorCuandoUnLibroNoEsValido() {
        return "El libro no corresponde a esta editorial";
    }

    public static String mensajeDeErrorCuandoAgregoLibrosUnaCantidadNegativa() {
        return "La cantidad de libros a agregar debe ser mayor a 0";
    }


    public Boolean contiene(String unItem) {
        return items.contains(unItem);
    }

    public Integer contidadDeUnItem(final String unItem) {
        return Math.toIntExact(
                items.stream().filter((item) -> item.equals(unItem)).count());
    }

    public Integer cantidadTotalDeItems() {
        return items.size();
    }

    public ArrayList<String> contenido(){
        return items;
    }

    public Long tuClienteId() {

        return cliente.getId();


    }

    public static Catalogo catalogo() {
        return catalogo;
    }
}
