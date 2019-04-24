
package com.gemalto.tokenlibrary.pojo.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionParameters {

    @SerializedName("destinationAddress")
    @Expose
    private String destinationAddress;

    @SerializedName("amount")
    @Expose
    private String amount;

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}