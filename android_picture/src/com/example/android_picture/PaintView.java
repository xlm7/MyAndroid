package com.example.android_picture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PaintView extends View {
	private Canvas mCanvas;		
	private Path mPath;		
	private Paint mBitmapPaint;		
	private Bitmap mBitmap;		
    private Paint mPaint;				
	private ArrayList<DrawPath> savePath;		
	private ArrayList<DrawPath> deletePath;		
	private DrawPath dp;				
	private float mX, mY;	
	private Context context;
	private static final float TOUCH_TOLERANCE = 4;	
	private int bitmapWidth;		
	private int bitmapHeight;
	public PaintView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//得到屏幕的分辨率	
		this.context=context;
		DisplayMetrics dm = new DisplayMetrics();			
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		//dm=getApplicationConText().getResource().getDisplayMetrics();
		bitmapWidth = dm.widthPixels;			
		bitmapHeight = dm.heightPixels - 2 * 45;					
		initCanvas();			
		savePath = new ArrayList<DrawPath>();			
		deletePath = new ArrayList<DrawPath>();	
		
	}

	private void initCanvas() {
		// TODO Auto-generated method stub
		mPaint = new Paint();			
		mPaint.setAntiAlias(true);			
		mPaint.setDither(true);			
		mPaint.setColor(0xFF000000);			
		mPaint.setStyle(Paint.Style.STROKE);			
		mPaint.setStrokeJoin(Paint.Join.ROUND);			
		mPaint.setStrokeCap(Paint.Cap.ROUND);		
		mPaint.setStrokeWidth(10);						
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);					 						
		//画布大小 			
		mBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, 			
		Bitmap.Config.RGB_565);			
		mCanvas = new Canvas(mBitmap);
		//所有mCanvas画的东西都被保存在了mBitmap中						
		mCanvas.drawColor(Color.WHITE);			
		mPath = new Path();			
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	}
	private void midinitcanvas(){
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);					 						
		//画布大小 			
		mBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, 			
		Bitmap.Config.RGB_565);			
		mCanvas = new Canvas(mBitmap);
		//所有mCanvas画的东西都被保存在了mBitmap中						
		mCanvas.drawColor(Color.WHITE);	
	}
	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		//得到屏幕的分辨率			
				DisplayMetrics dm = new DisplayMetrics();			
				((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);					
				bitmapWidth = dm.widthPixels;			
				bitmapHeight = dm.heightPixels - 2 * 43;					
				initCanvas();			
				savePath = new ArrayList<DrawPath>();			
				deletePath = new ArrayList<DrawPath>();	
	}

	public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	public Paint getmPaint() {
		return mPaint;
	}

	public void setmPaint(Paint mPaint) {
		this.mPaint = mPaint;
	}
   
	class DrawPath{			
		Path path;			
		Paint paint;
		DrawPath(){
			path=new Path();
			paint=new Paint();
		}
		}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);	 
		//显示旧的画布	 			
		if (mPath != null) {				
		// 实时的显示
			
			canvas.drawPath(mPath, mPaint);			
			}	
	}
	public void undo(){						
		System.out.println(savePath.size()+"--------------");			
		if(savePath != null && savePath.size() > 0){				
			//调用初始化画布函数以清空画布				
			//initCanvas();
			midinitcanvas();
			//将路径保存列表中的最后一个元素删除 ,并将其保存在路径删除列表中				
			DrawPath drawPath = savePath.get(savePath.size() - 1);				
			deletePath.add(drawPath);				
			savePath.remove(savePath.size() - 1);								
			//将路径保存列表中的路径重绘在画布上				
			Iterator<DrawPath> iter = savePath.iterator();		
			//重复保存				
			while (iter.hasNext()) {	
		DrawPath dp = iter.next();	
		System.out.println(dp.paint.getColor());
		mCanvas.drawPath(dp.path, dp.paint);
				
		}				
			invalidate();// 刷新	
			System.out.println(savePath.size()+"--------------");
			}		
		}		
	/**		 * 恢复的核心思想就是将撤销的路径保存到另外一个列表里面(栈)，		
	 * 然后从redo的列表里面取出最顶端对象，		
	 *  * 画在画布上面即可		
	 */		
	public void redo(){			
		if(deletePath.size() > 0){				
		//将删除的路径列表中的最后一个，也就是最顶端路径取出（栈）,并加入路径保存列表中				
			DrawPath dp = deletePath.get(deletePath.size() - 1);				
			savePath.add(dp);				
			//将取出的路径重绘在画布上				
			mCanvas.drawPath(dp.path, dp.paint);				
			//将该路径从删除的路径列表中去除				
			deletePath.remove(deletePath.size() - 1);				
			invalidate();			
			}		
		}		
	
		/*		 * 清空的主要思想就是初始化画布		
		 *  * 将保存路径的两个List清空		
		 *   * */		
public void removeAllPaint(){			
	//调用初始化画布函数以清空画布			
	initCanvas();			
	invalidate();
	//刷新			
	savePath.clear();			
	deletePath.clear();		
	}		
/* 	 保存所绘图形		
  * 返回绘图文件的存储路径		
  */		
public String saveBitmap(){				
	//获得系统当前时间，并以该时间作为文件名			
	SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");		
	Date curDate = new Date(System.currentTimeMillis());//获取当前时间 			
	String str = formatter.format(curDate);		
	String paintPath = "";			
	str = str + "paint.png";			
	File dir = new File("/sdcard/minidonghua/");			
	File file = new File("/sdcard/minidonghua/",str);			
	if (!dir.exists()) { 				
		dir.mkdir(); 			
		} 			
	else{				
		if(file.exists()){					
			file.delete();				
			}			
		}						
	try {				
		FileOutputStream out = new FileOutputStream(file);				
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); 				
		out.flush(); 				
		out.close(); 				
		//保存绘图文件路径				
		paintPath = "/sdcard/minidonghua/" + str;									
		} catch (FileNotFoundException e) {				
			// TODO Auto-generated catch block				
			e.printStackTrace();			
			} catch (IOException e) {				
				// TODO Auto-generated catch block				
				e.printStackTrace();			
				} 
	
	
	return paintPath;		
	}						
private void touch_start(float x, float y) {			
	mPath.reset();//清空path			
	mPath.moveTo(x, y);			
	mX = x;			
	mY = y;		
	}		
private void touch_move(float x, float y) {			
	float dx = Math.abs(x - mX);			
	float dy = Math.abs(y - mY);			
	if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {				
		//mPath.quadTo(mX, mY, x, y);				
		mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
		mX = x;				
		mY = y;			
		}		
	}	

private void touch_up() {		
	mPath.lineTo(mX, mY);
	System.out.println(mPaint.getColor());
	mCanvas.drawPath(mPath, mPaint);		
	savePath.add(dp);		
	mPath = null;					
	}				
@Override		
public boolean onTouchEvent(MotionEvent event) {			
	float x = event.getX();			
	float y = event.getY();						
	switch (event.getAction()) {				
	case MotionEvent.ACTION_DOWN:									
		mPath = new Path();					
		dp = new DrawPath();
		//dp.path=new Path(mPath);
		dp.paint = new Paint(mPaint);
		dp.path = mPath;					
		//dp.paint = mPaint;									
		touch_start(x, y);					
		invalidate(); //清屏				
		break;				
		case MotionEvent.ACTION_MOVE:					
			touch_move(x, y);					
			invalidate();					
			break;				
			case MotionEvent.ACTION_UP:					
				touch_up();					
				invalidate();					
				break;			
				}			
	return true;		
	}

public Bitmap returnBitmap() {
	// TODO Auto-generated method stub
	return mBitmap;
}
public void setBitmap(Bitmap b){
	mBitmap=b;
}
		}

