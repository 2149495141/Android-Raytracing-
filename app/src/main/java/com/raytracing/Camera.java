package com.raytracing;

public class Camera
{
	PointV origin;
	Vec3 horizontal;
	Vec3 vertical;
	Vec3 focal_length3;
	Vec3 lower_left_corner;
	double lens_radius;
	
	Vec3 u,v,w;
	
	Camera(PointV lookfrom, PointV lookat, Vec3 vup,
			double vfov, double aperture, double focus_dist ,double aspect_ratio)
	{
		//double aspect_ratio = 16.0/9.0;
		double theta = Rtweekend.degrees_to_radians(vfov);
		double h = Math.tan(theta/2);
		double viewport_height = 2.0*h;
		double viewport_width = aspect_ratio * viewport_height;
		//double focal_length = 1.0;

		
		w = Vec3.unit_vector(Vec3.Minus(lookfrom,lookat));
		u = Vec3.unit_vector(Vec3.cross(vup,w));
		v = Vec3.cross(w,u);
		
		origin = lookfrom;
		horizontal = new Vec3(focus_dist*viewport_width*u.e[0],focus_dist*viewport_width*u.e[1],focus_dist*viewport_width*u.e[2]);
		vertical = new Vec3(focus_dist*viewport_height*v.e[0],focus_dist*viewport_height*v.e[1],focus_dist*viewport_height*v.e[2]);
		lower_left_corner = new Vec3(origin.e[0] - horizontal.e[0]/2 - vertical.e[0]/2 - focus_dist*w.e[0],
									 origin.e[1] - horizontal.e[1]/2 - vertical.e[1]/2 - focus_dist*w.e[1],
									 origin.e[2] - horizontal.e[2]/2 - vertical.e[2]/2 - focus_dist*w.e[2]);
									 
		lens_radius = aperture / 2;
	}
	
	Ray get_ray(double s, double t)
	{
		Vec3 rd = new Vec3(lens_radius*Vec3.random_in_unit_disk().e[0],
						   lens_radius*Vec3.random_in_unit_disk().e[1],
						   lens_radius*Vec3.random_in_unit_disk().e[2]);
						   
		Vec3 urd = new Vec3(u.e[0]*rd.x(), u.e[1]*rd.x(), u.e[2]*rd.x());
		Vec3 vrd = new Vec3(v.e[0]*rd.y(), v.e[1]*rd.y(), v.e[2]*rd.y());
		Vec3 offset = new Vec3(urd.e[0]+vrd.e[0],urd.e[1]+vrd.e[1],urd.e[2]+vrd.e[2]);
		
		
		PointV o = new PointV(origin.e[0]+offset.e[0],
					 origin.e[1]+offset.e[1],
					 origin.e[2]+offset.e[2]);
		Vec3 l = new Vec3(lower_left_corner.e[0] + s*horizontal.e[0] + t*vertical.e[0] - origin.e[0] - offset.e[0],
					 lower_left_corner.e[1] + s*horizontal.e[1] + t*vertical.e[1] - origin.e[1] - offset.e[1],
					 lower_left_corner.e[2] + s*horizontal.e[2] + t*vertical.e[2] - origin.e[2] - offset.e[2]);
		
		return new Ray(o,l);
	}
}
