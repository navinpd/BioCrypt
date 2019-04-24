package com.gemalto.tokenlibrary.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAccountInfo {
    @SerializedName("xrpBalance")
    @Expose
    private String xrpBalance;

    @SerializedName("publicaddress")
    @Expose
    private String publicaddress;

    public String getXrpBalance() {
        return xrpBalance;
    }

    public void setXrpBalance(String xrpbalance) {
        this.xrpBalance = xrpbalance;
    }

    public String getPublicaddress() {
        return publicaddress;
    }

    public void setPublicaddress(String publicaddress) {
        this.publicaddress = publicaddress;
    }
}
