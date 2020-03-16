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

    @SerializedName("req_notes")
    @Expose
    private String notes;

    public UsersRequests(String[] answers, String id, String status, String time, String date, String title, String notes) {
        this.answers = answers;
        this.id = id;
        this.status = status;
        this.time = time;
        this.date = date;
        this.title = title;
        this.notes = notes;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
