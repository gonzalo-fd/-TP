package es.ucm.fdi.tp.practica6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import es.ucm.fdi.tp.practica5.ataxx.AtaxxFactoryExt;
import es.ucm.fdi.tp.practica5.attt.AdvancedTTTFactoryExt;
import es.ucm.fdi.tp.practica5.connectn.ConnectNFactoryExt;
import es.ucm.fdi.tp.practica5.damas.DamasFactoryExt;
import es.ucm.fdi.tp.practica5.ttt.TicTacToeFactoryExt;
import es.ucm.fdi.tp.basecode.bgame.control.ConsoleCtrl;
import es.ucm.fdi.tp.basecode.bgame.control.ConsoleCtrlMVC;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Esta es la clase con el metodo main de inicio del programa. Se utiliza la
 * libreria Commons-CLI para leer argumentos de la linea de ordenes: el juego al
 * que se quiere jugar y la lista de jugadores. Puedes encontrar mas información
 * sobre esta libreria en {@link https://commons.apache.org/proper/commons-cli/}
 */


public class Main {

	/**
	 * Vistas disponibles.
	 */
	enum ViewInfo {
		WINDOW("window", "Swing"), CONSOLE("console", "Console");

		private String id;
		private String desc;

		ViewInfo(String id, String desc) {
			this.id = id;
			this.desc = desc;
		}

		public String getId() {
			return id;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {
			return id;
		}
	}

	/**
	 * Juegos disponibles.
	 */
	enum GameInfo {
		CONNECTN("cn", "ConnectN"), TicTacToe("ttt", "Tic-Tac-Toe"), AdvancedTicTacToe("attt", "Advanced Tic-Tac-Toe"), Ataxx("ataxx", "Ataxx"), Damas("damas", "Damas");

		private String id;
		private String desc;

		GameInfo(String id, String desc) {
			this.id = id;
			this.desc = desc;
		}

		public String getId() {
			return id;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {
			return id;
		}
	}

	/**
	 * Modos de juego.
	 */
	enum PlayerMode {
		MANUAL("m", "Manual"), RANDOM("r", "Random"), AI("a", "Automatics");

		private String id;
		private String desc;

		PlayerMode(String id, String desc) {
			this.id = id;
			this.desc = desc;
		}

		public String getId() {
			return id;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {
			return id;
		}
	}
	
	/**
	 * Modos de aplicaci�n.
	 */
	enum ApplicationMode {
		NORMAL("n", "Normal"), CLIENT("c", "client"), SERVER("s", "server");

		private String id;
		private String desc;

		ApplicationMode(String id, String desc) {
			this.id = id;
			this.desc = desc;
		}

		public String getId() {
			return id;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {
			return id;
		}
	}
	
	final private static GameInfo DEFAULT_GAME = GameInfo.CONNECTN; /** Juego por defecto. */
	final private static ViewInfo DEFAULT_VIEW = ViewInfo.WINDOW; /** Vista por defecto. */
	final private static PlayerMode DEFAULT_PLAYERMODE = PlayerMode.MANUAL; /**  Modo de juego por defecto. */
	final private static ApplicationMode DEFAULT_APPMODE = ApplicationMode.NORMAL; /**  Modo de aplicacion por defecto. */
	
	/**
	 * Este atributo incluye una factoria de juego que se crea despues de
	 * extraer los argumentos de la linea de ordenes. Depende del juego
	 * seleccionado con la opcion -g (por defecto, {@link #DEFAULT_GAME}).
	 */
	private static GameFactory gameFactory;
	private static List<Piece> pieces; /** Lista de fichas proporcionadas con la opcion -p, u obtenidas de  {@link GameFactory#createDefaultPieces()} si no hay opcion -p. */
	/**
	 * Lista de jugadores. El jugador i-esimo corresponde con la ficha i-esima
	 * de la lista {@link #pieces}. Esta lista contiene lo que se proporciona en
	 * la opcion -p (o el valor por defecto {@link #DEFAULT_PLAYERMODE}).
	 */
	private static List<PlayerMode> playerModes;
	/**
	 * Vista a utilizar. Dependiendo de la vista seleccionada con la opcion -v o
	 * el valor por defecto {@link #DEFAULT_VIEW} si el argumento -v no se
	 * proporciona.
	 */
	private static ViewInfo view;
	/**
	 * {@code true} si se incluye la opcion -m, para utilizar una vista separada
	 * por cada ficha, o {@code false} en caso contrario.
	 */
	private static boolean multiviews;
	/**
	 * Numero de filas proporcionadas con la opcion -d, o {@code null} si no se
	 * incluye la opcion -d.
	 */
	private static Integer dimRows;
	/**
	 * Numero de columnas proporcionadas con la opcion -d, o {@code null} si no
	 * se incluye la opcion -d.
	 * 
	 */
	private static Integer dimCols;
	/**
	 * Numero de obstaculos proporcionados con la opcion -o, o {@code null} si no
	 * se incluye la opcion -o.
	 * 
	 */
	private static Integer obstacles;
	

	/**
	 * 
	 * 
	 */
	private static ApplicationMode applicationMode;
	
	private static Integer serverPort;
	
	private static String serverHost;
	
	/**
	 * Algoritmo a utilizar por el jugador automatico. Actualmente no se
	 * utiliza, por lo que siempre es {@code null}.
	 */
	private static AIAlgorithm aiPlayerAlg;
	/**
	 * Procesa la linea de ordenes del programa y crea los objetos necesarios
	 * para los atributos de esta clase. Por ejemplo, la factoria, las fichas,
	 * etc.
	 * @param args
	 *            Lista de argumentos de la linea de ordenes.
	 */
	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = new Options();
		cmdLineOptions.addOption(constructHelpOption()); // -h or --help
		cmdLineOptions.addOption(constructGameOption()); // -g or --game
		cmdLineOptions.addOption(constructViewOption()); // -v or --view
		cmdLineOptions.addOption(constructMlutiViewOption()); // -m or
																// --multiviews
		cmdLineOptions.addOption(constructPlayersOption()); // -p or --players
		cmdLineOptions.addOption(constructDimensionOption()); // -d or --dim
		cmdLineOptions.addOption(constructObstacleOption()); // -o or --obstacles
		cmdLineOptions.addOption(constructApplicationOption()); // -app-mode o -am
		cmdLineOptions.addOption(constructServerPortOption()); // -sp
		cmdLineOptions.addOption(constructServerHostOption()); // -sh

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseDimOptionn(line);
			parseObstaclesOptions(line);
			parseGameOption(line);
			parsePlayersOptions(line);
			parseViewOption(line);
			parseMultiViewOption(line);
			parseApplicationOption(line);
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}
		} catch (ParseException | GameError e) {
			// new Piece(...) might throw GameError exception
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}
	
	private static Option constructApplicationOption() {
		String optionInfo = "The application mode to use ( ";
		for (ApplicationMode a : ApplicationMode.values()) {
			optionInfo += a.getId() + " [for " + a.getDesc() + "] ";
		}
		optionInfo += "). By defualt, " + DEFAULT_APPMODE.getId() + ".";
		Option opt = new Option("am", "app-mode", true, optionInfo);
		opt.setArgName("app mode identifier");
		return opt;
	}
	
	private static void parseApplicationOption(CommandLine line) throws ParseException {
		String appVal = line.getOptionValue("am", DEFAULT_APPMODE.getId());
			if (appVal.equals("server")) {
				applicationMode = ApplicationMode.SERVER;
				parseServerPortOption(line);
			} else if(appVal.equals("client")) {
				applicationMode = ApplicationMode.CLIENT;
				parseServerHostOption(line);
				parseServerPortOption(line);
			}
		if (applicationMode == null) {
			applicationMode = ApplicationMode.NORMAL;
		}
	}
	
	
	
	private static void parseServerPortOption(CommandLine line) throws ParseException {
		String serverportVal = line.getOptionValue("sp");
		if (serverportVal != null) {
			serverPort = Integer.parseInt(serverportVal);
		} else {
			serverPort = 2000;
		}
	}
	
	private static Option constructServerPortOption() {
		return new Option("sp", true,
		"Indicates the port of the server");
	}
	
	private static void parseServerHostOption(CommandLine line) throws ParseException {
		String shost = line.getOptionValue("sh");
		if (shost != null) {
			serverHost = shost;
		} else {
			serverHost = "localhost";
		}
	}
	
	private static Option constructServerHostOption() {
		return new Option("sh", true,
			"Indicates the host of the server");
	}
	
	private static Option constructObstacleOption() {
		return new Option("o", "obstacle", true,
				"Create a random number of obstacles in the board");
	}
	
	/**
	 * Extrae la opcion de juego (-o).
	*/
	private static void parseObstaclesOptions(CommandLine line) throws ParseException {		
		String obstVal = line.getOptionValue("o");
		int maximoobst;
		if(obstVal != null) {
			try {
				obstacles = Integer.parseInt(obstVal);
				if (dimRows != null || dimCols != null) {
					maximoobst = ((dimRows * dimCols) - (2 + (dimRows + dimCols - 1)))/4;
					if(obstacles <= 0 && obstacles > maximoobst) {
						throw new ParseException("Invalid obstacles: " + obstacles);
					}
				} else {
					maximoobst = ((7 * 7) - (2 + (7 + 7 - 1)))/4;
					if(obstacles <= 0 && obstacles > maximoobst) {
						throw new ParseException("Invalid obstacles: " + obstacles);
					}
				}
			} catch(NumberFormatException e) {
				throw new ParseException("Invalid obstacles: " + obstacles);
			}
		}
	}

	/**
	 * Construye la opcion CLI -m.
	 * @return CLI {@link {@link Option} for the multiview option.
	 */

	private static Option constructMlutiViewOption() {
		return new Option("m", "multiviews", false,
				"Create a separate view for each player (valid only when using the " + ViewInfo.WINDOW + " view)");
	}

	/**
	 * Extrae la opcion multiview (-m) y asigna el valor de {@link #multiviews}.
	 * @param line
	 *            CLI {@link CommandLine} object.
	 */
	private static void parseMultiViewOption(CommandLine line) {
		multiviews = line.hasOption("m");
	}

	/**
	 * Construye la opcion CLI -v.
	 * @return Objeto {@link Option} de esta opcion.
	 */
	private static Option constructViewOption() {
		String optionInfo = "The view to use ( ";
		for (ViewInfo i : ViewInfo.values()) {
			optionInfo += i.getId() + " [for " + i.getDesc() + "] ";
		}
		optionInfo += "). By defualt, " + DEFAULT_VIEW.getId() + ".";
		Option opt = new Option("v", "view", true, optionInfo);
		opt.setArgName("view identifier");
		return opt;
	}

	/**
	 * Extrae la opcion view (-v) y asigna el valor de {@link #view}.
	 * @param line
	 *            CLI {@link CommandLine} object.
	 * @throws ParseException
	 *             If an invalid value is provided (the valid values are those
	 *             of {@link ViewInfo}.
	 */
	private static void parseViewOption(CommandLine line) throws ParseException {
		String viewVal = line.getOptionValue("v", DEFAULT_VIEW.getId());
		// view type
		for (ViewInfo v : ViewInfo.values()) {
			if (viewVal.equals(v.getId())) {
				view = v;
			}
		}
		if (view == null) {
			throw new ParseException("Uknown view '" + viewVal + "'");
		}
	}

	/**
	 * Construye la opcion CLI -p.
	 * @return Objeto {@link Option} de esta opcion.
	 */
	private static Option constructPlayersOption() {
		String optionInfo = "A player has the form A:B (or A), where A is sequence of characters (without any whitespace) to be used for the piece identifier, and B is the player mode (";
		for (PlayerMode i : PlayerMode.values()) {
			optionInfo += i.getId() + " [for " + i.getDesc() + "] ";
		}
		optionInfo += "). If B is not given, the default mode '" + DEFAULT_PLAYERMODE.getId()
				+ "' is used. If this option is not given a default list of pieces from the corresponding game is used, each assigmed the mode '"
				+ DEFAULT_PLAYERMODE.getId() + "'.";
		Option opt = new Option("p", "players", true, optionInfo);
		opt.setArgName("list of players");
		return opt;
	}

	/**
	 * Extrae la opcion players (-p) y asigna el valor de {@link #pieces} y
	 * {@link #playerModes}.
	 * @param line
	 *            CLI {@link CommandLine} object.
	 * @throws ParseException
	 *             Si se proporciona un valor invalido (@see
	 *             {@link #constructPlayersOption()}).
	 */
	private static void parsePlayersOptions(CommandLine line) throws ParseException {

		String playersVal = line.getOptionValue("p");

		if (playersVal == null) {
		
			pieces = gameFactory.createDefaultPieces();
			playerModes = new ArrayList<PlayerMode>();
			for (int i = 0; i < pieces.size(); i++) {
				playerModes.add(DEFAULT_PLAYERMODE);
			}
		} else {
			pieces = new ArrayList<Piece>();
			playerModes = new ArrayList<PlayerMode>();
			String[] players = playersVal.split(",");
			for (String player : players) {
				String[] playerInfo = player.split(":");
				if (playerInfo.length == 1) {
					pieces.add(new Piece(playerInfo[0]));
					playerModes.add(DEFAULT_PLAYERMODE);
				} else if (playerInfo.length == 2) {
					pieces.add(new Piece(playerInfo[0]));
					PlayerMode selectedMode = null;
					for (PlayerMode mode : PlayerMode.values()) {
						if (mode.getId().equals(playerInfo[1])) {
							selectedMode = mode;
						}
					}
					if (selectedMode != null) {
						playerModes.add(selectedMode);
					} else {
						throw new ParseException("Invalid player mode in '" + player + "'");
					}
				} else {
					throw new ParseException("Invalid player information '" + player + "'");
				}
			}
		}
	}

	/**
	 * Construye la opcion CLI -g.
	 * @return Objeto {@link Option} de esta opcion.
	 */

	private static Option constructGameOption() {
		String optionInfo = "The game to play ( ";
		for (GameInfo i : GameInfo.values()) {
			optionInfo += i.getId() + " [for " + i.getDesc() + "] ";
		}
		optionInfo += "). By defualt, " + DEFAULT_GAME.getId() + ".";
		Option opt = new Option("g", "game", true, optionInfo);
		opt.setArgName("game identifier");
		return opt;
	}

	/**
	 * Extrae la opcion de juego (-g). Asigna el valor del atributo
	 * {@link #gameFactory}. Normalmente necesita que se haya llamado antes a
	 * {@link #parseDimOptionn(CommandLine)} para extraer la dimension del
	 * tablero. 
	 * @param line
	 *            CLI {@link CommandLine} object.
	 * @throws ParseException
	 *             Si se proporciona un valor invalido (Los valores validos son
	 *             los de {@link GameInfo}).
	 */
	private static void parseGameOption(CommandLine line) throws ParseException {
		String gameVal = line.getOptionValue("g", DEFAULT_GAME.getId());
		GameInfo selectedGame = null;

		for( GameInfo g : GameInfo.values() ) {
			if ( g.getId().equals(gameVal) ) {
				selectedGame = g;
				break;
			}
		}

		if ( selectedGame == null ) {
			throw new ParseException("Uknown game '" + gameVal + "'");
		}
	
		switch ( selectedGame ) {
		case AdvancedTicTacToe:
			gameFactory = new AdvancedTTTFactoryExt();
			break;
		case CONNECTN:
			if (dimRows != null && dimCols != null && dimRows == dimCols) {
				gameFactory = new ConnectNFactoryExt(dimRows);
			} else {
				gameFactory = new ConnectNFactoryExt();
			}
			break;
		case TicTacToe:
			gameFactory = new TicTacToeFactoryExt();
			break;
		case Ataxx:
			if (dimRows != null && dimCols != null && dimRows == dimCols) {
				if(obstacles == null)
				gameFactory = new AtaxxFactoryExt(dimRows, 0);
				else
				gameFactory = new AtaxxFactoryExt(dimRows, obstacles);	
					
			}else if(dimRows == null && dimCols == null && obstacles !=null){
				gameFactory = new AtaxxFactoryExt(5, obstacles);
			}
			else {
				gameFactory = new AtaxxFactoryExt();
			}
			
			break;
		case Damas:
			gameFactory = new DamasFactoryExt();
			break;
		default:
			throw new UnsupportedOperationException("Something went wrong! This program point should be unreachable!");
		}
	
	}

	/**
	 * Construye la opcion CLI -d.
	 * 
	 * @return CLI {@link {@link Option} for the dimension.
	 *         <p>
	 *         Objeto {@link Option} de esta opcion.
	 */
	private static Option constructDimensionOption() {
		return new Option("d", "dim", true,
				"The board size (if allowed by the selected game). It must has the form ROWSxCOLS.");
	}

	/**
	 * Extrae la opcion dimension (-d). Asigna el valor de los atributos
	 * {@link #dimRows} and {@link #dimCols}. La dimension es de la forma
	 * ROWSxCOLS.
	 * @param line
	 *            CLI {@link CommandLine} object.
	 * @throws ParseException
	 *             If an invalid value is provided.
	 *             <p>
	 *             Si se proporciona un valor invalido.
	 */
	private static void parseDimOptionn(CommandLine line) throws ParseException {
		String dimVal = line.getOptionValue("d");
		if (dimVal != null) {
			try {
				String[] dim = dimVal.split("x");
				if (dim.length == 2) {
					dimRows = Integer.parseInt(dim[0]);
					dimCols = Integer.parseInt(dim[1]);
				} else {
					throw new ParseException("Invalid dimension: " + dimVal);
				}
			} catch (NumberFormatException e) {
				throw new ParseException("Invalid dimension: " + dimVal);
			}
		}
	}

	/**
	 * Construye la opcion CLI -h.
	 * @return CLI {@link {@link Option} for the help option.
	 *         <p>
	 *         Objeto {@link Option} de esta opcion.
	 */

	private static Option constructHelpOption() {
		return new Option("h", "help", false, "Print this message");
	}

	/**
	 * Extrae la opcion help (-h) que imprime informacion de uso del programa en
	 * la salida estandar.
	 * @param line
	 *            * CLI {@link CommandLine} object.
	 * @param cmdLineOptions
	 *            CLI {@link Options} object to print the usage information.
	 * 
	 */
	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	/**
	 * Método para iniciar un juego con el controlador {@link ConsoleCtrl}, no
	 * basado en MVC. Solo se utiliza para mostrar las diferencias con el
	 * controlador MVC.
	 */
	public static void startGameNoMVC() {
		Game g = new Game(gameFactory.gameRules());
		Controller c = null;

		switch (view) {
		case CONSOLE:
			ArrayList<Player> players = new ArrayList<Player>();
			for (int i = 0; i < pieces.size(); i++) {
				switch (playerModes.get(i)) {
				case AI:
					players.add(gameFactory.createAIPlayer(aiPlayerAlg));
					break;
				case MANUAL:
					players.add(gameFactory.createConsolePlayer());
					break;
				case RANDOM:
					players.add(gameFactory.createRandomPlayer());
					break;
				default:
					throw new UnsupportedOperationException(
							"Something went wrong! This program point should be unreachable!");
				}
			}
			c = new ConsoleCtrl(g, pieces, players, new Scanner(System.in));
			break;
		case WINDOW:
			throw new UnsupportedOperationException(
					"Swing Views are not supported in startGameNoMVC!! Please use startGameMVC instead.");
		default:
			throw new UnsupportedOperationException("Something went wrong! This program point should be unreachable!");
		}
		c.start();
	}

	/**
	 * Inicia un juego. Debe llamarse despues de {@link #parseArgs(String[])}
	 * para que los atributos tengan los valores correctos.
	 */
	public static void startGame() {
		Game g = new Game(gameFactory.gameRules());
		Controller c = null;

		switch (view) {
		case CONSOLE:
			ArrayList<Player> players = new ArrayList<Player>();
			for (int i = 0; i < pieces.size(); i++) {
				switch (playerModes.get(i)) {
				case AI:
					players.add(gameFactory.createAIPlayer(aiPlayerAlg));
					break;
				case MANUAL:
					players.add(gameFactory.createConsolePlayer());
					break;
				case RANDOM:
					players.add(gameFactory.createRandomPlayer());
					break;
				default:
					throw new UnsupportedOperationException(
							"Something went wrong! This program point should be unreachable!");
				}
			}
			c = new ConsoleCtrlMVC(g, pieces, players, new Scanner(System.in));
			gameFactory.createConsoleView(g, c);
			break;
		case WINDOW:
			c = new Controller(g, pieces);
				if(multiviews)
					for(Piece p: pieces)
						gameFactory.createSwingView(g, c, p, gameFactory.createRandomPlayer(), gameFactory.createAIPlayer(aiPlayerAlg));
				else
					gameFactory.createSwingView(g, c, null, gameFactory.createRandomPlayer(), gameFactory.createAIPlayer(aiPlayerAlg));
				break;
		default:
			throw new UnsupportedOperationException("Something went wrong! This program point should be unreachable!");
		}
		c.start();
	}

	/**
	 * Metodo main. Llama a {@link #parseArgs(String[])} y a continuacion inicia
	 * un juego con {@link #startGame()}.
	 * @param args
	 *            Command-line arguments.
	 */
	public static void main(String[] args) {
		parseArgs(args);
		switch(applicationMode) {
		case NORMAL:
			startGame();
			break;
		case CLIENT:
			startClient();
			break;
		case SERVER:
			startServer();
		break;
		}
	}
	
	private static void startClient() {
		try {
			GameClient c = new GameClient(serverHost, serverPort);
			gameFactory = c.getGameFactory();
			gameFactory.createSwingView(c, c, c.getPlayerPiece(), gameFactory.createRandomPlayer(), gameFactory.createAIPlayer(aiPlayerAlg)); // Da problemas en InvokeAndWait de las factorias extendidas
			c.start();
		} catch(Exception e) {
			System.err.println("Something went wrong trying to start a client" + e.getMessage()); // Aqui es donde se muestra el mensaje de error
		}
	}
	
	private static void startServer() {
		GameServer c = new GameServer(gameFactory, pieces, serverPort);
		c.start();
	}
}


