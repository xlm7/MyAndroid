package com.example.ourmemory;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import com.example.model.OurClass;
import com.example.model.Student;
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
public class BindclassActivity extends Activity {
private Button login=null;
private Button create=null;
private EditText classnum=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bindclass);
		login=(Button) findViewById(R.id.login3);
		create=(Button) findViewById(R.id.create);
		create.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//创建新的班级并绑定
				startActivity(new Intent(BindclassActivity.this,RegisterActivity.class));
				finish();
			}
		});
		classnum=(EditText) findViewById(R.id.classnumber);
		login.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(!classnum.getText().toString().equals("")){
				BmobQuery<OurClass> query=new BmobQuery<OurClass>();
				query.getObject(BindclassActivity.this, classnum.getText().toString(), new GetListener<OurClass>() {					
					@Override
					public void onFailure(int arg0, String arg1) {
						Toast.makeText(BindclassActivity.this, "班级号码不存在,请重新输入或创建班级", Toast.LENGTH_SHORT).show();						
					}					
					@Override
					public void onSuccess(OurClass arg0) {
						Student stu=(Student) BmobUser.getCurrentUser(BindclassActivity.this,Student.class);
						//设置用户班级号即绑定班级号
						stu.setClassId(classnum.getText().toString());	
						stu.update(BindclassActivity.this,new UpdateListener() {							
							@Override
							public void onSuccess() {
								startActivity(new Intent(BindclassActivity.this,ShowclassActivity.class));
								finish();
							}														@Override
							public void onFailure(int arg0, String arg1) {
								Toast.makeText(BindclassActivity.this, "绑定班级失败", Toast.LENGTH_SHORT).show();
							}
						});						
					}
				});
				
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
