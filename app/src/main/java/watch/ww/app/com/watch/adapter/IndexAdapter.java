package watch.ww.app.com.watch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import watch.ww.app.com.watch.R;
import watch.ww.app.com.watch.bean.IndexBean;
import watch.ww.app.com.watch.util.Constants;

/**
 * Created by ww on 2015/2/20.
 */
public class IndexAdapter extends BaseAdapter{
    private List<IndexBean> list;
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private LinearLayout watchLayout;
    private LinearLayout emergencyLayout;
    public IndexAdapter(Context context,int resource,List<IndexBean> list,LinearLayout watchLayout,LinearLayout emergencyLayout){
        this.list = list;
        this.resource = resource;
        this.context = context;
        this.watchLayout = watchLayout;
        this.emergencyLayout = emergencyLayout;
        inflater = LayoutInflater.from(context);
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

            holder.indexItemNum = (TextView) convertView.findViewById(R.id.indexItemNum);
            holder.startTime = (TextView) convertView.findViewById(R.id.startTime);
            holder.endTime = (TextView) convertView.findViewById(R.id.endTime);
            holder.indexItemImg = (ImageView) convertView.findViewById(R.id.indexItemImg);
            holder.indexItemBut = (ImageView) convertView.findViewById(R.id.indexItemBut);

            convertView.setTag(holder);
        }else{
            holder = (Holder)convertView.getTag();
        }
        IndexBean bean = list.get(position);

        holder.indexItemBut.setOnClickListener(new ItemBut(position));
        holder.indexItemImg.setOnClickListener(new ItemImg(position));
        holder.indexItemNum.setText(bean.getNum());
        holder.startTime.setText(bean.getStartTime());
        holder.endTime.setText(bean.getEndTime());
        if("".equals(bean.getStartTime())) {
            holder.indexItemImg.setVisibility(View.INVISIBLE);
            holder.indexItemBut.setVisibility(View.VISIBLE);
            holder.indexItemBut.setImageDrawable(context.getResources().getDrawable(R.drawable.index_start));
        }else if ("".equals(bean.getEndTime())) {
            holder.indexItemImg.setVisibility(View.INVISIBLE);
            holder.indexItemBut.setVisibility(View.VISIBLE);
            holder.indexItemBut.setImageDrawable(context.getResources().getDrawable(R.drawable.index_continue));
        }else if (bean.isAlarm()){
            holder.indexItemImg.setVisibility(View.VISIBLE);
            holder.indexItemBut.setVisibility(View.INVISIBLE);
            holder.indexItemImg.setImageDrawable(context.getResources().getDrawable(R.drawable.index_alarm));
        }else{
            holder.indexItemBut.setVisibility(View.INVISIBLE);
            holder.indexItemImg.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
    public class ItemBut implements View.OnClickListener{
        private int position;
        public ItemBut(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            if("".equals(list.get(position).getStartTime())){
                list.get(position).setAlarm(false);
                list.get(position).setStartTime(formatter.format(new Date()));

            }
            notifyDataSetChanged();
            Constants.startTime = list.get(position).getStartTime();
            Constants.butName = "startTime";
            Constants.indexId = position;
            watchLayout.performClick();
        }
    }
    public class ItemImg implements View.OnClickListener{
        private int position;
        public ItemImg(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            emergencyLayout.performClick();
        }
    }
    public class Holder{
        public TextView indexItemNum;
        public TextView startTime;
        public TextView endTime;
        public ImageView indexItemImg;
        public ImageView indexItemBut;
    }
}