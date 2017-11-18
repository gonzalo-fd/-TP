package es.ucm.fdi.tp.pr2.comandos;

import es.ucm.fdi.tp.pr1.control.ErrorDeInicializacion;
import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;
import es.ucm.fdi.tp.pr1.control.IndicesFueraDeRango;

public class ParserComandos {
	/**
	 * ARRAY DE CLASES
	 */
	public static Comando[] comando = {
		new Ayuda(),
		new Iniciar(),
		new Vaciar(),
		new CrearCelula(0,0),
		new EliminarCelula(0,0),
		new Paso(),
		new Salir(),
		new Jugar(0,0,0,0),
		new Cargar(""),
		new Guardar(""),
		new Error()
		
	};
	
	/**
	 * Metodo que recoge la cadena del textoAyuda de cada clase y lo junta todo en un atributo
	 * @return devuelve la cadena formada
	 */
	static public String AyudaComandos(){
		
		String cadena = "";
		
		for (int i = 0; i < comando.length; i++) {
			cadena += comando[i].textoAyuda() + '\n';
		}	
		return cadena;
		
	}
	
	/**
	 * Metodo que llama al parseaComando de cada clase para saber a que comando esta llamando y si esta bien escrito
	 * @param cadenas comando escrito por el usuario
	 * @return devuelve el comando pedido
	 * @throws FormatoNumericoIncorrecto  excepcion que indica si estas metiendo otra cosa que no sea un numero
	 * @throws ErrorDeInicializacion excepcion que inidica que el numero de celulas excede el numero de casillas
	 * @throws IndicesFueraDeRango 
	 */
	static public Comando parseaComando(String[ ] cadenas) throws ErrorDeInicializacion, FormatoNumericoIncorrecto, IndicesFueraDeRango{
		Comando comand = null;
		int contador = 0;
		
		while (comand == null) {
		comand = comando[contador].parsea(cadenas);
		contador++;
			
		}
		if(comand.equals(null)){
			comand = new Error();
		}
		return comand;
		
	}


}
