package watch.ww.app.com.watch.util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import watch.ww.app.com.watch.bean.IndexBean;

public class Constants {

	public static int userId = 0; // 用来存储登陆后的用户ID
	public static String userName = ""; // 用来存储登陆后的用户名

    public static int screenWidth = 0;//屏幕宽度
    public static int screenHeight = 0;//屏幕高度
    public static String startTime = "";//开始时间
    public static String endTime = "";//结束时间
    public static String butName = "";//点击按钮名
    public static int indexId = -1;//首页条目ID
    public static List<IndexBean> list = new ArrayList<IndexBean>();//首页list
    public static int listSize = 3;
    /*
	 * 错误提示对话框
	 */
	public static void showAlertDialog(String message, Context context) {
		new AlertDialog.Builder(context).setMessage(message)
				.setPositiveButton("OK", null).create().show();
	}

}
