package com.example.ourmemory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import com.example.model.Acti;
import com.example.model.Student;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
public class AllStudentActivity extends Activity {
	private ListView listview=null;
	List mylist=null;
	List<Student> students=null;
	SimpleAdapter adapter=null;
	Student stu;
	Student stud=new Student();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_student);
		listview=(ListView) findViewById(R.id.liststudent);
		mylist=new ArrayList<>();
		students=new ArrayList<>();
		stu=new Student();
		stu=BmobUser.getCurrentUser(AllStudentActivity.this,Student.class);
		String classid=stu.getClassId();
		BmobQuery<Student> stus=new BmobQuery<Student>();
		stus.addWhereEqualTo("classId", classid);
		stus.addWhereEqualTo("isexit", 1);
		stus.findObjects(AllStudentActivity.this, new FindListener<Student>() {			
			@Override
			public void onSuccess(List<Student> arg0) {
				students=arg0;
				for(int i=0;i<arg0.size();i++){
					Student student=arg0.get(i);
					HashMap map=new HashMap();					
					map.put("name", student.getUsername());
					map.put("stuId",student.getStudentId());
					mylist.add(map);
					}	
				 adapter=new SimpleAdapter(AllStudentActivity.this, mylist,
						R.layout.stulist, new String[]{"name","stuId"}, new int[]{R.id.stulistname,R.id.stulistphone});
				listview.setAdapter(adapter);
			}			
			@Override
			public void onError(int arg0, String arg1) {
				}
		});		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(AllStudentActivity.this,DstuActivity.class);
				intent.putExtra("id",students.get(position).getObjectId());
				startActivity(intent);				
			}
		});
	}
	@Override
	protected void onResume() {
		listview.setAdapter(adapter);
		super.onResume();
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
	Student getStudent(String stuid){		
		return stud;
	}
}
