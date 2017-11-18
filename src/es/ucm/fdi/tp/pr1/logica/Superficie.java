package es.ucm.fdi.tp.pr1.logica;

import java.io.*;

import es.ucm.fdi.tp.pr1.control.FormatoNumericoIncorrecto;
import es.ucm.fdi.tp.pr1.control.PalabraIncorrecta;
import es.ucm.fdi.tp.pr1.logica.Casilla;

public class Superficie {
	
	/**
	 * ATRIBUTOS
	 */
	private int filas;
	private int columnas;
	private Celula[][] superficie;
	private Casilla[] arrayCasilla;
	private boolean[][] arrayPosiciones;

	/**
	 * CONSTRUCTOR
	 * @param f Se le pasa una fila
	 * @param c Se le pasa una columna
	 */
	public Superficie(int f, int c) {

		this.filas = f;
		this.columnas = c;
		this.superficie = new Celula[filas][columnas];
		arrayCasilla = new Casilla[8];
		arrayPosiciones = new boolean[filas][columnas];
		vaciarSuperficie();
	}


	
	/**
	 * METODOS
	 */
	
	/**
	 * Metodo que recibe una fila y una columnas y crea una nueva celula en esa posicion.
	 * @param f fila para saber donde se crea la celula
	 * @param c columna para saber donde se crea la celula
	 */
	public void crearCelula(int f, int c, Celula celula){
		superficie[f][c] = celula;
	}
	
	/**
	 * Metodo que recibe una fila y una columna y elmina la celula en esa posicion.
	 * @param f fila para saber donde eliminar la celula
	 * @param c columna para saber donde eliminar la celula
	 */
	public void eliminarCelula(int f, int c){
		superficie[f][c] = null;
	}
	
	/**
	 * Metodo que reibe el numero de filas y columnas de la superficie y crea en posicones aleatorias el numero de Celulas simples y complejas que haya
	 * en las variables numCelulas y numCelulasComplejas
	 * @param f filas maximas de la superficie
	 * @param c columnas maximas de la superficie
	 * @param numCelulas numero de celulas simple que se van a crear en la superficie
	 * @param numCelulasComplejas numero de celulas complejas que se van a crear en la superficie
	 */
	public void iniciarSuperficie(int f, int c) {
		vaciarSuperficie();
		iniciarPosiciones(f, c);	
	}
	
	/**
	 * Metodo para iniciar las posiciones del tablero a false
	 * @param f Se le pasa el numero maximo de filas
	 * @param c Se le pasa el numero maximo de columnas
	 */
	public void iniciarPosiciones(int f, int c){
	
		for (int i = 0; i < f; i++) {
			for (int j = 0; j < c; j++) {
			arrayPosiciones[i][j] = false;			
			}
		}
	}
	
	/**
	 * Metodo que vacia la superficie 
	 */
	public void vaciarSuperficie() {// ?

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				superficie[i][j] = null;
			}
		}
	}
	
	/**
	 * Metodo que comprueba si una casilla esta vacia. Devuelve true si lo esta.
	 * @param f fila en la cual esta comprobando si esta vacia la casilla
	 * @param c columna en la cual esta comprobando si esta vacia la casilla
	 * @return devuelve true en el caso de que este vacia
	 */
	public boolean estadoCasilla(int f, int c) {
		boolean vacia = false;

		if (superficie[f][c] == null) {
			vacia = true;
		}
		return vacia;
	}
	
	/**
	 * Metodo que recibe una casilla y devuelve si la celula es comestible o no
	 * @param casilla casilla donde esta la celula
	 * @return devuelve si esComestible o no
	 */
	public boolean casillaCompleja (Casilla casilla){
		return this.superficie[casilla.getFila()][casilla.getColumna()].esComestible;
	}
	
	
	
	/**
	 *  Metodo que comprueba las posiciones libres que existen alrededor de una casilla dada. Devuelve el numero de casillas vacias.
	 * @param f fila en la que se encuentra la celula
	 * @param c columna en la que se encuentra la celula
	 * @return devuele el numero de casillas vacias que tiene a su alrededor
	 */
	 public int alrededorCelula(int f, int c) {
		int cont = 0;

		for (int i = Math.max(f - 1, 0); i <= Math.min(f + 1, filas - 1); i++) {		 //Bucle for para recorrer las casillas que estan alrededor
			for (int j = Math.max(c - 1, 0); j <= Math.min(c + 1, columnas - 1); j++) { // de la casilla dada.
				if (estadoCasilla(i, j)) {
					arrayCasilla[cont] = new Casilla(i, j);
					cont++;				
				}
			}

		}
		return cont;
	}
	
	 /**
	  * Metodo que busca la posicion aleatoria a la cual se va a mover la celulaCompleja
	  * @param f recive la fila donde esta la celulaCompleja
	  * @param c recive la columna donde esta la celulaCompleja
	  * @return devuelve la casilla a donde se va a mover la celulaCompleja
	  */
	public Casilla posicionAleatoriaCompleja(int f, int c){
		Casilla casilla = new Casilla(0,0);
		int fa, ca;
		
		do{
			fa = (int) (Math.random() * this.filas);
			ca = (int) (Math.random() * this.columnas);
			
		}while(fa == f && ca == c);
		
		casilla.setFila(fa);
		casilla.setColumna(ca);
		
		return casilla;	
	}
	
	/**
	 * Metodo para dibujar el tablero.
	 */
	public String toString() {
        String cadena = "";
         
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (estadoCasilla(i, j)){ 
                    cadena += " - " ;
                }
                else
                    cadena += superficie[i][j].toString(); 
                cadena += "  ";
            }
          cadena += '\n';
        }
         
        return cadena;
    }
	
	/**
	 * Metodo que mueve una celula de la posicion (f,c) a la posicion (fa,ca).
	 * @param fa fila aleatoria donde se va a mover la celula
	 * @param ca columna aleatoria donde se va a mover la celula
	 * @param f fila en la que estaba la celula
	 * @param c columna en la que estaba la celula
	 */
	public void moverCelula(int fa, int ca , int f, int c){
		superficie[fa][ca] = superficie[f][c];
		eliminarCelula(f,c);
		arrayPosiciones[fa][ca] = false; //Coloca movimiento a false para saber que ya se ha movido la celula.	
	}
	
	/**
	 * Metodo que llama el ejecutamovimiento
	 * @param f recive una fila
	 * @param c recive una columna
	 * @return devuelve una casilla
	 */
	public Casilla ejecutaMovimiento(int f, int c){
		return superficie[f][c].ejecutaMovimiento(f, c, this);
	}
	
	/**
	 * Metodo Get para saber el contenido del arrayPosiciones
	 * @param f recibe una fila para comprobar lo que hay 
	 * @param c recive una columna para comprobar lo que hay
	 * @return
	 */
	public boolean getArrayPosiciones(int f, int c) {
		
		return arrayPosiciones[f][c];
	}
	
	/**
	 * Metodo Set para modificar el arrayPosiciones
	 * @param f recive una fila 
	 * @param c recive una columna
	 * @param bool recive el booleano para cambiarlo 
	 */
	public void setArrayPosiciones(int f, int c, boolean bool) {
		arrayPosiciones[f][c] = bool;
	}
	
	/**
	 * Metodo get para saber las casillas vacias que hay alrededor
	 * @return devuelve el array de casillas vacias
	 */
	public Casilla[] getArrayCasilla() {
		return arrayCasilla;
	}
	
	/**
	 * Metodo que recibe un fichero y un BufferedReader y carga el tipo de celula, simple o compleja, y luego llamar al carga de cada una dependiendo
	 * del tipo de celula que haya leido.
	 * @param fichero fichero del cual esta cargando al informacio
	 * @param BR variable donde va almacenando linea tras linea la informacion del fichero
	 * @throws PalabraIncorrecta excepcion que indicia si hay una palabra mal escrita
	 * @throws FormatoNumericoIncorrecto excepcion que indica si donde deberia haber un numero, hay cualquier otra cosa
	 */
	public void cargar(Reader fichero, BufferedReader BR) throws PalabraIncorrecta, FormatoNumericoIncorrecto{
		
		try {
			String str;
			str = BR.readLine();
			do{	
				String[] cmdArray;
				cmdArray = str.split(" ");
				if(esNumero(cmdArray[0]) && esNumero(cmdArray[1]) && esNumero(cmdArray[3])){
					int fila = Integer.parseInt(cmdArray[0]);
					int	columna = Integer.parseInt(cmdArray[1]);
					
					if(cmdArray[2].equals("simple")){
						if(esNumero(cmdArray[4])){
						crearCelula(fila, columna, new CelulaSimple());
						superficie[fila][columna].cargar(cmdArray);
						}
						else{
							throw new FormatoNumericoIncorrecto();
						}
					}
					else if (cmdArray[2].equals("compleja")){
						
						crearCelula(fila, columna, new CelulaCompleja());
						superficie[fila][columna].cargar(cmdArray);
					}
					else{
						throw new PalabraIncorrecta();
					}
					str = BR.readLine();
				}
				else{
					throw new FormatoNumericoIncorrecto();
				}
			}while(str != null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que se encarga de guardar las posiciones de las celulas y llamar al guardar de celula(simple o compleja)
	 * @param fichero fichero donde guarda los datos
	 */
	public void guardar(FileWriter fichero){
		
		for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
            	
                if (!estadoCasilla(i, j)){ 
                    
                	try {
						fichero.write(Integer.toString(i) + ' ' + Integer.toString(j) + ' ');
						superficie[i][j].guardar(fichero);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
		}
	}
	
	/**
	 * Metodo para conseguir el numero de filas
	 * @return devuelve el numero de filas
	 */
	public int getFilas() {
		return filas;
	}


	/**
	 * Metodo para conseguir el numero de columnas
	 * @return devuelve el numero de columnas
	 */
	public int getColumnas() {
		return columnas;
	}
	
	/**
	 * Metodo que utilizamos para la excepcion que nos indicia si en vez de un numero, estamos metiendo cualquier otra cosa
	 * @param s parametro string que contiene el valor de lo que estamos cargando del fichero
	 * @return devuelve true si es un entero false si no lo es
	 */
	public boolean esNumero(String s){
		
		try{
			Integer.parseInt(s);
			return true;
			
		}catch(NumberFormatException fne){
			return false;
		}
		
	}
}
