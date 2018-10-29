package jgs.com.jogilsang.begginer;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

        Button button;
        TextView text;

        public final String url = "https://monero.miningpoolhub.com/index.php?page=api&action=getuserworkers&api_key=ec5c0ac01db32ec9c0598d5141b98363e45375e25b845010cc9d0e24939c8f40";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setListener();






    }

    private void initView() {

        button = (Button)findViewById(R.id.button);
        text = (TextView)findViewById(R.id.text);

    }

    private void setListener(){


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 뭔가 잘안됨
                //volleyExample();

                // 잘됨. String으로 반환
                //volleyExample_2();

                //잘됨. String으로 반환
                //volleyExample_3();

                //잘됨. JSONObject로 반환
                volleyExample_4();

            }
        });

    }


    public void volleyExample(){


//        String url = <서버 API> + msg (Json등의 message)


        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int result_code = response.getInt("return_code");
                    if (result_code == 0) {
                        Toast.makeText(MainActivity.this, "You are sign in successfully.", Toast.LENGTH_LONG).show();
                        // 성공시에 처리해야 하는 부분을 추가를 한다.
                        text.setText(response.toString());

                    } else {
                        Toast.makeText(MainActivity.this, "Sorry, Email or Password is incorrect.", Toast.LENGTH_LONG).show();
                        // 실패시에 처리해야 하는 구문 추가
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
        //[출처] [Android] Volley를 사용한 서버와 통신 방법|작성자 연금술사

        // 그냥 url인대...?
        text.setText(jsonObjectRequest.toString());

    }

    //Use newRequestQueue
    public void volleyExample_2(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        text.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    //Use newRequestQueue
    public void volleyExample_3(){

        RequestQueue mRequestQueue;

// Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

// Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        text.setText("Response is: "+ response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        text.setText("Response is: "+ "failed");
                    }
                });

// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);

    }

    public void volleyExample_4(){

        // option 2
        //RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        text.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        // option 2
        //queue.add(jsonObjectRequest);

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }




}



