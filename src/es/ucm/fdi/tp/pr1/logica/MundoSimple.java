package es.ucm.fdi.tp.pr1.logica;

import java.io.*;

import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;
import es.ucm.fdi.tp.pr1.control.IndicesFueraDeRango;
import es.ucm.fdi.tp.pr1.control.PalabraIncorrecta;

public class MundoSimple extends Mundo {

	/**
	 * ATRIBUTOS
	 */
	protected int numFilas; 
	protected int numColumnas;
	private int numCelulas;
	

	/**
	 * CONSTRUCTOR
	 * @param filas se le pasa el numero de filas
	 * @param columnas se le pasa el numero de columnas
	 * @param numCelulas se le pasa el numero de celulas 
	 * @throws IndicesFueraDeRango 
	 */
	public MundoSimple(int filas, int columnas, int numCelulas) throws IndicesFueraDeRango {
		this.numFilas = filas;
		this.numColumnas = columnas;
		this.numCelulas = numCelulas;
		this.superficie = new Superficie(this.numFilas,this.numColumnas);
		inicializaMundo();
	}
	
	@Override
	public void inicializaMundo() throws IndicesFueraDeRango {
		// TODO Auto-generated method stub
		int cont = 0, fa, ca;
		superficie.iniciarSuperficie(this.numFilas, this.numColumnas);	
		
		while (cont < numCelulas) {  //Mientras el contador sea menor que el numero de celulas dado por la constante, sigue creando células en posiciones aleatorias del tablero.
			fa = (int) (Math.random() * this.numFilas);
			ca = (int) (Math.random() * this.numColumnas);
			if (superficie.estadoCasilla(fa,ca)) { //Si la casilla esta vacia, crea la célula en la posición (fa,ca)
				crearCelula(fa, ca);
				cont++;
			}
		}
	}

	@Override
	public void guardar(String nombFich){
		// TODO Auto-generated method stub
		try {
			FileWriter fichero = new FileWriter(nombFich);
			fichero.write("simple" + '\r' + '\n'+ this.numFilas + '\r' + '\n' + this.numColumnas + '\r' + '\n');
			superficie.guardar(fichero);
			fichero.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void crearCelula(int fila, int columna) throws IndicesFueraDeRango{
		// TODO Auto-generated method stub
		if(fila < this.numFilas && fila > -1 && columna < this.numColumnas && columna > -1){
		superficie.crearCelula(fila,columna, new CelulaSimple());
		}
		else{
			throw new IndicesFueraDeRango();
		}
	}
}
		
