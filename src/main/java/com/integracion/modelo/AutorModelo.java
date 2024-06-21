package com.integracion.modelo;

/**
 * La clase AutorModelo cumple con el modelo para los autores de la API.
 *
 * @author Charo Lértora
 * @version 1.3
 * */
public class AutorModelo {
    private String idAutor;
    private String nombre;
    private String link;
    private String tituloArticulo;
    private String ambito;


    /**
     * Constructor sin parámetros.
     * */
    public AutorModelo() {
        super();
    }


    /**
     * Constructor con parámetros.
     *
     * @param tituloArticulo representa el titulo del artículo en el que participa el autor
     * @param nombre representa el nombre del autor
     * @param link representa las linkes que tiene el autor
     * */
    public AutorModelo(String id, String tituloArticulo, String nombre, String link, String ambito) {
        this.idAutor = id;
        this.tituloArticulo = tituloArticulo;
        this.nombre = nombre;
        this.link = link;
        this.ambito = ambito;
    }


    /**
     * Función que devuelve el nombre del autor
     * @return nombre(String)
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * Método que sirve para modificar el nombre del autor
     * @param nombre representa el nuevo nombre del autor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * Función que devuelve el link del articulo del autor
     * @return link(String)
     */
    public String getLink() {
        return link;
    }


    /**
     * Método que sirve para modificar el link del autor
     * @param link representa el nuevo link del autor
     */
    public void setLink(String link) {
        this.link = link;
    }


    /**
     * Función que devuelve el titulo del articulo del autor
     * @return tituloArticulo(String)
     */
    public String getTituloArticulo() {
        return tituloArticulo;
    }


    /**
     * Método que sirve para modificar el titulo del articulo del autor
     * @param tituloArticulo representa el nuevo titulo para el artículo del autor
     */
    public void setTituloArticulo(String tituloArticulo) {
        this.tituloArticulo = tituloArticulo;
    }


    /**
     * Función que devuelve el ID del autor
     * @return idAutor(String)
     */
    public String getIdAutor() {
        return idAutor;
    }


    /**
     * Método que sirve para modificar el ID del autor
     * @param idAutor representa el nuevo ID del autor
     */
    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }


    /**
     * Función que devuelve el ambito del autor
     * @return ambito(String)
     */
    public String getAmbito() {
        return ambito;
    }


    /**
     * Método que sirve para modificar el ambito del autor
     * @param ambito representa el nuevo ambito del autor
     */
    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    
}