package com.example.ourmemory;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import com.example.model.Student;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends Activity {
private EditText name1=null;
private EditText password1=null;
private Button login=null;
private Button register=null;
private Button forget=null;
private EditText et=null;
AlertDialog.Builder builder=null;
AlertDialog alert=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		name1=(EditText) findViewById(R.id.etname1);
		password1=(EditText) findViewById(R.id.etpw1);
		login=(Button) findViewById(R.id.flogin);
		et=new EditText(LoginActivity.this);
		forget=(Button) findViewById(R.id.forget);
		AlertDialog.Builder builder=new Builder(LoginActivity.this);
		builder.setTitle("请输入注册邮箱：");
		builder.setView(et);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface dialog, int which) {					
				String s=et.getText().toString();
				if(s!=null){
				BmobUser.resetPasswordByEmail(LoginActivity.this, s, new ResetPasswordByEmailListener() {					
					@Override
					public void onSuccess() {
							//重设密码成功
						}					
					@Override
					public void onFailure(int arg0, String arg1) {
					//重设密码失败	
					}
				});				
				alert.dismiss();
				}	
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alert.dismiss();					
			}
		});
		alert=builder.create(); 
		forget.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {		
					alert.show();
			}
		});
		register=(Button) findViewById(R.id.fregister);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Student stu=new Student();
				stu.setUsername(name1.getText().toString());
				stu.setPassword(password1.getText().toString());
				stu.login(LoginActivity.this, new SaveListener() {					
					@Override
					public void onSuccess() {
						Student s=BmobUser.getCurrentUser(LoginActivity.this, Student.class);				
						if(stu.getEmailVerified()){
						SharedPreferences sharedPreferences = getSharedPreferences("ourmemory", Context.MODE_PRIVATE); //私有数据
						Editor editor = sharedPreferences.edit();//获取编辑器
						editor.putString("name", name1.getText().toString());
						editor.putString("password", password1.getText().toString());
						editor.commit();
						if(!s.getClassId().equals("000")){							
						startActivity(new Intent(LoginActivity.this,ShowclassActivity.class));	
							finish();
						}else{						
							startActivity(new Intent(LoginActivity.this,BindclassActivity.class));	
							finish();
						}
					}else{
						Toast.makeText(LoginActivity.this, "请先激活邮箱！"+s.getEmail(), Toast.LENGTH_SHORT).show();
					}
					}					
					@Override
					public void onFailure(int arg0, String arg1) {
						Toast.makeText(LoginActivity.this, "用户名或密码输入错误！", Toast.LENGTH_SHORT).show();
						name1.setText("");
						password1.setText("");
					}
				});				
			}
		});		
		register.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//跳转至注册界面
				startActivity(new Intent(LoginActivity.this,SigninActivity.class));	
				finish();					
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
