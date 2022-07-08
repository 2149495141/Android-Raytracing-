package com.raytracing;

class Triangle implements hittable
{
	
	PointV[] vertex = new PointV[3]; 
	Material mp;
	Triangle(Material mat)
	{
		mp = mat;
	}
	Triangle(PointV[] vert, Material mat)
	{
		this.vertex = vert;
		mp = mat;
	}
	
	void setMaterial(Material mat)
	{
		mp = mat;
	}
	
	@Override
	public boolean hit(Ray r, double t_min, double t_max, hit_record rec)
	{
		// TODO: Implement this method

		Vec3 S  = Vec3.Minus(r.orig, vertex[0]);//先计算出平面
		Vec3 E1 = Vec3.Minus(vertex[1], vertex[0]);//求三角形边1
		Vec3 E2 = Vec3.Minus(vertex[2], vertex[0]);//求三角形边2
		Vec3 S1 = Vec3.cross(r.dir, E2);//
		Vec3 S2 = Vec3.cross(S, E1);//

		double S1E1 = Vec3.dot(S1, E1);
		double t = Vec3.dot(S2,E2)/S1E1;
		double b1 = Vec3.dot(S1,S)/S1E1;
		double b2 = Vec3.dot(S2,r.dir) / S1E1;

		if(t < t_min||t > t_max)//确保取最近交点t，防止自遮挡和物体之间的遮挡错误
			return false;

		Vec3 outward_normal = Vec3.unit_vector(Vec3.cross(E1,E2));

		if(Math.abs(Vec3.dot(outward_normal,r.direction())) < 1e-8)
			return false;

		//根据重心坐标判断合法性，b1b2大于0说明在三角形的两条边内，(1-b1-b2)大于0说明在第三条边内
		if(t >= 0 && b1 >= 0 && b2 >= 0 && (b1 + b2) <= 1)
		{
			rec.t = t;
			rec.u = b1;
			rec.v = b2;

			rec.set_face_normal(r,outward_normal);
			rec.mat_ptr = mp;
			rec.p = r.at(t);

			return true;
		}

		return false;

		/*
		Vec3 e1 = Vec3.Minus(vertex[1],vertex[0]);
		Vec3 e2 = Vec3.Minus(vertex[2],vertex[0]);

		Vec3 s1 = Vec3.cross(r.dir,e2);
		Vec3 s0;
		double det = Vec3.dot(e1,s1);
		if (det > 0){
			s0 = Vec3.Minus(r.orig, vertex[0]);
		}else {
			s0 = Vec3.Minus(vertex[0],r.orig);
			det = -det;
		}
		if (det < 0.0001)
			return false;

		//Vec3 s0 = Vec3.Minus(r.orig, vertex[0]);
		double u = Vec3.dot(s0,s1);
		if (u < 0 || u > det )
			return false;

		Vec3 s2 = Vec3.cross(s0, e1);
		double v = Vec3.dot(r.dir, s2);
		if (v < 0 || u+v > det)
			return false;

		double t = Vec3.dot(e2,s2)/det;
		if (t < 0 || t == 0)
			return false;
		if(t < t_min||t > t_max)//确保取最近交点t，防止自遮挡和物体之间的遮挡错误
			return false;

		Vec3 outward_normal = Vec3.unit_vector(Vec3.cross(e1,e2));
		//if(Math.abs(Vec3.dot(outward_normal,r.direction())) < 1e-8)
			//return false;

		rec.t = t;
		rec.u = u/det;
		rec.v = v/det;
		rec.set_face_normal(r,outward_normal);
		rec.mat_ptr = mp;
		rec.p = r.at(t);

		return true;*/
	}

	double getmin(PointV v){
		return Math.min(v.e[0],Math.min(v.e[1],v.e[2]));
	}

	double getmax(PointV v){
		return Math.max(v.e[0], Math.max(v.e[1], v.e[2]));
	}

	@Override
	public boolean bounding_box(double time0, double time1, bbox_var out_box)
	{
		// TODO: Implement this method
		PointV min = new PointV(Rtweekend.infinity,Rtweekend.infinity,Rtweekend.infinity);
		PointV max = new PointV(-Rtweekend.infinity,-Rtweekend.infinity,-Rtweekend.infinity);

		for(int i=0; i<3; ++i)
		{
			min = new PointV(Math.min(min.e[0],vertex[i].e[0]),
							 Math.min(min.e[1],vertex[i].e[1]),
							 Math.min(min.e[2],vertex[i].e[2]));
							 
			max = new PointV(Math.max(max.e[0],vertex[i].e[0]),
							 Math.max(max.e[1],vertex[i].e[1]),
							 Math.max(max.e[2],vertex[i].e[2]));

		}
		PointV dif = new PointV(0.01,0.01,0.01);
		min = new PointV(Vec3.Minus(min,dif));
		max = new PointV(Vec3.Add(max,dif));


		//PointV minv = new PointV(getmin(vertex[0]), getmin(vertex[1]), getmin(vertex[2]));
		//PointV maxv = new PointV(getmax(vertex[0]), getmax(vertex[1]), getmax(vertex[2]));

		out_box.bbox = new AABB(min, max);
		
		return true;
	}


}
