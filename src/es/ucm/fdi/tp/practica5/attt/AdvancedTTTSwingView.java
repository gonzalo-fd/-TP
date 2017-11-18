package es.ucm.fdi.tp.practica5.attt;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.common.FiniteRectBoardSwingView;

public class AdvancedTTTSwingView extends FiniteRectBoardSwingView{

	private Integer rowOrig;
	private Integer colOrig;
	private int cont = 0;
	private boolean ok;
	int movs;
	
	private AdvancedTTTSwingPlayer Player;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdvancedTTTSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randomPlayer, Player autoPlayer) {
		// TODO Auto-generated constructor stub
		super(g, c, localPiece, randomPlayer, autoPlayer);
		this.movs = 0;
	}

	@Override
	protected void handleMouseClick(int row, int col, int clickCount, int mouseButton) {
		// TODO Auto-generated method stub
		if (getRestart()){
			cont = 0;
			setRestart(false);
		}
		movs = getPieces().size()*3;
		if(cont >= movs){
			if(mouseButton == 1 && !ok){
				this.ok = true;
				this.rowOrig = row;
				this.colOrig = col;
				addContentToStatusArea("Your choice is: " + row + "x" + col);
				addContentToStatusArea("Click on the destination position");
			}
			else if(mouseButton == 1 && ok){
				this.ok = false;
				Player = new AdvancedTTTSwingPlayer(rowOrig, colOrig, row, col);
				super.decideMakeManualMove(Player);
				
			}
			else if(mouseButton == 3){
				ok = false;
				this.rowOrig = null;
				this.colOrig = null;
				addContentToStatusArea("You have cancel your choice");
			}
		}
		else{
			
			Player = new AdvancedTTTSwingPlayer(0, 0, row, col);
			super.decideMakeManualMove(Player);
			this.cont++;
		}	
	}

	@Override
	protected void activateBoard() {
		// TODO Auto-generated method stub
		inPlay = true;
	}

	@Override
	protected void deActivateBoard() {
		// TODO Auto-generated method stub
		inPlay = false;
	}

}
