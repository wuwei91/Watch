package watch.ww.app.com.watch.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import watch.ww.app.com.watch.R;
import watch.ww.app.com.watch.util.Constants;

public class LoginActivity extends Activity implements OnClickListener{
	private EditText userNameText;			//账号
	private EditText passwordText;			//密码
	private ImageView login;				//登录按钮
	private ImageView cancle;				//取消按钮
	private Intent intent;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sp = getSharedPreferences("user", MODE_PRIVATE);
		initView();//初始化
		isLogin();
	}
	/*
	 * 初始化UI控件
	 */
	private void initView(){
		userNameText = (EditText) findViewById(R.id.loginUser);
		passwordText = (EditText) findViewById(R.id.loginPassword);
		login = (ImageView) findViewById(R.id.login);
		cancle = (ImageView) findViewById(R.id.cancle);
		
		login.setImageDrawable(getResources().getDrawable(R.drawable.login));
		cancle.setImageDrawable(getResources().getDrawable(R.drawable.cancel));
		
		login.setOnClickListener(this);
		cancle.setOnClickListener(this);
	}
	
	/*
	 * 验证登陆输入框
	 */
	private void checkUser(){
		String nameStr = userNameText.getText().toString();
		String passwordStr = passwordText.getText().toString();
		if("".equals(nameStr)){
			userNameText.setError("请输入用户名！");
		}else if("".equals(passwordStr)){
			passwordText.setError("请输入密码！");
		}else if(("admin".equals(nameStr)&&"123456".equals(passwordStr))){
			login(nameStr,passwordStr,1);
		}else if(("dj".equals(nameStr)&&"123456".equals(passwordStr))){
			login(nameStr,passwordStr,2);
		}else{
			Constants.showAlertDialog("用户名或密码不正确", LoginActivity.this);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			checkUser();
			break;
		case R.id.cancle:
			finish();
			break;
		default:
			break;
		}
	}
	/*
	 * 登陆
	 */
	private void login(String username,String password,int userId){
		//存入数据
		Editor editor = sp.edit();
		editor.putInt("userId", userId);
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
		Constants.userId = userId;
        Constants.userName = username;
		isLogin();//判断用户是否登陆
	}
	/*
	 * 判断是否登陆
	 */
	private void isLogin(){
		if(Constants.userId != 0){
			intent = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
//	/*
//	 * 判断用户名、密码是否正确
//	 */
//	private boolean checkUser(String username,String password){
//		if(("admin".equals(username)&&"123456".equals(password))||("dj".equals(username)&&"123456".equals(password))){
//			return true;
//		}
//		return false;
//	}     
}
