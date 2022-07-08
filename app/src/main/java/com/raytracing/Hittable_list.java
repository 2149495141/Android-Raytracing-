package com.raytracing;

import java.util.ArrayList;

public class Hittable_list implements hittable
{
	
	ArrayList<hittable> objects;
	
	Hittable_list(){
		objects = new ArrayList<hittable>();
	}
	Hittable_list(hittable object)
	{
		objects = new ArrayList<hittable>();
		this.objects.add(object);
		
	}
	
	void clear()
	{
		objects.clear();
	}
	
	void add(hittable object)
	{
		objects.add(object);
	}

	@Override
	public boolean hit(Ray r, double t_min, double t_max, hit_record rec)
	{
		// TODO: Implement this method
		//hit_record temp_rec = new hit_record();
		double closest_so_far = t_max;
		boolean hit_anything = false;

		for(int i = 0, legth = objects.size(); i < legth; ++i)
		//for(hittable object : objects) //不用foreach，它会创建大量对象造成频繁gc
		{
			if(objects.get(i) /*object*/.hit(r, t_min, closest_so_far, /*temp_rec*/rec)){
				hit_anything = true;
				closest_so_far = rec.t;//temp_rec.t;//把光线碰撞点的位置传过来
				//rec = temp_rec; //不能这样赋值，这会导致传过来的对象是刚初始化的rec对象
				
			}
		}

		return hit_anything;
	}

	@Override
	public boolean bounding_box(double time0, double time1, bbox_var out_box)
	{
		// TODO: Implement this method
		if(objects.isEmpty()) return false;
		
		bbox_var temp_box = new bbox_var();
		boolean first_box = true;

		for(int i = 0, legth = objects.size(); i < legth; ++i)
		//for(hittable object:objects)
		{
			if(!objects.get(i) /*object*/.bounding_box(time0,time1,temp_box))
				return false;
			out_box.bbox = first_box ? temp_box.bbox : AABB.surrounding_box(out_box.bbox,temp_box.bbox);
			first_box = false;
			
		}
		
		return true;
	}
	
}
