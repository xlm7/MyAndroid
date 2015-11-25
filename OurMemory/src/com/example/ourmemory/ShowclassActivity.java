package com.example.ourmemory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.model.Acti;
import com.example.model.Student;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
public class ShowclassActivity extends Activity {
private ListView clalist=null;
List mylist=null;//���name��num
List<Acti> actilist=null;//���Acti����
SimpleAdapter adapter=null;
String classid=null;
private Student stu=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showclass);	
		clalist=(ListView) findViewById(R.id.claActi);
	    actilist=new ArrayList<Acti>();
		mylist=new ArrayList<>();
		stu=new Student();
		stu=BmobUser.getCurrentUser(ShowclassActivity.this,Student.class);
		classid=stu.getClassId();
		clalist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
				Intent intent=new Intent(ShowclassActivity.this,DeventActivity.class);
				intent.putExtra("id",actilist.get(position).getObjectId());
				startActivity(intent);					
			}
		});	
		clalist.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final String actiid=actilist.get(position).getObjectId();
				new AlertDialog.Builder(ShowclassActivity.this)
				.setMessage("�Ƿ�ɾ���¼���"+actilist.get(position).getName()+"����")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(stu.getRole()==1){
						Acti acti=new Acti();
						acti.setObjectId(actiid);
						acti.setIsexit(0);
						acti.update(ShowclassActivity.this, new UpdateListener() {							
							@Override
							public void onSuccess() {
								onResume();
							}						
							@Override
							public void onFailure(int arg0, String arg1) {
								Toast.makeText(ShowclassActivity.this, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
								}
						});						
						}
						else{
							Toast.makeText(ShowclassActivity.this, "����Ա����ɾ��Ȩ�ޣ�", Toast.LENGTH_SHORT).show();
						}
					}					
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
				}
				}).show();
				return false;
			}
		});
		}
	@Override
	protected void onResume() {
	    mylist.clear();
	    clalist.setAdapter(null);
		BmobQuery<Acti> actis=new BmobQuery<Acti>();
		actis.addWhereEqualTo("classId", classid);
		actis.addWhereEqualTo("isexit", 1);
		actis.findObjects(ShowclassActivity.this, new FindListener<Acti>() {			
			@Override
			public void onSuccess(List<Acti> arg0) {
				actilist=arg0;
				for(int i=0;i<arg0.size();i++){
					Acti act=arg0.get(i);
					HashMap map=new HashMap();				
					map.put("name", act.getName());
					map.put("num",i+1);
					mylist.add(map);					
				}
				adapter=new SimpleAdapter(ShowclassActivity.this, mylist,
						R.layout.actilist, new String[]{"name","num"}, new int[]{R.id.actname,R.id.actnum});				
				clalist.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}			
			@Override
			public void onError(int arg0, String arg1) {
				}
		});
		super.onResume();
	}
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.showclass, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menuclainfo:
			startActivity(new Intent(ShowclassActivity.this,ClainfoActivity.class));
			break;
		case R.id.menumyinfo:
			startActivity(new Intent(ShowclassActivity.this,MyinfoActivity.class));			
			break;
		case R.id.menustuinfo:
			startActivity(new Intent(ShowclassActivity.this,AllStudentActivity.class));
		     break;
		case R.id.menuadd:
			startActivity(new Intent(ShowclassActivity.this,AddActivity.class));
			break;
		case R.id.menuexit:
			SharedPreferences sharedPreferences = getSharedPreferences("ourmemory", Context.MODE_PRIVATE); //˽������
			Editor editor = sharedPreferences.edit();//��ȡ�༭��
			editor.putString("name", "");
			editor.putString("password", "");
			editor.commit();//�ύ�޸�
			finish();
		default:
			break;
		}		
		return super.onOptionsItemSelected(item);
	}
}
