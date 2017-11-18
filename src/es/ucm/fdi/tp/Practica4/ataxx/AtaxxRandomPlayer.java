package es.ucm.fdi.tp.Practica4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
/**
 * A random player for Ataxx.
 * 
 * <p>
 * Un jugador aleatorio para Ataxx.
 *
 */
public class AtaxxRandomPlayer extends Player {

	private static final long serialVersionUID = 1L;

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		
		int random, randomMove;	
		random = rules.validMoves(board, pieces, p).size();
		randomMove = (int) (Math.random() * random);
	
		return rules.validMoves(board, pieces, p).get(randomMove);
	}
}
