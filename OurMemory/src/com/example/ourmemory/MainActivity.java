package com.example.ourmemory;
import com.example.model.OurClass;
import com.example.model.Student;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.print.PrintAttributes.Margins;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;
public class MainActivity extends Activity {
	 private ImageView welcomeImg = null; 
	 private ImageView flowerImage=null;
	 boolean firstTime=true;
	 SharedPreferences prefs=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bmob.initialize(MainActivity.this, "db6539dda475d9917c23d1f832dfefa0");
		 welcomeImg = (ImageView) this.findViewById(R.id.imageView1);
		 flowerImage = (ImageView) this.findViewById(R.id.imageView2);
		 prefs = PreferenceManager.getDefaultSharedPreferences(this);
		 firstTime= prefs.getBoolean("first_time", true);
	     AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);  
	     // 设置动画显示时间  
	     anima.setDuration(3000);
	     welcomeImg.startAnimation(anima); 
	     flowerImage.startAnimation(anima);
	     anima.setAnimationListener(new AnimationImpl());
	}	
	private class AnimationImpl implements AnimationListener {  		  
        @Override  
        public void onAnimationStart(Animation animation) {  
        welcomeImg.setBackgroundResource(R.drawable.savetime);  
        }    
        @Override  
        public void onAnimationEnd(Animation animation) {  
            // 动画结束后跳转到别的页面  
        	skip(); 
        }   
        @Override  
        public void onAnimationRepeat(Animation animation) {  
   }  
        private void skip() {  
    	SharedPreferences sharedPreferences = getSharedPreferences("ourmemory", Context.MODE_PRIVATE); //私有数据  
    	//获取登录名称和密码
    	String name=sharedPreferences.getString("name","");
        String password=sharedPreferences.getString("password","");	
        final Student stu=new Student();
        //用所获登录名称和密码登录
		stu.setUsername(name);
		stu.setPassword(password);
		stu.login(MainActivity.this, new SaveListener() {
			@Override
			public void onSuccess() {
				//若登录成功 判断是否用户激活邮箱、绑定班级
				final Student currstu=new BmobUser().getCurrentUser(MainActivity.this, Student.class);
				if(currstu.getEmailVerified()&&(currstu.getClassId()!=null))
				{
					//获取用户绑定的班级对象
					BmobQuery<OurClass> query=new BmobQuery<OurClass>();
					query.getObject(MainActivity.this, currstu.getClassId(), new GetListener<OurClass>() {
						@Override
						public void onFailure(int arg0, String arg1) {
							startActivity(new Intent(MainActivity.this, LoginActivity.class));
							finish();
						}
						@Override
						public void onSuccess(OurClass arg0) {
							//登录成功 跳转至事件列表页面
								startActivity(new Intent(MainActivity.this, ShowclassActivity.class));
								finish();
						}
					});
				}
				else{
					startActivity(new Intent(MainActivity.this, LoginActivity.class)); 
					finish();
				}
			}
			@Override
			public void onFailure(int arg0, String arg1) {				
				startActivity(new Intent(MainActivity.this, LoginActivity.class)); 
				finish();
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
