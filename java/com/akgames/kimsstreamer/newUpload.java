package com.akgames.kimsstreamer;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class newUpload {
    private String Name;
    private String ImageUrl;
    private String FileUrl;
    private String Key;
    private String count, viewers;
    private String descript, author, tags, fileType, email;


    public newUpload() {
        //empty constructor needed
    }

    public newUpload(String mName, String mImageUrl, String mFileUrl, String descript, String author, String tags, String fileType, String email) {
        this.Name = mName;
        this.ImageUrl = mImageUrl;
        this.FileUrl = mFileUrl;
        this.descript = descript;
        this.author = author;
        this.tags = tags;
        this.fileType = fileType;
        this.email = email;
        this.count = "999999999999999999";
        this.viewers = "";
    }


    public String getViewers() {
        return viewers;
    }

    public void setViewers(String viewers) {
        this.viewers = viewers;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getKey() {
        return Key;
    }
    @Exclude
    public void setKey(String key) {
        Key = key;
    }

}
