package com.gemalto.tokenlibrary.pojo.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecoverWallet {
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