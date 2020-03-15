package engine;

public class ModelMat extends Mat {

	public ModelMat(double pitch, double roll, double yaw, double x, double y, double z) {
		super(4, 4);

		Mat yRot = new Mat(4, 4, new double[] { 
				Math.cos(yaw), -Math.sin(yaw), 0, 0, 
				Math.sin(yaw), Math.cos(yaw), 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1 });

		Mat pRot = new Mat(4, 4, new double[] { 
				Math.cos(pitch), 0, Math.sin(pitch), 0,
				0, 1, 0, 0, 
				-Math.sin(pitch), 0, Math.cos(pitch), 0,
				0, 0, 0, 1 });

		Mat rRot = new Mat(4, 4, new double[] { 
				1, 0, 0, 0, 
				0, Math.cos(roll), -Math.sin(roll), 0, 
				0, Math.sin(roll), Math.cos(roll), 0,
				0, 0, 0, 1 });

		Mat tvec = new Mat(4, 4, new double[] {
				1, 0, 0, x, 
				0, 1, 0, y,
				0, 0, 1, z, 
				0, 0, 0, 1
		});

		this.data = yRot.lmul(pRot).lmul(rRot).lmul(tvec).data;

	}

	public void setPose() {

	}
	/*
	 * Mat pRot = new Mat(4, 4, new double[] { 1, 0, 0, 0, 0, Math.cos(pitch),
	 * -Math.sin(pitch), 0, 0, Math.sin(pitch), Math.cos(pitch), 0, 0, 0, 0, 1 });
	 * 
	 * /*
	 * 
	 * Mat rRot = new Mat(4, 4, new double[] { 1, 0, 0, 0, 0, Math.cos(pitch),
	 * -Math.sin(pitch), 0, 0, Math.sin(roll), Math.cos(roll), 0, 0, 0, 0, 1 });
	 */
}
