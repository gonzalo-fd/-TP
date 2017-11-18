package es.ucm.fdi.tp.pr1.control;

public class ErrorDeInicializacion extends Exception{
	
	/**
	 * Excepcion que indica si el numero de celulas es mayor que el de casillas
	 */
	public ErrorDeInicializacion(){
		System.out.println("Error: el numero de celulas excede el numero de casillas.");
	}
}
