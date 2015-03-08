package watch.ww.app.com.watch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import watch.ww.app.com.watch.R;
import watch.ww.app.com.watch.util.Constants;

public class SetupFragment extends Fragment {

    private Spinner times;
    private String []nums = {"1","2","3","4","5","6","7","8"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_setup, container,false);
        initView(view);
		return view;
	}

    public void initView(View v){
        times = (Spinner) v.findViewById(R.id.times);

        times.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,nums));
        times.setOnItemSelectedListener(new ItemSelect());
    }

    public final class ItemSelect implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Constants.listSize = position+1;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
