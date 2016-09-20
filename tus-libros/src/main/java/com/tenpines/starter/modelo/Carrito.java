package com.tenpines.starter.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Catalogo> items = new ArrayList<Catalogo>();

    @OneToOne
    /*@JoinColumn(name="id_cliente")*/
    private Cliente cliente;


    public Carrito(){
     }

    public static Carrito crearCarrito(Cliente unCliente){
        Carrito carrito = new Carrito();
        carrito.setCliente(unCliente);
        return carrito;
    }
    //PERSISTENCIA

    private void setId(Long unId) {
        this.id = unId;
    }

    public Long getId() {
        return id;
    }

    public void setItems(ArrayList<Catalogo> unItem) {
        this.items = unItem;
    }

    public List<Catalogo> getItems() {
        return items;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    // METODOS

    private static Catalogo catalogo = new Catalogo();



    public Boolean estaVacio() {
        return items.size() == 0;
    }

    public void agregarLibro(Catalogo unLibro, Integer cantidad) {
        this. verificarQueLaCantidadSeaValida(cantidad);
        for (int i=0; i<cantidad; i++) {
            items.add(unLibro);
        }
    }

    private void verificarQueLaCantidadSeaValida(Integer cantidad) {
        if (cantidad <= 0){
            throw new RuntimeException(mensajeDeErrorCuandoAgregoLibrosUnaCantidadNegativa());
        }
    }


    public static String mensajeDeErrorCuandoAgregoLibrosUnaCantidadNegativa() {
        return "La cantidad de libros a agregar debe ser mayor a 0";
    }


    public Boolean contiene(Catalogo unItem) {
        return items.contains(unItem);
    }

    public Integer contidadDeUnItem(final Catalogo unItem) {
        return Math.toIntExact(
                items.stream().filter((item) -> item.equals(unItem)).count());
    }

    public Integer cantidadTotalDeItems() {
        return items.size();
    }

    public List<Catalogo> contenido(){
        return items;
    }

    public Long tuClienteId() {

        return cliente.getId();


    }

    public static Catalogo catalogo() {
        return catalogo;
    }
}
