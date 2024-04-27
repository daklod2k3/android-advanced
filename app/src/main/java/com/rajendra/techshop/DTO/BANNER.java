package com.rajendra.techshop.DTO;

public class BANNER {
    int banner_id;
    String image_url;
    String open_url;

    public BANNER(int banner_id, String image_url, String open_url){
        this.banner_id = banner_id;
        this.image_url = image_url;
        this.open_url = open_url;

    }

    public int getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(int banner_id) {
        this.banner_id = banner_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getOpen_url() {
        return open_url;
    }

    public void setOpen_url(String open_url) {
        this.open_url = open_url;
    }




}
