package com.example.tongasoa.ui.site;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tongasoa.R;
import com.example.tongasoa.modele.Site;
public class SiteCard extends Fragment {
/*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/

    // TODO: Rename and change types of parameters
 /*   private String mParam1;
    private String mParam2;*/
    private Site site;
    private ImageView imageSite;
    private TextView nameSite;

    public SiteCard() {
        // Required empty public constructor
    }

    public static SiteCard newInstance(Site site) {
        SiteCard fragment = new SiteCard();
      /*  Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        fragment.site = site;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
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