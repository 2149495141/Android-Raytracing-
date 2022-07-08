package com.raytracing;

import android.graphics.Color;

public class Vec3
{
	double[] e = new double[3];
	
	Vec3()
	{
		e[0] = 0; e[1] = 0; e[2] = 0;
		//e = {0.0, 0.0, 0.0};
	}
	
	Vec3(double e0, double e1, double e2)
	{
		e[0] = e0; e[1] = e1; e[2] = e2;
	}
	
	double x(){ return e[0];}
	double y(){ return e[1];}
	double z(){ return e[2];}
	
	Vec3 operator_negative()
	{
		return new Vec3(-e[0], -e[1], -e[2]);
	}
	
	double opertor_element(int i)
	{ 
		return e[i];
	}
	
	Vec3 opertorAdd(Vec3 v)
	{
		e[0] += v.e[0];
		e[1] += v.e[1];
		e[2] += v.e[2];
		
		return new Vec3(e[0], e[1], e[2]);
	}
	
	Vec3 opertorMultiply(float t)
	{
		e[0] *= t;
		e[1] *= t;
		e[2] *= t;
		
		return new Vec3(e[0], e[1], e[2]);
	}
	
	Vec3 opertorDivide(float t)
	{
		e[0] *= 1/t;
		e[1] *= 1/t;
		e[2] *= 1/t;
		
		return new Vec3(e[0], e[1], e[2]);
	}
	
	double lenght(){
		return Math.sqrt(lenght_squared());
	}
	
	double lenght_squared()
	{
		return e[0]*e[0] + e[1]*e[1] + e[2]*e[2];
	}
	
	static final double[] opertor(double[] out, Vec3 v){
		/*int R = (int) (v.e[0] * 255.999);
		int G = (int) (v.e[1] * 255.999);
		int B = (int) (v.e[2] * 255.999);
		return Color.argb(255, R, G, B);*/
		out[0] = v.e[0];
		out[1] = v.e[1];
		out[2] = v.e[2];
		return out;
	}
	
	static final Vec3 Add(Vec3 u, Vec3 v)
	{
		return new Vec3(u.e[0] + v.e[0], u.e[1] + v.e[1], u.e[2] + v.e[2]);
	}
	
	static final Vec3 Minus(Vec3 u, Vec3 v)
	{
		return new Vec3(u.e[0] - v.e[0], u.e[1] - v.e[1], u.e[2] - v.e[2]);
	}
	
	static final Vec3 Multiply(Vec3 u, Vec3 v)
	{
		return new Vec3(u.e[0] * v.e[0], u.e[1] * v.e[1], u.e[2] * v.e[2]);
	}
	
	static final Vec3 Multiply(double t, Vec3 v)
	{
		return new Vec3(t*v.e[0], t*v.e[1], t*v.e[2]);
	}
	
	static final Vec3 Multiply(Vec3 v, float t)
	{
		return new Vec3(t*v.e[0], t*v.e[1], t*v.e[2]);
	}
	
	static final Vec3 Divide(Vec3 v, float t)
	{
		return new Vec3((1/t)*v.e[0],(1/t)*v.e[1],(1/t)*v.e[2]);
	}
	
	static final double dot(Vec3 u, Vec3 v)
	{
		return u.e[0] * v.e[0]
			 + u.e[1] * v.e[1]
			 + u.e[2] * v.e[2];
	}
	
	static final Vec3 cross(Vec3 u, Vec3 v)
	{
		return new Vec3(u.e[1] * v.e[2] - u.e[2] * v.e[1],
					    u.e[2] * v.e[0] - u.e[0] * v.e[2],
					    u.e[0] * v.e[1] - u.e[1] * v.e[0]);
	}
	
	static final Vec3 unit_vector(Vec3 v)
	{
		return new Vec3(v.e[0]/v.lenght(),
						v.e[1]/v.lenght(),
						v.e[2]/v.lenght());
	}

	static final Vec3 random()
	{
		return new Vec3(Rtweekend.random_double(), Rtweekend.random_double(), Rtweekend.random_double());
	}
	
	static final Vec3 random(double min, double max)
	{
		return new Vec3(Rtweekend.random_double(min,max),
						Rtweekend.random_double(min,max),
						Rtweekend.random_double(min,max));
	}
	
	static final Vec3 random_in_uint_sphere()
	{
		while(true)
		{
			Vec3 p = random(-1,1);
			if(p.lenght_squared() >= 1) continue;
			return p;
		}
	}
	
	static final Vec3 random_unit_vector()
	{
		return unit_vector(random_in_uint_sphere());
	}
	
	static final Vec3 random_in_hemisphere(Vec3 normal)
	{
		Vec3 in_unit_sphere = random_in_uint_sphere();
		if(Vec3.dot(in_unit_sphere, normal) > 0.0)
			return in_unit_sphere;
		else
			return in_unit_sphere.operator_negative();
	}
	
	///////matrial
	boolean near_zero()
	{
		double s = 1e-8;
		return (Math.abs(e[0]) < s)&&(Math.abs(e[1]) < s)&&(Math.abs(e[1]) < s);
	}
	
	static final Vec3 reflect(Vec3 v, Vec3 n)
	{
		double vn = Vec3.dot(v,n);
		return new Vec3(v.e[0] - 2*vn*n.e[0], v.e[1] - 2*vn*n.e[1], v.e[2] - 2*vn*n.e[2]);
	}
	
	static final Vec3 refract(Vec3 uv, Vec3 n, double etai_over_etat)
	{
		double cos_theta = Math.min(dot(new Vec3(-uv.e[0],-uv.e[1],-uv.e[2]), n), 1.0);
		Vec3 r_out_perp = new Vec3(etai_over_etat * (uv.e[0] + (cos_theta*n.e[0])), 
								   etai_over_etat * (uv.e[1] + (cos_theta*n.e[1])), 
								   etai_over_etat * (uv.e[2] + (cos_theta*n.e[2])));
		
		
		Vec3 r_out_parallel = new Vec3(-Math.sqrt(Math.abs(1.0 - r_out_perp.lenght_squared())) * n.e[0], 
										-Math.sqrt(Math.abs(1.0 - r_out_perp.lenght_squared())) * n.e[1], 
										-Math.sqrt(Math.abs(1.0 - r_out_perp.lenght_squared())) * n.e[2]);
		
		return new Vec3(r_out_perp.e[0] + r_out_parallel.e[0], 
						r_out_perp.e[1] + r_out_parallel.e[1], 
						r_out_perp.e[2] + r_out_parallel.e[2]);
	}
	
	static final Vec3 random_in_unit_disk()
	{
		while(true)
		{
			Vec3 p = new Vec3(Rtweekend.random_double(-1,1),Rtweekend.random_double(-1,1), 0);
			if(p.lenght_squared() >= 1) continue;
			return p;
		}
	}
	
	
}
