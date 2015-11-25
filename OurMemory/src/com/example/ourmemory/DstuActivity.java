package com.example.ourmemory;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import com.example.model.Acti;
import com.example.model.Student;
import com.example.ourmemory.R.id;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class DstuActivity extends Activity {
private ImageView imageview=null;
private TextView stuname=null;
private TextView stusex=null;
private TextView stuphone=null;
private TextView stuaddress=null;
private TextView stuintro=null;
private TextView stuid=null;
private TextView sturole=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dstu);
		imageview=(ImageView) findViewById(R.id.stuimage);
		stuname=(TextView) findViewById(R.id.stuname);
		stusex=(TextView) findViewById(R.id.stusex);
		stuphone=(TextView) findViewById(R.id.stuphone);
		stuaddress=(TextView) findViewById(R.id.stuaddress);
		sturole=(TextView) findViewById(R.id.sturole);
		stuintro=(TextView) findViewById(R.id.stuintro);
		stuid=(TextView) findViewById(R.id.stuid);
		Intent intent=getIntent();
		String id=intent.getStringExtra("id");
		BmobQuery<Student> query=new BmobQuery<Student>();
		query.getObject(DstuActivity.this, id, new GetListener<Student>() {
			@Override
			public void onFailure(int arg0, String arg1) {
			//班级中没有该学生
			}			
			@Override
			public void onSuccess(Student arg0) {
				if(arg0.getIcon()!=null){
					//下载头像
				arg0.getIcon().loadImage(DstuActivity.this, imageview);
				}
				//加载基本信息
				stuname.setText("姓名:"+arg0.getUsername());
				String sex=arg0.isSex()?"男":"女";
				stusex.setText("性别:"+sex);
				stuid.setText("学号:"+arg0.getStudentId());
				stuaddress.setText("籍贯:"+arg0.getAddress());
				stuphone.setText(arg0.getPhone());
				stuintro.setText("个人简介:\n"+arg0.getIntro());
				sturole.setText("角色:"+(arg0.getRole()==0?"普通用户":"管理员"));
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
