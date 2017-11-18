package es.ucm.fdi.tp.practica5.common;

import java.awt.Color;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.common.BoardComponent.Shapes;


@SuppressWarnings("serial")
public abstract class FiniteRectBoardSwingView extends SwingView {
	/**
	 * ATRIBUTOS
	 */
	protected BoardComponent boardComp;
	
	/**
	 * CONSTRUCTOR
	 */
	public FiniteRectBoardSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randomPlayer,
			Player autoPlayer) {
		
		super(g, c, localPiece, randomPlayer, autoPlayer);
		
	}

	@Override
	protected void initBoardGui() {
		boardComp = new BoardComponent(game){
			@Override
			protected Color getDefaultColor(Piece p) {
				// TODO Auto-generated method stub	
				return FiniteRectBoardSwingView.this.getDefaultColor(p);
			}

			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mb) {
				// TODO Auto-generated method stub
				handleMouseClick(row, col, clickCount, mb);
			}
		
			protected Shapes getPieceShape(Piece p){
				return FiniteRectBoardSwingView.this.getShape(p);
			}
		};
		setBoardArea(boardComp); // install the board in the view
		
	}
	
	/**
	 * Metodo para saber si p es un player o un obstaculo
	 * @param p recive una pieza
	 * @return devuelve un CIRCLE o un RECTANGLE
	 */
	protected Shapes getShape(Piece p){
			
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
	 * Metodo para saber si p es un player
	 * @param p recive un pieza
	 * @return devuelve true si es un player
	 */
	private boolean player(Piece p){
			if(this.pieces != null){
			for (int i = 0; i < this.pieces.size(); i++) {
				if(pieces.get(i).equals(p) ){
					return true;
				}
			}
			}
			return false;	
		}
	
	
	@Override
	protected void redraw() {
		// TODO Auto-generated method stub
		boardComp.repaint();
	}
	/**
	 * Método para detectar la fila y la columna clickeadas en el tablero asi como el boton el ratón pulsado y la cantidad de clicks.
	 * @param row fila del tablero.
	 * @param col columna del tablero.
	 * @param clickCount contador de clicks.
	 * @param mouseButton botn del ratón pulsado.
	 */
	protected abstract void handleMouseClick(int row, int col, int clickCount, int mouseButton);

}
