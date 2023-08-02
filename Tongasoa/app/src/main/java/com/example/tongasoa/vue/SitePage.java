package com.example.tongasoa.vue;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.tongasoa.R;
import com.example.tongasoa.utils.slide.SlideAdapter;
import com.example.tongasoa.utils.slide.SlideItem;

import java.util.ArrayList;
import java.util.List;

public class SitePage extends Fragment {

    private ViewPager2 viewPager2;
    private WebView webView;
    private Handler slideHandler = new Handler();
    private long timeWaiting = 3000;

    private Runnable sliderHandler = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            slideHandler.removeCallbacks(sliderHandler);
            slideHandler.postDelayed(sliderHandler, timeWaiting);
        }
    };

    public SitePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_site_page, container, false);
        this.viewPager2 = view.findViewById(R.id.viewPager2);
        List<SlideItem> slideItemList = new ArrayList<SlideItem>();
        slideItemList.add(new SlideItem(R.drawable.home_image3));
        slideItemList.add(new SlideItem(R.drawable.home_image4));
        slideItemList.add(new SlideItem(R.drawable.home_image1));
        slideItemList.add(new SlideItem(R.drawable.home_image2));
        viewPager2.setAdapter(new SlideAdapter(slideItemList, viewPager2));

        // iframe is compnent HMTL on the youtube share option
        webView = view.findViewById(R.id.webView);
        String iframe = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/V2KCAfHjySQ\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(iframe,"text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        slideHandler.removeCallbacks(sliderHandler);
        slideHandler.postDelayed(sliderHandler, timeWaiting);
        return view;
    }
}