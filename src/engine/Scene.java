package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JPanel;

public class Scene extends JPanel{
	boolean isDone = false; 
	public Scene() {
		
	}
	
	public void update(double dt) {
	}
	
	public void drawCenteredString(Graphics2D g2d, String s, Font f, int x, int y) {
		int w = g2d.getFontMetrics(f).stringWidth(s);
		int h = g2d.getFontMetrics(f).getHeight();
		g2d.setFont(f);
		g2d.drawString(s, x - w/2, y - h/2);

	}
	

	public static void drawPolygon(Graphics2D g2d, Mat[] polyData, Mat transform, Color c) {
		Polygon p = new Polygon();
		for(int i = 0; i < polyData.length; i++) {
			//polyData[i] = Mat.dialationMat3x3(0.5).multiply(polyData[i]);
		    //polyData[i] = polyData[i].lmul(Mat.translationMat3x3(10, 10));
			Mat q = new Mat(3, 1, new double[] {polyData[i].getElem(0, 0), polyData[i].getElem(1, 0), polyData[i].getElem(2, 0)});

			q = q.lmul(transform);
			//System.out.println(q);

			p.addPoint((int)q.getElem(0, 0),(int)q.getElem(1, 0));
		}
		g2d.setColor(c);
		g2d.drawPolygon(p);
	}
	
	public static void fillPolygon(Graphics2D g2d, Mat[] polyData, Mat transform, Color c) {
		Polygon p = new Polygon();
		for(int i = 0; i < polyData.length; i++) {
			//polyData[i] = Mat.dialationMat3x3(0.5).multiply(polyData[i]);
		    //polyData[i] = polyData[i].lmul(Mat.translationMat3x3(10, 10));
			Mat q = new Mat(3, 1, new double[] {polyData[i].getElem(0, 0), polyData[i].getElem(1, 0), polyData[i].getElem(2, 0)});

			q = q.lmul(transform);
			//System.out.println(q);

			p.addPoint((int)q.getElem(0, 0),(int)q.getElem(1, 0));
		}
		g2d.setColor(c);
		g2d.fillPolygon(p);
	}
	public static void drawMesh(Graphics2D g2d, Mat[][] mesh, Mat transform, Color[] c) {
		for(int i = 0; i < mesh.length; i++) drawPolygon(g2d, mesh[i], transform, c[i]);
	}
	public static void fillMesh(Graphics2D g2d, Mat[][] mesh, Mat transform, Color[] c) {
		for(int i = 0; i < mesh.length; i++) fillPolygon(g2d, mesh[i], transform, c[i]);
	}
}
