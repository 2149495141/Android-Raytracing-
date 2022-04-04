package com.raytracing;

import java.util.Random;

public class Rtweekend
{
	static double infinity = Double.POSITIVE_INFINITY;//1.0 / 0.0;
	static double pi = 3.1415926535897932385;
	
	static final double degrees_to_radians(double degrees)
	{
		return degrees * pi / 180;
	}
	
	static final double random_double()
	{
		Random rand = new Random();
		
		return rand.nextDouble();
	}
	
	static final double random_double(double min, double max)
	{
		return min + (max-min) * random_double();
	}
	
	static final double clamp(double x, double min, double max)
	{
		if(x<min)return min;
		if(x>max)return max;
		return x;
	}
}
