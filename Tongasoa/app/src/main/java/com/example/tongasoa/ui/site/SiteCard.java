package com.example.tongasoa.ui.site;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tongasoa.R;
import com.example.tongasoa.modele.Site;
public class SiteCard extends Fragment {
    private Site site;
    private ImageView imageSite;
    private TextView nameSite;

    public SiteCard() {
        // Required empty public constructor
    }

    public static SiteCard newInstance(Site site) {
        SiteCard fragment = new SiteCard();
        fragment.site = site;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("CardView", "Site : "+ site.getName());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_site_card, container, false);
        imageSite = view.findViewById(R.id.imageSite);
        nameSite = view.findViewById(R.id.nameSite);
        nameSite.setText(site.getName());
        return view;
    }
}