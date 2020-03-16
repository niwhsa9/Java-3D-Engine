package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TestScene extends Scene {


	double pitch = 0;
	double roll = 0;
	double yaw = 0; 
	
	public void paintComponent(Graphics g) {
		
		//if(Input.keysPressed[Constants.KEY_A]) pitch+=0.1;
		
		Graphics2D g2d = (Graphics2D) g;
		Graphics3D g3d = new Graphics3D(g2d, 30, 0.1, 10);
		
		
		
		GameObject cube = g3d.getCube(0, 0, 0, 1.0);
		cube.setEulerAngles(0, 0, 0);
		cube.setPos(0.0, 0.0, 2.0); 
		
		//g3d.view.lookAt(new Vec3d(0.0, 0, 1.0));
		//System.out.println(cube.model);
		g3d.drawWireFrame(cube); 
		
		Vec3d[] pV = new Vec3d[] {
				new Vec3d(-0.5, 0, -0.5),
				new Vec3d(0.5, 0, -0.5),
			    new Vec3d(0.5, 0, 0.5),
				new Vec3d(-0.5, 0, 0.5),
				new Vec3d(0.0, 0.5, 0.0),
		};
		
		int[][] pF = new int[][] {
			new int[] {0, 1, 2, 3},
			new int[] {0, 1, 4},
			new int[] {1, 2, 4},
		
			new int[] {2, 3, 4},
			
		};
		GameObject pyramid = new GameObject(pV, pF);
		g3d.drawWireFrame(pyramid);

		
		
		
		
		
		
	}
}
