package com.raytracing;

public class Ray
{
	PointV orig;
	Vec3 dir;
	Ray(){}
	Ray(PointV origin, Vec3 direction)
	{
		orig = origin;
		dir = direction;
	}
	
	PointV origin(){return orig;}
	Vec3 direction(){return dir;}
	
	PointV at(double t)
	{
		return new PointV(orig.e[0] + (t * dir.e[0]),
					      orig.e[1] + (t * dir.e[1]),
		        	  	  orig.e[2] + (t * dir.e[2]));
	}
}
