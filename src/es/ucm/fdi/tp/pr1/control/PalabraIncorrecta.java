package es.ucm.fdi.tp.pr1.control;

public class PalabraIncorrecta extends Exception{
	
	/**
	 * Excepcion que indica si has introducido una palabra que no concuerda con lo esperado en el fichero
	 */
	public PalabraIncorrecta(){
		System.out.println("Error: palabra incorrecta en el fichero.");
	}
}
