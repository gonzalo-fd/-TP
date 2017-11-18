package es.ucm.fdi.tp.practica5.ataxx;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.Practica4.ataxx.AtaxxFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxFactoryExt extends AtaxxFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtaxxFactoryExt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AtaxxFactoryExt(int dim, int obstacles) {
		super(dim, obstacles);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,final Player random, final Player auto) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
			
			@Override
			public void run() {
				new AtaxxSwingView(g, c, viewPiece, random, auto);
			}
			
});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
