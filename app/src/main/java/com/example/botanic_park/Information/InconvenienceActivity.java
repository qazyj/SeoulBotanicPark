package com.example.botanic_park.Information;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.MainActivity;
import com.example.botanic_park.NetworkStatus;
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
    private static final String TAG_VIEWS = "views";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_REGISTRATION_DATE = "date";
    private static final String TAG_AMOUNT = "result";

    JSONArray posts = null;
    ArrayList<HashMap<String, String>> postList;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_inconvenience);
        AppManager.getInstance().setInconvenienceActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(NetworkStatus.getConnectivityStatus(InconvenienceActivity.this)!=3) {
            list = (ListView) findViewById(R.id.listviewpost);
            postList = new ArrayList<HashMap<String, String>>();

            getData("http://106.10.37.13/inconvenienceselect.php"); //수정 필요

            findViewById(R.id.registration_post_button2).setOnClickListener(new Button.OnClickListener() {
                                                                                public void onClick(View v) {
                                                                                    Intent intent = new Intent(InconvenienceActivity.this, RegistrationPostInInconvenienceActivity.class);
                                                                                    intent.putExtra("Post", "Registration");
                                                                                    startActivity(intent);
                                                                                }
                                                                            }
            );
        }
        else {
            Toast.makeText(InconvenienceActivity.this, "네트워크가 연결되지 않아 초기화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(InconvenienceActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            posts = jsonObj.getJSONArray(TAG_AMOUNT);
            for (int i = posts.length()-1; i >=0; i--) {
                JSONObject c = posts.getJSONObject(i);
                String number = c.getString(TAG_NUMBER);
                String title = c.getString(TAG_TITLE);
                String views = c.getString(TAG_VIEWS);
                String date = c.getString(TAG_REGISTRATION_DATE);
                String password = c.getString(TAG_PASSWORD);

                HashMap<String, String> posts2 = new HashMap<String, String>();

                posts2.put(TAG_NUMBER, number);
                posts2.put(TAG_TITLE, title);
                posts2.put(TAG_VIEWS, views);
                posts2.put(TAG_REGISTRATION_DATE, date);
                posts2.put(TAG_PASSWORD, password);
                postList.add(posts2);
            }
            ListAdapter adapter = new SimpleAdapter(
                    InconvenienceActivity.this, postList, R.layout.item_inconvenience_listview,
            new String[]{TAG_NUMBER, TAG_TITLE, TAG_VIEWS, TAG_REGISTRATION_DATE, TAG_PASSWORD},
            new int[]{R.id.item_number, R.id.item_title, R.id.item_views,R.id.item_registration_date, R.id.item_password});

            list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // TODO Auto-generated method stub
                    if(NetworkStatus.getConnectivityStatus(InconvenienceActivity.this)!=3) {
                        Intent intent = new Intent(getApplicationContext(),InconvenienceDetailPostActivity.class);

                        //json형태인 문자열에서 primarykey인 number값을 보내는 작업
                        Log.d("checkcheck", postList.get(position).toString());
                        String str1 = postList.get(position).toString();
                        String[] arr = str1.split(",");
                        int getnumber = arr[1].indexOf("number=");
                        String str2 = arr[1].substring(getnumber+7);
                        int getpassword = arr[2].indexOf("number=");
                        String str4 = arr[2].substring(getpassword+11);
                        int getviews1 = arr[4].indexOf("views=");
                        String str3 = arr[4].substring(getviews1+6,arr[4].length()-1);

                        Log.d("checkcheck", str4);

                        intent.putExtra("title", str2);
                        intent.putExtra("password",str4);
                        intent.putExtra("views", str3);

                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(InconvenienceActivity.this, "네트워크가 연결되어야 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }
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
