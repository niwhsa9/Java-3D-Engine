package engine;

public class GameObject {
	Vec4d[] mesh;// = new Vec4d();
	int faceSize;
	ModelMat model;
	Vec4d tvec;
	Vec4d velo;
	Vec4d rvec; //encode Euler angles 

	public GameObject(Vec4d[] mesh, int faceSize) {
		this.mesh = mesh; 
		this.faceSize = faceSize;
		this.rvec = new Vec4d(0,0,0,0);
		this.tvec = new Vec4d(0,0,0,0);
		this.model = new ModelMat(0, 0, 0, 0, 0, 0);
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
		
	}
	
	public double getPitch() {
		return rvec.x;
	}
	
}
