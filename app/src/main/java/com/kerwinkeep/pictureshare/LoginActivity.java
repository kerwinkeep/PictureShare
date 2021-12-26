package com.kerwinkeep.pictureshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText accountEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountEditText = findViewById(R.id.edittext_account);
        passwordEditText = findViewById(R.id.edittext_password);
        Button loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);
    }



    public boolean verifyLogin(String account, String password) {//登录
//        String url = "http://106.52.126.68:8200/user/login";
//        OkHttpClient client = new OkHttpClient();
//        //构建表单参数
//        HashMap<String,String> map=new HashMap<>();
//        map.put("id", id);
//        map.put("password",password);
//        Gson gson=new Gson();
//        String data=gson.toJson(map);
//        //添加请求体
//        RequestBody formbody;
//        formbody=RequestBody.create(MainActivity2.JSON,data);
//        Request request=new Request.Builder()
//                .url(url)
//                .post(formbody)
//                .build();
//        System.out.println(request.toString());
//        //异步请求
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("MainActivityPost---", "连接失败" + e.getLocalizedMessage());
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                if (!result.trim().equals("账号或密码错误")) {
//                    Log.d("登录信息---", result);
//                    Message message = new Message();
//                    message.obj = result;
//                    message.what = 1;
//                    handler.sendMessage(message);
//                } else
//                    Log.d("登录信息---", result);
//                response.body().close();
//            }
//        });
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.button_login == id) {
            if (verifyLogin(accountEditText.getText().toString(), passwordEditText.getText().toString())) {
                // 通过条件判断，登陆验证通过
                // 跳转

            }

        }
    }
}