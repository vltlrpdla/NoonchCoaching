package project.jeonghoon.com.nooncoaching;

/**
 * Created by kku on 2016-03-18.
 */
public class FavorItem extends Item {
//"title TEXT, category TEXT, imageUrl TEXT, phone TEXT, address TEXT
    private int seq;
    private String title;
    private String category;
    private String imageUrl;
    private String phone;
    private String address;
    public double longitude;
    public double latitude;

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getSeq() {
        return seq;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
