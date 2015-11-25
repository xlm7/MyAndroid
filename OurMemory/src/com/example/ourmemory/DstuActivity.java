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
			//�༶��û�и�ѧ��
			}			
			@Override
			public void onSuccess(Student arg0) {
				if(arg0.getIcon()!=null){
					//����ͷ��
				arg0.getIcon().loadImage(DstuActivity.this, imageview);
				}
				//���ػ�����Ϣ
				stuname.setText("����:"+arg0.getUsername());
				String sex=arg0.isSex()?"��":"Ů";
				stusex.setText("�Ա�:"+sex);
				stuid.setText("ѧ��:"+arg0.getStudentId());
				stuaddress.setText("����:"+arg0.getAddress());
				stuphone.setText(arg0.getPhone());
				stuintro.setText("���˼��:\n"+arg0.getIntro());
				sturole.setText("��ɫ:"+(arg0.getRole()==0?"��ͨ�û�":"����Ա"));
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
