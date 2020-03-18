package engine;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public class GameObject {
	Vec3d[] vertex;
	Vec2d[] texVertex;

	int[][] face;
	int[][] textureCoord; 
	ModelMat model;
	Vec4d tvec;
	Vec4d velo;
	Vec4d rvec; // encode Euler angles
	double scale = 1.0;
	Color[][] vertexColor;
	BufferedImage texture; 
	byte[] textureData; 


	public GameObject(Vec3d[] vertex, int[][] face, Color[][] vertexColor) {
		this.vertex = vertex;
		this.face = face;
		this.vertexColor = vertexColor;
		// this.faceSize = faceSize;
		this.rvec = new Vec4d(0, 0, 0, 0);
		this.tvec = new Vec4d(0, 0, 0, 0);
		this.model = new ModelMat(0, 0, 0, 0, 0, 0, scale);
		triangularize();
	}
	
	public GameObject(Vec3d[] vertex, int[][] face) {
		this.vertex = vertex;
		this.face = face;
		this.vertexColor = new Color[face.length][];
		for(int i = 0; i < face.length ;i++ ) {
			vertexColor[i] = new Color[face[i].length];
			for(int q = 0; q < face[i].length; q++) {
				vertexColor[i][q] = Color.red;
			}
		}
		// this.faceSize = faceSize;
		this.rvec = new Vec4d(0, 0, 0, 0);
		this.tvec = new Vec4d(0, 0, 0, 0);
		this.model = new ModelMat(0, 0, 0, 0, 0, 0, scale);
		triangularize();
	}

	public static GameObject readFromFile(String file, String tex) {

		try {
			InputStream objInputStream = new FileInputStream(file);
			Obj obj = ObjReader.read(objInputStream);
			obj = ObjUtils.triangulate(obj);
			

			Vec3d[] oV = new Vec3d[obj.getNumVertices()];
			Vec2d[] tV = new Vec2d[obj.getNumTexCoords()];
			

			for (int i = 0; i < obj.getNumVertices(); i++) {
				oV[i] = new Vec3d(obj.getVertex(i).getX(), obj.getVertex(i).getY(), obj.getVertex(i).getZ());
			}
			for(int i = 0; i < obj.getNumTexCoords(); i++) {
				tV[i] = new Vec2d(obj.getTexCoord(i).getX(), obj.getTexCoord(i).getY());
			}
			
			

			int[][] iV = new int[obj.getNumFaces()][];
			int[][] tF = new int[obj.getNumFaces()][];

			for (int i = 0; i < obj.getNumFaces(); i++) {
				iV[i] = new int[3];
				tF[i] = new int[3];
				for (int q = 0; q < 3; q++) {
					iV[i][q] = obj.getFace(i).getVertexIndex(q);
					tF[i][q] = obj.getFace(i).getTexCoordIndex(q);
				}
			}

			GameObject mesh = new GameObject(oV, iV);
			mesh.texVertex = tV;
			mesh.textureCoord = tF;
			System.out.println("loaded obj");
			
			try {
				mesh.texture = ImageIO.read(new File(tex));
				mesh.textureData = ((DataBufferByte) mesh.texture.getRaster().getDataBuffer()).getData();

			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return mesh; 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	//input normalized texture coord
	public Color getTexColor(double  xP, double yP) {
		int x = (int) (xP * (texture.getWidth()-1));
		int y = (int) (yP * (texture.getHeight()-1));
		
		int i = y * 3 * texture.getWidth() + x * 3; 
		return new Color(textureData[i+2] & 0xFF, textureData[i+1] & 0xFF, textureData[i] & 0xFF);
		

	}

	public void triangularize() {
		ArrayList<int[]> faces = new ArrayList<int[]>();
		ArrayList<Color[]> col = new ArrayList<Color[]>();

		for (int i = 0; i < face.length; i++) {
			if (face[i].length > 3) {
				for (int q = 2; q < face[i].length; q++) {
					faces.add(new int[] { face[i][q], face[i][q - 1], face[i][0] });
					col.add(new Color[] { vertexColor[i][q], vertexColor[i][q - 1], vertexColor[i][0]});
				}
			} else {
				faces.add(face[i]);
				col.add(vertexColor[i]);
			}
		}

		int[][] faceNew = new int[faces.size()][3];
		Color[][] colNew = new Color[col.size()][3];

		for (int i = 0; i < faces.size(); i++) {
			faceNew[i] = faces.get(i);
			colNew[i] = col.get(i);
		}
		vertexColor = colNew;
		face = faceNew;
		//System.exit(0);

	}

	public void setEulerAngles(double pitch, double roll, double yaw) {
		this.rvec.x = pitch;
		this.rvec.y = roll;
		this.rvec.z = yaw;

		this.model = new ModelMat(pitch, roll, yaw, tvec.x, tvec.y, tvec.z, scale);
	}

	public void setPos(double x, double y, double z) {
		this.tvec.x = x;
		this.tvec.y = y;
		this.tvec.z = z;

		this.model = new ModelMat(rvec.x, rvec.y, rvec.z, x, y, z, scale);
	}

	public void setScale(double s) {
		this.scale = s;
		this.model = new ModelMat(rvec.x, rvec.y, rvec.z, tvec.x, tvec.y, tvec.z, s);
	}

	public void setPose(double pitch, double roll, double yaw, double x, double y, double z) {
		setEulerAngles(pitch, roll, yaw);
		setPos(x, y, z);
	}

	// public void translateBy();
	// public void rotateBy

	public double getPitch() {
		return rvec.x;
	}

}
