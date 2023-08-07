package com.example.tongasoa.ui.site;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tongasoa.R;
import com.example.tongasoa.controle.SiteControleur;
import com.example.tongasoa.modele.Category;
import com.example.tongasoa.modele.Commentaire;
import com.example.tongasoa.modele.Favoris;
import com.example.tongasoa.modele.Media;
import com.example.tongasoa.modele.Region;
import com.example.tongasoa.modele.Site;
import com.example.tongasoa.modele.User;
import com.example.tongasoa.utils.Constante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

public class SitesFavorite extends Fragment {
    private SiteControleur siteControleur;
    private RecyclerView recycler;
    private FragmentManager fragmentManager;

    private RequestQueue queue;
    ProgressBar loadingSpinner;
    private User user;
    ArrayList<Site> sites;
    public SitesFavorite(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        loadingSpinner = view.findViewById(R.id.progressBar);
        loadingSpinner.setVisibility(View.VISIBLE);
        recyclerViewInitialize(view, null);
        user = new User();
        user.setId("2");
        Context ctx = view.getContext();
        final String[] lasText = {""};
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void recyclerViewInitialize(View view, String search) {
        if(this.siteControleur == null) {
            this.siteControleur = SiteControleur.getInstance(this.getContext());
        }
        queue = this.siteControleur.getRequestQueue();
        getListeSite(this.getContext(), view, search);
    }

    /**
     * Get Liste Site depuis le back
     * @param ctx
     * @return ArrayList<Site>
     */
    private void getListeSite(Context ctx, View view, String search) {
        try {
            String api = Constante.BASE_URL+"site/myfavorite";
            sites = new ArrayList<Site>();
            JSONObject requestBody = new JSONObject();
            requestBody.put("idUser", 2);
            recycler = view.findViewById(R.id.recyclerview);
            GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
            recycler.setLayoutManager(layoutManager);
            recycler.setHasFixedSize(false);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, api, requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonresponse) {
                            try {
                                JSONArray array = jsonresponse.getJSONArray("rows");
                                for(int i =0 ; i< array.length(); i++){
                                    JSONObject simpleObject = array.getJSONObject(i);
                                    Site site = new Site(
                                            simpleObject.getString("id"),
                                            simpleObject.getString("idRegion"),
                                            simpleObject.getString("idCategory"),
                                            simpleObject.getString("name"),
                                            simpleObject.getString("description"),
                                            simpleObject.getString("link"),
                                            (float) simpleObject.getDouble("rating"),
                                            Date.valueOf(simpleObject.getString("createdDate"))
                                    );
                                    JSONObject jsonRegion = simpleObject.getJSONObject("Region");
                                    JSONObject jsonCategory = simpleObject.getJSONObject("Category");
                                    JSONArray jsonMedias = simpleObject.getJSONArray("Media");
                                    JSONArray jsonCommentaire = simpleObject.getJSONArray("Commentaire");
                                    JSONArray jsonFavoris = simpleObject.getJSONArray("Favoris");
                                    site.setMedias(new ArrayList<Media>());
                                    for(int ii =0 ; ii< jsonMedias.length(); ii++){
                                        JSONObject jsonMedia = jsonMedias.getJSONObject(ii);
                                        Media media = new Media(jsonMedia.getInt("id"), jsonMedia.getString("link"));
                                        site.getMedias().add(media);
                                    }

                                    site.setCommentaires(new ArrayList<Commentaire>());
                                    for(int iii =0 ; iii< jsonCommentaire.length(); iii++){
                                        JSONObject jsonCom = jsonCommentaire.getJSONObject(iii);
                                        Commentaire coms = new Commentaire(String.valueOf(jsonCom.getInt("id")), String.valueOf(jsonCom.getInt("idUser")), String.valueOf(jsonCom.getInt("idSite")), jsonCom.getString("commentaire"), jsonCom.getString("note"), jsonCom.getString("createDate"));
                                        site.getCommentaires().add(coms);
                                    }

                                    site.setFavoris(new ArrayList<Favoris>());
                                    for(int iii =0 ; iii< jsonFavoris.length(); iii++){
                                        JSONObject jsonCom = jsonFavoris.getJSONObject(iii);
                                        Favoris favo = new Favoris(String.valueOf(jsonCom.getInt("id")), String.valueOf(jsonCom.getInt("idUser")), String.valueOf(jsonCom.getInt("idSite")), jsonCom.getString("description"), jsonCom.getInt("etat"), jsonCom.getString("createDate"));
                                        site.getFavoris().add(favo);
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
                                loadingSpinner.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("api", "onErrorResponse: "+ error.getLocalizedMessage());
                }
            });

            queue.add(jsonObjectRequest);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }
}
