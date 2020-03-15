package engine;

public class Vec3d {
	double x, y, z;
	public Vec3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString() {
		return "<" + x +", " + y + ", " + z + ">";
	}
	
	public Mat getMat() {
		return new Mat(3, 1, new double[] {x, y, z});
	}
	
	public double getMag() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
	
	public Vec3d normalize() {
		double mag = getMag();
		if(mag == 0) return new Vec3d(0, 0, 0);
		return new Vec3d(x/mag, y/mag, z/mag);
	}
	
	public Vec3d cross(Vec3d b) {
		return new Vec3d(y*b.z - z*b.y, z*b.x - x*b.z, x*b.y - y*b.x);
	}
	
	public Vec3d sub(Vec3d b) {
		return new Vec3d(x-b.x, y-b.y, z-b.z);
	}
	
	public double dot(Vec3d b) {
		return x * b.x + y * b.y + z * b.z;
	}
	
}
