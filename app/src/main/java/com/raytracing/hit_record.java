package com.raytracing;

public class hit_record
{
		PointV p;
		Vec3 normal;
		Material mat_ptr;
		
		double t;
		boolean front_face;
		hit_record()
		{
			normal = new Vec3();
			p = new PointV();
			
		}
		
		
		final void set_face_normal(Ray r, Vec3 outward_normal)
		{
			front_face = Vec3.dot(r.direction(), outward_normal) < 0;
			normal = front_face ? outward_normal : new Vec3(-outward_normal.e[0],-outward_normal.e[1],-outward_normal.e[2]);
		}
		
}
