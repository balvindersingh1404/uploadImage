
package com.buckleit.imhotel.responseModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {

    @SerializedName("access_key")
private String access_Key;

    public String getAccess_Key() {
        return access_Key;
    }

    public void setAccess_Key(String access_Key) {
        this.access_Key = access_Key;
    }
}