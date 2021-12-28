package com.kerwinkeep.pictureshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAccount;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextAccount = findViewById(R.id.editViewRegisterAccount);
        editTextPassword  = findViewById(R.id.editViewRegisterPassword);
        editTextName = findViewById(R.id.edittextRegisterUserName);
        editTextConfirmPassword = findViewById(R.id.editViewRegisterConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonCreateAccount);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("register", "点击了");
                String account = editTextAccount.getText().toString();
                String password = editTextPassword.getText().toString();
                String name = editTextName.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                if (password.equals(confirmPassword)) {
                    register(name, account, password);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (R.id.buttonCreateAccount)
    }

    public void register(String name, String account, String password) {//发送注册请求

        String url = "http://10.0.2.2:8081/user/register";
        OkHttpClient client = new OkHttpClient();
        //构建表单参数
        FormBody.Builder requestBuild = new FormBody.Builder();
        //添加请求体
        HashMap<String,String> map = new HashMap<>();
        map.put("account", account);
        map.put("password",password);
        map.put("name",name);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        //添加请求体
        RequestBody formBody;
        formBody = RequestBody.create(IndexActivity.JSON,data);
        Request request = new Request.Builder()
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
                if (result.trim().equals("Success.")) {
                    Log.d("注册成功---", result);
                    Message message = new Message();
                    message.obj = result;
                    message.what = 1;
                    handler.sendMessage(message);
                } else
                    Log.d("注册失败---", result);
                Message message = new Message();
                message.obj = result;
                message.what = 2;
                handler.sendMessage(message);
                response.body().close();
            }
        });
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            String msg1;
            switch (msg.what) {
                case 1:
                    msg1 = (String) msg.obj;
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                case 2:
                    Toast toast = Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT);
                    toast.show();
            }
        }
    };
}