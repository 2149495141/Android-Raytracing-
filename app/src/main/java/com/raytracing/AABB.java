package com.raytracing;

public class AABB
{
	PointV minimum = new PointV(), maximum = new PointV();
	
	AABB(){}
	AABB(PointV a, PointV b)
	{
		minimum = a;
		maximum = b;
	}
	
	final double min(int n)
	{
		return minimum.e[n];
	}
	
	final double max(int n)
	{
		return maximum.e[n];
	}
	
	final boolean hit(Ray r, double t_min, double t_max)
	{
		final Vec3 r_origin = r.origin();
		final Vec3 r_dir = r.direction();
		for(int a = 0; a < 3; ++a)
		{
			double invD = 1.0 / r_dir.e[a];
			double orig = r_origin.e[a];
			double t0 = (min(a) - orig)*invD;
			double t1 = (max(a) - orig)*invD;

			if(invD < 0)
			{
				double temp = t0; t0 = t1; t1 = temp;
			}
			
			t_min = t0 > t_min ? t0 : t_min;
			t_max = t1 < t_max ? t1 : t_max;
			if(Math.min( t1,t_max) <= Math.max(t0, t_min))
			{
				return false;
			}
		}
		
		return true;
	}
	
	static AABB surrounding_box(AABB box1, AABB box2)
	{
		PointV small = new PointV(Math.min(box1.minimum.e[0],box2.minimum.e[0]),
								  Math.min(box1.minimum.e[1],box2.minimum.e[1]),
								  Math.min(box1.minimum.e[2],box2.minimum.e[2]));
		PointV big = new PointV(Math.max(box1.maximum.e[0],box2.maximum.e[0]),
								Math.max(box1.maximum.e[1],box2.maximum.e[1]),
								Math.max(box1.maximum.e[2],box2.maximum.e[2]));
		return new AABB(small,big);
	}
}
