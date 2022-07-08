package com.raytracing;

import java.util.ArrayList;

public class TriangleModel implements hittable
{
	ArrayList<Triangle> triangleModel;
	
	TriangleModel()
	{
		triangleModel = new ArrayList<Triangle>();
	}
	
	void add(Triangle tri)
	{
		triangleModel.add(tri);
	}

	int size()
	{
		return triangleModel.size();
	}
	
	@Override
	public boolean hit(Ray r, double t_min, double t_max, hit_record rec)
	{
		// TODO: Implement this method
		double closest_so_far = t_max;
		boolean hit_anything = false;

		for(int i = 0, legth = triangleModel.size(); i < legth; ++i)
		//for(hittable tri : triangleModel)
		{
			if(triangleModel.get(i) /*tri*/.hit(r,t_min,closest_so_far,rec)){
				if(rec.t<closest_so_far)
				{
					closest_so_far = rec.t;
				}
				hit_anything = true;
			}
		}
		
		return hit_anything;
	}

	@Override
	public boolean bounding_box(double time0, double time1, bbox_var out_box)
	{
		// TODO: Implement this method
		if(triangleModel.isEmpty()) return false;

		bbox_var temp_box = new bbox_var();
		boolean first_box = true;

		for(int i = 0, legth = triangleModel.size(); i < legth; ++i)
		{
			if(!triangleModel.get(i).bounding_box(time0,time1,temp_box))
				return false;
			out_box.bbox = first_box ? temp_box.bbox : AABB.surrounding_box(out_box.bbox, temp_box.bbox);
			first_box = false;

		}

		return true;
	}
	
	
}
