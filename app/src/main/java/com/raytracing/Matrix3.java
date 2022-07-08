package com.raytracing;

public class Matrix3
{
	double[] m = new double[9];
	Matrix3(){
		double[] matrix = {1, 0, 0,
		 				   0, 1, 0,
						   0, 0, 1};
		m = matrix;
	}
	Matrix3(double[] matrix){
		m = matrix;
	}
	
	Vec3 Multiply(Matrix3 m, Vec3 v)
	{
		return new Vec3(m.m[0]*v.x() + m.m[1]*v.y() + m.m[2]*v.z(),
						m.m[3]*v.x() + m.m[4]*v.y() + m.m[5]*v.z(),
						m.m[6]*v.x() + m.m[7]*v.y() + m.m[8]*v.z());
	}
	
	Matrix3 Multiply(Matrix3 m1, Matrix3 m2)
	{
		double[] m = new double[9];
		Vec3 v036 = Multiply(m2,new Vec3(m1.m[0],m1.m[3],m1.m[6]));
		Vec3 v147 = Multiply(m2,new Vec3(m1.m[1],m1.m[4],m1.m[7]));
		Vec3 v258 = Multiply(m2,new Vec3(m1.m[2],m1.m[5],m1.m[8]));
		m[0] = v036.x(); m[1] = v147.x(); m[2] = v258.x();
		m[3] = v036.y(); m[4] = v147.y(); m[5] = v258.y();
		m[6] = v036.z(); m[7] = v147.z(); m[8] = v258.z();
		
		return new Matrix3(m);
	}
	
	
	static class Transform
	{
		static final void scale(TriangleModel model,double s)
		{
			for(int i = 0, legth = model.triangleModel.size(); i < legth; ++i)
			{
				model.triangleModel.get(i).vertex[0] = new PointV(Vec3.Multiply(s,model.triangleModel.get(i).vertex[0]));
				model.triangleModel.get(i).vertex[1] = new PointV(Vec3.Multiply(s,model.triangleModel.get(i).vertex[1]));
				model.triangleModel.get(i).vertex[2] = new PointV(Vec3.Multiply(s,model.triangleModel.get(i).vertex[2]));
			}
			
		}
		
		static final void translate(TriangleModel model, double x, double y, double z)
		{
			for(int i = 0, legth = model.triangleModel.size(); i < legth; ++i)
			{
				model.triangleModel.get(i).vertex[0] = new PointV(model.triangleModel.get(i).vertex[0].x()+x, model.triangleModel.get(i).vertex[0].y()+y, model.triangleModel.get(i).vertex[0].z()+z);
				model.triangleModel.get(i).vertex[1] = new PointV(model.triangleModel.get(i).vertex[1].x()+x, model.triangleModel.get(i).vertex[1].y()+y, model.triangleModel.get(i).vertex[1].z()+z);
				model.triangleModel.get(i).vertex[2] = new PointV(model.triangleModel.get(i).vertex[2].x()+x, model.triangleModel.get(i).vertex[2].y()+y, model.triangleModel.get(i).vertex[2].z()+z);
			}
		}
		
		static final void rotate(TriangleModel model, double x, double y, double z)
		{
			
		}
	}
}
