package es.ucm.fdi.tp.practica5.common;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


@SuppressWarnings("serial")
public class BoardComponent extends JComponent implements GameObserver {
	/**
	 * ATRIBUTOS
	 */
	private Board boarda; 
	private int _CELL_HEIGHT = 50; 
	private int _CELL_WIDTH = 50;
	private int rows;
	private int cols;
	private List<Piece> pieces;
	public enum Shapes {CIRCLE, RECTANGLE};
	private HashMap<Piece, Color> pieceColors = new HashMap<Piece, Color>();
	private Iterator<Color> colorsIter = Utils.colorsGenerator();

	/**
	 * Constructor de BoardComponent
	 * @param game recibe un game
	 */
	public BoardComponent(final Observable<GameObserver> game) {
		initGUI();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				game.addObserver(BoardComponent.this); 
			}
		});
	}
	
	
	/**
	 * Metodo que sirve para identificar las funciones del rator sobre el teclado
	 */
	private void initGUI() {

		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				//System.out.println("Mouse Released: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				//System.out.println("Mouse Pressed: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//System.out.println("Mouse Exited Component: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//System.out.println("Mouse Entered Component: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("Mouse Clicked:: " + "(" + e.getX() + "," + e.getY() + ")");
				
				
				int col = (e.getX() / _CELL_WIDTH);
				int row = (e.getY() / _CELL_HEIGHT);
				int mb = 0;
				if (SwingUtilities.isLeftMouseButton(e)) 
					mb = 1;
				else if (SwingUtilities.isMiddleMouseButton(e)) 
					mb = 2;
				else if (SwingUtilities.isRightMouseButton(e)) 
					mb = 3;
				if (mb == 0)
					return; 
				BoardComponent.this.mouseClicked(row, col, e.getClickCount(), mb);
			}
			
		});
		
		this.setPreferredSize(new Dimension(rows * _CELL_HEIGHT + 20 * cols, cols * _CELL_WIDTH));

		repaint();
		
	}
	
	
	 /**
	  * Metodo que llama a paintComponent, contour y repaint
	  */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		contour(g); 
		repaint();
	}
	
	/**
	 * Metodo que pinta el tablero y llama a drawcell por cada casilla que tiene dicho tablero
	 * @param g recibe un parametro graphics
	 */
	private void contour(Graphics g){
		if(boarda != null){
		int cols = boarda.getCols();
		int rows = boarda.getRows();
		
		_CELL_WIDTH  = this.getWidth()  / cols;
		_CELL_HEIGHT = this.getHeight() / rows;

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				drawCell(i, j, g);
		}
	}
	
	/**
	 * Metodo para conseguir el color de las piezas
	 * @param p recibe una pieza
	 * @return devuelve el color de la pieza
	 */
	protected Color getDefaultColor(Piece p) {
		Color c = pieceColors.get(p);

        if (c == null) {
            pieceColors.put(p, colorsIter.next());
        }

        return c;
	}
	
	/**
	 * Metodo que se utiliza para dibujar el contenido de una casilla del tablero
	 * @param row recibe la fila de la casilla
	 * @param col recibe la columna de la casilla
	 * @param g recibe un graphics
	 */
	private void drawCell(int row, int col, Graphics g) {

		int x = col * _CELL_WIDTH;
        int y = row * _CELL_HEIGHT;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);

        Piece p = boarda.getPosition(row, col);
        if (p != null) {
			Color c = getDefaultColor(p);
			Shapes s = getPieceShape(p);
			
			g.setColor(c);
			switch(s) {
			
			case CIRCLE:
				g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);

				g.setColor(Color.black);
				g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
				break;
			case RECTANGLE:
				g.setColor(Color.black);
				g.fillRect(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
				g.drawRect(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
				break;
			default:
				break;
			}
		}

	}
	
	/**
	 * Metodo que se encarga de saber la forma del interior de las casillas
	 * @param p recibe una pieza
	 * @return devuelve CIRCLE si es un jugador o RECTANGLE si es un obstaculo 
	 */
	protected Shapes getPieceShape(Piece p) {
		if(player(p)){
			return Shapes.CIRCLE;
		}
		else if(!player(p)) {
			return Shapes.RECTANGLE;
		}
		else{
			return null;
		}	
	}
	
	/**
	 * Metodo que nos indica si la pieza p es un jugador
	 * @param p pieza del jugador
	 * @return devuelce true si es un jugador
	 */
	private boolean player(Piece p){
		
		for (int i = 0; i < this.pieces.size(); i++) {
			if(pieces.get(i).equals(p) ){
				return true;
			}
		}	
		return false;	
	}
	
	/**
	 * Método para detectar la fila y la columna clickeadas en el tablero asi como el boton el ratón pulsado y la cantidad de clicks.
	 * @param row fila del tablero.
	 * @param col columna del tablero.
	 * @param clickCount contador de clicks.
	 * @param mouseButton botn del ratón pulsado.
	 */
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
	}

	/** GAME OBSERVER CALLBACKS **/
	/**
	 * Metodo para redibujar los cambios en el tablero
	 * @param board2 recibe un tablero
	 */
	private void changeOnBoard(Board board2) {
		this.boarda = board2; 
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                repaint(); 
            }
        });
	}
	
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		changeOnBoard(board);
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		changeOnBoard(board);
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {	
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {		
		changeOnBoard(board);

	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		changeOnBoard(board);
	}

	@Override
	public void onError(String msg) {
	}

}
