package es.ucm.fdi.tp.practica5.attt;

import java.util.List;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTMove;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AdvancedTTTSwingPlayer extends Player{
	
	private int rowOrig;
	private int colOrig;
	private int rowDest;
	private int colDest;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdvancedTTTSwingPlayer(int rowOrig, int colOrig, int rowDest, int colDest) {
		// TODO Auto-generated constructor stub
		super();
		this.rowOrig = rowOrig;
		this.colOrig = colOrig;
		this.rowDest = rowDest;
		this.colDest = colDest;
	}
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		// TODO Auto-generated method stub
		return  new AdvancedTTTMove(rowOrig, colOrig, rowDest, colDest, p);
	}

}
