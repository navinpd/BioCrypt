
package com.gemalto.tokenlibrary.pojo.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionParameters {

    @SerializedName("publicaddress")
    @Expose
    private String publicaddress;


    public String getPublicaddress() {
        return publicaddress;
    }

    public void setPublicaddress(String publicaddress) {
        this.publicaddress = publicaddress;
    }

}