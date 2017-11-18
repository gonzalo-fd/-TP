package es.ucm.fdi.tp.pr1.logica;
import java.io.*;
import java.util.Scanner;

import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;
import es.ucm.fdi.tp.pr1.control.IndicesFueraDeRango;
import es.ucm.fdi.tp.pr1.control.PalabraIncorrecta;

public class MundoComplejo extends Mundo{
	
	/**
	 * ATRIBUTOS
	 */
	protected int numFilas; 
	protected int numColumnas;
	private int numCelulas;
	private int numCelulasComplejas;
	
	/**
	 * CONSTRUCTOR
	 */
	public MundoComplejo(int filas, int columnas, int numCelulas, int numCelulasComplejas) {
		this.numFilas = filas;
		this.numColumnas = columnas;
		this.numCelulas = numCelulas;
		this.numCelulasComplejas = numCelulasComplejas;
		this.superficie = new Superficie(filas,columnas);
		inicializaMundo();
	}
	
	@Override
	public void inicializaMundo() {
		// TODO Auto-generated method stub
		int cont = 0, fa, ca;
		superficie.iniciarSuperficie(numFilas, numColumnas);
		
		
		while (cont < numCelulas) {  //Mientras el contador sea menor que el numero de celulas dado por la constante, sigue creando células en posiciones aleatorias del tablero.
			fa = (int) (Math.random() * this.numFilas);
			ca = (int) (Math.random() * this.numColumnas);
			if (superficie.estadoCasilla(fa,ca)) { //Si la casilla esta vacia, crea la célula en la posición (fa,ca)
				superficie.crearCelula(fa, ca, new CelulaSimple());
				cont++;
			}
		}
			cont = 0;
			
		while (cont < numCelulasComplejas) {  //Mientras el contador sea menor que el numero de celulas dado por la constante, sigue creando células en posiciones aleatorias del tablero.
			fa = (int) (Math.random() * this.numFilas);
			ca = (int) (Math.random() * this.numColumnas);
			if (superficie.estadoCasilla(fa,ca)) { //Si la casilla esta vacia, crea la célula en la posición (fa,ca)
				superficie.crearCelula(fa, ca, new CelulaCompleja());
				cont++;
				
			}
		}
	}
	
	@Override
	public void guardar(String nombFich) {
		// TODO Auto-generated method stub
		FileWriter fichero;
		try {
			fichero = new FileWriter(nombFich);
			fichero.write("complejo" + '\r' + '\n'+ this.numFilas + '\r' + '\n' + this.numColumnas + '\r' + '\n');
			superficie.guardar(fichero);
			fichero.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void crearCelula(int fila, int columna) throws IndicesFueraDeRango {
		// TODO Auto-generated method stub
		if(fila < this.numFilas && fila > -1 && columna < this.numColumnas && columna > -1){
			String str;
			System.out.println("De que tipo: Compleja (1) o Simple (2):");
			@SuppressWarnings("resource")
			Scanner scanner= new Scanner(System.in);
			str = scanner.nextLine();
			
			while( !str.equals("1") && !str.equals("2")){
				System.out.println("ERROR: valor incorrecto." + '\n' + "De que tipo: Compleja (1) o Simple (2):");
				str = scanner.nextLine();
			}
			
			if(str.equals("1")){
				superficie.crearCelula(fila, columna, new CelulaCompleja());
			}
			else if(str.equals("2")){		
				superficie.crearCelula(fila, columna, new CelulaSimple());
			}		
		}
		else{
			throw new IndicesFueraDeRango();
		}		
	}
	
}
