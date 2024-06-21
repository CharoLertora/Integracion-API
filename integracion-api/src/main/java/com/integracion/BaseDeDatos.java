package com.integracion;

import java.sql.*;
import java.util.ArrayList;

import com.integracion.modelo.AutorModelo;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

/**
 * La clase BaseDeDatos agrupa todos los métodos y atributos necesarios para poder utilizar una base de datos en el sistema junto con la app Java.
 *
 * @author Charo Lértora
 * @version 1.3
 * */
public class BaseDeDatos {
    final String NOMBRE_BD = "busquedas";
    final String NOMBRE_TABLA = "autores";
    private String url;
    private String usuario;
    private String contrasenia;
    private Connection conexion;
    private Statement declaracion;
    
    /**
     * Constructor sin parámetros.
     */
    public BaseDeDatos() {
        super();
    }


    /**
     * Constructor con parámetros.
     * @param url representa la url a la que debe conectarse la app en el sistema de Base de datos.
     * @param usuario representa el usuario que debe tenerse a la hora de conectarse al sistema de la base de datos.
     * @param contrasenia representa el usuario que debe tenerse a la hora de conectarse al sistema de la base de datos.
     * Si dichos parametros no contienen la información válida para la conexión a la Base de Datos, no funcionará la app.
     */
    public BaseDeDatos(String url, String usuario, String contrasenia) {
        this.url = url;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        crearBasedeDatos();
        crearTabla();
    }


    /**
     * Función que devuelve la url de la Base de datos.
     * @return url(String)
     */
    public String getUrl() {
        return url;
    }


    /**
     * Método que sirve para modificar la url de la base de datos
     * @param url representa la nueva url
     */
    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * Función que devuelve el usuario de la Base de datos.
     * @return usuario(String)
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Método que sirve para modificar el nombre de usuario de la base de datos
     * @param usuario representa el nuevo usuario de la base de datos
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    /**
     * Función que devuelve la contraseña de la Base de datos.
     * @return contrasenia(String)
     */
    public String getContrasenia() {
        return contrasenia;
    }


    /**
     * Método que sirve para modificar la contraseña de la base de datos
     * @param contrasenia representa la nueva contraseña
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }


    /**
     * Método que se encarga de realizar la conexión a la base de datos.
     * En el caso de que la conexión no sea posible, se lanzará una excepción con el error que haya ocurrido.
     */
    public void abrirConexion() {
        Connection conexion = null;
        Statement declaracion = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(this.url, this.usuario, this.contrasenia);
            declaracion = conexion.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            this.conexion = conexion;
            this.declaracion = declaracion;
        }   
    }


    /**
     * Método que se encarga de realizar el cierre a la conexión a la base de datos.
     * En el caso de que la conexión no sea posible, se lanzará una excepción con el error que haya ocurrido.
     */
    public void cerrarConexion() {
        
        try {
            this.conexion.close();
            this.declaracion.close();
        } catch (SQLException e) {
            System.out.println("Falló el cierre de la conexión.");
            e.printStackTrace();
        }
    }


    /**
     * Método que se encarga de crear una base de datos en el sistema, con un nombre fijo por defecto.
     * En el caso de que la base de datos no pueda ser creada, lanzará una excepción.
     * En el caso de que la base de datos ya se encuentre creada en el sistema, se le avisará al usuario por pantalla y se continuará con la ejecución del programa.
     */
    public void crearBasedeDatos() {
        abrirConexion();
        try {  
            String consulta = "CREATE DATABASE " + NOMBRE_BD;
            this.declaracion.executeUpdate(consulta);
            System.out.println("Base de datos creada.\n");

        }catch (CommunicationsException c) {
            System.out.println("Falló la conexión al sistema.");            
        }catch (SQLException e) {
            System.out.println("La base de datos ya se encuentra creada en el sistema.\n");
        }

    }


    /*
     * Método que se encargará de borrar una base de datos en el sistema.
     * @param nombreBD representa el nombre de la base de datos que se desee eliminar.
     * Este método es protected ya que no cualquiera debería poder acceder a él.
     */
    protected void borrarBaseDeDatos(String nombreBD) {
        
        try {
            String consulta = "DROP DATABASE " + nombreBD;
            this.declaracion.executeUpdate(consulta);
            System.out.println("Se ha eliminado la base de datos " + nombreBD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Método que se encarga de crear una tabla en la base de datos del sistema, con un nombre fijo por defecto.
     * En el caso de que la base de datos no pueda ser creada, lanzará una excepción.
     * En el caso de que la base de datos ya se encuentre creada en el sistema, se le avisará al usuario por pantalla y se continuará con la ejecución del programa.
     */
    public void crearTabla() {
        this.url = this.url + NOMBRE_BD;
        abrirConexion();
        try {
            abrirConexion();
            String consulta = "CREATE TABLE " + NOMBRE_TABLA +
                            "(id VARCHAR(255) not NULL, " +
                            "nombre VARCHAR(255), " + 
                            "tituloArticulo VARCHAR(255), " +
                            "link VARCHAR(255), " +
                            "ambito VARCHAR(255), " + 
                            "PRIMARY KEY ( id ))";
            
            this.declaracion.executeUpdate(consulta);
        } catch (SQLSyntaxErrorException e) {
            System.out.println("La tabla ya existe en el sistema.\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que se encarga de cargar un autor nuevo a la tabla de la base de datos.
     * En el caso de que no se pueda agregar un autor por un error de SQL, se lanzará una excepción.
     * En el caso de que el autor ya esté cargado previamente en la BD, se le avisará al usuario por pantalla y se continuará con la ejecución del programa.
     * 
     * @param idAutor representa el ID del autor que se desea cargar.
     * @param nombre representa el nombre del autor que se desea cargar.
     * @param link representa el link del autor que se desea cargar.
     * @param tituloArticulo representa el titulo del artículo del autor que se desea cargar.
     * @param ambito representa el ambito del autor que se desea cargar.
     */
    public void agregarAutor(String idAutor, String nombre, String link, String tituloArticulo, String ambito) {
        
        try {
            String consulta = "INSERT INTO " + NOMBRE_TABLA + " VALUES " + 
                            "('" + idAutor + "', " +
                            "'" + nombre + "', " + 
                            "'" + tituloArticulo + "', " + 
                            "'" + link + "', " +
                            "'" + ambito + "')";
            this.declaracion.executeUpdate(consulta);
        }catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("El autor " + nombre + " ya está cargado en nuestro sistema.\n");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que se encarga de buscar un autor ya cargado en la tabla de la base de datos.
     * En el caso de que no se pueda buscar un autor por un error de SQL, se lanzará una excepción.
     * 
     * @param idAutor representa el ID del autor que se desea buscar.
     * @param autorBuscado es una instancia de la clase AutorModelo donde se cargaran los datos obtenido desde la BD.
     */
    public void buscarAutorPorID(String ID, AutorModelo autorBuscado) {
        
        try{
            String consulta = "SELECT * FROM " + NOMBRE_BD + "." + NOMBRE_TABLA + " WHERE id = ?";
            PreparedStatement statement = this.conexion.prepareStatement(consulta);
            statement.setString(1, ID);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                autorBuscado.setIdAutor(resultado.getString("id"));
                autorBuscado.setNombre(resultado.getString("nombre"));
                autorBuscado.setTituloArticulo(resultado.getString("tituloArticulo"));
                autorBuscado.setLink(resultado.getString("link"));
                autorBuscado.setAmbito(resultado.getString("ambito"));
            }
            resultado.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Método que se encarga de buscar todos los autores que corresponden a un ambito y se encuentren cargados en la tabla de la base de datos.
     * En el caso de que no se pueda buscarlos por un error de SQL, se lanzará una excepción.
     * 
     * @param ambito representa el ambito de los autores que se desea buscar.
     * @param autores es un ArrayList donde cargaremos los autores encontrados con una instancia de AutorModelo nueva por cada uno de ellos.
     */
    public void buscarAutoresPorAmbito(String ambito, ArrayList<AutorModelo> autores) {
        
        try{
            String consulta = "SELECT * FROM " + NOMBRE_BD + "." + NOMBRE_TABLA + " WHERE ambito = ?";
            PreparedStatement statement = this.conexion.prepareStatement(consulta);
            statement.setString(1, ambito);
            ResultSet resultado = statement.executeQuery();
            AutorModelo autor = new AutorModelo();
            
            while (resultado.next()) {
                autor.setIdAutor(resultado.getString("id"));
                autor.setNombre(resultado.getString("nombre"));
                autor.setTituloArticulo(resultado.getString("tituloArticulo"));
                autor.setLink(resultado.getString("link"));
                autor.setAmbito(resultado.getString("ambito"));
                autores.add(autor);
            }
            resultado.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que se encarga de eliminar un autor ya cargado en la tabla de la base de datos.
     * En el caso de que no se pueda eliminar un autor por un error de SQL, se lanzará una excepción.
     * 
     * @param idAutor representa el ID del autor que se desea buscar.
     * @param autorEliminar es una instancia de la clase AutorModelo donde se cargaran los datos obtenidos del autor que se desea eliminar desde la BD, para lo cuál se utiliza el método buscarAutoresPorID.
     */
    public void eliminarAutorPorID(String ID, AutorModelo autorElminar) {
        
        try{

            buscarAutorPorID(ID, autorElminar);

            String consulta = "DELETE FROM " + NOMBRE_BD + "." + NOMBRE_TABLA + " WHERE id = ?";
            PreparedStatement statement = this.conexion.prepareStatement(consulta);
            statement.setString(1, ID);
            statement.executeUpdate();
            
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que se encarga de eliminar todos los autores que corresponden a un ambito y se encuentren cargados en la tabla de la base de datos.
     * En el caso de que no se pueda eliminarlos por un error de SQL, se lanzará una excepción.
     * 
     * @param ambito representa el ambito de los autores que se desea eliminar.
     * @param autores es un ArrayList donde cargaremos los autores encontrados que se desean eliminar con una instancia de AutorModelo nueva por cada uno de ellos, para lo cuál se utiliza el método buscarAutoresPorAmbito.
     */
    public void eliminarAutoresPorAmbito(String ambito, ArrayList<AutorModelo> autores) {
        
        try{

            buscarAutoresPorAmbito(ambito, autores);
            String consulta = "DELETE FROM " + NOMBRE_BD + "." + NOMBRE_TABLA + " WHERE ambito = ?";
            PreparedStatement statement = this.conexion.prepareStatement(consulta);
            statement.setString(1, ambito);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

