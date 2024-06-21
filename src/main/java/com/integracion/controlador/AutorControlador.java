package com.integracion.controlador;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

import com.integracion.BaseDeDatos;
import com.integracion.modelo.AutorModelo;
import com.integracion.vista.AutorVista;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

/**
 * La clase AutorControaldor cumple con el controlador para los autores de la API.
 *
 * @author Charo Lértora
 * @version 1.3
 * */
public class AutorControlador {

    private AutorVista vistaAutor;
    private ArrayList<AutorModelo> autoresGuardados;
    private BaseDeDatos BD;
    Scanner scan = new Scanner(System.in);


    /**
     * Constructor sin parámetros.
     */
    public AutorControlador() {
        super();
    }


    /**
     * Constructor con parámetros.
     * @param modeloAutor es el objeto para el modelo del autor que vamos a utilizar
     * @param vistaAutor es el objeto para la vista del autor que vamos a utilizar
     */
    public AutorControlador(AutorVista vistaAutor, BaseDeDatos BD) {
        this.vistaAutor = vistaAutor;
        this.autoresGuardados = new ArrayList<>();
        this.BD = BD;
    }


    /**
     * Método que se encarga de ejecutar los métodos y las funciones necesarias para que la app funcione.
     * 
     * Se va a ejecutar hasta que el usuario no desee utilizar más el sistema.
     */
    public void iniciarApp() {

        int respuestaUsuario = 1;
        do {
            respuestaUsuario = vistaAutor.menuOpciones();
            switch (respuestaUsuario) {
                case 1:
                    busquedaAPI();
                    break;
                case 2:
                    busquedaAutorPorIdEnBD();
                    break;
                case 3:
                    busquedaAutoresPorAmbitoEnBD();
                    break;
                case 4:
                    eliminarAutorPorIdEnBD();
                    break;
                case 5:
                    eliminarAutoresPorAmbitoEnBD();
                    break;
                default:
                    respuestaUsuario = 6;
                    break;
            }

        } while(respuestaUsuario != 6);
    }


    /**
     * Este método le permitirá al usuario elegir un ámbito en el cuál desee realizar la búsqueda de autores en la API de Google Scholar.
     * Los primeros 10 autores encontrados, se guardarán en nuestro sistema de Base de Datos.
     */
    public void busquedaAPI() {
        

        System.out.println("Ingrese el ámbito donde desea realizar la búsqueda: (Ej: ciencia, arte, política, etc)");
        String ambito = scan.next();
        consultarAutores(ambito);

        mostrarAutores(this.autoresGuardados);
        
    }


    /**
     * Método que le permite al usuario realizar la búsqueda de un autor por ID que se encuentre cargado en el sistema de Base de Datos.
     * Si dicho ID no es encontrado, se le avisará al usuario por pantalla que el mismo no existe.
     */
    public void busquedaAutorPorIdEnBD() {

        AutorModelo autorBuscado = new AutorModelo();
        System.out.println("Ingrese el ID del autor que desea buscar:");
        String id = scan.next();
        BD.buscarAutorPorID(id, autorBuscado);

        if(autorBuscado.getNombre() == null) {
            System.out.println("\nLo sentimos, el autor con el ID: " + id + " no se encuentra cargado en el sistema.\n");
        }else {
            vistaAutor.mostrarAutor(autorBuscado);
        }
    }


    /**
     * Método que le permite al usuario realizar la eliminación de un autor por ID que se encuentre cargado en el sistema de Base de Datos.
     * Si dicho ID no es encontrado, se le avisará al usuario por pantalla que el mismo no existe.
     */
    public void eliminarAutorPorIdEnBD() {

        AutorModelo autorEleminar = new AutorModelo();
        System.out.println("Ingrese el ID del autor que desea eliminar:");
        String id = scan.next();
        BD.eliminarAutorPorID(id, autorEleminar);

        if(autorEleminar.getNombre() == null) {
            System.out.println("\nLo sentimos, el autor con el ID: " + id + " no se encuentra cargado en el sistema.\n");
        }else {
            System.out.println("Fue eliminado el siguiente autor del sistema: \n");
            vistaAutor.mostrarAutor(autorEleminar);
        }
    }


    /**
     * Método que le permite al usuario realizar la búsqueda de todos los autores por un ámbito determinado que se encuentren cargados en el sistema de Base de Datos.
     * Si dicho ámbito no es encontrado, se le avisará al usuario por pantalla que el mismo no existe.
     */
    public void busquedaAutoresPorAmbitoEnBD() {

        ArrayList<AutorModelo> autoresEnSistema = new ArrayList<>();
        System.out.println("Ingrese el ambito de los autores que desea buscar:");
        String ambito = scan.next();
        BD.buscarAutoresPorAmbito(ambito, autoresEnSistema);

        if(autoresEnSistema.size() == 0) {
            System.out.println("\nLo sentimos, no se encontraron autores en el ámbito " + ambito + " cargados en nuestro sistema.\n");
        }else {
            System.out.println("Fueron eliminados los siguientes autores del sistema: \n");
            mostrarAutores(autoresEnSistema);
        }
    }

    
    /**
     * Método que le permite al usuario realizar la eliminación de todos los autores por un ámbito determinado que se encuentren cargados en el sistema de Base de Datos.
     * Si dicho ámbito no es encontrado, se le avisará al usuario por pantalla que el mismo no existe.
     */
    public void eliminarAutoresPorAmbitoEnBD() {

        ArrayList<AutorModelo> autoresEnSistema = new ArrayList<>();
        System.out.println("Ingrese el ambito de los autores que desea buscar:");
        String ambito = scan.next();
        BD.eliminarAutoresPorAmbito(ambito, autoresEnSistema);

        if(autoresEnSistema.size() == 0) {
            System.out.println("\nLo sentimos, no se encontraron autores en el ámbito " + ambito + " cargados en nuestro sistema.\n");
        }else {
            System.out.println("Fueron eliminados los siguientes autores del sistema: \n");
            mostrarAutores(autoresEnSistema);
        }
    }


    /**
     * Este método es el encargado de realizar la conexión con la URL y de realizar la consulta GET a la API de Google Scholar Author.
     * @param tipoBusqueda recibe el id del autor que queremos consultar.
     * @throws IOException Excepción general de Java
     * @throws InterruptedException Se lanza cuando un hilo está esperando, inactivo o ocupado de otro modo y el hilo se interrumpe, ya sea antes o durante la actividad.
     * @throws URISyntaxException Se lanza cuando una cadena no se puede analizar como una referencia de URI.
     * @throws JSONException Se lanza cuando ocurre un error con Json.
     */
    private void consultarAutores(String tipoBusqueda) {

        HttpClient client = HttpClient.newHttpClient();
        String apiKey = "4eb3107ae5bd993752fa635e91c2f708a5d74a83a3f29e7ec7856def0448dbbd";
        String baseUrl = "https://serpapi.com/search.json?engine=google_scholar";

        String apiUrl = baseUrl + "&q=" + tipoBusqueda + "&hl=es-419" + "&api_key=" + apiKey;
        try {

            HttpRequest req = HttpRequest.newBuilder().uri(new URI(apiUrl)).build();
            HttpResponse<String> respuesta = client.send(req, HttpResponse.BodyHandlers.ofString());

            String jsonResp = respuesta.body();
            JSONObject jsonObject = new JSONObject(jsonResp);

            if (jsonObject.has("organic_results")) {

                JSONArray resultados = jsonObject.getJSONArray("organic_results");

                for (int i = 0; i < resultados.length(); i++) {

                    JSONObject resultado = resultados.getJSONObject(i);
                    JSONObject publicacionInfo = resultado.getJSONObject("publication_info");

                    if (publicacionInfo != null) {
                        String id = resultado.getString("result_id");
                        String titulo = resultado.getString("title");
                        String link = resultado.getString("link");
                        String nombre = publicacionInfo.getString("summary");

                        AutorModelo nuevoAutor = new AutorModelo(id, titulo, nombre, link, tipoBusqueda);
                        autoresGuardados.add(nuevoAutor);

                    } else {
                        System.out.println("No se encontró información de publicación para este resultado.");
                    }
                }
            } else {
                System.out.println("No se encontró información en este ámbito de búsqueda.");
            }
        } catch (IOException | InterruptedException | URISyntaxException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar la respuesta JSON", e);
        }
    }


    /**
     * Método que se encarga de mostrar todos los autores guardados en la memoria Java.
     */
    private void mostrarAutores(ArrayList<AutorModelo> autores) {

        for(int i = 0; i < autores.size(); i++) {
            BD.agregarAutor(autores.get(i).getIdAutor(),
                            autores.get(i).getNombre(),
                            autores.get(i).getLink(),
                            autores.get(i).getTituloArticulo(), 
                            autores.get(i).getAmbito());
            vistaAutor.mostrarAutor(autores.get(i));
        }
    }
}
