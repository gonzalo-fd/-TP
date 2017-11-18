package es.ucm.fdi.tp.practica5.damas;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tpPractica4.damas.DamasMove;

public class DamasSwingPlayer extends Player {

	private static final long serialVersionUID = 1L;
	private int rowOrig;
	private int colOrig;
	private int rowDest;
	private int colDest;

	public DamasSwingPlayer(int rowOrig, int colOrig, int rowDest, int colDest) {
		super();
		this.rowOrig = rowOrig;
		this.colOrig = colOrig;
		this.rowDest = rowDest;
		this.colDest = colDest;
	}
	
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces,
			GameRules rules) {
		return new DamasMove(rowOrig,colOrig,rowDest,colDest, p);
	}

	
}
