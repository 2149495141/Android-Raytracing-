package com.raytracing;

public class PointV extends Vec3
{
	PointV(){super(0.0,0.0,0.0);}
	PointV(double e0, double e1, double e2){
		super(e0,e1,e2);
	}
	PointV(Vec3 v)
	{
		super(v.e[0], v.e[1], v.e[2]);
	}
}
