package com.xlm.firewall;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.android.internal.telephony.ITelephony;
import com.xlm.dao.DataBaseImp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyService extends Service {
	private ArrayList PhoneNum=null;
	private DataBaseImp db=null;
	MyReceiver receiver =null;
	int flag=-1;
	private ITelephony  iTelephony;
	final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	
	public MyService() {}

	@Override
	public IBinder onBind(Intent intent) {	
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
  }
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		flag=intent.getIntExtra("flag", -1);
		switch (flag) {
		case 0:
			smsSrevice();
			break;
		case 1:
			callservice();
			break;
		case 2:
			smsSrevice();
			callservice();
			break;
		default:
			break;
		}
	}
	
	//拦截电话
	public void callservice(){
		db=new DataBaseImp(MyService.this);
		PhoneNum=db.getPhoneNum();
	       TelephonyManager telManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
	       Class <TelephonyManager> c = TelephonyManager.class; 	       
	         Method getITelephonyMethod = null; 
	         try{
	         getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[])null);
	         getITelephonyMethod.setAccessible(true);  
	       iTelephony = (ITelephony) getITelephonyMethod.invoke(telManager, (Object[])null);  
	         }catch(IllegalArgumentException e){
	        	  e.printStackTrace(); 
	         } catch (Exception e) { 
	              e.printStackTrace(); 
	         } 
	       // 注册一个监听器对电话状态进行监听
	       telManager.listen(new MyPhoneStateListener(),PhoneStateListener.LISTEN_CALL_STATE);
}
	//拦截短信
	public void smsSrevice(){
		receiver = new MyReceiver(MyService.this); 
		IntentFilter filter = new IntentFilter();
		filter.addAction(SMS_RECEIVED);
		filter.setPriority(1000);
		registerReceiver(receiver, filter);
	}
	
	
	   private class MyPhoneStateListener extends PhoneStateListener {
	       MediaRecorder recorder;	     
           public void onCallStateChanged(int state, String incomingNumber) {
	           switch (state) {
	           case TelephonyManager.CALL_STATE_IDLE: /* 无任何状态时 */
                 break;
	           case TelephonyManager.CALL_STATE_OFFHOOK: /* 接起电话时 */
                 break;
	           case TelephonyManager.CALL_STATE_RINGING: /* 电话进来时 */
	           {
	        	  
	        	   try {
	        		   if(PhoneNum.contains(incomingNumber)){
					       iTelephony.endCall();
					       Log.i("黑名单电话", "拒接");
	        		   }
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	              
	               break;
	           default:
	              break;
	           }
	           super.onCallStateChanged(state, incomingNumber);
	       }
	   }

	       
	@Override
	public void onDestroy() {
		if(flag==0||flag==2){
			unregisterReceiver(receiver);
		}
		super.onDestroy();	
	}
}
