package com.raytracing;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.*;
import java.util.*;
import android.graphics.*;



public class LoadObj
{


	static TriangleModel loadObj(Context context, String file_path, Material mat)
	{
		
		TriangleModel obj = new TriangleModel();

		try
		{
			AssetManager assets = context.getAssets();
			InputStream flieData = assets.open(file_path);
			InputStreamReader InputStreamReader = new InputStreamReader(flieData/*in*/);
			BufferedReader buffer = new BufferedReader(InputStreamReader);

			String line = null;
			String[] text;
			int[] index;

			PointV[] vertex;
			Triangle triangle;

			ArrayList<Double> vertexArray = new ArrayList<Double>();
			ArrayList<Double> faceArrayResult = new ArrayList<Double>();

			//按行接收字符流，并判断是否为空
			while((line=buffer.readLine())!=null)
			{
				text = line.split(" ");//用空格分割数据，例如"v 1 2 "会被分割为"v","1","2"

				if(text[0].trim().equals("v"))//如果第一个元素是顶点
				{
					vertexArray.add(Double.parseDouble(text[1]));//接收第一个顶点
					vertexArray.add(Double.parseDouble(text[2]));//接收第二个顶点
					vertexArray.add(Double.parseDouble(text[3]));//第三个顶点
					Log.i("顶点数", Integer.toString(vertexArray.size()));
				}
				else if(text[0].trim().equals("f"))//如果第一个数是三角形面
				{
					vertex = new PointV[3];
					//面数据片段
					//f 1,2,3
					//f 1,3,4 //三角形面数据 类似于顶点索引
					//存储索引数组
					index = new int[3];
					index[0] = Integer.parseInt(text[1])-1;//获取第二个字(也就是v后面的顶点)，加“-1”是因为ArrayList是从[0]开始记录数据，1－1=0
					vertex[0] = new PointV(vertexArray.get(index[0]*3),
										   vertexArray.get(index[0]*3+1),
										   vertexArray.get(index[0]*3+2));

					index[1] = Integer.parseInt(text[2])-1;//接收第二个顶点索引
					vertex[1] = new PointV(vertexArray.get(index[1]*3),//*3是为跨3个数，例index=1，1*3=3对应读取list[3]里的数
										   vertexArray.get(index[1]*3+1),//index=1,1*3+1=4,对应list[4]里的数
										   vertexArray.get(index[1]*3+2));

					index[2] = Integer.parseInt(text[3])-1;
					vertex[2] = new PointV(vertexArray.get(index[2]*3),
										   vertexArray.get(index[2]*3+1),
										   vertexArray.get(index[2]*3+2));

					/*for(int i=0; i<3; ++i)
					 {
					 ind = Integer.parseInt(text[i])-1;
					 vertex[i] = new PointV(vertexArray.get(ind),
					 vertexArray.get(ind+1),
					 vertexArray.get(ind+2));
					 }*/
					triangle = new Triangle(vertex, mat);
					obj.add(triangle);

				}

				/*canvas = MainActivity.holder.lockCanvas();
				 canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
				 if(vertexArray.size()!=0)
				 canvas.drawText("vartexReading:"+Integer.toString(vertexArray.size()), 10, 800, paint);
				 else
				 canvas.drawText("faceBuilding:"+Integer.toString(obj.triangleModel.size()), 10, 800, paint);
				 MainActivity.holder.unlockCanvasAndPost(canvas);*/
			}

			Log.i("三角形面", Integer.toString(obj.triangleModel.size()));
			vertexArray.clear();
			//dRuntime.getRuntime().gc();
			InputStreamReader.close();
			flieData.close();
		}catch(Exception e)
		{

			Log.e("加载obj错误",e.toString());
		}

		return obj;
	}

}
