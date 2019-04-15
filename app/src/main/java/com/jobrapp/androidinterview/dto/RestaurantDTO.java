package com.jobrapp.androidinterview.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantDTO implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;


    @SerializedName("description")
    @Expose
    private String description;


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("cover_img_url")
    @Expose
    private String coverImgURL;

    @SerializedName("name")
    @Expose
    private String name;

    public String getStatusType() {
        return statusType;
    }

    @SerializedName("status_type")
    @Expose
    private String statusType;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getCoverImgURL() {
        return coverImgURL;
    }

    public String getName() {
        return name;
    }

}
