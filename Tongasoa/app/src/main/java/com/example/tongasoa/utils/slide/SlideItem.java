package com.example.tongasoa.utils.slide;

public class SlideItem {
//    If you want store url you can use string variables
    int image;
    String video;


    public SlideItem(int image, String video) {
        this.image = image;
        this.video = video;
    }

    public int getImage() {
        return image;
    }
    public String getVideo() {
        return video;
    }
}
