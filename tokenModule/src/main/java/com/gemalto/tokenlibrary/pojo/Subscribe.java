package com.gemalto.tokenlibrary.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscribe {
    @SerializedName("xrp")
    @Expose
    private double xrp;

    public double getXrp() {
        return xrp;
    }

    public void setXrp(double xrp) {
        this.xrp = xrp;
    }
}
