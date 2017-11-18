package es.ucm.fdi.tp.pr1.logica;

import es.ucm.fdi.tp.pr1.logica.Superficie;

public class Mundo {
	public static final int FILAS = 3;
	public static final int COLUMNAS = 4;
	private Superficie superficie;

	public Mundo() {
		this.superficie = new Superficie(FILAS, COLUMNAS);
	}

	public void evoluciona() {
		Celula celula = new Celula();

		for (int i = 0; i < FILAS; i++) {
			for (int j = 0; j < COLUMNAS; j++) {
				if (!superficie.estadoCasilla(i, j)) {
					celula = superficie.getCelula(i, j);
					paso(i, j, celula);

				}
			}

		}
		for (int i = 0; i < FILAS; i++) {
			for (int j = 0; j < COLUMNAS; j++) {
				if (!superficie.estadoCasilla(i, j)) {
					celula = superficie.getCelula(i, j);
					celula.setMovimiento(true);
				}
			}
		}
	}

	public void paso(int f, int c, Celula celula) {
		int fa, ca;

		if (celula.getMovimiento()) {
			if (superficie.alrededorCelula(f, c) != 0) {
				do {
					fa = (int) (Math.random() * FILAS);
					ca = (int) (Math.random() * COLUMNAS);
				} while (!(superficie.estadoCasilla(fa, ca)
						&& Math.max(f - 1, 0) <= fa
						&& fa <= Math.min(f + 1, FILAS - 1)
						&& Math.max(c - 1, 0) <= ca && ca <= Math.min(c + 1,
						COLUMNAS - 1)));

				superficie.moverCelula(fa, ca, f, c);
				eliminarCelula(f, c);
				System.out.println("Movimiento de (" + f + ',' + c + " a " + fa + ',' + ca + ')');
				if (celula.getnumReproduccion() == 0) {
					celula.setnumReproduccion(celula.PASOS_REPRODUCCION);
					crearCelula(f, c);
					System.out.println("Nace nueva celula en (" + f + ',' + c + ")cuyo padre ha sido (" + fa + ',' + ca + ')');
				} else {
					celula.setnumReproduccion(celula.getnumReproduccion() - 1);
				}

			} else {
				if (celula.getnumReproduccion() == 0) {
					eliminarCelula(f, c);
					System.out.println("Muere la celula de la casilla (" + f + ',' + c + ") por no poder reproducirse.");
				}
				else if(celula.getnumPasos() == 0){
					eliminarCelula(f, c);
					System.out.println("Muere la celula de la casilla (" + f + ',' + c + ") por inactividad.");
				}
				else if (celula.getnumPasos() != 0) {
					celula.setnumPasos(celula.getnumPasos() - 1);
				}
			}

		}
	}

	public void moverCelula(int f, int c) {
		int fa, ca;
		do {
			fa = (int) (Math.random() * FILAS);
			ca = (int) (Math.random() * COLUMNAS);
		} while (!(superficie.estadoCasilla(fa, ca) && Math.max(f - 1, 0) <= fa
				&& fa <= Math.min(f + 1, FILAS - 1) && Math.max(c - 1, 0) <= ca && ca <= Math
				.min(c + 1, COLUMNAS - 1)));
		superficie.moverCelula(fa, ca, f, c);
		eliminarCelula(f, c);
	}

	public void crearCelula(int f, int c) {
		superficie.crearCelula(f, c);
	}

	public void eliminarCelula(int f, int c) {
		superficie.eliminarCelula(f, c);
	}

	public void iniciar() {
		superficie.iniciarSuperficie(FILAS, COLUMNAS);
	}

	public void vaciar() {
		superficie.vaciarSuperficie();
	}

	public boolean estadoCasilla(int f, int c) {
		return superficie.estadoCasilla(f, c);
	}

	public void dibujar() {
		System.out.println(superficie.toString());
	}
}
