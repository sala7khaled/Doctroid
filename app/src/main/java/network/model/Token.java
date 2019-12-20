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
    @SerializedName("p_id")
    @Expose
    private String p_id;

    public Token() {
    }

    public Token(String accessToken, String confirm, String p_id) {
        this.accessToken = accessToken;
        this.confirm = confirm;
        this.p_id = p_id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getConfirm() {
        return confirm;
    }

    public String getP_id() {
        return p_id;
    }
}