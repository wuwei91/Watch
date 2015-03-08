package watch.ww.app.com.watch.bean;

/**
 * Created by ww on 2015/2/22.
 */
public class WatchBean {
    private int id;
    private String locNum;
    private String locName;
    private String locInfo;
    private boolean isCheck;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocNum() {
        return locNum;
    }

    public void setLocNum(String locNum) {
        this.locNum = locNum;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocInfo() {
        return locInfo;
    }

    public void setLocInfo(String locInfo) {
        this.locInfo = locInfo;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
