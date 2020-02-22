package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersRequests {

    @SerializedName("req_answers")
    @Expose
    private String[] answers;

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

    public UsersRequests(String[] answers, String id, String status, String time, String date, String title) {
        this.answers = answers;
        this.id = id;
        this.status = status;
        this.time = time;
        this.date = date;
        this.title = title;
    }

    public String[] getAnswers() {
        return answers;
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
}
