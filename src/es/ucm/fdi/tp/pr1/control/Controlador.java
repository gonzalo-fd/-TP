package es.ucm.fdi.tp.pr1.control;

import java.io.*;
import java.util.Scanner;

import es.ucm.fdi.tp.pr2.comandos.Comando;
import es.ucm.fdi.tp.pr2.comandos.ParserComandos;
import es.ucm.fdi.tp.pr1.logica.Mundo;
import es.ucm.fdi.tp.pr1.logica.MundoComplejo;
import es.ucm.fdi.tp.pr1.logica.MundoSimple;

public class Controlador {
	/**
	 * ATRIBUTOS
	 */
	private Mundo mundo;
	private Scanner scanner;
	private boolean simulacionTerminada;
	
	/**
	 * CONSTRUCTOR
	 * @param in 
	 * @throws IndicesFueraDeRango 
	 */
	public Controlador(Scanner in) throws IndicesFueraDeRango {
		mundo = new MundoSimple(4,4,6);
		scanner = in;
		simulacionTerminada = false;
	}
	
	
	/**
	 * Metodo que pide el comando al usuario y en caso de que el comando sea correcto llama al ejecuta de dicho comando
	 * @throws ErrorDeInicializacion  excepcion que indica que el numero de celulas es mayor que el numero de casillas
	 * @throws FormatoNumericoIncorrecto  excepcion que indica que estas metiendo otra cosa que no sea un numero
	 * @throws IndicesFueraDeRango 
	 */
	public void realizaSimulacion() throws ErrorDeInicializacion, FormatoNumericoIncorrecto, IndicesFueraDeRango {
		String str;
		String[] cmdArray;
		mundo.inicializaMundo();
				
		while(!simulacionTerminada){
			mundo.dibujar();
			System.out.println("Comando<");
			str = scanner.nextLine();
			cmdArray = str.split(" "); 	
			
			try{
				Comando comando = ParserComandos.parseaComando(cmdArray);
				if (comando!=null){
					comando.ejecuta(this);	
				}
			}catch(ErrorDeInicializacion ei){
				
			}catch(FormatoNumericoIncorrecto fni){
				
			}		
		}
	}
	
	/**
	 * Metodo que llama al evoluciona de mundo
	 */
	public void daUnPaso(){
		mundo.evoluciona();
	}
	
	/**
	 * Metodo que llama al inicializaMundo de mundo simple o mundo complejo dependiendo del mundo
	 * @throws IndicesFueraDeRango 
	 */
	public void iniciar() throws IndicesFueraDeRango{
		mundo.inicializaMundo();
	}
	
	/**
	 * Metodo que llama al eliminarCelula de mundo
	 * @param f fila donde se va a eliminar la celula
	 * @param c columna donde se va a eliminar la celula
	 */
	public void eliminarCelula(int f, int c){
		try{
			mundo.eliminarCelula(f, c);
		}
		catch(IndicesFueraDeRango fr){
			
		}
	}
	
	/**
	 * Metodo que llama error de mundo
	 */
	public void error(){
		mundo.error();
	}
	
	/**
	 * Metodo que llama al vaciar de mundo
	 */
	public void vaciar(){
		mundo.vaciar();
	}
	
	/**
	 * Metodo que modfica el atributo simulacion terminada
	 * @param simulacionTerminada indica si la simulacion a acabado o no a voluntad del usuario
	 */
	public void setSimulacionTerminada(boolean simulacionTerminada) {
		this.simulacionTerminada = simulacionTerminada;
	}
	
	/**
	 * Metodo que llema a inicializaMundo y lo iguala a mundo
	 * @param mundo nuevo mundo donde se va a empezar a jugar
	 * @throws IndicesFueraDeRango 
	 */
	public void jugar(Mundo mundo) throws IndicesFueraDeRango{	
		mundo.inicializaMundo();
		this.mundo = mundo;
	}
	
	/**
	 * Metodo que llama al crearCelula de mundo
	 * @param f fila donde se va a crear la celula
	 * @param c columna donde se va a crear la celula
	 */
	public void crearCelula(int f, int c){
		
		try{
			mundo.crearCelula(f, c);
		}
		catch(IndicesFueraDeRango fr){
			
		}
		
	}
	
	/**
	 * Metodo que carga un juego y dependiendo de si es simple o complejo , creara un mundo diferente
	 * @param nombFich fichero de donde va a cargar el tipo de mundo
	 * @throws FormatoNumericoIncorrecto excepcion que nos indica si en vez de introducir un numero, introducimos cualquier otra cosa
	 * @throws IndicesFueraDeRango 
	 */
	public void cargar(String nombFich) throws FormatoNumericoIncorrecto, IndicesFueraDeRango{
		try {
			Reader fichero;
			fichero = new FileReader(nombFich);
			BufferedReader BR = new BufferedReader(fichero);
			String str = BR.readLine();
			
			if (str.equals("simple")){
				
				this.mundo = new MundoSimple(0,0,0);
				mundo.cargar(fichero, BR);
			}
			
			else if ((str.equals("complejo"))){
				this.mundo = new MundoComplejo(0,0,0,0);
				mundo.cargar(fichero, BR);
			}
			else{
				BR.close();
				throw new PalabraIncorrecta();			
			}
			
			BR.close();
			fichero.close();
		} catch (FileNotFoundException e) { 
			// TODO Auto-generated catch block
			System.out.println("Archivo no encontrado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(PalabraIncorrecta pi){
			
		}
	
	}
	
	/**
	 * Metodo que llama al guardar de mundo
	 * @param nombFich fichero donde se va a guardar el mundo
	 */
	public void guardar(String nombFich){
		mundo.guardar(nombFich);
	}
	

			
}
