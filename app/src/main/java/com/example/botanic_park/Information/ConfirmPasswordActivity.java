package com.example.botanic_park.Information;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.NetworkStatus;
import com.example.botanic_park.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConfirmPasswordActivity extends AppCompatActivity {

    private static final String IP_ADDRESS = "106.10.37.13";
    private static final String TAG = "check";

    private EditText input_password;
    private Button delete;
    private Button delete_cancel;

    private static Intent intent;
    private static Intent saveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_confirm_password);

        intent = getIntent();
        saveData = new Intent();

        intent.getStringExtra("password");

        input_password = (EditText) findViewById(R.id.input_password);
        delete = (Button) findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(Integer.parseInt(input_password.getText().toString())).equals(intent.getStringExtra("password"))) {
                    saveData.putExtra("result", "delete");
                    setResult(RESULT_OK, saveData);
                    finish();
                }
                else {
                    Toast.makeText(ConfirmPasswordActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete_cancel = (Button) findViewById(R.id.delete_cancel_button);
        delete_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData.putExtra("result", "cancel");
                setResult(RESULT_OK, saveData);
                finish();
            }
        });
    }

    //창에서 뒤로가기하면 data가 null로 가서 팅기는 현상을 막기위함
    @Override
    public void onBackPressed() {
        saveData.putExtra("result", "cancel");
        setResult(RESULT_OK, saveData);
        finish();
    }

}
