package com.raytracing;

public interface Material
{
	boolean scatter(Ray r_in, hit_record rec, mat_var mv /*ColorV attenuation, Ray scattered*/ );
}
