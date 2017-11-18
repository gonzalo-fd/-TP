package es.ucm.fdi.tp.pr1.control;

import java.util.Scanner;
import es.ucm.fdi.tp.pr1.logica.Superficie;
import es.ucm.fdi.tp.pr1.logica.Mundo;

public class Controlador {
	private Mundo mundo;
	private Scanner scanner;

	public Controlador(Mundo mundo, Scanner in) {
		mundo = new Mundo();
		this.mundo = mundo;
		scanner = in;
	}

	public void realizaSimulacion() {
		boolean salir = false;
		String str;
		String[] cmdArray;

		do {
			System.out.println("Comando<");
			str = scanner.nextLine();
			str = str.toUpperCase();
			cmdArray = str.split(" ");
			int longitud = cmdArray.length;

			if (longitud == 1 || longitud == 3) {
				switch (cmdArray[0]) {
				case "PASO": {
					mundo.evoluciona();
				}
					break;
				case "INICIAR": {
					mundo.iniciar();
				}
					break;
				case "AYUDA": {

				}
					break;
				case "VACIAR": {
					mundo.vaciar();
				}
					break;
				case "SALIR": {
					salir = true;
				}
					break;
				case "CREARCELULA": {
					int fila = Integer.parseInt(cmdArray[1]);
					int columna = Integer.parseInt(cmdArray[2]);
					while (fila >= mundo.FILAS || fila < 0 || columna >= mundo.COLUMNAS || columna < 0) {
						System.out.println("Introduzca el comando de nuevo con un valor de filas y columnas valido:");
						str = scanner.nextLine();
						str = str.toUpperCase();
						cmdArray = str.split(" ");
						fila = Integer.parseInt(cmdArray[1]);
						columna = Integer.parseInt(cmdArray[2]);
					}

					if (mundo.estadoCasilla(fila, columna)) {
						mundo.crearCelula(fila, columna);
					} else {
						System.out.println("Error, no se ha podido crear la celula.");
					}

				}
					break;
				case "ELIMINARCELULA": {
					int fila = Integer.parseInt(cmdArray[1]);
					int columna = Integer.parseInt(cmdArray[2]);
					while (fila >= mundo.FILAS || fila < 0 || columna >= mundo.COLUMNAS || columna < 0) {
						System.out.println("Introduzca el comando de nuevo con un valor de filas y columnas valido:");
						str = scanner.nextLine();
						str = str.toUpperCase();
						cmdArray = str.split(" ");
						fila = Integer.parseInt(cmdArray[1]);
						columna = Integer.parseInt(cmdArray[2]);
					}
					if (!mundo.estadoCasilla(fila, columna)) {
						mundo.eliminarCelula(fila, columna);
					} else {
						System.out.println("Error, no se ha podido eliminar la celula.");
					}

				}
					break;

				default: {
					System.out.println("Error, introduzca un comando valido.");
				}
				}
			} else {
				System.out.println("Error, introduzca un comando valido.");
			}
			mundo.dibujar();
		} while (salir == false);
	}
}
