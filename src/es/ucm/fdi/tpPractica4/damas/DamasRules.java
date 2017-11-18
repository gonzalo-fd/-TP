package es.ucm.fdi.tpPractica4.damas;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.Practica4.ataxx.AtaxxMove;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class DamasRules implements GameRules {

	protected final Pair<State, Piece> gameInPlayResult = new Pair<State, Piece>(State.InPlay, null);
	private int dim;
	
	public DamasRules(){
		this.dim = 10;
	}
	
	@Override
	public String gameDesc() {
		return "Damas " + dim + "x" + dim;
	}

	@Override
	public Board createBoard(List<Piece> pieces) {
		FiniteRectBoard board = new FiniteRectBoard(dim, dim);
		
		for (int i = 0; i < dim; i += 2) {
			for (int j = 0; j < 4; j++) {
				if(j % 2 != 0){
				board.setPosition(j, i, pieces.get(0));
				}
				else{
					board.setPosition(j, i+ 1, pieces.get(0));
				}
			}
		}
			
		for (int i = 0; i < dim; i += 2) {
			for (int j = 6; j < 10; j++) {
				if(j % 2 == 0){
					board.setPosition(j, i+ 1, pieces.get(1));
					}
				else{
					board.setPosition(j, i, pieces.get(1));
				}
			}
		}
		board.setPieceCount(pieces.get(0), 20);
		board.setPieceCount(pieces.get(1), 20);
		return board;
	}

	@Override
	public Piece initialPlayer(Board board, List<Piece> pieces) {
		return pieces.get(0);
	}

	@Override
	public int minPlayers() {
		return 2;
	}

	@Override
	public int maxPlayers() {
		return 2;
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> playerPieces, Piece turn) {
		
		Piece p;
		
		for (int i = 0; i < playerPieces.size(); i++) {
			if(board.getPieceCount(playerPieces.get(i)) == 0){
				System.out.println("The player " + playerPieces.get(i) + " has been reduced to ashes." );
				playerPieces.remove(i);
				p = playerPieces.get(0); 
				return new Pair<State, Piece>(State.Won, p);
			}
			else if(validMoves(board, playerPieces, playerPieces.get((i + 1) % playerPieces.size())).isEmpty()){
				System.out.println("The player " + playerPieces.get(i) + " has been reduced to ashes." );
				playerPieces.remove(i);
				p = playerPieces.get(0); 
				return new Pair<State, Piece>(State.Won, p);			
			}	
		}	
		return gameInPlayResult;
	}

	@Override
	public Piece nextPlayer(Board board, List<Piece> playerPieces, Piece turn) {
		
		List<Piece> pieces = playerPieces;
		int i = pieces.indexOf(turn);
		while(validMoves(board, playerPieces, pieces.get((i + 1) % pieces.size())).isEmpty()){
			i++;
		}
		
		return pieces.get((i + 1) % pieces.size());
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> pieces, Piece turn) {

		List<GameMove> moves = new ArrayList<GameMove>();
		
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getCols(); j++) {
				if(board.getPosition(i, j) == turn){
					for (int k = Math.max(i - 2, 0); k <= Math.min(i + 2, board.getRows() - 1); k++) {
						for (int l = Math.max(j - 2, 0); l <= Math.min(j + 2, board.getCols() - 1); l++){
							if (board.getPosition(k, l) == null) {
								moves.add(new AtaxxMove(i, j, k, l, turn));
							}
						}
					}
				}
			}
		}
		return moves;
	}
	
	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn, Piece p) {
		// TODO Auto-generated method stub
		return 0;
	}
}
