package watch.ww.app.com.watch.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import net.tsz.afinal.FinalActivity;

import watch.ww.app.com.watch.R;
import watch.ww.app.com.watch.util.Constants;
import watch.ww.app.com.watch.util.GPSUtil;

public class LaunchActivity extends FinalActivity {
	
	private ImageView launch_img;//启动页面图片
    protected LocationManager locationManager;
    protected LocationListener locationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
        getScreen();
        initView();//界面初始化
        init();//初始化数据
	}
	/*
	 * 界面初始化
	 */
	private void initView(){
		launch_img = (ImageView) findViewById(R.id.launch_img);
		launch_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
	}
	/*
	 * 数据初始化
	 */
	private void init(){
        /**
         * 开启获取位置
         */
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        GPSUtil.initGPS(locationManager, null);

		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		Constants.userId = sp.getInt("userId", 0);
        Constants.userName = sp.getString("username","ww");
		Intent intent = new Intent(LaunchActivity.this,LoginActivity.class);
		startActivity(intent);
		finish();
	}

    private void getScreen(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        Constants.screenWidth = width;
        Constants.screenHeight = height;
    }
}
