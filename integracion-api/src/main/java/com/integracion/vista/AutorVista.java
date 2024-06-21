package com.integracion.vista;

import java.util.Scanner;

import com.integracion.modelo.AutorModelo;

/**
 * La clase AutorVista cumple con la vista para los autores de la API.
 *
 * @author Charo Lértora
 * @version 1.3
 * */
public class AutorVista {

    private Scanner scan = new Scanner(System.in);


    /**
     * Constructor sin parámetros.
     */
    public AutorVista() {
        super();
    }


    /**
     * Método que sirve para mostrar los datos de un autor.
     * @param autorModelo Objeto que contendrá la información que queremos mostrar, no puede ser nulo.
     */
    public void mostrarAutor(AutorModelo autorModelo) {
        System.out.println(
                "--------------------------------------" +
                "\nNombre: " + autorModelo.getNombre() +
                "\nID: " + autorModelo.getIdAutor() +
                "\nTitulo del artículo: " + autorModelo.getTituloArticulo() +
                "\nLink: " + autorModelo.getLink() +
                "\nAmbito: " + autorModelo.getAmbito() +
                "\n");
    }


    /**
     * Función que retornara un valor para saber si el usuario desea seguir buscando autores o no.
     * @return respuesta(int)
     */
    public int menuOpciones() {

        System.out.println("Ingrese el número que corresponda a la opción elegida: \n" +
                "1. Realizar otra búsqueda.\n" +
                "2. BUSCAR un autor POR ID cargado en el sistema.\n" +
                "3. BUSCAR autores POR AMBITO cargados en el sistema.\n" +
                "4. ELIMINAR autores POR ID cargados en el sistema.\n" +
                "5. ELIMINAR autores POR AMBITO cargados en el sistema.\n" +
                "6. Salir.");
        return scan.nextInt();
    }

}

