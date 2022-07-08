package com.raytracing;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Rtweekend
{
	static double infinity = 100000000;//Double.POSITIVE_INFINITY;//1.0 / 0.0;
	static double pi = 3.1415926535897932385;
	
	static final double degrees_to_radians(double degrees)
	{
		return degrees * pi / 180;
	}
	
	static final double random_double()
	{
		//Random rand = new Random();
		// rand.nextDouble();
		return ThreadLocalRandom.current().nextDouble();
	}
	
	static final double random_double(double min, double max)
	{
		//return min + (max-min) * random_double();
		return ThreadLocalRandom.current().nextDouble(min, max);
	}
	
	static final double clamp(double x, double min, double max)
	{
		if(x<min)return min;
		if(x>max)return max;
		return x;
	}
	
	static final int random_int(int min, int max)
	{
		return ThreadLocalRandom.current().nextInt(min,max);
	}
}
