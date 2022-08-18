package com.example.hostelmaintenance.testt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostelmaintenance.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class VollyTest extends AppCompatActivity {

    String url = "https://api.twilio.com/2010-04-01/Accounts/ACf0340978828bc1b671943f92ec12c0b3/Messages.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volly_test);

        /*curl 'https://api.twilio.com/2010-04-01/Accounts/ACf0340978828bc1b671943f92ec12c0b3/Messages.json' -X POST \
        --data-urlencode 'To=+918439046950' \
        --data-urlencode 'From=+15802036610' \
        --data-urlencode 'MessagingServiceSid=MG09b2716158c1a5a75c8598a0da3d546e' \
        --data-urlencode 'Body=Test Message ' \
        -u ACf0340978828bc1b671943f92ec12c0b3:[AuthToken]*/
    }

    public void check1(View view){
      /* final JSONObject params= new JSONObject();
        params.put("To","+918439046950");
        params.put("From","+15802036610");
        params.put("MessagingServiceSid", "MG09b2716158c1a5a75c8598a0da3d546e");
        params.put("Body","Hello");
        params.put("ACf0340978828bc1b671943f92ec12c0b3", "3da36370a3f492431a5caaceb4d0a7c6");

       */



        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(">>>>response",response+"");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(">>>>err",error.getMessage()+"");

            }
        }) {

            protected Map<String, String> getParams() {

                    Map<String,String> params = new HashMap<String, String>();
                    params.put("To","+918439046950");
                    params.put("From","+15802036610");
                    params.put("MessagingServiceSid", "MG09b2716158c1a5a75c8598a0da3d546e");
                    params.put("Body","Hello Ishika tu pagal h");
                    return params;
                     }




                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Basic QUNmMDM0MDk3ODgyOGJjMWI2NzE5NDNmOTJlYzEyYzBiMzozZGEzNjM3MGEzZjQ5MjQzMWE1Y2FhY2ViNGQwYTdjNg==");

                    params.put("Content-Type", "application/x-www-form-urlencoded");

                    return params;


            }




        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

       /* Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "To=+918439046950&From=+15802036610&MessagingServiceSid=MG09b2716158c1a5a75c8598a0da3d546e&Body=Test Message");
                Request request = new Request.Builder()
                        .url("https://api.twilio.com/2010-04-01/Accounts/ACf0340978828bc1b671943f92ec12c0b3/Messages.json")
                        .method("POST", body)
                        .addHeader("Authorization", "Basic QUNmMDM0MDk3ODgyOGJjMWI2NzE5NDNmOTJlYzEyYzBiMzozZGEzNjM3MGEzZjQ5MjQzMWE1Y2FhY2ViNGQwYTdjNg==")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d(">>>>",response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });
                t.start();



    }*/
//       OkHttpClient client = new OkHttpClient().newBuilder()
//               .build();
//    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//    RequestBody body = RequestBody.create(mediaType, "To=whatsapp:+918439046950&From=whatsapp:+14155238886&Body=Your appointment is coming up on July 21 at 3PM");
//    Request request = new Request.Builder()
//            .url("https://api.twilio.com/2010-04-01/Accounts/ACf0340978828bc1b671943f92ec12c0b3/Messages.json")
//            .method("POST", body)
//            .addHeader("Authorization", "Basic QUNmMDM0MDk3ODgyOGJjMWI2NzE5NDNmOTJlYzEyYzBiMzozZGEzNjM3MGEzZjQ5MjQzMWE1Y2FhY2ViNGQwYTdjNg==")
//            .addHeader("Content-Type", "application/x-www-form-urlencoded")
//            .build();
//    Response response = client.newCall(request).execute();


    }
