package com.raytracing;

public class Lambertian implements Material
{
	ColorV albedo;
	Lambertian(ColorV a)
	{
		albedo = a;
	}
	
	
	@Override
	public boolean scatter(Ray r_in, hit_record rec, mat_var mv /*ColorV attenuation, Ray scattered*/)
	{
		// TODO: Implement this method
		Vec3 randomVector = Vec3.random_unit_vector();
		Vec3 scatter_direction = new Vec3(rec.normal.e[0] + randomVector.e[0], 
										rec.normal.e[1] + randomVector.e[1], 
										rec.normal.e[2] + randomVector.e[2]);
		
		if(scatter_direction.near_zero())
			scatter_direction = rec.normal;
						
		mv.scattered = new Ray(rec.p, scatter_direction);
		mv.attenuation = albedo;
		
		return true;
	}

	
}
