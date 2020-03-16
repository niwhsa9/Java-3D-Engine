package engine;

import java.util.ArrayList;

public class GameObject {
	Vec4d[][] mesh;// = new Vec4d();
	ModelMat model;
	Vec4d tvec;
	Vec4d velo;
	Vec4d rvec; //encode Euler angles 

	public GameObject(Vec4d[][] mesh) {
		this.mesh = mesh; 
		//this.faceSize = faceSize;
		this.rvec = new Vec4d(0,0,0,0);
		this.tvec = new Vec4d(0,0,0,0);
		this.model = new ModelMat(0, 0, 0, 0, 0, 0);
		triangularize();
	}
	
	public void triangularize() {
		ArrayList<Vec4d[]> faces = new ArrayList<Vec4d[]>();
		
		for(int i = 0; i < mesh.length; i++) {
			if(mesh[i].length > 3) {
				for(int q = 2; q < mesh[i].length; q++) {
					faces.add(new Vec4d[] {
						mesh[i][q], 
						mesh[i][q-1],
						mesh[i][0]
					} );
				}
			} else faces.add(mesh[i]);
		}
		
		Vec4d[][] meshNew = new Vec4d[faces.size()][3];
		
		for(int i = 0; i < faces.size(); i++) meshNew[i] = faces.get(i);
		
		mesh = meshNew;
		
	}
	
	public void setEulerAngles(double pitch, double roll, double yaw) {
		this.rvec.x = pitch;
		this.rvec.y = roll;
		this.rvec.z = yaw; 
		
		this.model = new ModelMat(pitch, roll, yaw, tvec.x, tvec.y, tvec.z);
	}
	
	public void setPos(double x, double y, double z) {
		this.tvec.x = x;
		this.tvec.y = y;
		this.tvec.z = z; 
		
		this.model = new ModelMat(rvec.x, rvec.y, rvec.z, x, y, z);
	}
	
	public void setPose(double pitch, double roll, double yaw, double x, double y, double z) {
		setEulerAngles(pitch, roll, yaw);
		setPos(x, y, z);
	}
	
	//public void translateBy();
	//public void rotateBy
	
	public double getPitch() {
		return rvec.x;
	}
	
}
