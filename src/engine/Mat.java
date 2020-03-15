package engine;

public class Mat {
	public int row;
	public int col;
	double[] data;

	public Mat(int row, int col, double[] data) {
		this.row = row;
		this.col = col;
		this.data = data;
		//System.out.println("index: " + getIndex(1,0) + " num " + data[getIndex(1, 0)]);
	}
	
	public Mat(int row, int col) {
		this.row = row;
		this.col = col;
		this.data = new double[row*col];
		
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int r = 0; r < row; r++) {
			for(int c = 0; c < col; c++) {
				s += getElem(r, c);
				s+= "  ";
			}
			s+= "\n";
		}
		//s+= "]";
		return s;
	}
	
	public void setElem(int r, int c, double val) {
		data[getIndex(r,c)] = val;
	}
	
	public double getElem(int r, int c) {
		return data[getIndex(r,c)];
	}
	
	private int getIndex(int r, int c) {
		return r * col + c;
	}	
	
	
	public Mat rightMultiply(Mat m) { 
		Mat o = new Mat(row, m.col);
		if(col != m.row) System.out.println("illegal mat mul attempted");
		for(int mr = 0; mr < row; mr++) {
			for(int tc = 0; tc < m.col; tc++) {
				double s = 0;
				for(int mc = 0; mc < col; mc++) {
					//System.out.println("mr: " + mr + " mc: " + mc + " v: " + getElem(mr, mc));
					//System.out.println("mc: " + mc + " tc: " + tc + " v: " + m.getElem(mc, tc));
					s+= getElem(mr, mc) * m.getElem(mc, tc);
				}
				o.setElem(mr, tc, s);
			}
		} 
		return o;
	}
	
	
	public Mat leftMultiply(Mat m) { 
		Mat o = new Mat(m.row, col);
		if(row != m.col) System.out.println("illegal mat mul attempted");
		for(int tr = 0; tr < m.row; tr++) {
			for(int mc = 0; mc < col; mc++) {
				double s = 0;
				for(int tc = 0; tc < m.col; tc++) {
					//System.out.println("tr: " + tr + " tc: " + tc + " v: ");// + getElem(tr, tc));
					//System.out.println("tc: " + tc + " mc: " + mc + " v: ");// + m.getElem(tc, mc));
					s+= m.getElem(tr, tc) * getElem(tc, mc);
				}
				o.setElem(tr, mc, s);
			}
		} 
		return o;
	}
	
	public Mat lmul(Mat m) {
		return leftMultiply(m);
	}
	
	public Mat rmul(Mat m) {
		return rightMultiply(m);
	}
	
	public Mat multiply(double d) {
		Mat mat = new Mat(row, col);
		for(int i = 0; i < row * col; i++) mat.setElem(i/col, i % col, d*getElem(i/col, i % col));
		return mat;
	}
	
	public Mat add(Mat m) {
		Mat o = new Mat(m.row, m.col);
		for(int i = 0; i < data.length; i++) {
			o.data[i] = data[i] + m.data[i];
		}
		return o;
	}
	public Mat sub(Mat m) {
		Mat me = new Mat(row, col, data);
		return m.multiply(-1).add(me);
	}
	
	public static Mat getIdentityMat(int r, int c) {
		Mat m = new Mat(r, c);
		for(int i = 0; i < r * c; i++) m.data[i] = 1;
		return m;
	}
	
	
	public void homogenous() {
		data[data.length-1] = 1;
	}
	
	public Mat getHomogenous() {
		Mat o = new Mat(row, col, data);
		o.data[o.data.length-1] = 1;
		return o;
	}
	
	public double getMag() {
		double d = 0;
		for(int i = 0; i < data.length; i++) {
			d += data[i] * data[i];
		}
		return Math.sqrt(d);
	}
	
	public double dot(Mat m) {
		double d = 0;
		for(int i = 0; i < m.data.length; i++) {
			d += m.data[i] * data[i];
		}
		return d;
	}
	
	public static Mat translationMat3x3(double x, double y) {
		return new Mat(3, 3, 
				new double[] 
						{1, 0, x,
						 0, 1, y,
						 0, 0, 1
						}
		);
	}
	
	public static Mat rotationMat3x3(double theta) {
		return new Mat(3, 3, 
				new double[] 
						{Math.cos(theta), -Math.sin(theta), 0,
						 Math.sin(theta),  Math.cos(theta), 0,
						 0, 0, 1
						}
		);
	}
	
	public static Mat dialationMat3x3(double s) {
		return 
			new Mat(3, 3, 
					new double[] 
							{s, 0, 0,
							 0, s, 0,
							 0, 0, 1
							}
			);
	}
	
	public Mat transpose() {
		Mat t = new Mat(this.col, this.row);
		return null; 
	}
	
	/*
	public static Mat rotateCenter(Mat point, double theta) {
		return new Mat(3, 3, translateMat3x3());
	}*/
	
}
