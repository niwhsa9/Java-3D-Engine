package engine;

public class TrapezoidMotionProfile {
	
	
	double maxVelo;
	double maxAccel;
	double posSetpoint;
	double maxDecel;
	double time = 0;
	double prevVelo = 0;
	double velo = 0;
	double accel = 0;
	double distance = 0;
	boolean isDone = false;
	double prevDist;
	
	TrapezoidMotionProfile(double posSetpoint, double maxVelo, double maxAccel, double maxDecel) {
		this.posSetpoint = posSetpoint;
		this.maxVelo = maxVelo;
		this.maxAccel = maxAccel;
		this.maxDecel = maxDecel*-1;
		accel = maxAccel;
		if(posSetpoint == 0 ) isDone = true;
	}
	
	public double getPercent() {
		if(isDone) return 1.0;
		return distance/posSetpoint;
	}
	
	
	public double update(double dt) {
		time+=dt;
		distance+= prevVelo * dt;
		//check if I need to start slowing down 
		//System.out.println(posSetpoint-distance);
		//System.out.println((velo * velo/(2*maxDecel)));
		if( (-velo * velo/(2*maxDecel)) >= posSetpoint - distance) {
			accel = maxDecel;
			//System.out.println("here");
		}
		//check if I've reached v-max
		else if(velo >= maxVelo) accel = 0;
		
		if(Math.abs(distance - posSetpoint) <= 1.0 || distance < prevDist) {
			velo = 0;
			accel = 0;
			isDone = true;
		} else velo += accel * dt;
		
		prevVelo = velo;
		prevDist = distance;
		//System.out.println("dist: " + distance + " velo: " + velo + " accel: " + accel);
		if(isDone) velo = 0;
		return velo;
	}
}
