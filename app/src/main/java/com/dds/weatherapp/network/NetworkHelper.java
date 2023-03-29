package com.dds.weatherapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class NetworkHelper {

    private final Context context;

    public NetworkHelper(Context context) {
        this.context = context;
    }

    public boolean isNetworkConnected() {

        boolean networkConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            networkConnected = info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE;
        }

        return networkConnected;
    }


    public JsonObjectRequest makeGetRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
    }


}
