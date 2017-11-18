package es.ucm.fdi.tp.pr1.main;

import java.util.Scanner;

import es.ucm.fdi.tp.pr1.control.Controlador;
import es.ucm.fdi.tp.pr1.control.ErrorDeInicializacion;
import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;
import es.ucm.fdi.tp.pr1.control.IndicesFueraDeRango;

public class Main {
/**
 * @throws IndicesFueraDeRango 
 * @Autores: 
 * Gonzalo Fernandez-Diez Ponte
 * Manuel Antonio Fernandez Alonso
 */
	public static void main(String[] args) throws ErrorDeInicializacion, FormatoNumericoIncorrecto, IndicesFueraDeRango {
		Scanner s= new Scanner(System.in);
		Controlador controlador = new Controlador(s);
		controlador.realizaSimulacion();
		
	}
}

