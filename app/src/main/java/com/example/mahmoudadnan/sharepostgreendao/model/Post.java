package com.example.mahmoudadnan.sharepostgreendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Post {

    @Id(autoincrement = true)
    private Long id;

    private String postText;

    private String ImageUri;

    private String postOwner;

    private String ownerType;

    @Generated(hash = 1141505515)
    public Post(Long id, String postText, String ImageUri, String postOwner,
            String ownerType) {
        this.id = id;
        this.postText = postText;
        this.ImageUri = ImageUri;
        this.postOwner = postOwner;
        this.ownerType = ownerType;
    }

    @Generated(hash = 1782702645)
    public Post() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostText() {
        return this.postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getImageUri() {
        return this.ImageUri;
    }

    public void setImageUri(String ImageUri) {
        this.ImageUri = ImageUri;
    }

    public String getPostOwner() {
        return this.postOwner;
    }

    public void setPostOwner(String postOwner) {
        this.postOwner = postOwner;
    }

    public String getOwnerType() {
        return this.ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }
}
