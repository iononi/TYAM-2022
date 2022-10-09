package com.example.lorempicsum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Lorem {
    /*public String Author;
    public String URL;
    public String UrlDownload;*/

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("download_url")
    @Expose
    private String download_url;

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author=author;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url=url;
    }

    public String getDownload_url(){
        return download_url;
    }

    public void setDownload_url(String download_url){
        this.download_url=download_url;
    }

}
