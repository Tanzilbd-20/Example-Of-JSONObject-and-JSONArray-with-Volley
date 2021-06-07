package com.example.parsedata;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView id_tv, name_tv, email_tv;

    //Creating Queue request .
    RequestQueue queue;

    //Setting the url link
    String googleUrl = "https://www.google.com";
    String data = "https://jsonplaceholder.typicode.com/posts";
    String comments = "https://jsonplaceholder.typicode.com/comments";
    String todo = "https://jsonplaceholder.typicode.com/todos";
    String playWithObject = "https://jsonplaceholder.typicode.com/comments/1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_tv = findViewById(R.id.id_tv);
        name_tv = findViewById(R.id.name_tv);
        email_tv = findViewById(R.id.email_tv);

        //Instantiating Queue
        queue = Volley.newRequestQueue(this);

        getObjects();
        getJsonArrayTodo();
        getJsonArrayComments();
        getJsonArrayPost();
        getString();


    }

    private void getObjects() {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, playWithObject, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Json Object", "Email : "+response.getString("email"));
                    id_tv.setText(String.format("ID : %s", response.getString("id")));
                    name_tv.setText(String.format("Name : %s", response.getString("name")));
                    email_tv.setText(String.format("Email : %s", response.getString("email")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "test: Failed");
            }
        });


        queue.add(objectRequest);
    }

    private void getString() {
        //Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, googleUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //here in logcat will show first 500 characters of the response string.
                Log.d("Main", "onResponse: " + response.substring(0, 10));
                String result = response.substring(0,10);
                id_tv.setText(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Main", "onResponse: Failed to get info !");
            }
        });
        //Add the request to the RequestQueue..
        queue.add(stringRequest);
    }

    private void getJsonArrayTodo() {
        JsonArrayRequest todoArray = new JsonArrayRequest(Request.Method.GET, todo, null, response -> {

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);

                    Log.d("TODO ", "User ID :"+jsonObject.getInt("userId"));
                    Log.d("TODO ", "ID :"+jsonObject.getInt("id"));
                    Log.d("TODO ", "Title :"+jsonObject.getString("title"));
                    Log.d("TODO ", "Completed :"+jsonObject.getBoolean("completed"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ToDo ", "onCreate: Failed");
            }
        });

        queue.add(todoArray);
    }

    private void getJsonArrayComments() {
        JsonArrayRequest postArray = new JsonArrayRequest(Request.Method.GET, comments, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d("JSON Post ", "Post ID : "+jsonObject.getInt("postId"));
                        Log.d("JSON Post ", "ID : "+jsonObject.getInt("id"));
                        Log.d("JSON Post ", "Name : "+jsonObject.getString("name"));
                        Log.d("JSON Post ", "Email : "+jsonObject.getString("email"));
                        Log.d("JSON Post ", "Body : "+jsonObject.getString("body"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(postArray);
    }

    private void getJsonArrayPost() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, data, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d("Json ", "User ID : "+jsonObject.getInt("userId"));
                        Log.d("Json ", "ID : "+jsonObject.getInt("id"));
                        Log.d("Json ", "Title : "+jsonObject.getString("title"));
                        Log.d("Json ", "Body : "+jsonObject.getString("body"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Log.d("Json Data ", "onResponse: "+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Json Data ", "onResponse: Failed");
            }
        });

        queue.add(jsonArrayRequest);
    }


}