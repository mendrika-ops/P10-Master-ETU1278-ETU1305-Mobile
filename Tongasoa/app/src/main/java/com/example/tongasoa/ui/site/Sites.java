package com.example.tongasoa.ui.site;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tongasoa.R;
import com.example.tongasoa.controle.SiteControleur;
import com.example.tongasoa.modele.Category;
import com.example.tongasoa.modele.Media;
import com.example.tongasoa.modele.Region;
import com.example.tongasoa.modele.Site;
import com.example.tongasoa.utils.Constante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

public class Sites extends Fragment {

    private SiteControleur siteControleur;
    private RecyclerView recycler;
    private FragmentManager fragmentManager;

    private RequestQueue queue;

    ArrayList<Site> sites;
    public Sites(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sites, container, false);
        recyclerViewInitialize(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void recyclerViewInitialize(View view) {
        if(this.siteControleur == null) {
            this.siteControleur = SiteControleur.getInstance(this.getContext());
        }
        queue = this.siteControleur.getRequestQueue();
        getListeSite(this.getContext(), view);
    }

    /**
     * Get Liste Site depuis le back
     * @param ctx
     * @return ArrayList<Site>
     */
    private void getListeSite(Context ctx, View view) {
        try {
            String api = Constante.BASE_URL+"site";
            sites = new ArrayList<Site>();
            recycler = view.findViewById(R.id.recyclerview);
            GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
            recycler.setLayoutManager(layoutManager);
            recycler.setHasFixedSize(false);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                for(int i =0 ; i< array.length(); i++){
                                    JSONObject simpleObject = array.getJSONObject(i);
                                    Site site = new Site(
                                            simpleObject.getString("id"),
                                            simpleObject.getString("idRegion"),
                                            simpleObject.getString("idCategory"),
                                            simpleObject.getString("name"),
                                            simpleObject.getString("description"),
                                            simpleObject.getString("link"),
                                            Date.valueOf(simpleObject.getString("createdDate"))
                                    );
                                    JSONObject jsonRegion = simpleObject.getJSONObject("Region");
                                    JSONObject jsonCategory = simpleObject.getJSONObject("Category");
                                    JSONArray jsonMedias = simpleObject.getJSONArray("Media");
                                    site.setMedias(new ArrayList<Media>());
                                    for(int ii =0 ; ii< jsonMedias.length(); ii++){
                                        JSONObject jsonMedia = jsonMedias.getJSONObject(ii);
                                        Media media = new Media(jsonMedia.getInt("id"), jsonMedia.getString("link"));
                                        site.getMedias().add(media);
                                    }
                                    Region region = new Region(jsonRegion.getString("id"), jsonRegion.getString("idProvince"), jsonRegion.getString("name"));
                                    Category category = new Category(jsonCategory.getString("id"), jsonCategory.getString("name"));
                                    site.setRegion(region);
                                    site.setCategory(category);
                                    sites.add(site);
                                }
                                //ListeAdapter listSite = new ListeAdapter(this.siteControleur.getSites(), this.getContext());
                                ListeAdapter listSite = new ListeAdapter(sites, ctx, fragmentManager);
                                recycler.setAdapter(listSite);
                                listSite.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("api", "onResponse: "+ response.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("api", "onErrorResponse: "+ error.getLocalizedMessage());
                }
            });

            queue.add(stringRequest);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }
}