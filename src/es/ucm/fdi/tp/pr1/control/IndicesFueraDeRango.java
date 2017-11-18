package es.ucm.fdi.tp.pr1.control;

public class IndicesFueraDeRango extends Exception{

	/**
	 * Excepcion que indica si estas intentando acceder a una posicion fuera del tablero
	 */
	public IndicesFueraDeRango(){
		System.out.println("Error: fuera de rango.");
	}
}
