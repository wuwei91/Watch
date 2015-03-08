package watch.ww.app.com.watch.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import net.tsz.afinal.FinalBitmap;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import watch.ww.app.com.watch.R;

public class EmergencyFragment extends Fragment implements View.OnClickListener{
    private ImageView rescue;
    private ImageView hydropower;
    private ImageView manage;
    private ImageView photoImg;
    private ImageView photoBut;
    private Spinner grade;//报警级别
    private Spinner type;//报警类型
    private String []tells = {"18140549110","18140549110","18140549110"};
    private String tell = "";
    private String []grades = {"Ⅳ级（一般）","Ⅲ级（较重）","Ⅱ级（严重）","Ⅰ级（特别严重，立刻处理）"};
    private String []types = {"水电","火灾","人员伤亡","其他"};
    private File mPhotoFile;
    private String mPhotoPath;
    private FinalBitmap finalBitmap;
    public final static int CAMERA_RESULT = 8888;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_emergency, container,false);
        finalBitmap = FinalBitmap.create(getActivity().getApplicationContext());
		initView(view);
        init();
        return view;
	}

    private void initView(View v){
        photoImg = (ImageView) v.findViewById(R.id.photoImg);
        photoBut = (ImageView) v.findViewById(R.id.photoBut);
        grade = (Spinner) v.findViewById(R.id.grade);
        type = (Spinner) v.findViewById(R.id.type);
        manage = (ImageView) v.findViewById(R.id.manage);
        hydropower = (ImageView) v.findViewById(R.id.hydropower);
        rescue = (ImageView) v.findViewById(R.id.rescue);

        manage.setOnClickListener(this);
        hydropower.setOnClickListener(this);
        rescue.setOnClickListener(this);
    }

    private void init(){
        grade.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, grades));
        type.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, types));
        photoBut.setOnClickListener(new ButtonOnClickListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hydropower:
                tell = tells[0];
                break;
            case R.id.manage:
                tell = tells[1];
                break;
            case R.id.rescue:
                tell = tells[2];
                break;
        }
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tell));
        startActivity(intent);
    }

    private class ButtonOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            try {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                mPhotoPath = "mnt/sdcard/DCIM/Camera/" + getPhotoFileName();
                mPhotoFile = new File(mPhotoPath);
                if (!mPhotoFile.exists()) {
                    mPhotoFile.createNewFile();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(mPhotoFile));
                startActivityForResult(intent, CAMERA_RESULT);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 用时间戳生成照片名称
     *
     * @return
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RESULT) {
            Toast.makeText(getActivity(),mPhotoPath,Toast.LENGTH_SHORT).show();
            finalBitmap.display(photoImg,mPhotoPath);
        }
    }
}
