package com.example.ourmemory;
import com.example.model.Acti;
import com.example.model.Student;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UploadFileListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class DeventActivity extends Activity {
private ImageView imageview=null;
private TextView tvnum=null;
private TextView tvname=null;
private TextView tvintro=null;
private TextView tvtime=null;
private TextView tvwriter=null;
private String writerid=null;
ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devent);
		Intent intent=getIntent();
		String id=intent.getStringExtra("id");
		imageview=(ImageView) findViewById(R.id.eventimage);
		tvnum=(TextView) findViewById(R.id.evevtnum);
		tvname=(TextView) findViewById(R.id.eventname);
		tvintro=(TextView) findViewById(R.id.eventintro);
		tvwriter=(TextView) findViewById(R.id.eventwrite);
		tvtime=(TextView) findViewById(R.id.eventtime);
		BmobQuery<Acti> query=new BmobQuery<Acti>();
		query.getObject(DeventActivity.this, id, new GetListener<Acti>() {
			@Override
			public void onFailure(int arg0, String arg1) {
				}
			@Override
			public void onSuccess(Acti arg0) {
				//加载并显示基本信息
				if(arg0.getName()!=null){
				tvname.setText("事件名称:"+arg0.getName());
				}
				if(arg0.getClassId()!=null){
				tvnum.setText("事件编号:"+arg0.getClassId());
				}
				if(arg0.getIntro()!=null){
				tvintro.setText("事件简介:"+arg0.getIntro());
				}
				if(arg0.getOccurTime()!=null){
					tvtime.setText("事件发生时间:"+arg0.getOccurTime());
				}
				if(arg0.getWriter()!=null){
					 writerid=arg0.getWriter();
					 BmobQuery<Student> query2=new BmobQuery<Student>();
						query2.getObject(DeventActivity.this, writerid, new GetListener<Student>() {
							@Override
							public void onSuccess(Student arg0) {
								tvwriter.setText("事件记录者:"+arg0.getUsername());								
							}
							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub
								//未查到该账号用户
							}
						});
				}
				if(arg0.getPic()!=null){
					try{
					arg0.getPic().loadImage(DeventActivity.this, imageview);
					//Toast.makeText(DeventActivity.this, arg0.getPic().toString(), 1).show();
					}catch(Exception e){						
					}
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
