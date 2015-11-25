package com.example.ourmemory;
import java.util.List;
import com.example.model.OurClass;
import com.example.model.Student;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.FindStatisticsListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
public class ClainfoActivity extends Activity {
private TextView sname=null;
private TextView cname=null;
private TextView cnum=null;
private TextView cintro=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clainfo);
		sname=(TextView) findViewById(R.id.tvsname);
		cname=(TextView) findViewById(R.id.tvcname);
		cnum=(TextView) findViewById(R.id.tvcnum);
		cintro=(TextView) findViewById(R.id.tvcintro);
		Student stu=BmobUser.getCurrentUser(ClainfoActivity.this, Student.class);
		String classnum=stu.getClassId();
		if(!classnum.equals(""))
		{
			BmobQuery<OurClass> query=new BmobQuery<OurClass>();
		query.addWhereEqualTo("objectId", classnum);
	    query.findObjects(ClainfoActivity.this, new FindListener<OurClass>() {
		@Override
			public void onSuccess(List<OurClass> arg0) {
			if(arg0!=null){	
				//加载并设置班级基本信息
				sname.setText("学校："+arg0.get(0).getSchoolname());
				cname.setText("班级："+arg0.get(0).getClassname());
				cnum.setText("编号："+arg0.get(0).getObjectId());
				cintro.setText("简介：\n"+arg0.get(0).getClassintro());
			}
			}			
			@Override
			public void onError(int arg0, String arg1) {
				sname.setText("学校：");
				cname.setText("班级：");
				cnum.setText("编号：");
				cintro.setText("简介：\n");
			}
		});
	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
