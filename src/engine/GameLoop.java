package engine;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.SwingUtilities;

public class GameLoop {

	/* Top Level Graphics */
	static Window window;
    static Queue<Scene> sceneQueue = new LinkedList<Scene>(); 
	static Scene scene;
	
	/* Game Objects */
	


	public static void main(String[] args) {
		/** Graphics Thread **/
		new Thread(new Runnable() {

			@Override
			public void run() {
				//Initialize window
				window = new Window(Constants.WindowName, (int)Constants.WindowDims.getWidth(), (int)Constants.WindowDims.getHeight());
				window.getContentPane().setPreferredSize(Constants.WindowDims);
				window.pack();
				Input input = new Input();
				window.addKeyListener(input);
				
				sceneQueue.add(new TestScene());
				//sceneQueue.add(new Scene());

				while(!sceneQueue.isEmpty()) {
					scene = sceneQueue.remove();
					window.add(scene);
					while(!scene.isDone) {
						scene.revalidate();
						scene.repaint();
						//System.out.println("in scene" + System.currentTimeMillis());
					}
					window.remove(scene);
				}
				
				
			}
		}).start();

		/** World Thread **/
		while(scene == null) System.out.print("");
		//Game loop
		
		double prevTime = System.currentTimeMillis()/1000.0;
		while (true) {
			try {
				//SoundDriver.playHit();
				double currentTime = System.currentTimeMillis()/1000.0;
				scene.update(currentTime-prevTime);
				prevTime = currentTime;
				Thread.sleep(Constants.GamePeriod);
			} catch (Exception e) {}
		}
		
		/** Network Thread **/

	}

}