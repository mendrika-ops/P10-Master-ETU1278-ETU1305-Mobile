package com.example.tongasoa.utils.slide;

public class SlideItem {
//    If you want store url you can use string variables
    int image;
    String url;

    public SlideItem(int image, String url) {
        this.image = image;
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}
