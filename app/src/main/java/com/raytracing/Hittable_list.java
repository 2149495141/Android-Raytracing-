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
		hit_record temp_rec = new hit_record();
		double closest_so_far = t_max;
		boolean hit_anything = false;

		for(hittable object : objects){
			if(object.hit(r, t_min, closest_so_far, /*temp_rec*/rec)){
				hit_anything = true;
				closest_so_far = rec.t;//temp_rec.t;//把光线碰撞点的位置传过来
				//rec = temp_rec; //不能这样赋值，这会导致传过来的对象是刚初始化的rec对象
				
			}
		}

		return hit_anything;
	}
	
}
