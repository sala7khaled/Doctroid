package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestIDs {

    @SerializedName("requests")
    @Expose
    private String[] requests;

    public String[] getRequests() {
        return requests;
    }
}
