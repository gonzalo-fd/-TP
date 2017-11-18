package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;

public class Guardar extends Comando{
	/**
	 * ATRIBUTOS
	 */
	private String nombFich;
	
	/**
	 * CONSTRUCTOR
	 * @param nombFich fichero donde se van a guardar los datos
	 */
	public Guardar(String nombFich){
		this.nombFich = nombFich;
	}
	
	@Override
	public void ejecuta(Controlador controlador) {
		// TODO Auto-generated method stub
		controlador.guardar(nombFich);
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		// TODO Auto-generated method stub
		Comando comando = null;
		
		if (cadenaComando.length == 2 && cadenaComando[0].toUpperCase().equals("GUARDAR") ) {
			
			String nombFich = (cadenaComando[1]);		
			comando = new Guardar(nombFich);		
		}
		return comando;
	}

	@Override
	public String textoAyuda() {
		// TODO Auto-generated method stub
		String string = "GUARDA: Guarda la partida actual.";
		
		return string;
	}

}
