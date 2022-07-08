package com.raytracing;

public class Matrix4
{
	double[] matrix = new double[16];
	
	Matrix4(){
		double[] imatrix =
			{1, 0, 0, 0,
			 0, 1, 0, 0,
			 0, 0, 1, 0,
			 0, 0, 0, 1};
		this.matrix = imatrix;
	}
	Matrix4(double[] m){
		matrix = m;
	}
	
	Vec3 Multiply(Matrix4 m, Vec3 v)
	{
		return new Vec3(m.matrix[0]*v.x() + m.matrix[1]*v.y() + m.matrix[2]*v.z(),
						m.matrix[4]*v.x() + m.matrix[5]*v.y() + m.matrix[6]*v.z(),
						m.matrix[8]*v.x() + m.matrix[9]*v.y() + m.matrix[10]*v.z());
	}
	
	Matrix4 Multiply(Matrix4 m1, Matrix4 m2)
	{
		double[] m = new double[16];
		Vec3 v048 = Multiply(m2,new Vec3(m1.matrix[0],m1.matrix[4],m1.matrix[8]));
		Vec3 v159 = Multiply(m2,new Vec3(m1.matrix[1],m1.matrix[5],m1.matrix[9]));
		Vec3 v2610 = Multiply(m2,new Vec3(m1.matrix[2],m1.matrix[6],m1.matrix[10]));
		m[0] = v048.x(); m[1] = v159.x(); m[2] = v2610.x(); m[3]  = 0;
		m[4] = v048.y(); m[5] = v159.y(); m[6] = v2610.y(); m[7]  = 0;
		m[8] = v048.z(); m[9] = v159.z(); m[10] = v2610.z();m[11] = 0;
		m[12] = 0;		 m[13] = 0; 	  m[14] = 0; 	    m[15] = 1;
		
		return new Matrix4(m);
	}
	
	static class Transform
	{
		
	}
}
