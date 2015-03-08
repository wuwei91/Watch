package watch.ww.app.com.watch.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextClock;

import java.util.ArrayList;
import java.util.List;

import watch.ww.app.com.watch.R;
import watch.ww.app.com.watch.adapter.IndexAdapter;
import watch.ww.app.com.watch.bean.IndexBean;
import watch.ww.app.com.watch.util.Constants;

public class IndexFragment extends Fragment {

    private ListView listView;
    private ImageView weatherImg;
    private RelativeLayout weather;
    private IndexAdapter adapter;
    private LinearLayout watch_layout;
    private LinearLayout emergency_layout;
    private List<IndexBean> list = new ArrayList<IndexBean>();
    private TextClock textClockYear;
    private TextClock textClockTime;


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_index, container, false);
        initView(view);
        init();
        return view;
    }

    private void initView(View v){
        weather = (RelativeLayout) v.findViewById(R.id.weather);
        listView = (ListView) v.findViewById(R.id.indexListView);
        weatherImg = (ImageView) v.findViewById(R.id.weatherImg);
        watch_layout = (LinearLayout) rootView.findViewById(R.id.watch_layout);
        emergency_layout = (LinearLayout) rootView.findViewById(R.id.emergency_layout);

        textClockYear = (TextClock)v.findViewById(R.id.textClockYear);
        // 设置12时制显示格式
        textClockYear.setFormat12Hour("yyyy.MM.dd EEEE");

        textClockTime = (TextClock)v.findViewById(R.id.textClockTime);
        // 设置12时制显示格式
        textClockTime.setFormat12Hour("h:mm AA");

        listView.setOnItemClickListener(new ItemOnClick());

    }

    private void init(){
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) weather.getLayoutParams();
//        lp.width = Constants.screenWidth;
//        lp.height = Constants.screenHeight;
//        weather.setLayoutParams(lp);

        if(Constants.list.isEmpty()||Constants.listSize !=3){
            Constants.list.clear();
            for(int i = 0;i<Constants.listSize;i++){
                IndexBean bean = new IndexBean();
                bean.setId(i);
                bean.setNum(i+1+"");
                bean.setAlarm(true);
                bean.setStartTime("");
                bean.setEndTime("");
                bean.setState("");
                Constants.list.add(bean);
            }
        }
        if(Constants.indexId != -1){
            Constants.list.get(Constants.indexId).setEndTime(Constants.endTime);
            Constants.indexId = -1;
            Constants.endTime = "";
        }

        adapter = new IndexAdapter(IndexFragment.this.getActivity(),R.layout.index_list_item,Constants.list,watch_layout,emergency_layout);
        listView.setAdapter(adapter);
    }

    /**
     * 获取根视图上的控件
     */
    private View rootView;
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //获取activity根视图,rootView设为全局变量
        rootView=activity.getWindow().getDecorView();
    }
    public class ItemOnClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }

}
