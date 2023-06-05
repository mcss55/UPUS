package com.mcss.upus.Model;

import com.google.gson.annotations.SerializedName;

public class SlidePicture {
    @Override
    public String toString() {
        return "SlidePicture{" +
                "deviceId=" + deviceId +
                ", mainBoardId=" + mainBoardId +
                ", url='" + url + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                '}';
    }

    @SerializedName("device_id")
    Integer deviceId;

    @SerializedName("mainboard_id")
    Integer mainBoardId;

    @SerializedName("picture")
    String url;

    @SerializedName("date_from")
    String dateFrom;

    @SerializedName("date_to")
    String dateTo;

    public SlidePicture() {
    }

    public SlidePicture(Integer deviceId, Integer mainBoardId, String url, String dateFrom, String dateTo) {
        this.deviceId = deviceId;
        this.mainBoardId = mainBoardId;
        this.url = url;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getMainBoardId() {
        return mainBoardId;
    }

    public void setMainBoardId(Integer mainBoardId) {
        this.mainBoardId = mainBoardId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
