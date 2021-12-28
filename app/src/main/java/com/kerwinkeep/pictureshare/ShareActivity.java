package com.kerwinkeep.pictureshare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kerwinkeep.pictureshare.common.PictureParseTool;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewUploadPicture;
    private EditText editTextUploadTitle;
    private Bitmap picture;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        userId = getIntent().getStringExtra("userId");
        editTextUploadTitle = findViewById(R.id.editTextUploadTitle);
        Button buttonSelectPicture = findViewById(R.id.button_select_picture);
        imageViewUploadPicture = findViewById(R.id.imageViewUploadPicture);
        Button buttonUpload = findViewById(R.id.buttonUpload);
        buttonSelectPicture.setOnClickListener(this);
        imageViewUploadPicture.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.button_select_picture == id || R.id.imageViewUploadPicture == id) {
            gallery();
        } else if (R.id.buttonUpload == id) {
            if(picture != null){
                uploadPicture( PictureParseTool.bitmapToString(picture), editTextUploadTitle.getText().toString());
                Log.d("upload", "已经运行");
                Intent intent = new Intent(this, IndexActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(ShareActivity.this, "未选择图片", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void gallery() {
        /// 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            try {
                picture = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imageViewUploadPicture.setImageBitmap(picture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploadPicture(String pictureData,String title){//发送上传请求
        String url="http://10.0.2.2:8081/picture/uploadPicture";
//        String url="http://35.241.95.124:8081/picture/uploadPicture";
        OkHttpClient client=new OkHttpClient();
        //构建表单参数
        HashMap<String,String> map=new HashMap<>();
        Log.d("upload", userId + " " + title + " " + pictureData);
        map.put("userId", userId);
        map.put("title",title);
        map.put("pictureData",pictureData);
        Gson gson=new Gson();
        String data=gson.toJson(map);
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
                if(result.trim().equals("Upload complete.")){
                    Log.d("上传成功---", result);
                    Message message= new Message();
                    message.obj = result;
                    message.what = 3; handler.sendMessage(message);
                }else
                    Log.d("上传失败---", result);
                response.body().close();
            }
        });
    }

    Handler handler = new Handler(Looper.getMainLooper())
    { public void handleMessage(Message msg) {
        switch (msg.what) {
            case 2://点赞，成功时该cardview 的zan加1  /////////
                break;
            case 3://上传
                // ////////
                break;
        }
    } };
}