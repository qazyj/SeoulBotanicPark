package com.example.botanic_park.Information;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.NetworkStatus;
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

public class RegistrationPostInInconvenienceActivity extends Activity {

    private static final String IP_ADDRESS = "106.10.37.13";
    private static final String TAG = "check";

    String myJSON;

    private static Intent intent;
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_VIEWS = "views";
    private static final String TAG_REGISTRATION_DATE = "date";
    private static final String TAG_AMOUNT = "result";
    private static final String TAG_NUMBER = "number";

    JSONArray posts = null;

    private EditText title;
    private EditText content;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_registration_post_in_inconvenience);
        AppManager.getInstance().setRegistrationPostInInconvenienceActivity(this);

        intent = getIntent();
            title = (EditText) findViewById(R.id.input_title);

            password = (EditText) findViewById(R.id.input_password);
            password.setInputType(InputType.TYPE_CLASS_NUMBER);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            content = (EditText) findViewById(R.id.input_content);
            content.addTextChangedListener(new TextWatcher() {
                String previousString = "";

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    previousString = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (content.getLineCount() > 10) {
                        content.setText(previousString);
                        content.setSelection(content.length());
                    }
                }
            });
        if(intent.getStringExtra("Post").equals("Registration")) {
            Button buttonInsert = (Button) findViewById(R.id.registration_post_button2);
            buttonInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String post_title = title.getText().toString();
                    String post_content = content.getText().toString();
                    String post_password = password.getText().toString();

                    if (post_title.matches(" ")) {
                        Toast.makeText(RegistrationPostInInconvenienceActivity.this, "제목이 공백입니다. 댓글추가가 안됩니다.", Toast.LENGTH_SHORT).show();
                    } else if (post_content.matches(" ")) {
                        Toast.makeText(RegistrationPostInInconvenienceActivity.this, "내용이 공백입니다. 댓글추가가 안됩니다.", Toast.LENGTH_SHORT).show();
                    } else if (post_password.matches(" ")) {
                        Toast.makeText(RegistrationPostInInconvenienceActivity.this, "비밀번호가 공백입니다. 댓글추가가 안됩니다.", Toast.LENGTH_SHORT).show();
                    } else if (NetworkStatus.getConnectivityStatus(RegistrationPostInInconvenienceActivity.this) != 3) {

                        InsertData task = new InsertData();
                        task.execute("http://" + IP_ADDRESS + "/inconvenienceinsert.php", post_title, post_content, post_password);

                        finish();
                    } else {
                        Toast.makeText(RegistrationPostInInconvenienceActivity.this, "네트워크가 연결되어야 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            intent.getStringExtra("number");
            getData("http://" + IP_ADDRESS + "/inconvenienceselect.php");

            Button buttonInsert = (Button) findViewById(R.id.registration_post_button2);
            buttonInsert.setText("글            변            경");
            buttonInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String post_title = title.getText().toString();
                    String post_content = content.getText().toString();
                    String post_password = password.getText().toString();

                    if (post_title.matches(" ")) {
                        Toast.makeText(RegistrationPostInInconvenienceActivity.this, "제목이 공백입니다. 글 변경이 안됩니다.", Toast.LENGTH_SHORT).show();
                    } else if (post_content.matches(" ")) {
                        Toast.makeText(RegistrationPostInInconvenienceActivity.this, "내용이 공백입니다. 글 변경이 안됩니다.", Toast.LENGTH_SHORT).show();
                    } else if (post_password.matches(" ")) {
                        Toast.makeText(RegistrationPostInInconvenienceActivity.this, "비밀번호가 공백입니다. 글 변경이 안됩니다.", Toast.LENGTH_SHORT).show();
                    } else if (NetworkStatus.getConnectivityStatus(RegistrationPostInInconvenienceActivity.this) != 3) {
                        if(post_password.equals(intent.getStringExtra("password"))) {
                            UpdateData revisePost = new UpdateData();
                            revisePost.execute("http://" + IP_ADDRESS + "/updaterevisedpost.php", intent.getStringExtra("number"), post_title, post_content);

                            finish();
                        }
                        else {
                            Toast.makeText(RegistrationPostInInconvenienceActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegistrationPostInInconvenienceActivity.this, "네트워크가 연결되어야 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RegistrationPostInInconvenienceActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(RegistrationPostInInconvenienceActivity.this, result, Toast.LENGTH_LONG).show();

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String title = (String)params[1];
            String content = (String)params[2];
            String password = (String)params[3];
            int views = 0;
            long now = System.currentTimeMillis();
            Date today = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String date = sdf.format(today);
            if(password.length()>=5){
                password.substring(0, 4);
            }

            String serverURL = (String)params[0];
            String postParameters = "title=" + title + "&content=" + content + "&password=" + password + "&views=" + views + "&date=" + date ;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

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
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return null;
            }

        }
    }

    class UpdateData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RegistrationPostInInconvenienceActivity.this,
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
            String title = (String)params[2];
            String content = (String)params[3];

            String serverURL = (String)params[0];
            String postParameters = "number=" + number + "&title=" + title + "&content=" + content;

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

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            posts = jsonObj.getJSONArray(TAG_AMOUNT);
            for (int i = 0; i < posts.length(); i++) {
                JSONObject c = posts.getJSONObject(i);
                if (c.getString(TAG_NUMBER).equals(intent.getStringExtra("number"))){

                    TextView post_title = findViewById(R.id.input_title);
                    post_title.setText(c.getString(TAG_TITLE));

                    TextView post_content = findViewById(R.id.input_content);
                    post_content.setText(c.getString(TAG_CONTENT));
                }
            }
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
