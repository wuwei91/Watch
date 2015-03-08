package watch.ww.app.com.watch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import watch.ww.app.com.watch.R;
import watch.ww.app.com.watch.util.Constants;

public class PersionFragment extends Fragment {
    private TextView name;
    private TextView age;
    private TextView gender;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_persion, container,false);
        initView(view);
		return view;
	}

    private void initView(View v){
        name = (TextView) v.findViewById(R.id.name);
        age = (TextView) v.findViewById(R.id.age);
        gender = (TextView) v.findViewById(R.id.gender);
        name.setText("姓名："+Constants.userName);
        gender.setText("性别：男");
        age.setText("年龄：24");
    }
}
