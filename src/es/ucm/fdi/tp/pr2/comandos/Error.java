package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;

public class Error extends Comando{

	@Override
	public void ejecuta(Controlador controlador) {
		controlador.error();	
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		
		return new Error();
	}

	@Override
	public String textoAyuda() {
		
		String string = "ERROR: Comando no valido.";
		
		return string;
	}

}
