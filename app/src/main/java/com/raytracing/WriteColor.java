package com.raytracing;

import android.graphics.Color;
import android.util.Log;

import static com.raytracing.Rtweekend.clamp;

class WriteColor
{
	static int writeColor(ColorV c, int sampler_per_pixel)
	{
		double r = c.e[0];
		double g = c.e[1];
		double b = c.e[2];
		
		double scale = 1.0/(double)sampler_per_pixel;
		r = Math.sqrt(scale * r);
		g = Math.sqrt(scale * g);
		b = Math.sqrt(scale * b);
		
		int R = (int)(clamp(r,0.0,0.99) * 256);
		int G = (int)(clamp(g,0.0,0.99) * 256);
		int B = (int)(clamp(b,0.0,0.99) * 256);
		
		
		
		//String s = Integer.toHexString(R);
		//String s1 = Integer.toHexString(ir);
		//Log.i("R的颜色值是＞＞＞＞＞＞＞＞＞＞＞＞＞＞＞＞＞＞＞＞＞＞",s);
		//Log.i("ir的颜色值是==================================",s1);
		return (255 & 0xff)<<24 | (R & 0xff)<<16 | (G & 0xff)<<8 | (B & 0xff);
	}
}
