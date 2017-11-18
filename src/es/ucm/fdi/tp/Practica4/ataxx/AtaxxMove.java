package es.ucm.fdi.tp.Practica4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

/**
 * A Class representing a move for Ataxx.
 * 
 * <p>
 * Clase para representar un movimiento del juego Ataxx.
 * 
 */

public class AtaxxMove extends GameMove {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The row where to place the piece return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int rowDest;
	
	/**
	 * Fila de origen de la ficha que se quiere mover.
	 */
	protected int rowOrig;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int colDest;
	
	/**
	 * Columna de origen de la ficha que se quiere mover.
	 */
	protected int colOrig;
	
	/**
	 * This constructor should be used ONLY to get an instance of
	 * {@link ConnectNMove} to generate game moves from strings by calling
	 * {@link #fromString(String)}
	 * 
	 * <p>
	 * Solo se debe usar este constructor para obtener objetos de
	 * {@link ConnectNMove} para generar movimientos a partir de strings usando
	 * el metodo {@link #fromString(String)}
	 * 
	 */

	public AtaxxMove() {
	}

	/**
	 * Constructs a move for placing a piece of the type referenced by {@code p}
	 * at position ({@code row},{@code col}).
	 * 
	 * <p>
	 * Construye un movimiento para colocar una ficha del tipo referenciado por
	 * {@code p} en la posicion ({@code row},{@code col}).
	 * 
	 * @param rowOrig
	 *            Number of the source row.
	 *            <p>
	 *            Numero de la fila de origen.
	 * @param rowDest
	 * 			Number of the destination row.
	 * 			<p>
	 * 			Numero de la fila de destino.
	 * @param colOrig
	 *            Number of the source column.
	 *            <p>
	 *            Numero de la columna de origen.
	 * @param colDest
	 * 			Number of the destination column.
	 * 			<p>
	 * 			Numero de la columna de destino.
	 * @param p
	 *            A piece to be place at ({@code rowDest},{@code colDest}).
	 *            <p>
	 *            Ficha a colocar en ({@code rowDest},{@code colDest}).
	 */
	public AtaxxMove(int rowOrig, int colOrig, int rowDest, int colDest, Piece p) {
		super(p);
		
		this.rowOrig = rowOrig;
		this.colOrig = colOrig;
		this.rowDest = rowDest;
		this.colDest = colDest;
	}


	@Override
	public void execute(Board board, List<Piece> pieces) {

		if (board.getPosition(rowDest, colDest) == null){
			
			if(board.getPosition(rowOrig, colOrig) == getPiece()){
			
				if(Math.abs(rowOrig - rowDest) <= 1 && Math.abs(colOrig - colDest) <= 1){
					board.setPosition(rowDest, colDest, getPiece());
					board.setPieceCount(getPiece(), board.getPieceCount(getPiece())+1);
				}
				else if(Math.abs(rowOrig - rowDest) <= 2 && Math.abs(colOrig - colDest) <= 2){
					board.setPosition (rowOrig, colOrig, null);
					board.setPosition(rowDest, colDest, getPiece());
					
				}
				else{
					throw new GameError("position (" + rowDest + "," + colDest + ") is not a valid move!");
				}
			}
			else{
				throw new GameError("position (" + rowDest + "," + colDest + ") is not your piece!");
			}
			
			for (int i = Math.max(rowDest - 1, 0); i <= Math.min(rowDest + 1, board.getRows() - 1); i++) {
				for (int j = Math.max(colDest - 1, 0); j <= Math.min(colDest + 1, board.getCols() - 1); j++){
					if(board.getPosition(i, j) != null && pieces.contains(board.getPosition(i, j))){
						board.setPieceCount(board.getPosition(i, j), board.getPieceCount(board.getPosition(i, j))-1);
						board.setPosition(i, j, getPiece());
						board.setPieceCount(getPiece(), board.getPieceCount(getPiece())+1);			
					}
				}
			}
				
		} else {
			throw new GameError("position (" + rowDest + "," + colDest + ") is already occupied!");
		}	
	}
	
	/**
	 * Creates a move that is called from {@link #fromString(Piece, String)}.
	 * Separating it from that method allows us to use this class for other
	 * similar games by overriding this method.
	 * 
	 * <p>
	 * Crea un nuevo movimiento con la misma ficha utilizada en el movimiento
	 * actual. Llamado desde {@link #fromString(Piece, String)}; se separa este
	 * metodo del anterior para permitir utilizar esta clase para otros juegos
	 * similares sobrescribiendo este metodo.
	 * 
	 * @param rowDest
	 *            Row of the move being created.
	 *            <p>
	 *            Fila del nuevo movimiento.
	 * @param rowOrig
	 *            Row of the piece that is going to be moved.
	 *            <p>
	 *            Fila de la ficha que se va a mover.
	 * @param colDest
	 *            Column of the move being created.
	 *            <p>
	 *            Columna del nuevo movimiento.
	 * @param colOrig
	 *            Column of the piece that is going to be moved.
	 *            <p>
	 *            Columna de la ficha que se va a mover.
	 *            
	 */
	protected GameMove createMove(int rowOrig, int colOrig, int rowDest, int colDest, Piece p) {
		return new AtaxxMove(rowOrig, colOrig, rowDest, colDest, p);
	}

	@Override
	public GameMove fromString(Piece p, String str) {
		
		String[] words = str.split(" ");
		if (words.length != 4) {
			return null;
		}
		try {
			int rowOrig, colOrig, rowDest, colDest;
			rowOrig = Integer.parseInt(words[0]);
			colOrig = Integer.parseInt(words[1]);
			rowDest = Integer.parseInt(words[2]);
			colDest = Integer.parseInt(words[3]);
			return createMove (rowOrig, colOrig, rowDest, colDest, p);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String help() {
		return "Row and column for origin and for destination, separated by spaces (four numbers).";
	}
}
