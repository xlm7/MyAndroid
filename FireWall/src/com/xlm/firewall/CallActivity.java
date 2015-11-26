package com.xlm.firewall;

import com.xlm.dao.DBHelper;
import com.xlm.dao.DataBaseImp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CallActivity extends Activity {
	
	private SimpleCursorAdapter adapter=null;
	private Button callAdd=null;
	private EditText et=null;
	private TextView calltv=null;
	private ListView callListView=null;
	private DataBaseImp db=null;
	Cursor cursor=null;
	int selItem=1;
	int changeOrInse=0;//0：插入 1：修改
	AlertDialog.Builder builder=null;
	AlertDialog alert=null;
	SQLiteDatabase db0=null;
	DBHelper helper =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
		init();
	}
	
	void init(){
		callAdd=(Button) findViewById(R.id.callAdd0);
		calltv=(TextView) findViewById(R.id.calltv0);
		callListView=(ListView) findViewById(R.id.callListView0);
		et=new EditText(CallActivity.this);
		db=new DataBaseImp(CallActivity.this);
		helper = new DBHelper(CallActivity.this, "fw.db", null,1);  
	    db0=helper.getWritableDatabase();
		cursor = db0.rawQuery( "SELECT _id, phone FROM PhoneNum",null);  
		String[] from = { "_id", "phone"};
		int[] to = { R.id.txtID, R.id.txtPhone};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter( this, R.layout.listview, cursor, from, to);
		calltv.setText("已有"+cursor.getCount()+"条电话黑名单号码");
		callListView.setAdapter(adapter);
		builder=new Builder(CallActivity.this);
		builder.setTitle("请输入");
		builder.setView(et);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface dialog, int which) {					
				String s=et.getText().toString();
				if(s!=null&&changeOrInse==0){
				db.addPhoneNum(s);
				Toast.makeText(CallActivity.this, "添加电话黑名单成功", Toast.LENGTH_SHORT).show();
				onCreate(null);
				alert.dismiss();
				}	
				
				if(s!=null&&changeOrInse==1){
					db.changePhoneNum(s, selItem);
					Toast.makeText(CallActivity.this, "修改电话黑名单成功", Toast.LENGTH_SHORT).show();
					onCreate(null);
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
		registerForContextMenu(callListView); 
		callAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeOrInse=0;
				alert.show();
				
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call, menu);
		return true;
	}

	@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			super.onCreateContextMenu(menu, v, menuInfo);
			menu.setHeaderTitle("操作");
			getMenuInflater().inflate(R.menu.call, menu);
		}
	
	@Override
		public boolean onContextItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
		switch ( item.getItemId() ) {
	    case R.id.item1:
		deal(item);
		return true;
	    case R.id.item2:
		//修改
	    	change(item);
		return true;
	    default:  
        return false;
	}
		}
	
	public void deal(MenuItem item){
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();		
		if(info.id>0){
		    new AlertDialog.Builder(this).setTitle("删除" + info.id)
		                 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		                 public void onClick(DialogInterface dialog, int which) {
		                 DBHelper helper = new DBHelper(getApplicationContext(), "fw.db", null,1);  
		                 SQLiteDatabase db=helper.getWritableDatabase();
		                 db.execSQL( "Delete from PhoneNum where _id=?", new Object[ ]{ info.id } );
		                 db.close();    
		                 Toast.makeText(CallActivity.this, "记录删除成功", Toast.LENGTH_SHORT).show();
		                 onCreate(null);     //重新加载一次Activity，刷新
		         }
		                 })
		                 .setNegativeButton("取消", null)
		                 .show();
		}
	}
	
public void change(MenuItem item){
	final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();		
	if(info.id>0){
	changeOrInse=1;
	selItem=(int) info.id;
	alert.show();
	}
	}
	
}
