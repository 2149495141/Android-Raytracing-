package com.raytracing;


public interface hittable
{
	
	boolean hit(Ray r, double t_min, double t_max, hit_record rec);
	boolean bounding_box(double time0, double time1, bbox_var out_box);
}
