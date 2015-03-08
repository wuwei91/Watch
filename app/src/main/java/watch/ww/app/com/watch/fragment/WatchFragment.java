package watch.ww.app.com.watch.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zbar.lib.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import watch.ww.app.com.watch.R;
import watch.ww.app.com.watch.adapter.WatchAdapter;
import watch.ww.app.com.watch.bean.WatchBean;
import watch.ww.app.com.watch.util.Constants;
import watch.ww.app.com.watch.util.GPSUtil;

public class WatchFragment extends Fragment implements View.OnClickListener{

    private LinearLayout index_layout;
    private ListView listView;
    private ImageView watchGps;
    private ImageView watchCode;
    private ImageView watchOver;
    private ImageView watchAlarm;
    private ScrollView scrollView;
    private TextView startTime;
    private TextView endTime;
    private WatchAdapter adapter;
    private List<WatchBean> list = new ArrayList<WatchBean>();
    private LinearLayout emergency_layout;
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_watch, container,false);
        initView(view);
        init();
		return view;
	}

    private void initView(View v){
        watchGps = (ImageView) v.findViewById(R.id.gps);
        index_layout = (LinearLayout) rootView.findViewById(R.id.index_layout);
        emergency_layout = (LinearLayout) rootView.findViewById(R.id.emergency_layout);
        watchCode = (ImageView) v.findViewById(R.id.code);
        listView = (ListView) v.findViewById(R.id.watchListView);
        watchOver = (ImageView) v.findViewById(R.id.overWatch);
        watchAlarm = (ImageView) v.findViewById(R.id.alarm);
        startTime = (TextView) v.findViewById(R.id.watchStartTime);
        endTime = (TextView) v.findViewById(R.id.watchEndTime);
        scrollView = (ScrollView) v.findViewById(R.id.scrollView);
        watchGps.setOnClickListener(this);
        watchCode.setOnClickListener(this);
        watchOver.setOnClickListener(this);
        watchAlarm.setOnClickListener(this);
    }

    private void init(){
        for (int i = 0;i<6;i++){
            WatchBean bean = new WatchBean();
            bean.setId(i);
            bean.setLocNum("位置"+(i+1));
            bean.setLocName("正门");
            bean.setLocInfo("");
            list.add(bean);
        }
        startTime.setText(Constants.startTime);
        endTime.setText(Constants.endTime);

        adapter = new WatchAdapter(getActivity(),R.layout.watch_list_item,list);
        listView.setAdapter(adapter);
        scrollView.scrollTo(10, 10) ;
    }

    /**
     * 获取被选中项
     * @return选中序号
     */
    private int isCheck(){
        int i = 0;
        for (;i<adapter.checkList.size();i++){
            if(adapter.checkList.get(i)){
                return i;
            }else if(i == adapter.checkList.size()-1){
                return -1;
            }
        }
        return i;
    }

    /**
     *填充GPS信息
     */
    private void getGps(){
        if(isCheck() == -1){
            Constants.showAlertDialog("请选择位置",getActivity());
        }else{
            list.get(isCheck()).setLocInfo(GPSUtil.getCurLoc()[0]+","+GPSUtil.getCurLoc()[1]);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 &&resultCode == getActivity().RESULT_OK){
            list.get(isCheck()).setLocInfo(data.getStringExtra("code"));
            adapter.notifyDataSetChanged();
        }
    }

    private void isCode(){
        if(isCheck() == -1){
            Constants.showAlertDialog("请选择位置",getActivity());
        }else{
            codeWatch();
        }
    }
    /**
     * 二维码签到
     */
    private void codeWatch(){
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * 结束
     */
    private void overWatch(){
        String isOver = "";
        for(int i = 0;i<list.size();i++){
            if("".equals(list.get(i).getLocInfo())){
                isOver += list.get(i).getLocNum()+",";
            }
        }
        if ("".equals(isOver)){
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            Constants.butName = "endTime";
            Constants.endTime = formatter.format(new Date());
            endTime.setText(Constants.endTime);
            index_layout.performClick();
        }else{
            Constants.showAlertDialog(isOver.substring(0,isOver.length()-1)+"未签到",getActivity());
        }
    }
    private View rootView;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //获取activity根视图,rootView设为全局变量
        rootView=activity.getWindow().getDecorView();
    }
    @Override
    public void onClick(View v) {
        if("".equals(startTime.getText().toString())){
            index_layout.performClick();
        }else{
            switch (v.getId()){
                case R.id.gps:
                    getGps();
                    break;
                case R.id.code:
                    isCode();
                    break;
                case R.id.overWatch:
                    overWatch();
                    break;
                case R.id.alarm:
                    Constants.list.get(Constants.indexId).setAlarm(true);
                    emergency_layout.performClick();
                    break;
            }
        }

    }

}
