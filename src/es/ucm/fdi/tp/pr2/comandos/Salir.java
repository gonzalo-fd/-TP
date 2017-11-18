package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;

public class Salir extends Comando {

	@Override
	public void ejecuta(Controlador controlador) {
		controlador.setSimulacionTerminada(true);
		System.out.println("Hasta luego.");
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		Comando comando = null;
		
		if (cadenaComando.length == 1 && cadenaComando[0].toUpperCase().equals("SALIR") ) {
			comando = new Salir();
		}
		return comando;
	}

	@Override
	public String textoAyuda() {
		String string = "SALIR: cierra la aplicacion.";
		
		return string;
	}
}
