package com.kerwinkeep.pictureshare.ui.home;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kerwinkeep.pictureshare.IndexActivity;
import com.kerwinkeep.pictureshare.bean.Picture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Picture>> livePictureList;

    public HomeViewModel() {
        livePictureList = new MutableLiveData<>();
        List<Picture> pictureList = new ArrayList<>();
//        livePictureList.setValue();
    }

    public MutableLiveData<List<Picture>> getLivePictureList() {
//        if (livePictureList == null) {
//            livePictureList = new MutableLiveData<>();
//            List<Picture> pictureList = new ArrayList<>();
//            livePictureList.setValue(pictureList);
//        }
        return livePictureList;
    }

    public void setLivePictureList(MutableLiveData<List<Picture>> pictureList) {
        this.livePictureList = pictureList;
    }


}