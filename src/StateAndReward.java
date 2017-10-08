public class StateAndReward {
	
	private static final double MAX_ANGLE = Math.PI - 1;

	private static final int MAX_VX_STATES = 3;
	private static final double MAX_X_SPEED = Math.PI / 2;

	private static final int MAX_VY_STATES = 4;
	private static final double MAX_Y_SPEED = Math.PI;
	
	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {
		int discreteAng = discretize2(angle, 10, -MAX_ANGLE, MAX_ANGLE); 
		
		return "" + discreteAng;
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {
		double reward = Math.PI-Math.abs(angle);
		return reward;
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {
		
		String angleState = getStateAngle(angle, vx, vy);
		int discreteVx = discretize(vx, MAX_VX_STATES, -MAX_X_SPEED, MAX_X_SPEED);
		int discreteVy = discretize(vy, MAX_VY_STATES, -MAX_Y_SPEED, MAX_Y_SPEED);
		
		return angleState + ":" + discreteVx + ":" + discreteVy;	
		
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		double angleReward = getRewardAngle(angle, vx, vy);
		double vxReward = getXSpeedReward(vx);
		double vyReward = getYSpeedReward(vy);
		

		return angleReward + vxReward + vyReward;
	}
	private static double getYSpeedReward(double speed) {
		double abs = Math.abs(speed);
		if (abs >= MAX_Y_SPEED) {
			return 0;
		}
		return MAX_Y_SPEED - abs;
	}
	
	private static double getXSpeedReward(double speed) {
		double abs = Math.abs(speed);
		if (abs >= MAX_X_SPEED) {
			return 0;
		}
		return MAX_X_SPEED - abs;
	}
	
	
	

	// ///////////////////////////////////////////////////////////
		// discretize() performs a uniform discretization of the
		// value parameter.
		// It returns an integer between 0 and nrValues-1.
		// The min and max parameters are used to specify the interval
		// for the discretization.
		// If the value is lower than min, 0 is returned
		// If the value is higher than min, nrValues-1 is returned
		// otherwise a value between 1 and nrValues-2 is returned.
		//
		// Use discretize2() if you want a discretization method that does
		// not handle values lower than min and higher than max.
		// ///////////////////////////////////////////////////////////
		public static int discretize(double value, int nrValues, double min,
				double max) {
			if (nrValues < 2) {
				return 0;
			}

			double diff = max - min;

			if (value < min) {
				return 0;
			}
			if (value > max) {
				return nrValues - 1;
			}

			double tempValue = value - min;
			double ratio = tempValue / diff;

			return (int) (ratio * (nrValues - 2)) + 1;
		}

		// ///////////////////////////////////////////////////////////
		// discretize2() performs a uniform discretization of the
		// value parameter.
		// It returns an integer between 0 and nrValues-1.
		// The min and max parameters are used to specify the interval
		// for the discretization.
		// If the value is lower than min, 0 is returned
		// If the value is higher than min, nrValues-1 is returned
		// otherwise a value between 0 and nrValues-1 is returned.
		// ///////////////////////////////////////////////////////////
		public static int discretize2(double value, int nrValues, double min,
				double max) {
			double diff = max - min;

			if (value < min) {
				return 0;
			}
			if (value > max) {
				return nrValues - 1;
			}

			double tempValue = value - min;
			double ratio = tempValue / diff;

			return (int) (ratio * nrValues);
		}
		

}
