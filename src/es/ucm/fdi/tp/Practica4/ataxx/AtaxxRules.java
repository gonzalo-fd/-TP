package es.ucm.fdi.tp.Practica4.ataxx;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
/**
 * Rules for Ataxx game.
 * <ul>
 * <li>The game is played on an odd NxN board (with N>=5).</li>
 * <li>The number of players is between 2 and 4.</li>
 * <li>The player turn in the given order, each placing a piece on an empty
 * cell not more that 2 cells away from the source cell. The winner is the one who has more pieces when
 * the game ends.
 * .</li>
 * </ul>
 * 
 * <p>
 * Reglas del juego ConnectN.
 * <ul>
 * <li>El juego se juega en un tablero impar NxN (con N>=5).</li>
 * <li>El numero de jugadores esta entre 2 y 4.</li>
 * <li>Los jugadores juegan en el orden proporcionado, cada uno colocando una
 * ficha en una casilla vacia a no mas de dos celdas de distancia de la celda de origen. El ganador es el que 
 * tiene más fichas cuando el juego termina.
 * </li>
 * </ul>
 *
 */
public class AtaxxRules implements GameRules {
	
	protected final Pair<State, Piece> gameInPlayResult = new Pair<State, Piece>(State.InPlay, null);

	private int dim;
	private int obstacles;

	public AtaxxRules(int dim, int obstacles) {
		if (dim < 5 && dim % 2 != 0) {
			throw new GameError("Dimension must be at least 5 and odd: " + dim);
		} else {
			this.dim = dim;
		}
		
		if(obstacles < ((dim*dim) - 8) && obstacles >= 0 && obstacles % 2 == 0){
			this.obstacles = obstacles;
		}
		else{
			throw new GameError("Obstacles must be possitive, even and less than " + ((dim*dim) - maxPlayers()*2));
		}
	}
	@Override
	public String gameDesc() {
		// TODO Auto-generated method stub
		return "Ataxx " + dim + "x" + dim;
	}

	@Override
	public Board createBoard(List<Piece> pieces) {
		// TODO Auto-generated method stub
		FiniteRectBoard board = new FiniteRectBoard(dim, dim);
		int dimAux = dim-1, rr, rc;
		Piece o = new Piece("*");
		
		for (int i = 0; i < pieces.size(); i++) {
			
			switch(i){
			
			case 0: 
				board.setPosition(0, 0, pieces.get(i));
				board.setPosition(dimAux, dimAux, pieces.get(i));
				board.setPieceCount(pieces.get(i), 2);
				break;
			case 1:
				board.setPosition(0, dimAux, pieces.get(i));
				board.setPosition(dimAux, 0, pieces.get(i));
				board.setPieceCount(pieces.get(i), 2);
				break;
			case 2:
				board.setPosition(dimAux / 2, 0, pieces.get(i));
				board.setPosition(dimAux / 2, dimAux, pieces.get(i));
				board.setPieceCount(pieces.get(i), 2);
				break;
			case 3:
				board.setPosition(0, dimAux / 2, pieces.get(i));
				board.setPosition(dimAux, dimAux / 2, pieces.get(i));
				board.setPieceCount(pieces.get(i), 2);
				break;
			}	
		}	
		for (int i = 0; i < obstacles; i+=2) {
			do{
				rr = (int) (Math.random() * dim);
				rc = (int) (Math.random() * dim);	
			}while(board.getPosition(rr, rc) != null);
			board.setPosition(rr, rc, o);
			board.setPosition(dim-(rr + 1), dim -(rc + 1), o);
		}
				
		return board;
	}

	@Override
	public Piece initialPlayer(Board board, List<Piece> pieces) {
		// TODO Auto-generated method stub
		return pieces.get(0);
	}

	@Override
	public int minPlayers() {
		return 2;
	}

	@Override
	public int maxPlayers() {
		return 4;
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> playerPieces, Piece turn) {
		int contPieces = playerPieces.size();
		// TODO Auto-generated method stub
		Piece p;
		if(board.isFull()){
			
			int j = board.getPieceCount(playerPieces.get(0));
			p = playerPieces.get(0);
			for (int i = 1; i < playerPieces.size(); i++) {
							
				if(j < board.getPieceCount(playerPieces.get(i))){
					j = board.getPieceCount(playerPieces.get(i));
					p = playerPieces.get(i);
				}
			}
			for (int i = 1; i < playerPieces.size(); i++) {
				if(j == board.getPieceCount(playerPieces.get(i)) && p != playerPieces.get(i)){
					return new Pair<State, Piece>(State.Draw, null);
				}
			}
			return new Pair<State, Piece>(State.Won, p);
		}
		else{
			for (int i = 0; i < playerPieces.size(); i++) {
				if(board.getPieceCount(playerPieces.get(i)) == 0){
					//System.out.println("The player " + playerPieces.get(i) + " has been reduced to ashes." );
					//playerPieces.remove(i);
					contPieces--;
				}
			}
			if(contPieces == 1){
				int aux = 0, winner = 0;
				for (int i = 0; i < playerPieces.size(); i++) {	
					if(board.getPieceCount(playerPieces.get(i)) > aux){
						aux = board.getPieceCount(playerPieces.get(i));
						winner = i;
					}
				}
				p = playerPieces.get(winner); 
				return new Pair<State, Piece>(State.Won, p);
			}
		}	
		return gameInPlayResult;
	}

	@Override
	public Piece nextPlayer(Board board, List<Piece> playerPieces, Piece turn) {
		// TODO Auto-generated method stub
		List<Piece> pieces = playerPieces;
		int i = pieces.indexOf(turn);
		while(validMoves(board, playerPieces, pieces.get((i + 1) % pieces.size())).isEmpty()){
			i++;
		}
		
		return pieces.get((i + 1) % pieces.size());
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		
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
