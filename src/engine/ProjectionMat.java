package engine;

public class ProjectionMat extends Mat {
	public ProjectionMat(double ar, double fov, double n, double f) {
		super(4, 4);
		double r, l, b, t;
		t = Math.tan(fov / 2) * n;
		b = -t;
		r = t * ar;
		l = t * -ar;

		this.data = new double[] { (2 * n) / (r - l), 0, (r + l) / (r - l), 0, 0, (2 * n) / (t - b),
				(t + b) / (t - b), 0, 0, 0, -(f + n) / (f - n), -(2 * f * n) / (f - n), 0, 0, -1, 0 };
		
		
	}
}
