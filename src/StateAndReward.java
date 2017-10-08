public class StateAndReward {

	private static final double max_vx = 3.0;
	private static final int max_vx_states = 4;
	
	private static final double max_vy = 3.0;
	private static final int max_vy_states = 4;
	
	
	
	
	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {
		final int nrValues = 6;
		/* TODO: IMPLEMENT THIS FUNCTION */
		String state;
		
		int disVal = discretize2(angle, nrValues, -Math.PI, Math.PI );
		
		switch (disVal) {
		case 0:
			state="SW";
			break;
		case 1: 
			state = "W";
			break;
		case 2: 
			state = "NW";
			break;
		case 3: 
			state = "NE";
			break;
		case 4: 
			state = "E";
			break;
		case 5: 
			state = "SE";
			break;
		default: state="NOPE";
		
		}
		return state;
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {
		double reward = Math.PI-Math.abs(angle);
		return reward;
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {
		final int nrValues = 6;
		/* TODO: IMPLEMENT THIS FUNCTION */
		int disValAng = discretize2(angle, nrValues, -Math.PI, Math.PI );
		String state = getStateAngle(angle, vx, vy);
		int stateVx = discretize(vx, max_vx_states, -max_vx, max_vx);
		int stateVy = discretize(vy, max_vy_states, -max_vy, max_vy);
		return state + ":" + stateVx + ":" + stateVy;
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		//double reward = 5*Math.PI-5*Math.abs(angle) - Math.abs(vy) - Math.abs(vx);
		double reward = getRewardAngle(angle, vx, vy) + getRewardVx(vx) + getRewardVy(vy);
		return reward;
	}
	
	public static double getRewardVx(double vx) {
		if (Math.abs(vx) >= max_vx) {
			return 0;
		}
		return Math.pow((1 - Math.abs(vx))/max_vx, 2);
	}
	
	public static double getRewardVy(double vy) {
		if (Math.abs(vy) >= max_vy) {
			return 0;
		}
		return Math.pow((1 - Math.abs(vy))/max_vy, 2);
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
