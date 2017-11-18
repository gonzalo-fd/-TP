package es.ucm.fdi.tp.pr1.logica;

import java.io.*;

import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;
import es.ucm.fdi.tp.pr1.control.IndicesFueraDeRango;
import es.ucm.fdi.tp.pr1.control.PalabraIncorrecta;
import es.ucm.fdi.tp.pr1.logica.Superficie;

public abstract class Mundo {
	
	/**
	 * ATRIBUTOS
	 */
	protected Superficie superficie;
	protected int numFilas; 
	protected int numColumnas;
	
	/**
	 * CONSTRUCTOR
	 */
	public Mundo() {
		this.superficie = null;
		this.numFilas = 0;
		this.numColumnas = 0;
	}

	/**
	 * METODOS
	 */
	
	/**
	 * Metodo que reaiza todo lo que es la inteligencia de las celulas. Primero busca en el tablero donde se encuentran las celulas
	 * y despues llama el ejecutaMovimiento de la celula(simple o compleja)
	 */
	public void evoluciona() {
		
		for (int i = 0; i < superficie.getFilas(); i++) {         
			for (int j = 0; j < superficie.getColumnas(); j++) {  
				if (!superficie.estadoCasilla(i, j)) {		//Si hay una celula, coloca la posicion del tablero booleano a true			
					superficie.setArrayPosiciones(i, j, true);						
				}
				else{
					superficie.setArrayPosiciones(i, j, false);	//Si esta vacia a false
				}
			}
		}

		for (int i = 0; i < superficie.getFilas(); i++) {
			for (int j = 0; j < superficie.getColumnas(); j++) {
				if (superficie.getArrayPosiciones(i,j)) { 
					superficie.ejecutaMovimiento(i, j);
				}
			}
		}	
	}
	
	/**
	 * Metodo que elemina una celula en una fila y columna dada
	 * @param f fila donde se encuentra la celula que vas a eliminar
	 * @param c columna donde se encuentra la celula que vas a eliminar
	 * @throws IndicesFueraDeRango excepcion para saber si has introducido un posicion valida del tablero
	 */
	public void eliminarCelula(int f, int c) throws IndicesFueraDeRango {
		
		if(f < superficie.getFilas() && f > -1 && c < superficie.getColumnas() && c > -1){
		superficie.eliminarCelula(f, c);
		}
		else{
			throw new IndicesFueraDeRango();
		}	
	}
	
	/**
	 * Metodo que encarga de cargar de un fichero el tamaño del tablero y generar una superfecie con dicho tamaño
	 * @param fichero fichero de donde coge las filas y las columnas que va a tener la nueva superficie
	 * @param BR variable que lee las filas y las columnas del fichero
	 * @throws PalabraIncorrecta excepcion que se encarga de comprobar si el fichero contiene una palabra mal escrita
	 * @throws FormatoNumericoIncorrecto excepcion que se encarga de comprobar si el fichero contiene una letra donde le correspondia un numero
	 */
	public void cargar(Reader fichero, BufferedReader BR) throws PalabraIncorrecta, FormatoNumericoIncorrecto {
		// TODO Auto-generated method stub
		try {
			String fila = BR.readLine();
			String columna = BR.readLine();
			if(superficie.esNumero(fila) && superficie.esNumero(columna)){
				this.numFilas = Integer.parseInt(fila);	
				this.numColumnas  = Integer.parseInt(columna);
				this.superficie = new Superficie(this.numFilas,this.numColumnas);			
				superficie.cargar(fichero, BR);
					
				BR.close();
			}
			else{
				throw new FormatoNumericoIncorrecto();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void vaciar() {
		superficie.vaciarSuperficie();
	}
	
	/**
	 * Metodo que llama al println de supericie
	 */
	public void dibujar() {
		System.out.println(superficie.toString());
	}
	
	/**
	 * Metodo para conseguir una casilla
	 * @return devuelve la casilla en la que esta
	 */
	public Casilla[] getArrayCasilla(){
		return superficie.getArrayCasilla();
	}
	
	/**
	 * Metodo para mostrar un error en caso de introducir mal los datos
	 */
	public void error(){
		System.out.println("ERROR: Comando no valido." + '\n');
	}

	/**
	 * Metodo que llama a inciarSuperficieCompleja o inciarSuperficieSimple dependiendo de si estÃ¡s en un mundo complejo o simple
	 * @throws IndicesFueraDeRango 
	 */
	public abstract void inicializaMundo() throws IndicesFueraDeRango;
	
	/**
	 * Metodo que se encarga de guardar que tipo de mundo es en el que estas jugando y el numero de filas y de columnas que lo forman
	 * @param nombFich fichero donde se va a guardar el mundo al que estas jugando
	 */
	public abstract void guardar(String nombFich);
	
	/**
	 * Metodo que se encarga de crear una celula en la posicion que tu elijas. Dependiendo de si es un mundo simple o complejo, te creara solo celulas
	 * simple o te pedira que elijas el tipo de celula que quieres crear, simple o compleja
	 * @param fila fila donde vas a crear la nueva celula
	 * @param columna columna donde vas a crear la nueva celula
	 * @throws IndicesFueraDeRango excepcion que te indicia que si la nueva celula que vas a crear esta en una posicion valida
	 */
	public abstract void crearCelula(int fila, int columna) throws IndicesFueraDeRango;
}
