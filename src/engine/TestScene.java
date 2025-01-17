package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.InputStream;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public class TestScene extends Scene {


	double pitch = 0;
	double roll = 0;
	double yaw = 0; 
	double x = 0;
	
	GameObject mesh;
    Font c = new Font("Courier", Font.PLAIN, 18);

	
	public TestScene() {
		readObj();
	}
	
	double prevTime;
	
	public void readObj() {
		mesh = GameObject.readFromFile("Crate1.obj", "crate_1.jpg");
		
	}
	
	public void paintComponent(Graphics g) {
		
		//if(Input.keysPressed[Constants.KEY_A]) pitch+=0.1;
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		
		Graphics3D g3d = new Graphics3D(g2d, 30, 0.1, 20);
		
		double cur = System.currentTimeMillis()/1000.0;
		g2d.setColor(Color.RED);
		super.drawCenteredString(g2d, "" + 1.0/(cur-prevTime), c, Constants.WindowDims.width - 50, 50);
		prevTime = cur;
		
		GameObject cube = g3d.getCube(0, 0, 0, 1.0);
		cube.setEulerAngles(pitch, roll, yaw);
		cube.setPos(x, 0.0, 2.0);
		
		if(Input.keysPressed[Constants.KEY_D]) pitch+=0.1;
		if(Input.keysPressed[Constants.KEY_A]) pitch-=0.1;
		
		if(Input.keysPressed[Constants.KEY_W]) roll+=0.1;
		if(Input.keysPressed[Constants.KEY_S]) roll-=0.1;

		if(Input.keysPressed[Constants.KEY_E]) yaw+=0.1;
		if(Input.keysPressed[Constants.KEY_Q]) yaw-=0.1;
		
		if(Input.keysPressed[Constants.KEY_I]) x-=0.06;
		if(Input.keysPressed[Constants.KEY_K]) x+=0.06;

		
		g3d.view.lookAt(new Vec3d(0, 0, 0));
		//g3d.view.setPos(new Vec3d(0.0, 0.0, x));
		//System.out.println(g3d.view.pos.z - cube.tvec.z);
		//cube.setScale(2.0);
		
		//g3d.view.lookAt(new Vec3d(0.0, 0, 1.0));
		//System.out.println(cube.model);
		//g3d.drawWireFrame(cube); 
		
		
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
		//pyramid.setEulerAngles(Math.PI, Math.PI, 0);
		pyramid.setPos(-2.0, -1.0, 1.0);
		//g3d.drawWireFrame(pyramid);
		mesh.setScale(0.3);
		mesh.setEulerAngles(pitch, roll, yaw);

		g3d.renderMesh(mesh, true);
		
		//System.out.println(mesh.getTexColor(0.64375, 0.328125).getGreen());
		//g3d.drawWireFrame(pyramid);
		//g3d.drawWireFrame(mesh);
		//g3d.drawTriangle(g2d, new Vec2d(100, 100), new Vec2d(300, 500), new Vec2d(600, 100), Color.RED, Color.GREEN, Color.YELLOW);
		
		/* TODO 
		 * clip cul
		 * Textures 
		 * Lighting 
		 * skybox
		 * Track
		 * perspective correct texture 
		 */
		
		
		
		
		
	}
	
	
	
}
