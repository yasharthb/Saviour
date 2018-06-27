package com.saviour.yasharth.saviour;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.widget.Toast;

public class GetNearbyPlaces extends VolleyError {

    GoogleMap mMap;
    String url;
    InputStream is;
    InputStream placesis;
    StringBuilder stringBuilder;
    String data="";
    RequestQueue mQueue;
    String ContactNumber;
    Context context;
    LatLng latLng;
    public GetNearbyPlaces(Context context){
        this.context=context;
    }


    protected String doInBackground(Object... params) {
                mMap = (GoogleMap)params[0];
        url=(String)params[1];

        mQueue= Volley.newRequestQueue(context);


        System.out.println(url);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject= jsonArray.getJSONObject(i);

                                JSONObject locationobject= jsonObject.getJSONObject("geometry").getJSONObject("location");
                                String latitude = locationobject.getString("lat");
                                String longitude = locationobject.getString("lng");
                                String name =jsonObject.getString("name");
                                LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));


                                String placeid=jsonArray.getJSONObject(i).getString("place_id");
                                String placename=jsonArray.getJSONObject(i).getString("name");
                                System.out.println(placename+placeid);

                                ContactDetails(placeid);
                                onPostExecute(latLng,name);



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        mQueue.add(request);

        return data;
    }


    protected void onPostExecute(LatLng latLng,String name)
    {


                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(latLng);
                mMap.addMarker(markerOptions);


    }

    public void ContactDetails(String placeid){

        String placedata="";
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        stringBuilder.append("placeid="+placeid);
        stringBuilder.append("&key="+"AIzaSyD8yqqSr3cE0Dl7UZ7qL0mulCOVrpHASBA");

        String placeurl = stringBuilder.toString();


        ///////////////////////////////////////////////////////////////////////
        System.out.println(placeurl);
        System.out.println("CONTACT DETAILS FUCNTION CALLED");
        ////////////////////////////////////////////////////////////////////

        //Try Catch Block of sending HTTP request to server to get Places Details
        mQueue= Volley.newRequestQueue(context);


       // System.out.println(placeurl);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, placeurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonArray = response.getJSONObject("result");



                                ContactNumber=jsonArray.getString("international_phone_number");
                                System.out.println(ContactNumber);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        mQueue.add(request);


    }
}
