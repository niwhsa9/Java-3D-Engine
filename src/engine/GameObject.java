package engine;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public class GameObject {
	Vec4d[][] mesh;// = new Vec4d();
	Vec3d[] vertex;
	int[][] face;
	ModelMat model;
	Vec4d tvec;
	Vec4d velo;
	Vec4d rvec; // encode Euler angles
	double scale = 1.0;

	public GameObject(Vec3d[] vertex, int[][] face) {
		this.vertex = vertex;
		this.face = face;
		// this.faceSize = faceSize;
		this.rvec = new Vec4d(0, 0, 0, 0);
		this.tvec = new Vec4d(0, 0, 0, 0);
		this.model = new ModelMat(0, 0, 0, 0, 0, 0, scale);
		triangularize();
	}

	public static GameObject readFromFile(String file) {

		try {
			InputStream objInputStream = new FileInputStream(file);
			Obj obj = ObjReader.read(objInputStream);
			obj = ObjUtils.triangulate(obj);

			Vec3d[] oV = new Vec3d[obj.getNumVertices()];

			for (int i = 0; i < obj.getNumVertices(); i++) {
				oV[i] = new Vec3d(obj.getVertex(i).getX(), obj.getVertex(i).getY(), obj.getVertex(i).getZ());
			}

			int[][] iV = new int[obj.getNumFaces()][];

			for (int i = 0; i < obj.getNumFaces(); i++) {
				iV[i] = new int[3];
				for (int q = 0; q < 3; q++) {
					iV[i][q] = obj.getFace(i).getVertexIndex(q);
				}
			}

			GameObject mesh = new GameObject(oV, iV);
			System.out.println("loaded obj");
			return mesh; 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void triangularize() {
		ArrayList<int[]> faces = new ArrayList<int[]>();

		for (int i = 0; i < face.length; i++) {
			if (face[i].length > 3) {
				for (int q = 2; q < face[i].length; q++) {
					faces.add(new int[] { face[i][q], face[i][q - 1], face[i][0] });
				}
			} else
				faces.add(face[i]);
		}

		int[][] faceNew = new int[faces.size()][3];

		for (int i = 0; i < faces.size(); i++)
			faceNew[i] = faces.get(i);

		face = faceNew;

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
