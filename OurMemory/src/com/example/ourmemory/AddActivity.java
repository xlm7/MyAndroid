package com.example.ourmemory;
import java.io.File;
import java.util.Calendar;

import com.example.model.Acti;
import com.example.model.Student;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
public class AddActivity extends Activity {
private ImageButton imagebutton=null;
private EditText actname=null;
private EditText actintro=null;
private Button save=null;
private Acti acti=null;
private Student stu=null;
private Button buttime=null;
private EditText ettime=null;
private int year, monthOfYear, dayOfMonth;
BmobFile file;
Thread thread;
ProgressDialog progressDialog;
private boolean isrun=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		 Calendar calendar = Calendar.getInstance();
	        year = calendar.get(Calendar.YEAR);
	        monthOfYear = calendar.get(Calendar.MONTH);
	        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		acti=new Acti();
		stu=new Student();
		ettime=(EditText) findViewById(R.id.ettime);
		stu=BmobUser.getCurrentUser(AddActivity.this, Student.class);
		buttime=(Button) findViewById(R.id.buttime);
		buttime.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				 DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener()
	                {
	                    @Override
	                    public void onDateSet(DatePicker view, int year, int monthOfYear,
	                            int dayOfMonth)
	                    {
	                        ettime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
	                    }
	                }, year, monthOfYear, dayOfMonth);	                
	                datePickerDialog.show();
			}
		});
		imagebutton=(ImageButton) findViewById(R.id.actpic);
		imagebutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(
			            Intent.ACTION_PICK,
			            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			            startActivityForResult(i,1);
			}
		});
		actname=(EditText) findViewById(R.id.actname);
		actintro=(EditText) findViewById(R.id.actintro);
		save=(Button) findViewById(R.id.actsave);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isrun=true;
				acti.setName(actname.getText().toString());
				acti.setIntro(actintro.getText().toString());
				acti.setClassId(stu.getClassId());
				acti.setWriter(stu.getObjectId());
				acti.setIsexit(1);
				acti.setOccurTime(ettime.getText().toString());				
				 if(file!=null){
					 progressDialog = ProgressDialog.show(AddActivity.this, "保存信息", "信息上传中,请稍后...", true, false); 						
				file.upload(AddActivity.this, new UploadFileListener() {					
					@Override
						public void onSuccess() {
					    	acti.setPic(file);
					    	//Toast.makeText(AddActivity.this, "图片上传成功！", Toast.LENGTH_SHORT).show();
					    	acti.save(AddActivity.this, new SaveListener() {
								@Override
								public void onSuccess() {
									progressDialog.dismiss();
									finish();
									}
								@Override
								public void onFailure(int arg0, String arg1) {
									Toast.makeText(AddActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();
									}
							});
							}
						@Override
						public void onFailure(int arg0, String arg1) {
							Toast.makeText(AddActivity.this, "图片上传失败！", Toast.LENGTH_SHORT).show();							
						}
					});
			}else{
				acti.save(AddActivity.this, new SaveListener() {					
					@Override
					public void onSuccess() {
						finish();
						}					
					@Override
					public void onFailure(int arg0, String arg1) {
						Toast.makeText(AddActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();						
					}
				});
			}
				}
		});
	}
	        @Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
		            Bitmap btm2=Bitmap.createScaledBitmap(b, 150, 180, true); //自定义
		            BitmapDrawable bd2=new BitmapDrawable(btm2);
		            //imagebutton.setBackgroundDrawable(bd2); 
		            imagebutton.setBackground(bd2);
		             imagebutton.setImageBitmap(null);
		             file=new BmobFile(new File(picturePath));
		}
			}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}	
}
