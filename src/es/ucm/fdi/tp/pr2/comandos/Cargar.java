package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.Controlador;
import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;
import es.ucm.fdi.tp.pr1.control.IndicesFueraDeRango;

public class Cargar extends Comando{
	/**
	 * ATRIBUTOS
	 */
	private String nombFich;
	
	/**
	 * CONSTRUCTOR
	 * @param nombFich fichero del cual se va a cargar la informacion
	 */
	public Cargar(String nombFich){
		this.nombFich = nombFich;
	}
	
	@Override
	public void ejecuta(Controlador controlador) throws FormatoNumericoIncorrecto, IndicesFueraDeRango {
		// TODO Auto-generated method stub
		controlador.cargar(nombFich);
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		// TODO Auto-generated method stub
		Comando comando = null;
		
		if (cadenaComando.length == 2 && cadenaComando[0].toUpperCase().equals("CARGAR") ) {
			
			String nombFich = (cadenaComando[1]);		
			comando = new Cargar(nombFich);		
		}
		return comando;
	}

	@Override
	public String textoAyuda() {
		// TODO Auto-generated method stub		
		return "CARGAR: Carga una partida guardada.";
	}

}
