package es.ucm.fdi.tp.practica6;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.practica6.Response;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.PlayCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.QuitCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.RestartCommand;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameClient extends Controller implements Observable<GameObserver> {
	
	private String host;
	private int port;
	private List<GameObserver> observers;
	private Piece localPiece;
	private GameFactory gameFactory;
	private Connection connectionToServer; 
	private boolean gameOver; 
	
	public GameClient(String host, int port) throws Exception {
		super(null, null);
		this.host = host;
		this.port = port;
		this.observers = new ArrayList<GameObserver>();
		connect();
	}
	
	public GameFactory getGameFactory() {
		return gameFactory;
	}
	public Piece getPlayerPiece() {
		return localPiece;
	}

	
	
	@Override
	public void addObserver(GameObserver o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(GameObserver o) {
		observers.add(o);
	}
	
	@Override
	public void makeMove(Player p) {
		forwardCommand(new PlayCommand(p));
	}
	
	@Override
	public void stop() {
		forwardCommand(new QuitCommand());
	}
	
	@Override
	public void restart() {
		forwardCommand(new RestartCommand());
	}
	
	private void forwardCommand(Command cmd) {
		if(!gameOver) {
			try {
				connectionToServer.sendObject(cmd);
			} catch (Exception e) {
				throw new GameError("Something went wrong while sending a cmd" + e.getMessage());
			}
		}
	}
	
	private void connect() throws Exception {
		connectionToServer = new Connection(new Socket(host, port)); 
		
		connectionToServer.sendObject("Connect"); 
		
		Object response = connectionToServer.getObject(); 
		if(response instanceof Exception) {
			throw (Exception) response;
		}
		
		try {
			gameFactory = (GameFactory) connectionToServer.getObject();
			localPiece = (Piece) connectionToServer.getObject();
		} catch(Exception e) {
			throw new GameError("Unknown server response: " + e.getMessage());
		}
	}
	
	public void start() {
		this.observers.add(new GameObserver() {

			@Override
			public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {	}

			@Override
			public void onGameOver(Board board, State state, Piece winner) {
				gameOver = true;
				try {
					connectionToServer.stop();
				} catch (Exception e) {
					throw new GameError("Exception caugth while trying to close the server connection " + e.getMessage());
				}
			}
			
			@Override
			public void onMoveStart(Board board, Piece turn) { }

			@Override
			public void onMoveEnd(Board board, Piece turn, boolean success) { }

			@Override
			public void onChangeTurn(Board board, Piece turn) {	}

			@Override
			public void onError(String msg) { }			
		});
		
		gameOver = false;
		while(!gameOver) {
			try {
				Response res = (Response) connectionToServer.getObject(); 
				for(GameObserver o: observers) {
					res.run(o);
				}
			} catch (Exception e) {
				throw new GameError("Something went wrong while reading the server answer" + e.getMessage());
			}
		}
	}
}
