package es.ucm.fdi.tpPractica4.damas;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class DamasMove extends GameMove{

	private static final long serialVersionUID = 1L;
	protected int rowDest;
	protected int rowOrig;
	protected int colDest;
	protected int colOrig;
	
	public DamasMove(){		
	}
	
	public DamasMove(int rowOrig, int colOrig, int rowDest, int colDest, Piece p) {
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
				if(board.getPosition(rowOrig, colOrig) == pieces.get(0)){
				
					if((rowDest == (rowOrig +1)) &&
							(rowDest >= 0 && rowDest <= 9) &&
							(colDest >= 0 && colDest <= 9) &&
							((colDest == colOrig +1) || (colDest == colOrig -1))){
						board.setPosition (rowOrig, colOrig, null);
						board.setPosition(rowDest, colDest, getPiece());
					}
					else if((rowDest == (rowOrig +2)) &&
							((rowDest >= 0 && rowDest <= 9) &&
							(colDest >= 0 && colDest <= 9)) &&
							((colDest == colOrig +2) || (colDest == colOrig -2)))
							{
					
						
							if(colDest > colOrig){
								if(board.getPosition(rowOrig + 1, colOrig + 1) == pieces.get(1)){
									board.setPosition(rowOrig + 1, colOrig + 1, null);
									board.setPosition(rowDest, colDest, getPiece());
									board.setPosition(rowOrig, colOrig, null);
									board.setPieceCount(pieces.get(1), board.getPieceCount(pieces.get(1))-1);
								}
							}
							else if (colDest < colOrig){
								if(board.getPosition(rowOrig + 1, colOrig - 1) == pieces.get(1)){
									board.setPosition(rowOrig + 1, colOrig - 1, null);
									board.setPosition(rowDest, colDest, getPiece());
									board.setPosition(rowOrig, colOrig, null);
									board.setPieceCount(pieces.get(1), board.getPieceCount(pieces.get(1))-1);
								}
							}
							else{
								throw new GameError("position (" + rowDest + "," + colDest + ") is not a valid move!");
							}
						
					}
					else{
						throw new GameError("position (" + rowDest + "," + colDest + ") is not a valid move!");
					}
				}
				else if(board.getPosition(rowOrig, colOrig) == pieces.get(1)){
				
					if((rowDest == (rowOrig-1)) &&
							(rowDest >= 0 && rowDest <= 9) &&
							(colDest >= 0 && colDest <= 9) &&
							((colDest == colOrig +1) || (colDest == colOrig -1))){
						board.setPosition (rowOrig, colOrig, null);
						board.setPosition(rowDest, colDest, getPiece());
					}
					else if((rowDest == (rowOrig-2)) &&
							((rowDest >= 0 && rowDest <= 9) &&
							(colDest >= 0 && colDest <= 9)) &&
							((colDest == colOrig +2) || (colDest == colOrig -2))) 						
							{						
							if(colDest > colOrig){
								if(board.getPosition(rowOrig - 1, colOrig + 1) == pieces.get(0)){
									board.setPosition(rowOrig - 1, colOrig + 1, null);
									board.setPosition(rowDest, colDest, getPiece());
									board.setPosition(rowOrig, colOrig, null);
									board.setPieceCount(pieces.get(0), board.getPieceCount(pieces.get(0))-1);
								}
							}
							else if (colDest < colOrig){
								if(board.getPosition(rowOrig - 1, colOrig - 1) ==pieces.get(0)){
								board.setPosition(rowOrig - 1, colOrig - 1, null);
								board.setPosition(rowDest, colDest, getPiece());
								board.setPosition(rowOrig, colOrig, null);
								board.setPieceCount(pieces.get(0), board.getPieceCount(pieces.get(0))-1);
								}
							}
							else{
								throw new GameError("position (" + rowDest + "," + colDest + ") is not a valid move!");
							}
					}
				
					else{
						throw new GameError("position (" + rowDest + "," + colDest + ") is not a valid move!");
					}		}
			}
				else{
					throw new GameError("position (" + rowOrig + "," + colOrig + ") is not your piece!");
				}
			
		} else {
			throw new GameError("position (" + rowDest + "," + colDest + ") is already occupied!");
		}	
	
	}
	
	protected GameMove createMove(int rowOrig, int colOrig, int rowDest, int colDest, Piece p) {
		return new DamasMove(rowOrig, colOrig, rowDest, colDest, p);
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
