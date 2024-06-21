package com.integracion;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import com.integracion.controlador.AutorControlador;
import com.integracion.vista.AutorVista;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, ClassNotFoundException {
		Scanner scan = new Scanner(System.in);

		System.out.println("Ingrese la url de la base de datos para el sistema: \n" +
							"Ejemplo: jdbc:mysql://localhost:3306/ \n");
		String url = scan.next();
		System.out.println("Ingrese el usuario de la base de datos para el sistema: \n" +
							"Ejemplo: root \n");
		String root = scan.next();
		System.out.println("Ingrese la contraseña de la base de datos para el sistema: \n" +
							"Ejemplo: SuContraseña \n");
		String contrasenia = scan.next();
		
		AutorVista vista = new AutorVista();
		BaseDeDatos BD = new BaseDeDatos(url, root, contrasenia);
		AutorControlador controlador = new AutorControlador(vista, BD);
        
		controlador.iniciarApp();
        
		System.out.println("Gracias por elegirnos! Hasta pronto :)");
		BD.cerrarConexion();
		scan.close();
	}
}