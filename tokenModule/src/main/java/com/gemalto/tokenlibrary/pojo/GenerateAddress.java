package com.gemalto.tokenlibrary.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateAddress {

    @SerializedName("keytoenglish")
    @Expose
    private String keytoenglish;

    public String getKeytoenglish() {
        return keytoenglish;
    }

    public void setKeytoenglish(String keytoenglish) {
        this.keytoenglish = keytoenglish;
    }
}