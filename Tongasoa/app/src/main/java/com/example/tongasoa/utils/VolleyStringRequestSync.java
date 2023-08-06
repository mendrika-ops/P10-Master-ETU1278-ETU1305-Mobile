package com.example.tongasoa.utils;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.concurrent.CountDownLatch;

public class VolleyStringRequestSync extends Request<String> {

    private CountDownLatch latch;
    private String responseResult;

    public VolleyStringRequestSync(int method, String url, CountDownLatch latch ,Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.latch = latch;
    }

    @Override
    protected void deliverResponse(String response) {
        responseResult = response;
        latch.countDown();
    }

    @Override
    protected Response<String> parseNetworkResponse(com.android.volley.NetworkResponse response) {
        return Response.success(new String(response.data), getCacheEntry());
    }

    public String getResponseResult() {
        return responseResult;
    }
}