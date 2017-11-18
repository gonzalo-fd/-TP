package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;
import es.ucm.fdi.tp.pr1.control.ErrorDeInicializacion;
import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;
import es.ucm.fdi.tp.pr1.control.IndicesFueraDeRango;

public abstract class Comando {
	
	/**
	 * Metodo que llama el ejecuta del comando correspondiente
	 * @param mundo se le pasa un mundo
	 * @throws FormatoNumericoIncorrecto 
	 * @throws IndicesFueraDeRango 
	 */
	public abstract void ejecuta(Controlador controlador) throws FormatoNumericoIncorrecto, IndicesFueraDeRango;
	
	/**
	 * Metodo que llamar al Parsea. Dependiendo de la clase que sea comprobara si el array es de tamaño 1, tamño 3,
	 * si la palabra esta bien escrita y en caso afirmativo llama a la clase.
	 * @param cadenaComando se le pasa un comando el cual parsea
	 * @return devuelve el comando correspondiente
	 * @throws ErrorDeInicializacion 
	 * @throws FormatoNumericoIncorrecto 
	 * @throws IndicesFueraDeRango 
	 */
	public abstract Comando parsea(String[ ] cadenaComando) throws ErrorDeInicializacion, FormatoNumericoIncorrecto, IndicesFueraDeRango;
	
	/**
	 * Metodo que muestra el texto de ayuda del comando correspondiente
	 * @return devuelve un string con el texto de ayuda
	 */
	public abstract String textoAyuda();
	
	/**
	 * Metodo que utilizamos para la excepcion que nos indicia si en vez de un numero, estamos metiendo cualquier otra cosa
	 * @param s parametro string que contiene el valor de lo que estamos cargando del fichero
	 * @return devuelve true si es un entero false si no lo es
	 */
	public boolean esNumero(String s){
		
		try{
			Integer.parseInt(s);
			return true;
			
		}catch(NumberFormatException fne){
			return false;
		}
		
	}

}
