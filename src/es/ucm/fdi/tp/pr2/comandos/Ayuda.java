package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;

public class Ayuda extends Comando {

	@Override
	/**
	 * Ejecuta el AyudaComandos
	 */
	public void ejecuta(Controlador controlador) {
		
		System.out.println(ParserComandos.AyudaComandos());

	}

	@Override
	/**
	 * Comprueba si es una array de tamañp uno y si la palabra esta bien escrita
	 */
	public Comando parsea(String[] cadenaComando) {
		Comando comando = null;
	
		if (cadenaComando.length == 1 && cadenaComando[0].toUpperCase().equals("AYUDA") ) {
			comando = new Ayuda();
		}
		return comando;
	}

	public String textoAyuda() {
		String string = "AYUDA: muestra esta ayuda.";
		
		return string;
	}

}
