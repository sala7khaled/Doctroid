package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Token implements Serializable {

    @SerializedName("token")
    @Expose
    private String accessToken;
    @SerializedName("confirm")
    @Expose
    private String confirm;

    public String getAccessToken() {
        return accessToken;
    }

    public String getConfirm() {
        return confirm;
    }
}