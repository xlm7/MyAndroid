package com.example.android_picture;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class Start extends Activity implements OnClickListener{
private PaintView paintView;
private Button button1,button2,button3,button4,button5;
//private List<Map<String,Object>>dataList;
private String[]setting=new String[]{"画笔颜色","画笔粗细","画面数目",};
private int count=3;//默认图片数量
private int cc=0;//计数器，为已绘制图片数量
//public List<Bitmap> list_bitmap;
public List<String>list_name;
private SimpleAdapter colorAdapter;
private SimpleAdapter widthAdapter;
public int getCc() {
	return cc;
}


public void setCc(int cc) {
	this.cc = cc;
}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		button1=(Button)findViewById(R.id.button11);//清空
		button4=(Button) findViewById(R.id.button14);//设置
		button3=(Button) findViewById(R.id.button13);//撤销
		button2=(Button) findViewById(R.id.button12);//完成
		button5=(Button) findViewById(R.id.button15);//加载图片
		paintView=(PaintView) findViewById(R.id.paint_layout);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		//list_bitmap=new LinkedList<Bitmap>();
		list_name=new LinkedList<String>();
		int[] colorId= new int[]{R.drawable.red,R.drawable.green,R.drawable.pink,R.drawable.yellow,R.drawable.black};
		String[]colorTitle=new String[]{"红色","绿色","粉色","黄色","黑色"};
		int[] widthId=new int[]{R.drawable.s,R.drawable.m,R.drawable.l};
		String[]widthTitle=new String[]{"细","中","粗"};
		List<Map<String,Object>>colorItem=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>>widthItem=new ArrayList<Map<String,Object>>();
		for(int i=0;i<colorId.length;i++){
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("image", colorId[i]);
			map.put("title",colorTitle[i]);
			colorItem.add(map);
		}
		for(int i=0;i<widthId.length;i++){
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("image", widthId[i]);
			map.put("title",widthTitle[i]);
			widthItem.add(map);
		}
		colorAdapter=new SimpleAdapter(this,colorItem,R.layout.list_item, new String[]{"image","title"},new int[]{R.id.listimg,R.id.listtext} );
		widthAdapter=new SimpleAdapter(this,widthItem,R.layout.list_item, new String[]{"image","title"},new int[]{R.id.listimg,R.id.listtext} );
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		return true;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		//清除画布
		case R.id.button11:
			paintView.removeAllPaint();
			break;
		//完成本次绘图
		case R.id.button12:
			if(cc<count){
			cc++;
			Bitmap b=paintView.returnBitmap();
			//list_bitmap.add(b);
			String name=paintView.saveBitmap();
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);     
			Uri uri = Uri.fromFile(new File(name));     
		   intent.setData(uri);     
			sendBroadcast(intent);    
			list_name.add(name);//储存的是图片路径"/sdcard/minidonghua/"
			paintView.removeAllPaint();
			}
			if(cc==count)
			{
				Intent intent1=new Intent(Start.this, DongHua.class);
				intent1.putExtra("count", count);
				for(int i=1;i<=count;i++){
					String s="bitmap"+i;
					intent1.putExtra(s, list_name.get(i-1));
				}
				startActivity(intent1);
				//Log.i("after", "after");
			}
			break;
			//撤销
		case R.id.button13:
			paintView.undo();
			 break;
		    //设置动画画面数目
		case R.id.button14:
		    showListDialog();
			break;
			//重做
		case R.id.button15:
			Intent i = new Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i,1);
			default:
				break;	
		}
	}
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
	            Uri selectedImage = data.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	            Cursor cursor = getContentResolver().query(selectedImage,
	            filePathColumn, null, null, null);
	            cursor.moveToFirst();
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();
	            Bitmap b=BitmapFactory.decodeFile(picturePath);
	                          //修改图片尺寸
		            Bitmap btm2=Bitmap.createScaledBitmap(b, 850, 1000, true); //自定义
		            
	            paintView.setBitmap(btm2);	            
	        }
		}
	  private InputStream  Bitmap2IS(Bitmap bm){  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	            InputStream sbs = new ByteArrayInputStream(baos.toByteArray());    
	            return sbs;  
	        }
	private void showListDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("设置：");
		builder.setItems(setting, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					 showColorListDialog();
					 
					break;
				case 1:
					showWidthListDialog();
		           break;
				case 2:
					showsetCountDialog();
				default:
					break;
				}			
			}
		
		});
		AlertDialog dialog=builder.create();
		dialog.show();
	}

	protected void showWidthListDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new AlertDialog.Builder(Start.this);
		builder.setTitle("设置画笔粗细：");
		final ListView widList = new ListView(this);		
		widList.setAdapter(widthAdapter);		
		widList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					paintView.getmPaint().setStrokeWidth(5);					
					break;
				case 1:
					paintView.getmPaint().setStrokeWidth(20);
					break;
				case 2:
					paintView.getmPaint().setStrokeWidth(40);
					break;
				default:
					break;
				}
			}
		});
		widList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub				
			}
		});
		 builder.setView(widList); 
		 builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {      	    
             public void onClick(DialogInterface dialog, int which) {           	
            	 }
             });
		 AlertDialog dialog=builder.create();
			dialog.show();
	}


	protected void showColorListDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new AlertDialog.Builder(Start.this);
		builder.setTitle("设置颜色：");
		final ListView colList = new ListView(this);
		
		colList.setAdapter(colorAdapter);
		
		colList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					paintView.getmPaint().setColor(0xFFFF0000);
					
					break;
				case 1:
					paintView.getmPaint().setColor(0xFF458B00);
					break;
				case 2:
					paintView.getmPaint().setColor(0xFFFFAEB9);
					break;
				case 3:
					paintView.getmPaint().setColor(0xFFEEEE00);
					break;
				case 4:
					paintView.getmPaint().setColor(0xFF292929);
					break;
				default:
					break;
				}
			}
		});
		colList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		 builder.setView(colList); 
		 builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {      	    
             public void onClick(DialogInterface dialog, int which) {           	
            	 }
             });
		 AlertDialog dialog=builder.create();
			dialog.show();
			
	}


	@SuppressLint("ShowToast")
	private void showsetCountDialog() {
		// TODO Auto-generated method stub
		 AlertDialog.Builder builder=new AlertDialog.Builder(this);
		 final EditText inputServer = new EditText(this);
		 inputServer.setFocusable(true);
         inputServer.setInputType(InputType.TYPE_CLASS_NUMBER); 
         builder.setTitle("请设置图片数目（默认3幅）:");
         builder.setView(inputServer);   
         builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {      	    
                     public void onClick(DialogInterface dialog, int which) {
                         String inputString = inputServer.getText().toString();
                         if(inputString.equals("")){
                        	 inputString="3";
                        	 Toast.makeText(getApplicationContext(), "没有输入，则设置为默认值！",  Toast.LENGTH_SHORT).show(); 
                         }
                         int inputNumber= Integer.parseInt(inputString);
                         if(inputNumber<=0){
                        	 Toast.makeText(getApplicationContext(), "图片数目应大于0！",  Toast.LENGTH_SHORT).show();
                         }
                         else{
                         setCount(inputNumber);
                         }
                     }
                 });        
		AlertDialog dialog=builder.create();
		dialog.show();
	}
	public  int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
