package com.kerwinkeep.pictureshare.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.kerwinkeep.pictureshare.R;
import com.kerwinkeep.pictureshare.bean.Picture;
import com.kerwinkeep.pictureshare.ui.profile.ProfileViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PersonPictureAdapter extends RecyclerView.Adapter<PersonPictureAdapter.PersonPictureViewHolder> {

    private List<Picture> pictureList;
    private final Context context;

    public PersonPictureAdapter(Context context) {
        this.context = context;
    }


    public PersonPictureAdapter(List<Picture> pictureList, Context context) {
        this.pictureList = pictureList;
        this.context = context;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    @NonNull
    @Override
    public PersonPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_picture, parent, false);
        PersonPictureViewHolder viewHolder = new PersonPictureViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull PersonPictureViewHolder holder, int position) {
        holder.imageViewPicture.setImageBitmap(pictureList.get(position).getPictureData());
        holder.textViewTitle.setText(pictureList.get(position).getTitle());
        holder.textViewLikeNum.setText(String.valueOf(pictureList.get(position).getLikeNum()));
        holder.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File("/sdcard/Pictures/", "pictureName.jpg");
                if (file.exists()) {
                    file.delete();
                }
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    Bitmap bitMap = ((BitmapDrawable) holder.imageViewPicture.getDrawable()).getBitmap();//通过强制转化weiBitmapDrable然后获取Bitmap
                    bitMap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);//然后按照指定的图片格式转换，并以stream方式保存文件
                    Toast toast = Toast.makeText(context,
                            "保存成功",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
                        context.getContentResolver(),
                        pictureList.get(position).getPictureData(),
                        null,
                        null));
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);//设置分享行为
                intent.setType("image/*");//设置分享内容的类型
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent = Intent.createChooser(intent, "分享");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictureList == null ? 0 : pictureList.size();
    }

    public static class PersonPictureViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewPicture;
        private final TextView textViewTitle;
        private final TextView textViewLikeNum;
        private final Button buttonSave;
        private final Button buttonShare;

        public PersonPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPicture = itemView.findViewById(R.id.imageview_picture);
            textViewTitle = itemView.findViewById(R.id.textview_picture_title);
            textViewLikeNum = itemView.findViewById(R.id.textViewLikeNum);
            buttonSave = itemView.findViewById(R.id.button_save);
            buttonShare = itemView.findViewById(R.id.button_share);
        }
    }

}
