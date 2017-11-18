package es.ucm.fdi.tp.pr1.logica;

public class Celula {
	public static final int MAX_PASOS_SIN_MOVER = 1;
	public static final int PASOS_REPRODUCCION = 2;
	private int numPasos;
	private int numReproduccion;
	private boolean movimiento;

	public Celula() {
		numPasos = MAX_PASOS_SIN_MOVER;
		numReproduccion = PASOS_REPRODUCCION;
		movimiento = true;
	}

	public int getnumPasos() {
		return numPasos;
	}

	public void setnumPasos(int numPasos) {
		this.numPasos = numPasos;
	}

	public int getnumReproduccion() {
		return numReproduccion;
	}

	public void setnumReproduccion(int numReproduccion) {
		this.numReproduccion = numReproduccion;
	}
	public boolean getMovimiento(){
		return movimiento;
		
	}
	public boolean setMovimiento(boolean cambio){
		this.movimiento = cambio;
		return movimiento;
	}	
}
