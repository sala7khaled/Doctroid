package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class P_ID {

    @SerializedName("p_id")
    @Expose
    private String p_id;

    public P_ID() {
    }

    public P_ID(String p_id) {
        this.p_id = p_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }
}
