package watch.ww.app.com.watch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import watch.ww.app.com.watch.R;
import watch.ww.app.com.watch.bean.WatchBean;

/**
 * Created by ww on 2015/2/20.
 */
public class WatchAdapter extends BaseAdapter{
    private List<WatchBean> list;
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    public List<Boolean> checkList = new ArrayList<Boolean>();
    public WatchAdapter(Context context, int resource, List<WatchBean> list) {

        this.list = list;
        this.resource = resource;
        this.context = context;
        inflater = LayoutInflater.from(context);
        initCheckBox();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(resource,null);
            holder = new Holder();

            holder.watchCheck = (CheckBox) convertView.findViewById(R.id.watchCheck);
            holder.watchNum = (TextView) convertView.findViewById(R.id.watchNum);
            holder.watchName = (TextView) convertView.findViewById(R.id.watchName);
            holder.watchInfo = (TextView) convertView.findViewById(R.id.watchInfo);

            convertView.setTag(holder);
        }else{
            holder = (Holder)convertView.getTag();
        }
        WatchBean bean = list.get(position);
        if (!"".equals(bean.getLocInfo())){
            checkList.set(position,false);
        }
        holder.watchCheck.setTag(bean.isCheck());
        holder.watchCheck.setOnClickListener(new ItemBut(position));
        holder.watchCheck.setChecked(checkList.get(position));
        holder.watchNum.setText(bean.getLocNum());
        holder.watchName.setText(bean.getLocName());
        holder.watchInfo.setText(bean.getLocInfo());

        return convertView;
    }

    /**
     * checkBox点击事件
     */
    public class ItemBut implements View.OnClickListener{
        private int position;
        public ItemBut(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            if("".equals(list.get(position).getLocInfo())){
                boolean flag = checkList.get(position);
                initCheckBox();
                checkList.set(position,!flag);
            }else{

            }

            notifyDataSetChanged();
        }
    }
    /*
     *初始化CheckBox
     */
    private void initCheckBox(){
        checkList.clear();
        for (int i = 0;i<list.size();i++){
            checkList.add(false);
        }
    }
    public class Holder{
        public CheckBox watchCheck;
        public TextView watchNum;
        public TextView watchName;
        public TextView watchInfo;
    }
}