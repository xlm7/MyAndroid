package com.example.ourmemory;
import cn.bmob.v3.listener.SaveListener;
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
public class SigninActivity extends Activity {
private EditText name=null;
private EditText password=null;
private EditText email=null;
private Button register=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		name=(EditText) findViewById(R.id.resign_name);
		password=(EditText) findViewById(R.id.resign_password);
		email=(EditText) findViewById(R.id.resirn_email);
	    register=(Button) findViewById(R.id.register2);
		register.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				String na=name.getText().toString();
				String pw=password.getText().toString();
				String emai=email.getText().toString();
				final Student stu=new Student();
			stu.setUsername(na);
			stu.setPassword(pw);
			stu.setEmail(emai);
			stu.setClassId("000");
			stu.setIsexit(1);
			stu.signUp(SigninActivity.this, new SaveListener() {				
				@Override
				public void onSuccess() {					
					Toast.makeText(SigninActivity.this, "◊¢≤·≥…π¶£¨º§ªÓ” œ‰∫Ûº¥ø…µ«¬º£°", Toast.LENGTH_SHORT).show();
					 startActivity(new Intent(SigninActivity.this, LoginActivity.class)); 	           
					}				
				@Override
				public void onFailure(int arg0, String arg1) {
					Toast.makeText(SigninActivity.this, "∞•£¨◊¢≤· ß∞‹¡À£°", Toast.LENGTH_SHORT).show();
				}
			});
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
