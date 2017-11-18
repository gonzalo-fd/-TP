package es.ucm.fdi.tp.practica5.damas;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.ataxx.AtaxxSwingPlayer;
import es.ucm.fdi.tp.practica5.common.FiniteRectBoardSwingView;

public class DamasSwingView extends FiniteRectBoardSwingView{

	private static final long serialVersionUID = 1L;
	
	private Integer rowOrig;
	private Integer colOrig;
	private int rowDest;
	private int colDest;
	private boolean ok;
	
	private DamasSwingPlayer player;
	
	public DamasSwingView(Observable<GameObserver> g, Controller c,
			Piece localPiece, Player randomPlayer, Player autoPlayer) {
		super(g, c, localPiece, randomPlayer, autoPlayer);
		this.ok = true;
	}

	@Override
	protected void handleMouseClick(int row, int col, int clickCount, int mouseButton) {
		
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
					player = new DamasSwingPlayer(rowOrig,colOrig,rowDest,colDest);//mejor ir actualizando que crearlo cada vez
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
