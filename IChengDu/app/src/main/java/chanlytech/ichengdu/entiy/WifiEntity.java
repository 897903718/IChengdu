package chanlytech.ichengdu.entiy;

/**
 * Created by Lyy on 2015/12/2.
 */
public class WifiEntity {
    private String ssid;
    private String pass;
    private String acc;
    private String typename;
    private int isfree;
    private int isupdate;
    private int level;//信号强度

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getIsfree() {
        return isfree;
    }

    public void setIsfree(int isfree) {
        this.isfree = isfree;
    }

    public int getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(int isupdate) {
        this.isupdate = isupdate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
