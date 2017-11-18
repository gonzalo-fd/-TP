package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;

public class Paso extends Comando {

	@Override
	public void ejecuta(Controlador controlador) {
		controlador.daUnPaso();
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		Comando comando = null;
		
		if (cadenaComando.length == 1 && cadenaComando[0].toUpperCase().equals("PASO") ) {
			comando = new Paso();
		}
		return comando;
	}

	public String textoAyuda() {
		
		String string = "PASO: realiza un paso en la simulacion.";
		
		return string;
	}

}
