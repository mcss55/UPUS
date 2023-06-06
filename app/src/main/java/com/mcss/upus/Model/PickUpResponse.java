package com.mcss.upus.Model;

import com.google.gson.annotations.SerializedName;

public class PickUpResponse {

    @SerializedName("device_id")
    Integer deviceId;

    @SerializedName("mainboard_id")
    Integer mainboardId;

    @SerializedName("box_no")
    String boxNo;

    public PickUpResponse(Integer deviceId, Integer mainboardId, String boxNo) {
        this.deviceId = deviceId;
        this.mainboardId = mainboardId;
        this.boxNo = boxNo;
    }

    @Override
    public String toString() {
        return "PickUpResponse{" +
                "deviceId=" + deviceId +
                ", mainboardId=" + mainboardId +
                ", boxNo='" + boxNo + '\'' +
                '}';
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getMainboardId() {
        return mainboardId;
    }

    public void setMainboardId(Integer mainboardId) {
        this.mainboardId = mainboardId;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }
}
