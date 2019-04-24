
package com.gemalto.tokenlibrary.pojo.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenReq {

    @SerializedName("device_id")
    @Expose
    private Integer deviceId;


    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

}