package es.ucm.fdi.tp.practica5.ataxx;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.common.FiniteRectBoardSwingView;



public class AtaxxSwingView extends FiniteRectBoardSwingView {
	
	/**
	 * 
	 */
	
	private Integer rowOrig;
	private Integer colOrig;
	private int rowDest;
	private int colDest;
	private boolean ok;
	
	
	private static final long serialVersionUID = 1L;
	
	private AtaxxSwingPlayer player;
	
	public AtaxxSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randomPlayer,
			Player autoPlayer) {
		
		super(g, c, localPiece, randomPlayer, autoPlayer);
		this.ok = true;
		
	}

	@Override
	protected void handleMouseClick(int row, int col, int clickCount, int mouseButton) {
	 // do nothing if the board is not active	
		//new AtaxxSwingPlayer();
		if(ok){
			if(mouseButton == 1){
				rowOrig = row;
				colOrig = col;
				ok = false;
				addContentToStatusArea("Your choice is: " + row + "x" + col);
				addContentToStatusArea("Click on the destination position");
				
			}
		}
			else{
				if(mouseButton == 1){
					rowDest = row;
					colDest = col;
					player = new AtaxxSwingPlayer(rowOrig,colOrig,rowDest,colDest);//mejor ir actualizando que crearlo cada vez
					super.decideMakeManualMove(player);
					ok = true;
				}
				else if(mouseButton == 3){
					rowOrig= null;
					colOrig= null;
					ok = true;
					addContentToStatusArea("You have cancel your choice");
				}	
				
			}
	}

	@Override
	protected void activateBoard() {
		inPlay = true;
	}

	@Override
	protected void deActivateBoard() {
		inPlay = false;
	}
}
