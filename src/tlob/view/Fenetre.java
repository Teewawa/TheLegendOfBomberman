package tlob.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import tlob.model.*;
import tlob.controller.*;

public class Fenetre extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private Panel panel;
	private GameController controller;
	
	public Fenetre(Level level, final GameController controller, LoadIAD loadIAD)  {
	    setVisible(true) ;
	    setSize(800, 15*42+117);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.controller = controller;
		addKeyListener(controller);
		panel = new Panel(level, loadIAD);
		getContentPane().add(panel);
		Timer timer = new Timer(30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.update();
				repaint();
			}
		});
		timer.start();
	}
	
	public void setGameController (GameController controller){
		this.controller = controller;
	}


}
