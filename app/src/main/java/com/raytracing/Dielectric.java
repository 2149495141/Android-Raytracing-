package com.raytracing;

public class Dielectric implements Material
{
	double ir;
	
	Dielectric(double index_of_refraction)
	{
		ir = index_of_refraction;
	}
	
	@Override
	public boolean scatter(Ray r_in, hit_record rec, mat_var mv)
	{
		// TODO: Implement this method
		mv.attenuation = new ColorV(1.0, 1.0, 1.0);
		double refraction_ratio = rec.front_face ? (1.0/ir):ir;
		
		Vec3 unit_direction = Vec3.unit_vector(r_in.direction());
		double cos_theta = Math.min(Vec3.dot(new Vec3(-unit_direction.e[0],-unit_direction.e[1],-unit_direction.e[2]),rec.normal), 1.0);
		double sin_theta = Math.sqrt(1.0 - cos_theta*cos_theta);
		
		boolean cannot_refract = refraction_ratio*sin_theta > 1.0;
		Vec3 direction = new Vec3();
		
		if(cannot_refract || reflectance(cos_theta, refraction_ratio) > Rtweekend.random_double())
			direction = Vec3.reflect(unit_direction, rec.normal);
		else
			direction = Vec3.refract(unit_direction, rec.normal, refraction_ratio);
		
		mv.scattered = new Ray(rec.p, direction);
		
		return true;
	}
	
	double reflectance(double cosine, double ref_idx)
	{
		double r0 = (1.0-ref_idx) / (1.0+ref_idx);
		r0 = r0*r0;
		return r0+(1.0-r0)*Math.pow((1.0 - cosine),5.0);
	}

}
