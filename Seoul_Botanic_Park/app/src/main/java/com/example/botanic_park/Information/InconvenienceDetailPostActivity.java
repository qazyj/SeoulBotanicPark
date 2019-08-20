package com.example.botanic_park.Information;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.botanic_park.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class InconvenienceDetailPostActivity  extends Activity {

    private static String IP_ADDRESS = "106.10.37.13";

    String myJSON;

    private static Intent intent;
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_RECOMMENDATION = "recommendation";
    private static final String TAG_NOTRECOMMENDATION = "notrecommendation";
    private static final String TAG_REGISTRATION_DATE = "date";
    private static final String TAG_AMOUNT = "result";

    JSONArray posts = null;

    private EditText content;           //댓글 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inconvenience_detail_post);

        intent = getIntent();

        getData("http://" + IP_ADDRESS + "/inconvenienceselect.php"); //수정 필요

        findViewById(R.id.recommend_button).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                TextView add_recommendation_number = findViewById(R.id.recommendation_number);
                add_recommendation_number.setText(String.valueOf(Integer.parseInt(add_recommendation_number.getText().toString())+1));

                UpdateData task = new UpdateData();
                task.execute("http://" + IP_ADDRESS + "/addpostrecommendation.php", intent.getStringExtra("title"), add_recommendation_number.getText().toString());
            }
        }
        );

        findViewById(R.id.not_recommended_button).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                TextView add_not_recommendation_number = findViewById(R.id.not_recommendation_number);
                add_not_recommendation_number.setText(String.valueOf(Integer.parseInt(add_not_recommendation_number.getText().toString())+1));
            }
        }
        );

        content = (EditText)findViewById(R.id.input_commend_content);

        ImageButton buttonInsert = (ImageButton)findViewById(R.id.add_comment_button);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String commend_content = content.getText().toString();

                InsertCommend task = new InsertCommend();
                task.execute("http://" + IP_ADDRESS + "/insertinconveniencecommend.php", intent.getStringExtra("title"), commend_content);
            }
        });
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

    class UpdateData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(InconvenienceDetailPostActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String number = (String) params[1];
            String recommendation = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "recommendation" + recommendation + "&number" + number;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("texttest", "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d("texttest", "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    class InsertCommend extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(InconvenienceDetailPostActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String number = (String)params[1];
            String content = (String)params[2];
            long now = System.currentTimeMillis();
            Date today = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(today);

            String serverURL = (String)params[0];
            String postParameters = "number" + number + "&content" + content + "&date" +date;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("inserttext", "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d("inserttext", "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

}


