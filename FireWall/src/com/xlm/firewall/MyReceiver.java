package com.xlm.firewall;

import java.util.ArrayList;
import java.util.Iterator;

import com.xlm.dao.DBHelper;
import com.xlm.dao.DataBaseImp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
	
   // private static final String queryString ="@echo";
    private Context context=null;
    private DataBaseImp db=null;
   
    //�������б�
    ArrayList<String> blackSmsNum = new ArrayList<String>();
    ArrayList<String> blackSensiWords = new ArrayList<String>();
	public MyReceiver(Context context) {
		this.context=context;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.
		
		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
			Log.i("XXXXX", "XXXXX");
			SmsManager sms = SmsManager.getDefault();
			Bundle bundle = intent.getExtras();
			db=new DataBaseImp(context);
			blackSmsNum= db.getSmsNum();
			blackSensiWords= db.getSensiWords();
			if (bundle != null){
			    Object[] pdus = (Object[]) bundle.get("pdus");
			    SmsMessage[] messages = new SmsMessage[pdus.length];
			    for (int i = 0; i < pdus.length; i++)
			        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			    for (SmsMessage message : messages){			
			        String msg = message.getMessageBody();
			        String phoneNum = message.getOriginatingAddress();
			        if(blackSmsNum.contains(phoneNum)){
			        	Log.i("yyyyy", "yyyyy");  	
				abortBroadcast(); //����ں������У�����ֹ�㲥�����ո��û�����
			         }
			         else{
			        	 Iterator it1 = blackSensiWords.iterator();
			             while(it1.hasNext()){
			            	  String str = (String) it1.next();
			            	 if (msg.contains(str)){
			            		 Log.i("zzzzz", "zzzzz");
			            		 abortBroadcast();
			        			 //	blackPhoneNum.add(phoneNum);  //���������
//			        				String out ="deny you!";    //�Զ��ط�һ�����Ÿ�to
//			        				sms.sendTextMessage(phoneNum, null, out, null, null);
			        			             }
			             }
			            
			        }//end if
			     }//end for
			}//end if(bundle != null)
		           }//end if(intent.getAction)
		    }//end onReceive
	
	
}
