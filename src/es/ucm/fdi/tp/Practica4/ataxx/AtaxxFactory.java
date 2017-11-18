package es.ucm.fdi.tp.Practica4.ataxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.ConsolePlayer;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.DummyAIPlayer;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.views.GenericConsoleView;

/**
 * Factoria para la creacion de juegos Ataxx. Vease {@link AtaxxRules}
 * para la descripcion del juego.
 */
 
public class AtaxxFactory implements GameFactory  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dim;
	private int obstacles;

	public AtaxxFactory() {
		this(5, 0);
	}

	public AtaxxFactory(int dim, int obstacles) {
		if (dim < 5 || (dim % 2) == 0) {
			throw new GameError("Dimension must be at least 5 and odd: " + dim);
		} else {
			this.dim = dim;
		}
		
		if(obstacles < ((dim*dim) - 8) && obstacles >= 0 && obstacles % 2 == 0){
			this.obstacles = obstacles;
		}
		else{
			throw new GameError("Obstacles must be possitive, even and less than " + ((dim*dim) - 8));
		}		
	}
	@Override
	public GameRules gameRules() {
		return new AtaxxRules(dim, obstacles);
		
	}

	@Override
	public Player createConsolePlayer() {
		ArrayList<GameMove> possibleMoves = new ArrayList<GameMove>();
		possibleMoves.add(new AtaxxMove());
		return new ConsolePlayer(new Scanner(System.in), possibleMoves);
	}

	@Override
	public Player createRandomPlayer() {
		// TODO Auto-generated method stub
		return new AtaxxRandomPlayer();
	}

	@Override
	public Player createAIPlayer(AIAlgorithm alg) {
		// TODO Auto-generated method stub
		return new DummyAIPlayer(createRandomPlayer(), 1000);
	}
	
	/**
	 * Por defecto, hay dos jugadores, X y O.
	 */	
	@Override
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("G"));
		pieces.add(new Piece("M"));
		return pieces;
	}

	@Override
	public void createConsoleView(Observable<GameObserver> game, Controller ctrl) {
		new GenericConsoleView(game, ctrl);	
	}

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl,Piece viewPiece, Player randPlayer, Player aiPlayer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("There is no swing view");
	}

}
