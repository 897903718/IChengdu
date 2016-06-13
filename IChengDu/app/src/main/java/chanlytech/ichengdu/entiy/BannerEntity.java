package chanlytech.ichengdu.entiy;

/**
 * Created by Lyy on 2015/11/19.
 */
public class BannerEntity {
    private String id;
    private String name;
    private String imgurl;
    private String url;
    public BannerEntity(String id,String name,String imgurl,String url){
        this.id=id;
        this.name=name;
        this.imgurl=imgurl;
        this.url=url;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
