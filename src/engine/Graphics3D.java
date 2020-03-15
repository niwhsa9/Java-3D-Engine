package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graphics3D {
	
	Graphics2D g2d;
	ProjectionMat proj;
	CameraMat view; 
	double fov;
	double nearClip;
	double farClip;


	
	public Graphics3D(Graphics2D g2d, double fov, double nearClip, double farClip) {
		this.g2d = g2d; 
		this.nearClip = nearClip;
		this.farClip = farClip;
		proj = new ProjectionMat(Constants.WindowDims.width/Constants.WindowDims.height, fov, nearClip, farClip);
		view = new CameraMat();
	}
	

	
	public void drawWireFrame(GameObject obj) {
		//System.out.println("drawing wireframe");
		g2d.setColor(Color.RED);
		//int prevQ = 0;
		//System.out.println("-----------------");
		int[] u = new int[obj.mesh.length];
		int[] v = new int[obj.mesh.length];
		
		for(int i = 0; i < obj.mesh.length; i++) {
			Mat p = obj.mesh[i].getMat();
			p = p.lmul(obj.model);//.lmul(proj);//(p.lmul(obj.model)).lmul(proj);//.lmul(proj);
			//System.out.println("x " + p.data[0] + " y " + p.data[1] + " z " +  p.data[2]);
			p = p.lmul(view);
			p = p.lmul(proj);
			
			int x = (int) ((p.data[0]/p.data[3]) * Constants.WindowDims.width/2); 
			int y = (int) ((p.data[1]/p.data[3]) * Constants.WindowDims.width/2); 
			
			//System.out.println("x " + obj.mesh[i].x + " y " + obj.mesh[i].y);
			//System.out.println("x " + p.data[0] + " y " + p.data[1]);

			y = Constants.WindowDims.height/2 - y; 
			x = Constants.WindowDims.width/2 + x; 
			u[i] = x;
			v[i] = y;
			//g2d.fillRect(x, y, 5, 5);
		}
		
		for(int i = 0; i < u.length ; i++) {
			if(i % obj.faceSize == 0 && i + obj.faceSize-1 < u.length)  g2d.drawLine(u[i], v[i], u[i+3], v[i+3]);
			else g2d.drawLine(u[i], v[i], u[i-1], v[i-1]);
			
		}
	}
	
	public GameObject getCube(double centerX, double centerY, double centerZ, double side) {
		Vec4d[] mesh = new Vec4d[4 * 6];
		
		double halfSide = side/2;
		
		//front
		mesh[0] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[1] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[2] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		mesh[3] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		//back
		
		mesh[4] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[5] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[6] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		mesh[7] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		//left 
		
		mesh[8] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[9] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[10] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		mesh[11] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		//right
		mesh[12] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[13] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[14] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		mesh[15] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		//top
		mesh[16] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[17] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[18] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[19] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ + halfSide, 1.0);		
		//bot
		mesh[20] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		mesh[21] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		mesh[22] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		mesh[23] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		
		
		
		return new GameObject(mesh, 4);
		
	}

	

	

}