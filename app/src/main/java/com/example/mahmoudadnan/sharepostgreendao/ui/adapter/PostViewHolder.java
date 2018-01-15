package com.example.mahmoudadnan.sharepostgreendao.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mahmoudadnan.sharepostgreendao.R;
import com.example.mahmoudadnan.sharepostgreendao.model.Post;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.postOwnerTV) TextView postOwner;
    @BindView(R.id.postTV) TextView postText;
    @BindView(R.id.typeTV) TextView typeText;
    @BindView(R.id.postImage) ImageView postImage;
    @BindView(R.id.likeBTN) ImageButton likeBtn;
    @BindView(R.id.commentET) EditText commentText;
    @BindView(R.id.add_comment) Button addCommentBTN;

    boolean isClicked;
    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        isClicked = true;
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClicked == true){
                    likeBtn.setBackgroundResource(R.drawable.liked);
                    isClicked = false;
                }else {
                    likeBtn.setBackgroundResource(R.drawable.like);
                    isClicked = true;
                }
            }
        });
    }
    void setPostView(Post postView){
        String imageUri = postView.getImageUri();
        postText.setText(postView.getPostText());
        postImage.setImageURI(Uri.parse(imageUri));
        postOwner.setText(postView.getPostOwner());
        typeText.setText(postView.getOwnerType());
    }
}
