package es.ucm.fdi.tp.practica5.ttt;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

public class TicTacToeSwingPlayer extends Player{

	
	private int row;
	private int col;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TicTacToeSwingPlayer(int row, int col) {
		// TODO Auto-generated constructor stub
		this.row = row;
		this.col = col;
	}
	
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces,
			GameRules rules) {
		// TODO Auto-generated method stub
		return  new ConnectNMove(row, col, p);
	}
}
