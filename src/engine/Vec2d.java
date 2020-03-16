package engine;

public class Vec2d {
	double x, y;
	public Vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "<" + x +", " + y + ">";
	}
	
	public Mat getMat() {
		return new Mat(3, 1, new double[] {x, y});
	}
	
	public double getMag() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public Vec2d normalize() {
		double mag = getMag();
		if(mag == 0) return new Vec2d(0, 0);
		return new Vec2d(x/mag, y/mag);
	}
	
	
	public Vec2d sub(Vec2d b) {
		return new Vec2d(x-b.x, y-b.y);
	}
	
	public double dot(Vec2d b) {
		return x * b.x + y * b.y;
	}
	
	public Vec3d getHomogenous() {
		return new Vec3d(x, y, 1.0);
	}
	
}
