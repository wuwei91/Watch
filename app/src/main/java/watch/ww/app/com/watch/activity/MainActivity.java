package watch.ww.app.com.watch.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import watch.ww.app.com.watch.R;
import watch.ww.app.com.watch.fragment.EmergencyFragment;
import watch.ww.app.com.watch.fragment.IndexFragment;
import watch.ww.app.com.watch.fragment.PersionFragment;
import watch.ww.app.com.watch.fragment.SetupFragment;
import watch.ww.app.com.watch.fragment.WatchFragment;
import watch.ww.app.com.watch.util.Constants;
import watch.ww.app.com.watch.util.GPSUtil;

public class MainActivity extends FragmentActivity implements OnClickListener{

	//定义首页个Fragment的对象
	private IndexFragment fg1;						//首页
	private WatchFragment fg2;						//巡更
	private EmergencyFragment fg3;					//报警
	private SetupFragment fg4;						//设置
	private PersionFragment fg5;					//个人
	//定义底部导航栏的五个布局
	private LinearLayout index_layout;				
	private LinearLayout watch_layout;				
	private LinearLayout emergency_layout;			
	private LinearLayout setup_layout;				
	private LinearLayout persion_layout;			
	//定义底部导航栏中的ImageView
	private ImageView index;
	private ImageView watch;
	private ImageView emergency;
	private ImageView setup;
	private ImageView persion;
	//定义FragmentManager对象
	private FragmentManager fManager;
	private long firstBack;//记录第一次点击back键时间
	//导航栏标题
	private TextView title_left; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fManager = getSupportFragmentManager();
		initViews();
		setChioceItem(0);//默认选中第一个fragment
	}
	
	
	//完成组件的初始化
	public void initViews()
	{
		index = (ImageView) findViewById(R.id.index);
		watch = (ImageView) findViewById(R.id.watch);
		emergency = (ImageView) findViewById(R.id.emergency);
		setup = (ImageView) findViewById(R.id.setup);
		persion = (ImageView) findViewById(R.id.persion);
		
		title_left = (TextView) findViewById(R.id.title_left);
		
		index_layout = (LinearLayout) findViewById(R.id.index_layout);
		watch_layout = (LinearLayout) findViewById(R.id.watch_layout);
		emergency_layout = (LinearLayout) findViewById(R.id.emergency_layout);
		setup_layout = (LinearLayout) findViewById(R.id.setup_layout);
		persion_layout = (LinearLayout) findViewById(R.id.persion_layout);
		
		index_layout.setOnClickListener(this);
		watch_layout.setOnClickListener(this); 
		emergency_layout.setOnClickListener(this);
		setup_layout.setOnClickListener(this);
		persion_layout.setOnClickListener(this);
	}
	
	//重写onClick事件
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.index_layout:
			setChioceItem(0);
			break;
	    case R.id.watch_layout:
	    	setChioceItem(1);
	    	break;
	    case R.id.emergency_layout:
	    	setChioceItem(2);
	    	break;
	    case R.id.setup_layout:
	    	setChioceItem(3);
	    	break;
	    case R.id.persion_layout:
	    	setChioceItem(4);
	    	break;
	    default:
			break;
		}
		
	}
	
	
	//定义一个选中一个item后的处理
	public void setChioceItem(int id)
	{
		//重置选项+隐藏所有Fragment
		FragmentTransaction transaction = fManager.beginTransaction();  
		clearChioce();
		hideFragments(transaction);
		switch (id) {
		case 0:
			title_left.setText(R.string.index_title);
			index.setImageResource(R.drawable.index_c);
            if ((Constants.indexId != -1&&"endTime".equals(Constants.butName))||fg1 == null||Constants.listSize != 3) {
                // 如果fg1为空，则创建一个并添加到界面上
                fg1 = new IndexFragment();  
                transaction.add(R.id.content, fg1);  
            } else {  
                // 如果MessageFragment不为空，则直接将它显示出来  
                transaction.show(fg1);  
            }  
            break;  

		case 1:
			title_left.setText(R.string.watch_title);
			watch.setImageResource(R.drawable.watch_c);  
            if (fg2 == null||"startTime".equals(Constants.butName)) {
                Constants.butName = "";
                fg2 = new WatchFragment();
                transaction.add(R.id.content, fg2);  
            } else {  
                transaction.show(fg2);  
            }  
            break;      
		
		 case 2:
			 title_left.setText(R.string.emergency_title);
			 emergency.setImageResource(R.drawable.emergency_c);  
            if (fg3 == null) {  
                fg3 = new EmergencyFragment();  
                transaction.add(R.id.content, fg3);  
            } else {  
                transaction.show(fg3);  
            }  
            break;                 
		 case 3:
			 title_left.setText(R.string.setup_title);
			 setup.setImageResource(R.drawable.setup_c);  
			 if (fg4 == null) {  
				 fg4 = new SetupFragment();  
				 transaction.add(R.id.content, fg4);  
			 } else {  
				 transaction.show(fg4);  
			 }  
			 break;                 
		 case 4:
			 title_left.setText(R.string.persion_title);
			 persion.setImageResource(R.drawable.persion_c);  
			 if (fg5 == null) {  
				 fg5 = new PersionFragment();  
				 transaction.add(R.id.content, fg5);  
			 } else {  
				 transaction.show(fg5);  
			 }  
			 break;                 
		}
		transaction.commit();
	}
	
	//隐藏所有的Fragment,避免fragment混乱
	private void hideFragments(FragmentTransaction transaction) {  
        if (fg1 != null) {  
            transaction.hide(fg1);  
        }  
        if (fg2 != null) {  
            transaction.hide(fg2);  
        }  
        if (fg3 != null) {  
            transaction.hide(fg3);  
        }  
        if (fg4 != null) {  
        	transaction.hide(fg4);  
        }  
        if (fg5 != null) {  
        	transaction.hide(fg5);  
        }  
    }  
		
	
	//定义一个重置所有选项的方法
	public void clearChioce()
	{
		index.setImageResource(R.drawable.index_n);
		watch.setImageResource(R.drawable.watch_n);
		emergency.setImageResource(R.drawable.emergency_n);
		setup.setImageResource(R.drawable.setup_n);
		persion.setImageResource(R.drawable.persion_n);
	}
	
	/**
	 * 连续按两次返回键就退出
	 */
	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - firstBack < 2000) {
            GPSUtil.stopGPS();
            finish();
		} else {
			firstBack = System.currentTimeMillis();
			Toast.makeText(this, "再次点击退出应用！", Toast.LENGTH_SHORT).show();
		}
	}
}
