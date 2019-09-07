package com.example.botanic_park.Information;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;

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

    private EditText title;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_registration_post_in_inconvenience);
        AppManager.getInstance().setRegistrationPostInInconvenienceActivity(this);

        title = (EditText)findViewById(R.id.input_title);
        content = (EditText)findViewById(R.id.input_content);
        content.addTextChangedListener(new TextWatcher() {
            String previousString = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                previousString= s.toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (content.getLineCount() > 18)
                {
                    content.setText(previousString);
                    content.setSelection(content.length());
                }
            }
        });

        Button buttonInsert = (Button)findViewById(R.id.registration_post_button2);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post_title = title.getText().toString();
                String post_content = content.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/inconvenienceinsert.php", post_title, post_content);

                finish();
            }
        });
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

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String title = (String)params[1];
            String content = (String)params[2];
            int views = 0;
            long now = System.currentTimeMillis();
            Date today = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String date = sdf.format(today);

            String serverURL = (String)params[0];
            String postParameters = "title=" + title + "&content=" + content + "&views=" + views + "&date=" + date ;


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
                Log.d(TAG, "POST response code - " + responseStatusCode);

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

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

}
