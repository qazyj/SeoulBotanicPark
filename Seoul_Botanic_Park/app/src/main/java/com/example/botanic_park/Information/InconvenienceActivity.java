package com.example.botanic_park.Information;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.botanic_park.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class InconvenienceActivity extends Activity {

    String myJSON;

    private static final String TAG_NUMBER = "number";
    private static final String TAG_TITLE = "title";
    private static final String TAG_RECOMMENDATION = "recommendation";
    private static final String TAG_REGISTRATION_DATE = "date";
    private static final String TAG_AMOUNT = "result";

    JSONArray posts = null;
    ArrayList<HashMap<String, String>> postList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inconvenience);

        list = (ListView) findViewById(R.id.listviewpost);
        postList = new ArrayList<HashMap<String, String>>();

        getData("http://106.10.37.13/inconvenienceselect.php"); //수정 필요

        findViewById(R.id.registration_post_button2).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(InconvenienceActivity.this, RegistrationPostInInconvenienceActivity.class);
                startActivity(intent);
            }
        }
        );

    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            posts = jsonObj.getJSONArray(TAG_AMOUNT);
            for (int i = 0; i < posts.length(); i++) {
                JSONObject c = posts.getJSONObject(i);
                String number = c.getString(TAG_NUMBER);
                String title = c.getString(TAG_TITLE);
                String recommendation = c.getString(TAG_RECOMMENDATION);
                String date = c.getString(TAG_REGISTRATION_DATE);

                HashMap<String, String> posts2 = new HashMap<String, String>();

                posts2.put(TAG_NUMBER, number);
                posts2.put(TAG_TITLE, title);
                posts2.put(TAG_RECOMMENDATION, recommendation);
                posts2.put(TAG_REGISTRATION_DATE, date);
                postList.add(posts2);
            }
            ListAdapter adapter = new SimpleAdapter(
                    InconvenienceActivity.this, postList, R.layout.item_inconvenience_listview,
            new String[]{TAG_NUMBER, TAG_TITLE, TAG_RECOMMENDATION, TAG_REGISTRATION_DATE},
            new int[]{R.id.item_number, R.id.item_title, R.id.item_recommended_number,R.id.item_registration_date});

            list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // TODO Auto-generated method stub

                    Intent intent = new Intent(getApplicationContext(),InconvenienceDetailPostActivity.class);

                    //json형태인 문자열에서 primarykey인 number값을 보내는 작업
                    String str1 = postList.get(position).toString();
                    String[] arr = str1.split(",");
                    int nIdx = arr[1].indexOf("number=");
                    String str2 = arr[1].substring(nIdx+7);

                    intent.putExtra("title", str2);

                    startActivity(intent);

                }
            });

            list.setAdapter(adapter);
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
