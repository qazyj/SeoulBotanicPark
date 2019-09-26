package com.example.botanic_park.Information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.botanic_park.MainActivity;
import com.example.botanic_park.NetworkStatus;
import com.example.botanic_park.OnSingleClickListener;
import com.example.botanic_park.R;

public class ConfirmPasswordActivity extends AppCompatActivity {

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
        delete.setOnClickListener(new OnSingleClickListener() {
            @Override
             public void onSingleClick(View v) {
                String getEdit = input_password.getText().toString();
                //공백(스페이스바)만 눌러서 넘기는 경우도 안된다고 할때에는 아래코드도 살림
                getEdit = getEdit.trim();

                if(getEdit.getBytes().length <= 0){
                    Toast.makeText(ConfirmPasswordActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(NetworkStatus.getConnectivityStatus(ConfirmPasswordActivity.this) != 3 &&
                        String.valueOf(Integer.parseInt(input_password.getText().toString())).equals(intent.getStringExtra("password"))) {
                    saveData.putExtra("result", "delete");
                    setResult(RESULT_OK, saveData);
                    finish();
                }
                else if(NetworkStatus.getConnectivityStatus(ConfirmPasswordActivity.this) == 3) {
                    Toast.makeText(ConfirmPasswordActivity.this, "네트워크가 연결되지 않아 초기화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ConfirmPasswordActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete_cancel = (Button) findViewById(R.id.delete_cancel_button);
        delete_cancel.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                saveData.putExtra("result", "cancel");
                setResult(RESULT_OK, saveData);
                if (NetworkStatus.getConnectivityStatus(ConfirmPasswordActivity.this) == 3) {
                    Toast.makeText(ConfirmPasswordActivity.this, "네트워크가 연결되지 않아 초기화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                }
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
