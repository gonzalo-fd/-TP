package es.ucm.fdi.tp.pr1.logica;

public class Superficie {
	private static final int NUM_CELULAS = 6;
	// atributos
	private int filas;
	private int columnas;
	private Celula[][] superficie;

	// metodos
	public Superficie(int f, int c) {// ?

		this.filas = f;
		this.columnas = c;
		this.superficie = new Celula[filas][columnas];
		vaciarSuperficie();
	}

//	public boolean crearCelula(int f, int c) {
//		boolean ok = false;
//
//		if (estadoCasilla(f, c)) {
//			superficie[f][c] = new Celula();
//			ok = true;
//		}
//		return ok;
//	}
	
	public void crearCelula(int f, int c){
		superficie[f][c] = new Celula();
	}
	
	public void eliminarCelula(int f, int c){
		superficie[f][c] = null;
	}

//	public boolean eliminarCelula(int f, int c) {
//		boolean ok = false;
//
//		if (!estadoCasilla(f, c)) {
//			superficie[f][c] = null;
//			ok = true;
//		}
//		return ok;
//	}

	public void iniciarSuperficie(int f, int c) {
		int cont = 0, fa, ca;
		vaciarSuperficie();
		while (cont < NUM_CELULAS) {
			fa = (int) (Math.random() * f);
			ca = (int) (Math.random() * c);
			if (superficie[fa][ca] == null) {
				crearCelula(fa, ca);
				cont++;
			}
		}
	}

	public void vaciarSuperficie() {// ?

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				superficie[i][j] = null;
			}
		}
	}

	public boolean estadoCasilla(int f, int c) {
		boolean vacia = false;

		if (superficie[f][c] == null) {
			vacia = true;
		}
		return vacia;
	}

	public int alrededorCelula(int f, int c) {

		int cont = 0;

		for (int i = Math.max(f - 1, 0); i <= Math.min(f + 1, filas - 1); i++) {
			for (int j = Math.max(c - 1, 0); j <= Math.min(c + 1, columnas - 1); j++) {
				if (estadoCasilla(i, j)) {
					cont++;
				}
			}

		}
		return cont;
	}
	
	public String toString() {
        String cadena = "";
         
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (estadoCasilla(i, j)){
                    cadena += " - " ;
                }
                else
                    cadena += superficie[i][j].getnumPasos() + "-" + superficie[i][j].getnumReproduccion();
                cadena += "  ";
            }
          cadena += '\n';
        }
         
        return cadena;
    }
	
	public void moverCelula(int fa, int ca , int f, int c){
		superficie[fa][ca] = superficie[f][c];
		superficie[fa][ca].setMovimiento(false);
	}
	
	public Celula getCelula(int f , int c){
		return superficie[f][c];
	}
}
