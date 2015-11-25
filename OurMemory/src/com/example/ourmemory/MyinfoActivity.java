package com.example.ourmemory;
import java.io.File;

import com.example.model.Student;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
public class MyinfoActivity extends Activity {
private EditText name=null;
private EditText sex=null;
private EditText address=null;
private EditText phone=null;
private EditText intro=null;
private EditText classid=null;
private ImageButton imagebutton=null;
private Button save=null;
private EditText stuid=null;
Student stu=null;
BmobFile file;
ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		stu=BmobUser.getCurrentUser(MyinfoActivity.this, Student.class);
	    name=(EditText) findViewById(R.id.etname);
	    sex=(EditText) findViewById(R.id.etsex);
	    address=(EditText) findViewById(R.id.etaddress);
	    phone=(EditText) findViewById(R.id.etphone);
	    intro=(EditText) findViewById(R.id.etintro);
	    stuid=(EditText) findViewById(R.id.etstuid);
	    classid=(EditText) findViewById(R.id.etstuclassid);
	    imagebutton=(ImageButton) findViewById(R.id.imageButton1);
	    save=(Button) findViewById(R.id.save);
	    save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			  stu.setUsername(name.getText().toString());
			  stu.setSex(sex.getText().toString().equals("男"));
			  stu.setAddress(address.getText().toString());
			  stu.setPhone(phone.getText().toString());
			 stu.setIntro(intro.getText().toString());
			 stu.setStudentId(stuid.getText().toString());
			 stu.setClassId(classid.getText().toString());
			 if(file==null){
			 stu.update(MyinfoActivity.this,new UpdateListener() {
				  @Override
				public void onSuccess() {
					 Toast.makeText(MyinfoActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
					}				
				@Override
				public void onFailure(int arg0, String arg1) {
					 Toast.makeText(MyinfoActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();
		}
			});
			}else{
				progressDialog = ProgressDialog.show(MyinfoActivity.this, "保存信息", "信息上传中,请稍后...", true, false);				 
file.upload(MyinfoActivity.this, new UploadFileListener() {					
					@Override
					public void onSuccess() {
					stu.setIcon(file);
					stu.update(MyinfoActivity.this,new UpdateListener() {						
						@Override
						public void onSuccess() {
							progressDialog.dismiss();
							 Toast.makeText(MyinfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
						}	
						@Override
						public void onFailure(int arg0, String arg1) {
							progressDialog.dismiss();
							Toast.makeText(MyinfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
						}
					});	
					}					
					@Override
					public void onFailure(int arg0, String arg1) {
						progressDialog.dismiss();
						Toast.makeText(MyinfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
			 }
		});
	    if(stu.getIcon()!=null){
	    	 stu.getIcon().loadImage(MyinfoActivity.this, imagebutton);
	    }	   
	    imagebutton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(
			            Intent.ACTION_PICK,
			            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			            startActivityForResult(i,1);				
			}
		});
	    name.setText(stu.getUsername());
	    sex.setText(stu.isSex()?"男":"女");
	    address.setText(stu.getAddress());
	    phone.setText(stu.getPhone());
	    intro.setText(stu.getIntro());
	    stuid.setText(stu.getStudentId());
	    classid.setText(stu.getClassId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
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
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
