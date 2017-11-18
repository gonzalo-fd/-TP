package es.ucm.fdi.tp.practica5.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.practica6.GameServer;


@SuppressWarnings("serial")
public abstract class SwingView extends JFrame implements GameObserver {

	/**
	 *  ATRIBUTOS
	 */
	protected Controller ctrl;
	protected Observable<GameObserver> game;
	
	private Map<Piece, Color> pieceColors;
	Iterator<Color> colorsIter;

	final protected Color getDefaultColor(Piece p) {
		Color c = pieceColors.get(p);
		return c;
	}
	
	private Map<Piece, PlayerMode> playerTypes;
	
	enum PlayerMode {
		MANUAL("Manual"), RANDOM("Random"), AI("Intelegent");
		private String name;

		PlayerMode(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	private Piece turn;
	private Piece localPiece;
	protected List<Piece> pieces;
	protected boolean inPlay;
	private boolean movement;
	private Player randomPlayer;
	private Player aiPlayer;
	protected  Board board;
	private JPanel boardPanel;
	private JPanel toolBarPanel;
	private JTextArea statusArea;
	private JButton randomButton;
	private JButton intelligentButton;
	private JButton quitButton;
	private PlayerInfoTableModel infoTable;
	private JComboBox<Piece> playerModesCB;
	private JComboBox<PlayerMode> modesCB;
	private JComboBox<Piece> playerColorsCB;
	private boolean restart;
	private boolean gameOver;
	private String gameDesc;
	private int contAtt = 0;
	
	
	/**
	 * CONSTRUCTOR
	 */
	public SwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randomPlayer, Player aiPlayer) {

		this.ctrl = c;
		this.game = g;
		  
		  this.localPiece = localPiece;
		  this.pieceColors = new HashMap<Piece, Color>();
		  this.playerTypes =  new HashMap<Piece, PlayerMode>();
		  this.playerModesCB = new JComboBox<Piece>();
		  this.playerColorsCB = new JComboBox<Piece>();
		  this.colorsIter = Utils.colorsGenerator();
		  this.aiPlayer = aiPlayer;
		  this.randomPlayer = randomPlayer;
		  this.restart = false;
		  this.gameOver = false;
		  deActivateBoard();
		  
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				game.addObserver(SwingView.this);
			}	
		});
		initGUI();
	}
	
	/**
	 * Método que crea el JPanel principal y permite configurar los listeners.
	 */
	private void initGUI() {
		
		this.setTitle("Board Games");
		
			JPanel mainPanel = new JPanel(new BorderLayout());
		
		this.setContentPane(mainPanel);

			 // board panel
				boardPanel = new JPanel(new BorderLayout());
			
			mainPanel.add(boardPanel, BorderLayout.CENTER);
			mainPanel.setPreferredSize(new Dimension(750, 500));	
			initBoardGui();

			 // tool bar panel
				toolBarPanel = new JPanel();
			    toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.Y_AXIS));
			
			mainPanel.add(toolBarPanel, BorderLayout.LINE_END);
			
		initCtrlPanel();

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
				quit();
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
			
		});

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
	}

	/**
	 * Inicia el panel de control.
	 */
	protected void initCtrlPanel() {
		addStatusArea();
		addPlayerInfoTable () ;
		addPlayerColorsCtrl() ;
		addPlayerModesCtrl();
		addAutoPlayerButtons();
		addQuitButton();	
	}

	/**
	 * Inicia y añade el área de estado.
	 */
	final protected void addStatusArea() {

		JPanel p = new JPanel(new BorderLayout());
		//p.setPreferredSize(new Dimension(225, 175));
		p.setBorder(BorderFactory.createTitledBorder("Status Messages"));

		statusArea = new JTextArea(5, 10);
		statusArea.setEditable(false);

		JScrollPane statusAreaScroll = new JScrollPane(statusArea);
		
		p.add(statusAreaScroll, BorderLayout.CENTER);
		
		addToCtrlArea(p);
	}
	
	/**
	 * Clase interna que permite añadir la información de la tabla de información de los jugadores.
	 */
	class PlayerInfoTableModel extends DefaultTableModel {
		private String[] colNames;

		PlayerInfoTableModel() {
			this.colNames = new String[] { "Player", "Mode", "#Pieces" };
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		@Override
		public int getColumnCount() {
			return colNames.length;
		}

		@Override
		public int getRowCount() {
			return pieces == null ? 0 : pieces.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (pieces == null) {
				return null;
			}
			Piece p = pieces.get(rowIndex);
			if (columnIndex == 0) {
				return p;
			} else if (columnIndex == 1) {
				if(localPiece == (null))
				return playerTypes.get(p);
				else if(localPiece.equals(p)){
				return playerTypes.get(p);
				}
				else{
					return "";
				}
				
			} else {
				return board.getPieceCount(p);
			}
		}

		public void refresh() {
			fireTableDataChanged();
		}
	};
	
	/**
	 * Crea el panel con la tabla de información de los jugadores.
	 */
	final protected void addPlayerInfoTable (){

		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder("Player Information"));
		 infoTable = new PlayerInfoTableModel();
		
		JTable table = new JTable(infoTable) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				comp.setBackground(pieceColors.get(pieces.get(row)));
				return comp;
			}
		};
		table.setFillsViewportHeight(true);
		JScrollPane sp = new JScrollPane(table);
		p.setPreferredSize(new Dimension(100, 100));
		p.add(sp);
		addToCtrlArea(p);
		table.setEnabled(false);
	}

	/**
	 * Añade el panel con el boton y el combo box para el cambio de color de los jugadores.
	 */
	final protected void addPlayerColorsCtrl() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.setBorder(BorderFactory.createTitledBorder("Piece Colors"));
		playerColorsCB = new JComboBox<Piece>();
		JButton setColorButton = new JButton("Choose Color");
		setColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Piece p = (Piece) playerColorsCB.getSelectedItem();
				ColorChooser c = new ColorChooser(new JFrame(), "Select Piece Color", pieceColors.get(p));
				if (c.getColor() != null) {
					pieceColors.put(p, c.getColor());
					repaint();
				}
			}
		});
		p.add(playerColorsCB);
		p.add(setColorButton);
		addToCtrlArea(p);
	}

	/**
	 * Añade los botones de random e intelligent además configura los listeners de ambos.
	 */
	final protected void addAutoPlayerButtons() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p.setPreferredSize(new Dimension(100, 150));
		p.setBorder(BorderFactory.createTitledBorder("Automatic moves"));
		
		randomButton = new JButton("Random");
		randomButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				decideMakeAutomaticMove(randomPlayer);
			}
		});
		p.add(randomButton);		
		
		intelligentButton = new JButton("Intelligent");
		intelligentButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				decideMakeAutomaticMove(aiPlayer);
			}
		});
		p.add(intelligentButton);
		addToCtrlArea(p);
		
	}
	
	/**
	 * Decide si un movimiento manual es válido.
	 * @param manualPlayer se le pasa un jugador.
	 */
	final protected void decideMakeManualMove(Player manualPlayer){
		if(movement || !inPlay){
			return;
		}
		if(localPiece != null && !localPiece.equals(turn)){
			return;
		}
		if(playerTypes.get(turn) != PlayerMode.MANUAL){
			return;
		}
		makeManualMove(manualPlayer);
	}
	
	/**
	 * Llama al makeMove.
	 */
	public void makeManualMove(Player manualPlayer){
		passMoveToCtroller(manualPlayer);
	}
	
	/**
	 * Decide si un movimiento random o ai.
	 */
	private void decideMakeAutomaticMove(){
		if(playerTypes.get(turn) == PlayerMode.AI){
			decideMakeAutomaticMove(aiPlayer);
		}
		else{
			decideMakeAutomaticMove(randomPlayer);
		}		
	}
	/**
	 * Decide si un movimiento automático es válido.
	 * @param player se le pasa un jugador.
	 */
	final protected void decideMakeAutomaticMove(Player player){
		if(movement || !inPlay){
			return;
		}
		if(localPiece != null && !localPiece.equals(turn)){
			return;
		}
//		if(playerTypes.get(turn) == PlayerMode.MANUAL){ descomentar para que el manual no pueda hacer movimientos aleatorios
//			return;
//		}
		makeAutomaticMove(player);
	}
	
	/**
	 * Llama al makeMove.
	 */
	public void makeAutomaticMove(Player player){
		passMoveToCtroller(player);
	}
	
	/**
	 *  Le pasa un movimiento al controlador.
	 * @param player se le pasa un jugador.
	 */
	public void passMoveToCtroller(final Player player){
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					ctrl.makeMove(player);
				}
				catch (GameError _e){
					
				}
				
			}
			
		});
	}
	
	/**
	 * Añade el panel con los comboBox y el boton para cambiar el modo de juego de los jugadores.
	 */
	final protected void addPlayerModesCtrl() {
		JPanel panelb = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelb.setBorder(BorderFactory.createTitledBorder("Player Modes"));
		modesCB = new JComboBox<PlayerMode>();
		modesCB.addItem(PlayerMode.MANUAL);
		if (randomPlayer != null) {
			modesCB.addItem(PlayerMode.RANDOM);
		}
		if (aiPlayer != null) {
			modesCB.addItem(PlayerMode.AI);
		}
		playerModesCB = new JComboBox<Piece>(new DefaultComboBoxModel<Piece>() {	
			@Override
			public void setSelectedItem(Object o) {
				super.setSelectedItem(o);
				if (playerTypes.get(o) != PlayerMode.MANUAL) {
					modesCB.setSelectedItem(PlayerMode.AI);
				} else {
					modesCB.setSelectedItem(PlayerMode.MANUAL);
				}
			}
		});

		JButton setModeButton = new JButton("Set");
		setModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Piece p = (Piece) playerModesCB.getSelectedItem();
				PlayerMode m = (PlayerMode) modesCB.getSelectedItem();
				playerTypes.put(p, m);
				infoTable.refresh();
				if(localPiece != null){
					if(playerTypes.get(p) == PlayerMode.RANDOM || playerTypes.get(p) == PlayerMode.AI){
						randomButton.setEnabled(false);
						intelligentButton.setEnabled(false);	
						decideMakeAutomaticMove();
				}
					else{
						randomButton.setEnabled(true);
						intelligentButton.setEnabled(true);	
					}
				}
			}
		});
		panelb.add(playerModesCB);
		panelb.add(modesCB);
		panelb.add(setModeButton);
		addToCtrlArea(panelb);
	}

	/**
	 * Añade los botones de quit y restart con sus correspondientes action listeners.
	 */
	final protected void addQuitButton(){
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(gameOver){
				quit();
				}
				else{
					JOptionPane.showMessageDialog(new JFrame(), "You can't quit untill the game is over.", "Quit", 0);
				}
			}
		});
		p.add(quitButton);
		//if(localPiece == null) {
		JButton restartButton = new JButton("Restart");
		if(GameServer.isButton()){
			restartButton.setEnabled(false);
		}
		final Controller aux = ctrl;
		restartButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					if(gameOver){
					aux.restart();
					statusArea.setText("");
					restart = true;
					gameOver = false;
					}
					else{
						JOptionPane.showMessageDialog(new JFrame(), "You can't restart untill the game is over.", "Restart", 0);
					}
					
				} catch(GameError _e){
					
				}
			}
		});
		p.add(restartButton);
		addToCtrlArea(p);
	}
	
	

	/**
	 * Muestra el dialogo cuando pulsas el botón de quit 
	 */
	final protected void quit() {
		
		int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (n == 0) {
			
			try {
				ctrl.stop();
			} catch (GameError _e) {
			}
			
			setVisible(false);
			dispose();
			System.exit(0);
			
		}
		
	}
	

	/** GAME OBSERVER CALLBACKS **/

	@Override
	public void onGameStart(final Board board, final String gameDesc,final List<Piece> pieces, final Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleGameStart(board, gameDesc, pieces, turn);
			}
		});
	}
	
	/**
	 * Se encarga del inicio del juego.
	 * @param board recibe un tablero
	 * @param gameDesc recibe la descipción del juego.
	 * @param pieces recibe la lista de piezas.
	 * @param turn recibe el jugador actual.
	 */
	private void handleGameStart(Board board, String gameDesc,List<Piece> pieces, Piece turn) {
		this.setTitle("Board Games: " + gameDesc + (localPiece == null ? "" : " (" + localPiece + ")"));
		this.turn = turn;
		this.board = board;
		this.pieces = pieces;
		this.gameDesc = gameDesc;
		activateBoard();
		
		if(localPiece==null)
		initializePlayersTypes();
		else{
		playerTypes.put(localPiece, PlayerMode.MANUAL);
		playerModesCB.addItem(localPiece);			
		}
		initializePiecesColors();
		handleTurnChange(turn, gameDesc);
		redraw();
	}
	
	/**
	 * Se encarga del cambio de turno.
	 * @param turn recibe el turno del jugador al que le toca jugar.
	 */
	 private void handleTurnChange(Piece turn, String gameDesc){
		 this.turn = turn;
		 addContentToStatusArea("Turn for " + (turn.equals(localPiece) ? "You (" + turn + ")" : turn));
		 informationGame(this.gameDesc, turn);
		 infoTable.refresh();
		 if(playerTypes.get(turn) != PlayerMode.MANUAL){
			 decideMakeAutomaticMove();
		 }
	 }
	
	 /**
	  * Inicializa el modo de juego de los jugadores, manual por defecto.
	  */
	private void initializePlayersTypes() {
		for (Piece p : pieces) {
			if (playerTypes.get(p) == null) {
				playerTypes.put(p, PlayerMode.MANUAL);
				playerModesCB.addItem(p);
			}
			}
		}

	/**
	 * Inicializa el color por defecto de los jugadores de manera aleatoria.
	 */
	private void initializePiecesColors() {
		playerColorsCB.removeAllItems();
		for (Piece p : pieces) {
			if (pieceColors.get(p) == null) {
				pieceColors.put(p, Utils.randomColor());
			}
			playerColorsCB.addItem(p);
		}
	}
	
	@Override
	public void onGameOver(final Board board, final State state, final Piece winner) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handleOnGameOver(board,state,winner);
				
			}});
	}
	
	/**
	 * Se encarga de indicar quien es el ganador del juego.
	 * @param board recibe un tablero.
	 * @param state recibe el estado del juego.
	 * @param winner recibe el ganador.
	 */
	private void handleOnGameOver(Board board,State state,Piece winner){
		this.gameOver = true;
		addContentToStatusArea("Game Over:"+ state);
		String s = "";
		if(state == State.Stopped){
			setVisible(false);
			dispose();
		}
		else if(state == State.Draw){
			s = "Draw";
			JOptionPane.showMessageDialog(new JFrame(), s, "Draw", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(state == State.Won){
			if(winner == localPiece){
				s = "You Won";
			}
			else{
				s = "The winner is: " +winner;
				JOptionPane.showMessageDialog(new JFrame(), s, "Winner", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		infoTable.refresh();
		addContentToStatusArea(statusArea.getText() + s);
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		if(this.turn == turn){
			movement = false;
		}
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		if(this.turn == turn){
			movement = false;
		}
		if(!success){
			handleTurnChange(turn, "");
		}
	}

	@Override
	public void onChangeTurn(Board board, final Piece turn) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handleTurnChange(turn, "");
				
			}
		});
	}

	@Override
	public void onError(String msg) {
		if(!inPlay||localPiece == null||turn.equals(localPiece)){
			JOptionPane.showMessageDialog(new JFrame(), msg, "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void informationGame(String gameDesc, Piece turn){
		String[] strArray;
		String ConnectN = "ConnectN", Ataxx = "Ataxx", ttt = "Tic-Tac-Toe",attt = "Advanced";
		strArray = gameDesc.split(" ");
		
		if(((strArray[0].equals(ConnectN) || strArray[0].equals(ttt) || (strArray[0].equals(attt) && (contAtt < 3))) && (localPiece == turn || localPiece == (null)))){
			addContentToStatusArea("Click on an empty cell");
			contAtt++;
		}
		else if (((strArray[0].equals(Ataxx) || ((strArray[0].equals(attt) && (contAtt == 3)))) && (localPiece == (turn) || localPiece == (null)))){
			addContentToStatusArea("Click on an origin piece");		
		}
	}
	
	public boolean getRestart(){
		
		return this.restart;
	}
	public void setRestart(boolean rest){
		this.restart = rest;
	}
	
	protected Piece getTurn() {
		return turn;
	}

	final protected List<Piece> getPieces() {
		return pieces;
	}
	
	protected Board getBoard() {
		return board;
	}
	
	/**
	 * Añade un componente a la vista.
	 * @param c recibe un componente.
	 */
	final protected void setBoardArea(JComponent c) {
		boardPanel.add(c, BorderLayout.CENTER);
	}
	
	/**
	 * Añade un componente al área de control.
	 * @param c recibe un componente.
	 */
	final protected void addToCtrlArea(JComponent c) {
		toolBarPanel.add(c);
	}
	/**
	 * Añade un mensaje al text area.
	 * @param msg recibe un mensaje.
	 */
	final protected void addContentToStatusArea(String msg) {
		statusArea.append("* " + msg + "\n");
	}

	/**
	 * Inicia la interfaz gráfica del tablero.
	 */
	protected abstract void initBoardGui();

	/**
	 * Activa el tablero.
	 */
	protected abstract void activateBoard();
	/**
	 * Desactiva el tablero.
	 */
	protected abstract void deActivateBoard();
	/**
	 * Repinta el tablero.
	 */
	protected abstract void redraw();

}