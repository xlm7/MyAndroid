package com.example.android_picture;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android_picture.*;
import com.example.android_picture.R.id;
@SuppressLint("ShowToast")
public class DongHua extends Activity implements android.view.View.OnClickListener{
	 private Button m_start = null;
	 private Button stop = null;
	 private ImageView image = null;
	 private Button time = null;
	 private int timeGap;
	 private int x_count;
	 private AnimationDrawable animationDrawable = null;
	 LinkedList<Bitmap> pictures;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donghua);
		m_start = (Button)findViewById(R.id.start);
		stop = (Button)findViewById(R.id.stop);
		time = (Button)findViewById(R.id.time);
		image = (ImageView)findViewById(R.id.imageview);
		//animationDrawable = new AnimationDrawable();
		m_start.setOnClickListener(this);  
		time.setOnClickListener(this);
        stop.setOnClickListener(this);
        Intent intent=getIntent();
       // timeGap=0;
        x_count=intent.getExtras().getInt("count");
        pictures=new LinkedList<Bitmap>();
       for(int i =1;i<=x_count;i++){
    	   String s="bitmap"+i;    	 
    	   String picturePath=intent.getStringExtra(s);	      
    	   Bitmap b=BitmapFactory.decodeFile(picturePath);
           pictures.add(b); 
       }
       /* for(int i =0;i<x_count;i++){
        	Log.i("tag", "success");
        	Drawable drawable =new BitmapDrawable(pictures.get(i));
        	//pictureList.add(drawable);
        	animationDrawable.addFrame(drawable, timeGap);
        	//animationDrawable.set
        }*/
 }
public int getTimeGap() {
		return timeGap;
	}
	public int getX_count() {
	return x_count;
}
public void setX_count(int x_count) {
	this.x_count = x_count;
}
	public void setTimeGap(int timeGap) {
		this.timeGap = timeGap;
	}
@Override
public void onWindowFocusChanged(boolean hasFocus) {
	// TODO Auto-generated method stub
	super.onWindowFocusChanged(hasFocus);
	
}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub	
		switch (v.getId()) {
		case R.id.start:
			animationDrawable = new AnimationDrawable();
			startFfame();				
			break;
		case R.id.stop:
			if(animationDrawable!=null){
			stopFfame();	
			}
			break;
		case R.id.time:	
			if(animationDrawable!=null){
				stopFfame();	
				}
			 showsetTimeDialog();
		      break;
		default:
			break;
		}
	}
	private void stopFfame() {
		// TODO Auto-generated method stub		
		animationDrawable.stop();		
	}
	private void startFfame() {
		// TODO Auto-generated method stub
		for(int i =0;i<x_count;i++){
        	Drawable drawable =new BitmapDrawable(pictures.get(i));	
        	if(getTimeGap()==0)
        		setTimeGap(500);
        	animationDrawable.addFrame(drawable, getTimeGap());	        	
        }
		animationDrawable.setOneShot(false);
	    image.setImageDrawable(animationDrawable);
		animationDrawable.start();		
	}
	private void showsetTimeDialog() {
		// TODO Auto-generated method stub
		 AlertDialog.Builder builder=new AlertDialog.Builder(this);
		 final EditText inputServer = new EditText(this);
		 inputServer.setFocusable(true);
         inputServer.setInputType(InputType.TYPE_CLASS_NUMBER); 
         builder.setTitle("请设置时间间隔,默认500ms:");
         builder.setView(inputServer);   
         builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {      	    
					public void onClick(DialogInterface dialog, int which) {
                         String inputString = inputServer.getText().toString();
                         if(inputString.equals("")){
                        	 inputString="500";
                        	 Toast.makeText(getApplicationContext(), "没有输入，则设置为默认值！",  Toast.LENGTH_SHORT).show(); 
                         }
                         int inputNumber= Integer.parseInt(inputString);
                         if(inputNumber<=0){
                        	 Toast.makeText(getApplicationContext(), "时间间隔应大于0！",  Toast.LENGTH_SHORT).show();    
                         }
                         else{
                         setTimeGap(inputNumber);
                         }
                     }
                 });
        
		AlertDialog dialog=builder.create();
		dialog.show();
	}
	}



