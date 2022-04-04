package com.raytracing;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.content.Context;
import android.util.Log;
import android.graphics.PixelFormat;

import java.util.Random;
import java.util.*;



public class MainActivity extends Activity
{
	RenderView renderView;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		renderView = new RenderView(this);
		setContentView(renderView);
		
    }

	@Override
	public boolean moveTaskToBack(boolean nonRoot)
	{
		// TODO: Implement this method
		return super.moveTaskToBack(nonRoot);
		
	}
	


	class RenderView extends SurfaceView implements SurfaceHolder.Callback, Runnable
	{
		
		Bitmap renderImage;
		Canvas canvas;
		Thread renderThread;
		SurfaceHolder holder;
		
		double aspect_ratio = 16.0/9.0;
		int image_width = 1080; 
		int image_height = (int)(image_width / aspect_ratio);
		int sampler_per_pixel = 16;
		int max_depth = 32;
		
		
		Paint paint;
		
		/*double viewport_height = 2.0;
		double viewport_width = aspect_ratio * viewport_height;
		double focal_length = 1.0;
		
		PointV origin = new PointV(0.0, 0.0, 0.0);
		Vec3 horizontal = new Vec3(viewport_width, 0.0, 0.0);
		Vec3 vertical = new Vec3(0.0, viewport_height, 0.0);
		Vec3 focal_length3 = new Vec3(0.0, 0.0, focal_length);
		Vec3 lower_left_corner = new Vec3(origin.e[0] - horizontal.e[0]/2 - vertical.e[0]/2 - focal_length3.e[0],
										  origin.e[1] - horizontal.e[1]/2 - vertical.e[1]/2 - focal_length3.e[1],
										  origin.e[2] - horizontal.e[2]/2 - vertical.e[2]/2 - focal_length3.e[2]);
		*/
		
		Camera camera;
		Hittable_list world;
		
							
		RenderView(Context context)
		{
			super(context);
			this.getHolder().setFormat(PixelFormat.TRANSLUCENT);//设置surface的像素格式，不然会导致图像颜色不过渡导致断层
			this.getHolder().addCallback(this);
			renderThread = new Thread(this);
			holder = getHolder();
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder surfaceHolder)
		{
			
			renderThread.start();
			
			/*String s1 = Integer.toHexString(renderImage.getPixel(0,1));
			String s2 = Integer.toHexString(renderImage.getPixel(0,2));
			String s3 = Integer.toHexString(renderImage.getPixel(0,3));
			Log.i("颜色y1>>>>>>>>>>>>>>",s1);
			Log.i("颜色y2>>>>>>>>>>>>>>",s2);
			Log.i("颜色y3>>>>>>>>>>>>>>",s3);*/
			
			/*canvas = surfaceHolder.lockCanvas();
			canvas.setDensity(1000);
			canvas.drawBitmap(renderImage, 0, 0, null);
			this.getHolder().unlockCanvasAndPost(canvas);
			*/
		}

		@Override
		public void surfaceChanged(SurfaceHolder param1SurfaceHolder, int param1Int1, int param1Int2, int param1Int3)
		{
			// TODO: Implement this method
			/*int t1 = 1;
			 int t2 = 512;
			 double i = (double)t1 / (double)t2;
			 String s = Double.toString(i);
			 Log.i(">>>>>>>>>>>>>>>>",s);*/
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder param1SurfaceHolder)
		{
			// TODO: Implement this method
		}
		
		double hit_sphere(PointV center,double radius, Ray r)
		{
			Vec3 oc = new Vec3(r.origin().x() - center.x(),
								r.origin().y() - center.y(),
								r.origin().z() - center.z());
			double a = r.direction().lenght_squared();
			//double b = 2.0 * Vec3.dot(oc, r.direction());
			double half_b = Vec3.dot(oc, r.direction());
			double c = oc.lenght_squared() - radius*radius;
			double discriminant = half_b*half_b - a*c;
			
			if(discriminant < 0)
			{
				return -1.0;
			}else
			{
				return (-half_b - Math.sqrt(discriminant)) / a;
			}
			
		}
		
		ColorV rayColor(Ray r, hittable world, int depth)
		{
			ColorV color = new ColorV();
			
			hit_record rec = new hit_record();
			
			double t;//= hit_sphere(new PointV(0.0,0.0,-1.0), 0.5, r);
			
			
			
			/*if(t > 0.0)
			{
				Vec3 M = new Vec3(0.0, 0.0, -1.0);
				Vec3 N = Vec3.unit_vector(new Vec3(r.at(t).e[0] - M.e[0]/0.5,
												   r.at(t).e[1] - M.e[1]/0.5,
												   r.at(t).e[2] - M.e[2]/0.5));
				return new ColorV(0.5*(N.e[0]+1.0), 0.5*(N.e[1]+1.0), 0.5*(N.e[2]+1.0));
			}
			*/
			if(depth <= 0)
			{
				return new ColorV();
			}
			
			/*if(world.hit(r, 0.001, Rtweekend.infinity, rec))
			{
				//Vec3 randVec = Vec3.random_unit_vector();
				//PointV target = new PointV(rec.p.e[0] + rec.normal.e[0] + randVec.e[0],rec.p.e[1] + rec.normal.e[1] + randVec.e[1],
				//						   rec.p.e[2] + rec.normal.e[2] + randVec.e[2]);
				
				Vec3 hemisphere  = Vec3.random_in_hemisphere(rec.normal);
				PointV target = new PointV(rec.p.e[0] + hemisphere.e[0], rec.p.e[1] + hemisphere.e[1], rec.p.e[2] + hemisphere.e[2]);
				ColorV raycolor = rayColor(new Ray(rec.p, new PointV(target.e[0]-rec.p.e[0], target.e[1]-rec.p.e[1], target.e[2]-rec.p.e[2])), world, depth-1);
				return new ColorV(0.5 * raycolor.e[0], 0.5 * raycolor.e[1], 0.5 * raycolor.e[2]);
			}*/
			
			mat_var matVar = new mat_var();
			if(world.hit(r, 0.001, Rtweekend.infinity, rec))
			{
				//return new ColorV(0.5*(rec.normal.e[0]+(color.e[0]=1)), 0.5*(rec.normal.e[1]+(color.e[1]=1)), 0.5*(rec.normal.e[2]+(color.e[2]=1)));
				
				if(rec.mat_ptr.scatter(r, rec, matVar))
				{
					ColorV ray_color = rayColor(matVar.scattered, world, depth-1);
					return new ColorV(matVar.attenuation.e[0] * ray_color.e[0], matVar.attenuation.e[1] * ray_color.e[1], matVar.attenuation.e[2] * ray_color.e[2]);
				}
				return new ColorV();
				

			}
			
			Vec3 unit_direction = Vec3.unit_vector(r.direction());
			
			t = 0.5*(unit_direction.y()+1.0);
			
			return new ColorV(  (1.0-t)*(color.e[0] = 1.0)+t*(color.e[0] = 0.5),
								(1.0-t)*(color.e[1] = 1.0)+t*(color.e[1] = 0.7),
								(1.0-t)*(color.e[2] = 1.0)+t*(color.e[2] = 1.0));
		}
		
		
		@Override
		public void run()
		{
			renderImage = Bitmap.createBitmap(image_width, image_height, Bitmap.Config.ARGB_8888);
			// TODO: Implement this method
			PointV lookfrom =new PointV(-1, 3, 2);
			PointV lookat = new PointV(0, 0,-1);
			Vec3 vup = new Vec3(0, 1, 0);
			Vec3 looklength = new Vec3(lookfrom.e[0]-lookat.e[0],lookfrom.e[1]-lookat.e[1],lookfrom.e[2]-lookat.e[2]);
			
			double dist_to_focus = looklength.lenght();
			double aperture = 0.2;
			
			camera= new Camera(lookfrom,lookat ,vup , 20, aperture, dist_to_focus,aspect_ratio);
			
			world = new Hittable_list();
			
			Material ground_material = new Lambertian(new ColorV(0.8, 0.8, 0.0));
			Material center_material = new Lambertian(new ColorV(0.8,0.8,0.8/*0.7, 0.3, 0.3*/));
			Material left_material = new Dielectric(1.5);
			Material right_material = new Metal(new ColorV(0.8, 0.7, 0.5), 0.1);
			
			Sphere ground = new Sphere(new PointV(0, -100.5, -1), 100, ground_material);
			Sphere diffuseSphere = new Sphere(new PointV(0.0, 0.0, -1.0), 0.5, center_material);
			Sphere metalSphereL = new Sphere(new PointV(-1.0, 0.0, -1.0), 0.49, left_material);
			Sphere metalSphereL_in = new Sphere(new PointV(-1.0, 0.0, -1.0), -0.4, left_material);
			Sphere metalSphereR = new Sphere(new PointV(1.0, 0.0, -1.0), 0.5, right_material);
			
			
			
			world.add(diffuseSphere);
			//world.add(metalSphereL_in);
			world.add(metalSphereL);
			world.add(metalSphereR);
			world.add(ground);

			paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setTextSize(64);
			
			
			ColorV pixelColor, color;
			Random rand =new Random();
			Ray r;
			
			double u, v;
			
			int imageh=image_height-1; 
			int y=-1;
			for(int j = imageh; j >= 0; --j)
			{
				y = y+1;
				for(int x = 0; x < image_width; ++x)
				{
					/*int R = x;
					 int G = j;
					 int B = 128;

					 renderImage.setPixel(x,y,Color.rgb(R,G,0));
					 */
					pixelColor = new ColorV();//每个像素的初始化，在for外初始化会导致颜色不断累加成白色
					for(int s=0; s<sampler_per_pixel; ++s)
					{
						 u = ((double)x + rand.nextDouble()) / (double)(image_width-1);
						 v = ((double)j + rand.nextDouble()) / (double)(image_height-1);

						r = camera.get_ray(u,v);
						color = rayColor(r, world, max_depth);
						pixelColor = new ColorV( color.e[0]+pixelColor.e[0],
												 color.e[1]+pixelColor.e[1],
												 color.e[2]+pixelColor.e[2]);
						
					}
					renderImage.setPixel(x,y,WriteColor.writeColor(pixelColor,sampler_per_pixel));
				}
				canvas = this.getHolder().lockCanvas();
				canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
				canvas.drawText(Double.toString(j),10,800,paint);
				this.getHolder().unlockCanvasAndPost(canvas);
			}
			canvas = this.getHolder().lockCanvas();
			canvas.setDensity(1300);
			canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			canvas.drawBitmap(renderImage, 0, 0, null);
			this.getHolder().unlockCanvasAndPost(canvas);
			
			
		}
		
	}
}
