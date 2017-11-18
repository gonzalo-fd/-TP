package es.ucm.fdi.tp.practica6;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.practica6.ChangeTurnResponse;
import es.ucm.fdi.tp.practica6.ErrorResponse;
import es.ucm.fdi.tp.practica6.GameOverResponse;
import es.ucm.fdi.tp.practica6.GameStartResponse;
import es.ucm.fdi.tp.practica6.MoveEndResponse;
import es.ucm.fdi.tp.practica6.MoveStartResponse;
import es.ucm.fdi.tp.practica6.Response;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameServer extends Controller implements GameObserver {
	private int port;
	private int numPlayers;
	private int numOfConnectedPlayers;
	private GameFactory gameFactory;
	private List<Connection> clients;
	private JTextArea infoArea;
	
	volatile private ServerSocket server;
	volatile private boolean stopped;
	volatile private boolean gameOver;
	private boolean start = true;
	private static boolean button = true;
	
	public GameServer(GameFactory gameFactory, List<Piece> pieces, int port) {
		super(new Game(gameFactory.gameRules()), pieces);
	
		this.numOfConnectedPlayers = 0;
		this.gameFactory = gameFactory;
		this.port = port;
		this.numPlayers = pieces.size();
		this.clients = new ArrayList<Connection>();
		game.addObserver(this);
	}
	
	@Override
	public synchronized void makeMove(Player player) {
		try { 
			super.makeMove(player); 
		} catch (GameError e) { 
			log("Something went wrong while trying to make a move" + e.getMessage());
		}
	}
	
	@Override
	public synchronized void stop() {
		try { 
			super.stop(); 
		} catch (GameError e) {
			log("Something went wrong while trying to stop the controller" + e.getMessage());
		}
	}
	
	@Override
	public synchronized void restart() {
		try { 
			super.restart(); 
		} catch (GameError e) {
			log("Something went wrong while trying to restart the game" + e.getMessage());
		}
	}
	
	@Override
	public void start() {
		controlGUI();
		startServer();
	}
	
	private void controlGUI() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					constructGUI();
				}
			});
		} catch(InvocationTargetException | InterruptedException e) {
			throw new GameError("Something went wrong when constructing the GUI");
		}
	}
	
	private void constructGUI() {
		JFrame window = new JFrame("Game Server");
		JPanel panel = new JPanel(new BorderLayout());
		
		infoArea = new JTextArea();
		infoArea.setEditable(false);
		
		JButton quitButton = new JButton("Stop Server");
		quitButton.setPreferredSize(new Dimension(100, 100));
		
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopped = true;
				if(!gameOver) {
					stop();
					gameOver = true;
					for(Connection c: clients) {
						try {
							c.stop();
						} catch (Exception e1) {
							log("Something went wrong trying to disconnect a client" + e1.getMessage());
						}
					}
				}
				try {
					server.close();
				} catch (IOException e1) { 
					log("Something went wrong trying to turn the server off" + e1.getMessage());
				}
				stopped = true;
				try {
					server.close();
				} catch (IOException e1) {
					log("Something went wrong trying to turn the server off" + e1.getMessage());
				}
				window.dispose();
			}
		});
		
		panel.add(infoArea, BorderLayout.CENTER);
		panel.add(quitButton, BorderLayout.SOUTH);
		window.add(panel);
		window.setLocation(750, 300);
		window.setPreferredSize(new Dimension(600, 600));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}
	
	private void log(String msg) {
	}
	
	private void startServer() {
		try {
			server = new ServerSocket(port);
		} catch (IOException e1) {
			log("Something went wrong trying to run a Socket" + e1.getMessage());
		}
		stopped = false;
		
		while(!stopped) {
			try {
				Socket s = server.accept(); 
				log("Conexion aceptada");			
				handleRequest(s); 
			} catch(Exception e) {
				if(!stopped) {
					log("Error while waiting for a connection: " + e.getMessage());
				}
			}
		}
	}
	
	private void handleRequest(Socket s) throws Exception {
		try {
			Connection c = new Connection(s);
			
			Object clientRequest = c.getObject();
			log("The client's first message is: " + clientRequest);
			if(!(clientRequest instanceof String) && !((String) clientRequest).equalsIgnoreCase("Connect")) {
				c.sendObject(new GameError("Invalid Request"));
				c.stop();
				return;
			}

			numOfConnectedPlayers++;
			clients.add(c);
			c.sendObject("OK");
			c.sendObject(gameFactory);
			c.sendObject(pieces.get((clients.size()-1)));
			if(numOfConnectedPlayers == numPlayers) { 
				if(start) {
					super.start();
					start = false;
				} else {
					this.restart();
				}
			}
			startClientListener(c);
		} catch(IOException | ClassNotFoundException e) {
			log("Exception caught processing the client's request" + e.getMessage());
		}
	}
	
	private void startClientListener(Connection c) {
		gameOver = false;
		Thread t = new Thread() {
			@Override
			public void run() {
				while(!stopped && !gameOver) {
					try{
						Command cmd;
						cmd = (Command) c.getObject();
						cmd.execute(GameServer.this);
					} catch (Exception e) {
						if(!stopped && !gameOver) {							
							for(Connection co: clients) {
								try {
									co.stop();
								} catch (Exception e1) {
									log("Something went wrong trying to disconnect a client" + e1.getMessage());
								}
							}
						}
					}
				}
			}
		};
		t.start();
	}
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		forwardNotification(new GameStartResponse(board, gameDesc, pieces, turn));
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		forwardNotification(new GameOverResponse(board, state, winner));
		this.stop();
		log("The clients have been disconnected");
		for(Connection co: clients) {
			try {
				co.stop();
			} catch (Exception e1) {
				log("Something went wrong trying to disconnectd a client" + e1.getMessage());
			}
		}
		this.numOfConnectedPlayers = 0; 
		this.clients = new ArrayList<Connection>(); 
		gameOver = true;
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		forwardNotification(new MoveStartResponse(board, turn));
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		forwardNotification(new MoveEndResponse(board, turn, success));
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		forwardNotification(new ChangeTurnResponse(board, turn));
	}

	@Override
	public void onError(String msg) {
		forwardNotification(new ErrorResponse(msg));
	}
	
	void forwardNotification(Response r) {
		for(Connection c: clients) { 
			try {
				c.sendObject(r);
			} catch (Exception e) {
				log("Exception caugth while sending the response object from client");
			}
		}
	}

	public static boolean isButton() {
		return button;
	}

	public void setButton(boolean button) {
		this.button = button;
	}
}
