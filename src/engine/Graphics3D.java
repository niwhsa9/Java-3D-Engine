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
		int[][] u = new int[obj.mesh.length][];
		int[][] v = new int[obj.mesh.length][];
		
		for(int i = 0; i < obj.mesh.length; i++) {
			u[i] = new int[obj.mesh[i].length];
			v[i] = new int[obj.mesh[i].length];
			for(int q = 0; q < obj.mesh[i].length; q++) {
				Mat p = obj.mesh[i][q].getMat();
				p = p.lmul(obj.model);//.lmul(proj);//(p.lmul(obj.model)).lmul(proj);//.lmul(proj);
				//System.out.println("x " + p.data[0] + " y " + p.data[1] + " z " +  p.data[2]);
				p = p.lmul(view);
				p = p.lmul(proj);
			
				int x = (int) ((p.data[0]/p.data[3]) * Constants.WindowDims.width/2); 
				int y = (int) ((p.data[1]/p.data[3]) * Constants.WindowDims.width/2); 
			
			
				y = Constants.WindowDims.height/2 + y; 
				x = Constants.WindowDims.width/2 + x; 
				u[i][q] = x;
				v[i][q] = y;
			}
		}
		
		for(int i = 0; i < u.length ; i++) {
			
			for(int q = 1; q < u[i].length; q++) {
				g2d.drawLine(u[i][q], v[i][q], u[i][q-1], v[i][q-1]);
			}
			g2d.drawLine(u[i][0], v[i][0], u[i][u[i].length-1], v[i][u[i].length-1]);
		}
	}
	
	public GameObject getCube(double centerX, double centerY, double centerZ, double side) {
		Vec4d[][] mesh = new Vec4d[6][4];
		
		double halfSide = side/2;
		
		//front
		mesh[0][0] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[0][1] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[0][2] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		mesh[0][3] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		//back
		
		mesh[1][0] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[1][1] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[1][2] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		mesh[1][3] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		//left 
		
		mesh[2][0] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[2][1] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[2][2] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		mesh[2][3] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		//right
		mesh[3][0] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[3][1] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[3][2] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		mesh[3][3] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		//top
		mesh[4][0] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[4][1] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ - halfSide, 1.0);
		mesh[4][2] = new Vec4d(centerX + halfSide, centerY + halfSide, centerZ + halfSide, 1.0);
		mesh[4][3] = new Vec4d(centerX - halfSide, centerY + halfSide, centerZ + halfSide, 1.0);		
		//bot
		mesh[5][0] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		mesh[5][1] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ - halfSide, 1.0);
		mesh[5][2] = new Vec4d(centerX + halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		mesh[5][3] = new Vec4d(centerX - halfSide, centerY - halfSide, centerZ + halfSide, 1.0);
		
		
		
		return new GameObject(mesh);
		
	}

	

	

}