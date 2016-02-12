package project.jeonghoon.com.nooncoaching;

import java.io.Serializable;

/**
 * Created by han on 2015-11-24.
 */
public class Item implements Serializable {
    public String title;
    public String imageUrl;
    public String address;
    public String newAddress;
    public String zipcode;
    public String phone;
    public double longitude;
    public double latitude;
    public double distance;
    public String category;
    public String id;
    public String placeUrl;
    public String direction;
    public String addressBCode;

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhone() {
        return phone;
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
}