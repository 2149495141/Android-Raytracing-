package com.raytracing;

public class mat_var
{
	Ray scattered;
	ColorV attenuation;
	mat_var()
	{
		scattered = new Ray();
		attenuation = new ColorV();
	}
}
