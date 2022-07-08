package com.raytracing;

import com.raytracing.hittable;

public class Sphere implements hittable
{

	PointV center;
	double radius;
	Material mat_ptr;
	
	Sphere(){}
	Sphere(PointV cen, double ra, Material mat)
	{
		center = cen;
		radius = ra;
		mat_ptr = mat;
	}

	@Override
	public boolean hit(Ray r, double t_min, double t_max, hit_record rec)
	{
		// TODO: Implement this method
		Vec3 oc = new Vec3(r.origin().x() - center.x(),
						   r.origin().y() - center.y(),
						   r.origin().z() - center.z());
		double a = r.direction().lenght_squared();
		double half_b = Vec3.dot(oc, r.direction());
		double c = oc.lenght_squared() - radius*radius;
		
		double discriminant = half_b*half_b - a*c;

		if(discriminant < 0) 
			return false;
			
		double sqrtd = Math.sqrt(discriminant);
		double root = (-half_b - sqrtd) / a;
		
		if(root < t_min || t_max < root)
		{
			root = (-half_b + sqrtd) /a;
			if(root < t_min || t_max < root)
				return false;
		}

		rec.t = root;
		rec.p = r.at(root);
		Vec3 outward_normal =  new Vec3( (rec.p.e[0] - center.e[0]) / radius,
									    (rec.p.e[1] - center.e[1]) / radius,
									   (rec.p.e[2] - center.e[2]) / radius );
		rec.set_face_normal(r, outward_normal);
		rec.mat_ptr = mat_ptr;
		
		return true;
	}

	@Override
	public boolean bounding_box(double time0, double time1, bbox_var out_box)
	{
		// TODO: Implement this method
		return false;
	}
	
}
