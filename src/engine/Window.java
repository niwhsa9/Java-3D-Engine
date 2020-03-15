package engine;

import javax.swing.JFrame;

public class Window extends JFrame {
	public Window(String name, int width, int height) {
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		//Panel j = new Panel();
		//add(j);
	}
	
	//public void add()
}
