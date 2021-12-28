package com.kerwinkeep.pictureshare.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kerwinkeep.pictureshare.R;
import com.kerwinkeep.pictureshare.bean.Picture;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private List<Picture> pictureList;
    private final Context context;

    public PictureAdapter(Context context) {
        this.context = context;
    }

    public PictureAdapter(List<Picture> pictureList, Context context) {
        this.pictureList = pictureList;
        this.context = context;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.cardview_picture, null);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        holder.imageViewPicture.setImageBitmap(pictureList.get(position).getPictureData());
        holder.textViewTitle.setText(pictureList.get(position).getTitle());
        holder.textViewAuthor.setText(pictureList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return pictureList == null ? 0 : pictureList.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewPicture;
        private final TextView textViewTitle;
        private final TextView textViewAuthor;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPicture = itemView.findViewById(R.id.imageview_picture);
            textViewTitle = itemView.findViewById(R.id.textview_picture_title);
            textViewAuthor = itemView.findViewById(R.id.textview_picture_author);
        }
    }
}
