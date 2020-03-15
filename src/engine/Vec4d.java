package engine;

public class Vec4d {
	double x, y, z, w;
	public Vec4d(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Mat getMat() {
		return new Mat(4, 1, new double[] {x, y, z, w});
	}
	
}
