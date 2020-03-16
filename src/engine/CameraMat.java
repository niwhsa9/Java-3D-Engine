package engine;

public class CameraMat extends Mat{
	Vec3d pos = new Vec3d(0, 0, 0); 
	Vec3d fwd = new Vec3d(0, 0, 1); 
	Vec3d left = new Vec3d(1, 0, 0);
	Vec3d up = new Vec3d(0, 1, 0);
	public CameraMat() {
		super(4, 4);
		pos = new Vec3d(1.0, 0.0, -2.0);
		lookAt(new Vec3d(0, 0, 0));
		
	}
	
	void lookAt(Vec3d target) {

		fwd = pos.sub(target).normalize();
		
		left = up.cross(fwd);


		up = fwd.cross(left);
		//System.out.println(fwd.dot(left) + " " + left.dot(up) + " " + up.dot(fwd));


		
		Mat r = new Mat(4, 4, new double[] {
				left.x, left.y, left.z, 0,	
				up.x, up.y, up.z, 0,
				fwd.x, fwd.y, fwd.z, 0,
				0, 0, 0, 1
		});
		
		Mat t = new Mat(4, 4, new double[] {
			1, 0, 0, -pos.x,
			0, 1, 0, -pos.y,
			0, 0, 1, -pos.z, 
			0, 0, 0, 1 
		});
		
		this.data = t.lmul(r).data; 
	}
	
}
