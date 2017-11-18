package es.ucm.fdi.tp.practica5.ttt;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.common.FiniteRectBoardSwingView;

public class TicTacToeSwingView extends FiniteRectBoardSwingView{

	
	private TicTacToeSwingPlayer player;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TicTacToeSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randomPlayer,
			Player autoPlayer) {
		// TODO Auto-generated constructor stub
		super(g,c,localPiece,randomPlayer,autoPlayer);
	}

	@Override
	protected void handleMouseClick(int row, int col, int clickCount,
			int mouseButton) {
		// TODO Auto-generated method stub
		player = new TicTacToeSwingPlayer(row, col);
		super.decideMakeManualMove(player);
	}

	@Override
	protected void activateBoard() {
		// TODO Auto-generated method stub
		inPlay = true;
		
	}

	@Override
	protected void deActivateBoard() {
		// TODO Auto-generated method stub
		inPlay = false;
	}

}
