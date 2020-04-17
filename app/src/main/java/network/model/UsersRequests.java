package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersRequests {
    
    @SerializedName("req_comment")
    @Expose
    private String comment;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("req_status")
    @Expose
    private String status;

    @SerializedName("req_time")
    @Expose
    private String time;

    @SerializedName("req_date")
    @Expose
    private String date;

    @SerializedName("req_test")
    @Expose
    private String title;

    @SerializedName("req_notes")
    @Expose
    private String notes;

    @SerializedName("c_id")
    @Expose
    private String c_id;

    @SerializedName("t_id")
    @Expose
    private String t_id;

    public UsersRequests(String comment, String id, String status, String time, String date, String title, String notes, String c_id, String t_id) {
        this.comment = comment;
        this.id = id;
        this.status = status;
        this.time = time;
        this.date = date;
        this.title = title;
        this.notes = notes;
        this.c_id = c_id;
        this.t_id = t_id;
    }

    public String getComment() {
        return comment;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public String getC_id() {
        return c_id;
    }
    public String getT_id() {
        return t_id;
    }
}
