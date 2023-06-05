package com.mcss.upus.Model;

import com.google.gson.annotations.SerializedName;

public class SlidePicture {

    @SerializedName("url")
    Integer url;

    public Integer getUrl() {
        return url;
    }

    public void setUrl(Integer url) {
        this.url = url;
    }

    public SlidePicture(Integer url) {
        this.url = url;
    }
}
