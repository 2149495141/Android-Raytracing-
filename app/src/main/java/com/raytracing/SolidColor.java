package com.raytracing;

public class SolidColor implements Textrue
{
	ColorV color_value;
	
	SolidColor(){}
	SolidColor(ColorV c)
	{
		color_value = c;
	}
	SolidColor(double r, double g, double b)
	{
		color_value = new ColorV(r,g,b);
	}
	
	@Override
	public ColorV value(double u, double v, PointV p)
	{
		// TODO: Implement this method
		return color_value;
	}
	
}
