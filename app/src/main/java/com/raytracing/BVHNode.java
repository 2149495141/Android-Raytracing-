package com.raytracing;

import java.util.*;
import android.util.*;

public class BVHNode implements hittable
{
	
	AABB box;
	hittable left;
	hittable right;

	int axis;

	BVHNode BVH;
	
	BVHNode(){}
	BVHNode(Hittable_list list, double time0, double time1)
	{
		BVH = new BVHNode(list.objects, 0, list.objects.size(), time0, time1);
	}
	BVHNode(ArrayList<hittable> objects, int start, int end, double time0, double time1){
		axis = Rtweekend.random_int(0,2);
		//boolean comparator = (axis==0) ? box_x_compare() : (axis==1) ? box_y_compare() : box_z_compare();

		int object_span = end - start;
		if(object_span == 1)
		{
			left = right = objects.get(start);
		}else if(object_span == 2)
		{
			if(comparator(objects.get(start),objects.get(start+1),axis))
			{
				left = objects.get(start);
				right = objects.get(start+1);
			}else
			{
				left = objects.get(start+1);
				right = objects.get(start);
			}
		}
		else
		{
			objects.sort(new Comparator<hittable>()
			{
				@Override
				public int compare(hittable o1, hittable o2)
				{
					// TODO: Implement this method
					if(comparator(o1,o2,axis))
						return -1;
					else
						return 1;
				}
			});
			int mid = start + object_span/2;
			left = new BVHNode(objects, start, mid, time0, time1);
			right = new BVHNode(objects, mid, end, time0, time1);
		}
		
		bbox_var box_left = new bbox_var(), box_right = new bbox_var();
		
		if(!left.bounding_box(time0, time1, box_left) || !right.bounding_box(time0, time1, box_right))
			Log.i("BVH错误", "构造错误");
			
		box = AABB.surrounding_box(box_left.bbox, box_right.bbox);
	}

	BVHNode(TriangleModel model) {
		BVH = new BVHNode(model.triangleModel,0, model.size());
	}
	BVHNode(ArrayList<Triangle> objects, int start, int end){
		axis = Rtweekend.random_int(0,2);

		int object_span = end - start;
		if(object_span == 1)
		{
			left = right = objects.get(start);
		}else if(object_span == 2)
		{
			if(comparator(objects.get(start),objects.get(start+1),axis))
			{
				left = objects.get(start);
				right = objects.get(start+1);
			}else
			{
				left = objects.get(start+1);
				right = objects.get(start);
			}
		} else
		{

			int mid = start + object_span/2;
			left = new BVHNode(objects, start, mid);
			right = new BVHNode(objects, mid, end);
		}

		bbox_var box_left = new bbox_var(), box_right = new bbox_var();

		if(!left.bounding_box(0, 1, box_left) || !right.bounding_box(0, 1, box_right))
			Log.i("BVH错误", "构造错误");

		box = AABB.surrounding_box(box_left.bbox, box_right.bbox);
	}

	private boolean comparator(hittable a,hittable b,int axis)
	{
		return (axis==0) ? box_x_compare(a,b) : (axis==1) ? box_y_compare(a,b) : box_z_compare(a,b);
	}
	
	boolean box_compare(hittable a, hittable b, int axis_index)
	{
		bbox_var boxA = new bbox_var();
		bbox_var boxB = new bbox_var();
		
		if(!a.bounding_box(0,0,boxA) || !b.bounding_box(0,0,boxB))
			Log.i("BVH错误","比较错误");
		
		return boxA.bbox.min(axis_index) < boxB.bbox.min(axis_index);
	}

	boolean box_x_compare(hittable a, hittable b)
	{
		return box_compare(a,b,0);
	}
	
	boolean box_y_compare(hittable a, hittable b)
	{
		return box_compare(a,b,1);
	}
	
	boolean box_z_compare(hittable a, hittable b)
	{
		return box_compare(a,b,2);
	}

	@Override
	public boolean hit(Ray r, double t_min, double t_max, hit_record rec)
	{
		// TODO: Implement this method
		/*if (!box.hit(r,t_min,t_max))
			return false;
		boolean hit_left = left.hit(r,t_min,t_max,rec);
		boolean hit_right = right.hit(r, t_min, hit_left? rec.t : t_max, rec);

		return hit_left||hit_right;*/
		if (box.hit(r,t_min,t_max)) {
			if (left.hit(r,t_min,t_max,rec)){
				right.hit(r,t_min,rec.t,rec);
				return true;
			}else {
				return right.hit(r,t_min,t_max,rec);
			}
		}
		return false;

	}

	@Override
	public boolean bounding_box(double time0, double time1, bbox_var out_box)
	{
		// TODO: Implement this method
		out_box.bbox = box;
		return true;
	}
	
}
