package com.example.botanic_park.Information;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.botanic_park.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class InconvenienceDetailPostActivity  extends Activity {

    String myJSON;

    private static Intent intent;
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_RECOMMENDATION = "recommendation";
    private static final String TAG_NOTRECOMMENDATION = "notrecommendation";
    private static final String TAG_REGISTRATION_DATE = "date";
    private static final String TAG_AMOUNT = "result";

    JSONArray posts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inconvenience_detail_post);

        intent = getIntent();
        //intent.getStringExtra("title")

        getData("http://106.10.37.13/inconvenienceselect.php"); //수정 필요

    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            posts = jsonObj.getJSONArray(TAG_AMOUNT);
            JSONObject c = posts.getJSONObject(Integer.parseInt(intent.getStringExtra("title"))-1);

            TextView post_title = findViewById(R.id.title);
            post_title.setText(c.getString(TAG_TITLE));

            TextView post_content = findViewById(R.id.content);
            post_content.setText(c.getString(TAG_CONTENT));

            TextView post_date = findViewById(R.id.date);
            post_date.setText(c.getString(TAG_REGISTRATION_DATE));

            TextView post_recommendation = findViewById(R.id.recommendation_number);
            post_recommendation.setText(c.getString(TAG_RECOMMENDATION));

            TextView post_not_recommendation = findViewById(R.id.not_recommendation_number);
            post_not_recommendation.setText(c.getString(TAG_NOTRECOMMENDATION));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }

        }

        GetDataJSON g = new GetDataJSON();

        g.execute(url);

    }

}
