package project.jeonghoon.com.nooncoaching;

/**
 * Created by user on 2016-01-04.
 */
public class SingerItem {

    String title;
    String imageUrl;
    String phone;
    String fDate;

    public SingerItem(String title, String imageUrl,String phone,String fDate) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.fDate = fDate;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public String getfDate() {
        return fDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }
}
