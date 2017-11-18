package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;
import es.ucm.fdi.tp.pr1.control.ErrorDeInicializacion;
import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;
import es.ucm.fdi.tp.pr1.control.IndicesFueraDeRango;
import es.ucm.fdi.tp.pr1.logica.Mundo;
import es.ucm.fdi.tp.pr1.logica.MundoComplejo;
import es.ucm.fdi.tp.pr1.logica.MundoSimple;

public class Jugar extends Comando {
	/**
	 * ATRIBUTOS
	 */
	Mundo mundo;
	
	/**
	 * CONSTRUCTOR MUNDO SIMPLE
	 * @param filas filas que va a tener la superficie de mundo simple
	 * @param columnas columnas que va a tener la superficie de mundo simple
	 * @param celulas numero de celulas simple que va a tener el mundo
	 * @throws IndicesFueraDeRango 
	 */
	public Jugar(int filas, int columnas, int celulas) throws IndicesFueraDeRango{
		this.mundo = new MundoSimple(filas, columnas, celulas);

	}
	
	/**
	 * CONSTRUCTOR MUNDO COMPLEJO
	 * @param filas filas que va a tener la superficie del mundo complejo
	 * @param columnas columnas que va a tener la superficie del mundo complejo
	 * @param celulas numero de celulas simples que va a tener la superficie del mundo complejo
	 * @param celulasComplejas numero de celulas complejas que va a tener la superficie del mundo complejo
	 */
	public Jugar(int filas, int columnas, int celulas, int celulasComplejas){
		this.mundo = new MundoComplejo(filas, columnas, celulas, celulasComplejas);

	}
	@Override
	public void ejecuta(Controlador controlador) throws IndicesFueraDeRango {
		// TODO Auto-generated method stub
		controlador.jugar(mundo);
	}

	@Override
	public Comando parsea(String[] cadenaComando) throws ErrorDeInicializacion, FormatoNumericoIncorrecto, IndicesFueraDeRango {
		// TODO Auto-generated method stub
		Comando comando = null;
		
		if (cadenaComando.length == 4 && cadenaComando[0].toUpperCase().equals("JUGARSIMPLE") ) {
			if(esNumero(cadenaComando[1]) && esNumero(cadenaComando[2]) && esNumero(cadenaComando[3])){
				int fila = Integer.parseInt(cadenaComando[1]);     
				int columna = Integer.parseInt(cadenaComando[2]);
				int celulas = Integer.parseInt(cadenaComando[3]);
				if(fila*columna >= celulas){
					comando = new Jugar(fila, columna, celulas);
				}
				else{
					throw new ErrorDeInicializacion();
				}
			}
			else{
				throw new FormatoNumericoIncorrecto();
			}
			
		}
		else if(cadenaComando.length == 5 && cadenaComando[0].toUpperCase().equals("JUGARCOMPLEJO")){
			if(esNumero(cadenaComando[1]) && esNumero(cadenaComando[2]) && esNumero(cadenaComando[3])  && esNumero(cadenaComando[4])){
				int fila = Integer.parseInt(cadenaComando[1]);     
				int columna = Integer.parseInt(cadenaComando[2]);
				int celulas = Integer.parseInt(cadenaComando[3]);
				int celulasComplejas = Integer.parseInt(cadenaComando[4]);
				if(fila*columna >= (celulas + celulasComplejas)){
				comando = new Jugar(fila, columna, celulas, celulasComplejas);
				}
				else{
					throw new ErrorDeInicializacion();
				}
			}
			else{
				throw new FormatoNumericoIncorrecto();
			}
		}
		return comando;
	}

	@Override
	public String textoAyuda() {
		// TODO Auto-generated method stub
		
		String string = "JUGAR: Cambia entre modo de juego simple y complejo.";
		
		return string;
		
	}

}
