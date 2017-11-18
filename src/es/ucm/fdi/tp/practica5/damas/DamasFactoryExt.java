package es.ucm.fdi.tp.practica5.damas;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tpPractica4.damas.DamasFactory;

public class DamasFactoryExt extends DamasFactory{

	private static final long serialVersionUID = 1L;

	public DamasFactoryExt() {
		super();
	}
	
	@Override
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,final Player random, final Player auto) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
			
			@Override
			public void run() {
				new DamasSwingView(g, c, viewPiece, random, auto);
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
