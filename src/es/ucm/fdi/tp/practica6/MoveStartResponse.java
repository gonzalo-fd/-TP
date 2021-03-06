package es.ucm.fdi.tp.practica6;



import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
@SuppressWarnings("serial")
public class MoveStartResponse implements Response {
	
	private Board board;
	private Piece turn;

	public MoveStartResponse(Board board, Piece turn) {
		this.board = board;
		this.turn = turn;
	}
	
	public void run(GameObserver o) {
		o.onMoveStart(board, turn);
	}


}
