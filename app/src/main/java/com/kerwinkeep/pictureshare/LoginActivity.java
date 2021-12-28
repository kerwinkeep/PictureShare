package com.kerwinkeep.pictureshare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText accountEditText;
    private EditText passwordEditText;
    ImageView pwdVisibility;
    private Boolean pwdSwitch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountEditText = findViewById(R.id.edittext_account);
        passwordEditText = findViewById(R.id.edittext_password);
        pwdVisibility = findViewById(R.id.password_visible);
        Button loginButton = findViewById(R.id.button_login);
        Button registerButton = findViewById(R.id.btn_register);
        loginButton.setOnClickListener(this);
        pwdVisibility.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    public boolean verifyLogin(String account, String password) {//登录

        String url = "http://10.0.2.2:8081/user/login";
        OkHttpClient client = new OkHttpClient();
        //构建表单参数
        HashMap<String,String> map = new HashMap<>();
        map.put("account", account);
        map.put("password",password);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        //添加请求体
        RequestBody formBody;
        formBody=RequestBody.create(IndexActivity.JSON,data);
        Request request=new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        System.out.println(request.toString());
        //异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainActivityPost---", "连接失败" + e.getLocalizedMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (!result.trim().equals("The account or password is incorrect.")) {
                    Log.d("登录信息---", result);
                    Message message = new Message();
                    message.obj = result;
                    message.what = 1;
                    handler.sendMessage(message);
                } else
                    Log.d("登录信息---", result);
                response.body().close();
            }
        });
        return false;
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String id = (String) msg.obj;
                    Intent intent = new Intent(LoginActivity.this, IndexActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("id", id);
                    Log.d("Login", id);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.button_login == id) {
            String account = accountEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            Log.d("Login", account);
            Log.d("Login", password);
            if (account.length() != 0 && password.length() != 0) {
                verifyLogin(account, password);
            } else {
                new AlertDialog.Builder(this)
                            .setTitle("账号或登录错误")
                            .setMessage("请重新输入")
                            .setIcon(R.mipmap.ic_launcher)
                            .create()
                            .show();
            }
        } else if (R.id.btn_register == id) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (R.id.password_visible == id) {
            pwdSwitch = !pwdSwitch;
            if (pwdSwitch) {
                pwdVisibility.setImageResource(
                        R.drawable.ic_outline_visibility_24);
                passwordEditText.setInputType(
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                pwdVisibility.setImageResource(
                        R.drawable.ic_outline_visibility_off_24);
                passwordEditText.setInputType(
                        InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                InputType.TYPE_CLASS_TEXT);
                passwordEditText.setTypeface(Typeface.DEFAULT);
            }

        }
    }
}