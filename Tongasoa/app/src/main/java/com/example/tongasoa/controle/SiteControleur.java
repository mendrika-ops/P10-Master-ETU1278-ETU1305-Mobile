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

    private RequestQueue requestQueue;

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    private SiteControleur(Context ctx) {
        super();
        requestQueue = Volley.newRequestQueue(ctx);
    }

    public ArrayList<Site> getSites() {
        return this.instance.sites;
    }

    /**
     * Création Instance
     * @return instance
     */
    public static final synchronized SiteControleur getInstance(Context ctx) {
        if(SiteControleur.instance == null) {
            SiteControleur.instance = new SiteControleur(ctx);
        }
        return SiteControleur.instance;
    }
}
