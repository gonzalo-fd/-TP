package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;
import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;

public class EliminarCelula extends Comando {
	/**
	 * ATRIBUTOS
	 */
	private int fila;
	private int columna;
	
	/**
	 * CONSTRUCTOR
	 * @param fila fila donde se va a eliminar la celula
	 * @param columna columna donde se va a eliminar la celula
	 */
	public EliminarCelula(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}

	@Override
	public void ejecuta(Controlador controlador) {
		controlador.eliminarCelula(fila, columna);

	}

	@Override
	public Comando parsea(String[] cadenaComando) throws FormatoNumericoIncorrecto {
		Comando comando = null;
		
		if (cadenaComando.length == 3 && cadenaComando[0].toUpperCase().equals("ELIMINARCELULA") ) {
			if(esNumero(cadenaComando[1]) && esNumero(cadenaComando[2])){
				int fila = Integer.parseInt(cadenaComando[1]);
				int columna = Integer.parseInt(cadenaComando[2]);
				comando = new EliminarCelula(fila,columna);
			}else{
				throw new FormatoNumericoIncorrecto();
				
			}
		}						
			
		return comando;
	}

	@Override
	public String textoAyuda() {
		String string = "ELIMINARCELULA F C: elimina una celula en la posicion (f,c) si es posible.";
		
		return string;
	}

}
