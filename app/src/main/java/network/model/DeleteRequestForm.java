package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteRequestForm {

    @SerializedName("p_id")
    @Expose
    private String p_id;

    @SerializedName("req_id")
    @Expose
    private String req_id;

    public DeleteRequestForm(String p_id, String req_id) {
        this.p_id = p_id;
        this.req_id = req_id;
    }


}
