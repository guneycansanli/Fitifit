package com.example.fitifit.Model;

public class Post {
    private String postId;
    private String postAbout;
    private String postFrom;
    private String postImage;

    public Post() {

    }

    public Post(String postId, String postAbout, String postFrom, String postImage) {
        this.postId = postId;
        this.postAbout = postAbout;
        this.postFrom = postFrom;
        this.postImage = postImage;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostAbout() {
        return postAbout;
    }

    public void setPostAbout(String postAbout) {
        this.postAbout = postAbout;
    }

    public String getPostFrom() {
        return postFrom;
    }

    public void setPostFrom(String postFrom) {
        this.postFrom = postFrom;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }
}
