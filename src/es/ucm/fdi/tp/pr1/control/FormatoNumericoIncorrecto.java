package es.ucm.fdi.tp.pr1.control;

public class FormatoNumericoIncorrecto extends Exception {
	
	/**
	 * Excepcion que indica si estas introduciendo cualquier cosa que no sea un numero
	 */
	public FormatoNumericoIncorrecto(){
		System.out.println("Error: caracteres no numericos");
	}
}
