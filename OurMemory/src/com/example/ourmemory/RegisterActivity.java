package com.example.ourmemory;
import com.example.model.OurClass;
import com.example.model.Student;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class RegisterActivity extends Activity {
private Button binding=null;
private EditText schoolName=null;
private EditText className=null;
private EditText classIntro=null;
Student stu=null;
OurClass cla=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		binding=(Button) findViewById(R.id.binding);
		schoolName=(EditText) findViewById(R.id.et_schoolName);
		className=(EditText) findViewById(R.id.et_className);
		classIntro=(EditText) findViewById(R.id.et_classIntro);
		binding.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(!className.getText().toString().equals("")&&!schoolName.getText().equals("")
						&&!classIntro.getText().toString().equals("")){
			stu=(Student) BmobUser.getCurrentUser(RegisterActivity.this,Student.class);
			cla=new OurClass();
			cla.setClassname(className.getText().toString());
			cla.setSchoolname(schoolName.getText().toString());
			cla.setClassintro(classIntro.getText().toString());
			cla.save(RegisterActivity.this, new SaveListener() {				
				@Override
				public void onSuccess() {
					stu.setClassId(cla.getObjectId());
					stu.setRole(1);
					stu.update(RegisterActivity.this, new UpdateListener() {						
						@Override
						public void onSuccess() {
							startActivity(new Intent(RegisterActivity.this,ShowclassActivity.class));
							finish();							
						}						
						@Override
						public void onFailure(int arg0, String arg1) {
							Toast.makeText(RegisterActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
							}
					});					
				}				
				@Override
				public void onFailure(int arg0, String arg1) {
					Toast.makeText(RegisterActivity.this, "创建班级失败！", Toast.LENGTH_SHORT).show();
				}
			});			
			}
				else{
					Toast.makeText(RegisterActivity.this, "班级名、学校名、班级信息不能为空！", Toast.LENGTH_SHORT).show();
				}
			}
		});		
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
