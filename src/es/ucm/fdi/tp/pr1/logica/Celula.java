package es.ucm.fdi.tp.pr1.logica;

import java.io.*;


public abstract class Celula {
	
	/**
	 * ATRIBUTO
	 */
	protected boolean esComestible;
	
	/**
	 * METODOS
	 */
	
	/**
	 * Metodo abstracto que se utiliza en todas las clase hija
	 * @param f Se le pasa una fila	
	 * @param c se la pasa una columna
	 * @param superficie Se le pasa una Superficie
	 * @return Devuelve una casilla
	 */
	public abstract Casilla ejecutaMovimiento(int f, int c, Superficie superficie);
	
	/**
	 * Se utiliza para saber que tipo de celula es(Simple o Compleja)
	 * @return Devuelve true si es simple y false si es compleja
	 */
	public abstract boolean esComestible();
	/**
	 * Se utiliza para cargar el numPasos numReproduccion o numComidas dependiendo de si es el cargar de celula simple o de la compleja.
	 * @param cmdArray Es el array que se forma al coger una linea del fichero,es decir, contiene los siguientes datos fila,columna,simple o compleja, y ya 
	 * dependiendo si es simple o compleja pues contendra numPasos numReproduccion o numComidas respectivamente.
	 */
	public abstract void cargar(String[] cmdArray);
	/**
	 * Se utiliza para guardar el tipo de celula que es,simple o compleja, y dependiendo del tipo de celula, guardará numPasos numReproduccion o numComidas.
	 * @param fichero es el fichero donde vas a guardar la partida.
	 */
	public abstract void guardar(FileWriter fichero);
	
}



