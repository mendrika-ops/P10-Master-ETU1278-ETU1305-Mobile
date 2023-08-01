package com.example.tongasoa.controle;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tongasoa.modele.Category;
import com.example.tongasoa.modele.Region;
import com.example.tongasoa.modele.Site;
import com.example.tongasoa.utils.Constante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

public final class SiteControleur {
    private static SiteControleur instance = null;

    private ArrayList<Site> sites;
    private SiteControleur() {
        super();
    }

    public ArrayList<Site> getSites() {
        return this.instance.sites;
    }

    /**
     * Création Instance
     * @return instance
     */
    public static final SiteControleur getInstance(Context ctx) {
        if(SiteControleur.instance == null) {
            SiteControleur.instance = new SiteControleur();
            SiteControleur.instance.getListeSite(ctx);
        }
        return SiteControleur.instance;
    }

    /**
     * Get Liste Site depuis le back
     * @param ctx
     * @return ArrayList<Site>
     */
    private void getListeSite(Context ctx) {
        try {
            String api = Constante.BASE_URL+"site";
            RequestQueue queue = Volley.newRequestQueue(ctx);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                SiteControleur.instance.sites = new ArrayList<Site>();
                                JSONArray array = new JSONArray(response);
                                for(int i =0 ; i< array.length(); i++){
                                    JSONObject simpleObject = array.getJSONObject(i);
                                    Site u = new Site(
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
                                    Region region = new Region(jsonRegion.getString("id"), jsonRegion.getString("idProvince"), jsonRegion.getString("name"));
                                    Category category = new Category(jsonCategory.getString("id"), jsonCategory.getString("name"));
                                    u.setRegion(region);
                                    u.setCategory(category);
                                    SiteControleur.instance.sites.add(u);
                                }
                                for(int i = 0;i< SiteControleur.instance.sites.size();i++){
                                    Site s = SiteControleur.instance.sites.get(i);
                                    Log.e("list User", "user"+ s.getName());

                                }

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
