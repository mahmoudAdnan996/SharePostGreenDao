package com.example.mahmoudadnan.sharepostgreendao.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahmoudadnan.sharepostgreendao.R;
import com.example.mahmoudadnan.sharepostgreendao.model.Post;

import java.util.List;

/**
 * Created by mahmoud.adnan on 12/31/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    List<Post> postList;
    Context context;

    public PostAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    public void add(Post post){
        postList.add(post);
    }
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.setPostView(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
