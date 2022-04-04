package com.raytracing;

public class Metal implements Material
{
	ColorV albedo;
	double fuzz;
	Metal(ColorV a, double f)
	{
		albedo = a;
		fuzz = f<1 ? f:1;
	}
	
	@Override
	public boolean scatter(Ray r_in, hit_record rec, mat_var mv /*ColorV attenuation, Ray scattered*/)
	{
		// TODO: Implement this method
		Vec3 unitVector = Vec3.unit_vector(r_in.direction()); 
		Vec3 reflected = Vec3.reflect(unitVector, rec.normal);
		Vec3 randV = Vec3.random_in_uint_sphere();
		Vec3 fuzzV = new Vec3(fuzz*randV.e[0],fuzz*randV.e[1],fuzz*randV.e[2]);
		mv.scattered = new Ray(rec.p,new Vec3(reflected.e[0] + fuzzV.e[0], 
											  reflected.e[1] + fuzzV.e[1], 
											  reflected.e[2] + fuzzV.e[2]));
		mv.attenuation = albedo;
		
		return (Vec3.dot(mv.scattered.direction(), rec.normal) > 0);
	}
	
	
}
