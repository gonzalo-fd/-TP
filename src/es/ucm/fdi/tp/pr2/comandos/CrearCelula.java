package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;
import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;

public class CrearCelula extends Comando {
	
	/**
	 * ATRIBUTOS
	 */
	private int fila;
	private int columna;
	
	/**
	 * CONSTRUCTOR
	 * @param fila fila donde se va a crear la celula
	 * @param columna columna donde se va a crear la celula
	 */
	public CrearCelula(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}
	@Override
	public void ejecuta(Controlador controlador) {
		// TODO Auto-generated method stub
		controlador.crearCelula(this.fila, this.columna);
	}

	@Override
	public Comando parsea(String[] cadenaComando) throws FormatoNumericoIncorrecto {
		// TODO Auto-generated method stub
		Comando comando = null;
		
		if (cadenaComando.length == 3 && cadenaComando[0].toUpperCase().equals("CREARCELULA") ) {	
			
			if(esNumero(cadenaComando[1]) && esNumero(cadenaComando[2])){
				int fila = Integer.parseInt(cadenaComando[1]);
				int columna = Integer.parseInt(cadenaComando[2]);
				comando = new CrearCelula(fila,columna);
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
		String string = "CREARCELULA F C: crea una nueva celula simple o compleja en la posicion (f,c) si es posible.";
		
		return string;
	}

}
