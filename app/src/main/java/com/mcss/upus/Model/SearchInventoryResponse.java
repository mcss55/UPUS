package com.mcss.upus.Model;

import com.google.gson.annotations.SerializedName;

public class SearchInventoryResponse {
    String phoneNumber;

    public SearchInventoryResponse(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "SearchInventoryResponse{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
