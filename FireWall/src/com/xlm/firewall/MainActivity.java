package com.xlm.firewall;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{
private Button blackCall=null;
private Button open=null;
private Button sensitiveWord=null;
private Button blackSms=null;
private Button close=null;

private int flag=3;//0:短信 1：电话 2：电话和短信  3:关闭
//这个值必须是"android.provider.Telephony.SMS_RECEIVED"
	final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	//动态注册用
	MyReceiver receiver = new MyReceiver(MainActivity.this); 
	TelephonyManager tManager;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		blackSms=(Button) findViewById(R.id.blackSms);
		sensitiveWord=(Button) findViewById(R.id.sensitiveWord);
		blackCall=(Button) findViewById(R.id.blackCall);
		open=(Button) findViewById(R.id.open);
		close=(Button) findViewById(R.id.close);
		blackCall.setOnClickListener(this);
		sensitiveWord.setOnClickListener(this);
		blackSms.setOnClickListener(this);
		open.setOnClickListener(this);
		close.setOnClickListener(this);
		
}

	 void showdialog(){
		 new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.shu1)
		 .setTitle("防火墙类型选择：")	
		 .setMessage("请选择拦截的内容：")
		 .setPositiveButton("电话", new DialogInterface.OnClickListener() {
		     public void onClick(DialogInterface dialog, int whichButton) {
		    	 switch (flag) {
				case 0:
				case 2:  				
					turnoff();
					flag=1;
					turnOn();
					break;
				case 1:
					break;
				case 3:
					flag=1;
					turnOn();
					break;
			    default:
					break;
				}
		}
		 })
		 .setNeutralButton("短信", new DialogInterface.OnClickListener() {
		     public void onClick(DialogInterface dialog, int whichButton) {
		    	 switch (flag) {
					case 1:
					case 2:  				
						turnoff();
						flag=0;
						turnOn();
						break;
					case 0:
						break;
					case 3:
						flag=0;
						turnOn();
						break;
				    default:
						break;
					}
			
		     }
		 })
		 .setNegativeButton("电话和短信", new DialogInterface.OnClickListener() {
		     public void onClick(DialogInterface dialog, int whichButton) {
		    	 switch (flag) {
					case 0:
					case 1:  				
						turnoff();
						flag=2;
						turnOn();
						break;
					case 2:
						break;
					case 3:
						flag=2;
						turnOn();
						break;
				    default:
						break;
					}			
		     }
		 })
		 .show();		
	 }
	 
	void turnOnSms(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(SMS_RECEIVED);
		filter.setPriority(1000);
		registerReceiver(receiver, filter);
	}
	
	//拦截电话
   void turnOnCall(){		
	   Intent service = new Intent(MainActivity.this,MyService.class);
	   service.putExtra("flag", flag);	   
       MainActivity.this.startService(service); 
	}
  
	void turnoff(){		
			  if(flag!=3) {                               
			 Intent name = new Intent(MainActivity.this,MyService.class);  
			 MainActivity.this.stopService(name);	
			  }
		
	}
	
	
	void turnOn(){ 
		Intent service = new Intent(MainActivity.this,MyService.class);
		service.putExtra("flag", flag);	
	    MainActivity.this.startService(service); 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	
	

	@Override
	public void onClick(View v) {		
		switch (v.getId()) {
		case R.id.blackCall:
			Intent intent1=new Intent(MainActivity.this,CallActivity.class);
			startActivity(intent1);
			break;
		case R.id.blackSms:
			Intent intent2=new Intent(MainActivity.this,SmsActivity.class);
			startActivity(intent2);
			break;
		case R.id.sensitiveWord:
			Intent intent3=new Intent(MainActivity.this,SensiActivity.class);
			startActivity(intent3);
			break;
		case R.id.open:
			showdialog();
			break;
		case R.id.close:
			turnoff();
			flag=3;
			Toast.makeText(MainActivity.this, "已成功关闭防火墙！", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}		
	}
}
