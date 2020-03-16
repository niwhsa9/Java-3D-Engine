package engine;

import java.awt.Color;
import java.awt.Font;
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

    Font c = new Font("Courier", Font.PLAIN, 18);

	
	public Graphics3D(Graphics2D g2d, double fov, double nearClip, double farClip) {
		this.g2d = g2d; 
		this.nearClip = nearClip;
		this.farClip = farClip;
		proj = new ProjectionMat(Constants.WindowDims.width/Constants.WindowDims.height, fov, nearClip, farClip);
		view = new CameraMat();
	}
	

	
	public void drawWireFrame(GameObject obj) {
		//System.out.println("drawing wireframe");
		//System.out.println(getTriangleArea(new Vec3d(0, 2, 0), new Vec3d(0, 1, 0), new Vec3d(3, 1, 0)));
		g2d.setColor(Color.RED);
		
		int[][] u = new int[obj.face.length][];
		int[][] v = new int[obj.face.length][];
		
		for(int i = 0; i < obj.face.length; i++) {
			u[i] = new int[obj.face[i].length];
			v[i] = new int[obj.face[i].length];
			for(int q = 0; q < obj.face[i].length; q++) {
				Mat p = obj.vertex[obj.face[i][q]].getHomogenous().getMat();
				p = p.lmul(obj.model).lmul(view).lmul(proj); 
		
			
				int x = (int) ((p.data[0]/p.data[3]) * Constants.WindowDims.width/2); 
				int y = (int) ((p.data[1]/p.data[3]) * Constants.WindowDims.width/2); 
			
			
				y = Constants.WindowDims.height/2 + y; 
				x = Constants.WindowDims.width/2 + x; 
				u[i][q] = x;
				v[i][q] = y;
				
				drawCenteredString(g2d, Math.round((p.data[2])*100)/100.0 +"", c, x, y);
			}
		}
		
		for(int i = 0; i < u.length ; i++) {
			
			for(int q = 1; q < u[i].length; q++) {
				g2d.drawLine(u[i][q], v[i][q], u[i][q-1], v[i][q-1]);
			}
			g2d.drawLine(u[i][0], v[i][0], u[i][u[i].length-1], v[i][u[i].length-1]);
		}
	}
	
	
	private double getTriangleArea(Vec3d a, Vec3d b, Vec3d c) {
		return 0.5 * ( (a.sub(c)).cross(b.sub(c))).getMag();
	}
	
	private Vec3d getBarycentricCoord(Vec3d p, Vec3d v0, Vec3d v1, Vec3d v2) {
		double area = getTriangleArea(v0, v1, v2);
		double lambda0 = getTriangleArea(v1, v2, p)/area;
		double lambda1 = getTriangleArea(v2, v0, p)/area;
		double lambda2 = getTriangleArea(v0, v1, p)/area;
		
		
		return new Vec3d(lambda0, lambda1, lambda2);
	}
	
	public void drawTriangle(Graphics2D g2d, Vec2d v0, Vec2d v1, Vec2d v2, Color c1, Color c2, Color c3) {
		int minX = (int)Math.min(Math.min(v0.x, v1.x), v2.x);
		int width = (int)Math.max(Math.max(v0.x, v1.x), v2.x) - minX;
		
		int minY = (int)Math.min(Math.min(v0.y, v1.y), v2.y);
		int height = (int)Math.max(Math.max(v0.y, v1.y), v2.y) - minY;
		
		for(int x = minX; x < minX+width; x++) {
			for(int y = minY; y < minY+height; y++) {
				Vec3d b = getBarycentricCoord(new Vec3d(x, y, 1.0), v0.getHomogenous(), v1.getHomogenous(), v2.getHomogenous());
				
				
				if(b.x + b.y + b.z <= 1.0) {
					Color co = new Color( (int) (b.x * c1.getRed() + b.y * c2.getRed() + b.z * c3.getRed()), 
							(int)(b.x * c1.getGreen() + b.y * c2.getGreen() + b.z * c3.getGreen()) , 
							(int)(b.x * c1.getBlue() + b.y * c2.getBlue() + b.z * c3.getBlue())) ;
					g2d.setColor(co);
					//System.out.println(co.x + " " + co.y + " " + co.z);
					g2d.fillRect(x, y, 1, 1);
				}
			}
		}
		
	}
	
	
	public GameObject getCube(double centerX, double centerY, double centerZ, double side) {
		double halfSide = side/2;

		Vec3d[] pV = new Vec3d[] {
				new Vec3d(centerX - halfSide, centerY + halfSide, centerZ + halfSide), 
				new Vec3d(centerX + halfSide, centerY + halfSide, centerZ + halfSide),
				new Vec3d(centerX + halfSide, centerY - halfSide, centerZ + halfSide),
				new Vec3d(centerX - halfSide, centerY - halfSide, centerZ + halfSide),
				
				new Vec3d(centerX - halfSide, centerY + halfSide, centerZ - halfSide), 
			    new Vec3d(centerX + halfSide, centerY + halfSide, centerZ - halfSide), 
				new Vec3d(centerX + halfSide, centerY - halfSide, centerZ - halfSide), 
				new Vec3d(centerX - halfSide, centerY - halfSide, centerZ - halfSide),
		};
		
		int[][] pF = new int[][] {
			new int[] {0, 1, 2, 3},
			new int[] {4, 5, 6, 7},
			new int[] {4, 0, 3, 7},
			new int[] {5, 1, 2, 6},
			new int[] {4, 5, 1, 0},
			new int[] {7, 6, 2, 3}
		};
		GameObject cube = new GameObject(pV, pF);
		return cube;
		
		/*
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
		
		
		
		return new GameObject(mesh); */
	
		
	}

	public void drawCenteredString(Graphics2D g2d, String s, Font f, int x, int y) {
		int w = g2d.getFontMetrics(f).stringWidth(s);
		int h = g2d.getFontMetrics(f).getHeight();
		g2d.setFont(f);
		g2d.drawString(s, x - w/2, y - h/2);

	}
	

	

}