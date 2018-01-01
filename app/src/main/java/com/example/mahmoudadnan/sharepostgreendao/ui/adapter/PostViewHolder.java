package com.example.mahmoudadnan.sharepostgreendao.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahmoudadnan.sharepostgreendao.R;
import com.example.mahmoudadnan.sharepostgreendao.model.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mahmoud.adnan on 12/31/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.postOwnerTV) TextView postOwner;
    @BindView(R.id.postTV) TextView postText;
    @BindView(R.id.typeTV) TextView typeText;
    @BindView(R.id.postImage)
    ImageView postImage;
    @BindView(R.id.likeBTN)
    ImageButton likeBtn;
    @BindView(R.id.commentBTN) ImageButton commentBtn;
    @BindView(R.id.commentET)
    EditText commentText;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeBtn.setBackgroundResource(R.drawable.liked);
            }
        });
    }
    void setPostView(Post postView){
        postText.setText(postView.getPostText());
        postImage.setImageURI(Uri.parse(postView.getImageUri()));
        postOwner.setText(postView.getPostOwner());
        typeText.setText(postView.getOwnerType());
    }
}
