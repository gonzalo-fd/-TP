package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;
import es.ucm.fdi.tp.pr1.control.IndicesFueraDeRango;

public class Iniciar extends Comando {

	@Override
	public void ejecuta(Controlador controlador) throws IndicesFueraDeRango {
		controlador.iniciar();
		
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		Comando comando = null;
		
		if (cadenaComando.length == 1 && cadenaComando[0].toUpperCase().equals("INICIAR") ) {
			comando = new Iniciar();
		}
		return comando;
	}

	@Override
	public String textoAyuda() {
		String string = "INICIAR: inicia la nueva simulacion.";
		
		return string;
	}

}
