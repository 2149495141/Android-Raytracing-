package com.raytracing;

public interface hittable
{
	
	boolean hit(Ray r, double t_min, double t_max, hit_record rec);
	 
}
