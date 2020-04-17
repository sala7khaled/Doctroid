package network.model;

public class Appoint {

    private String id;
    private String title;
    private String comment;
    private String date;
    private String time;
    private String status;
    private String notes;
    private String pre_ar;
    private String pre_en;

    public Appoint(String id, String title, String comment, String date, String time, String status, String notes, String pre_en, String pre_ar) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.date = date;
        this.time = time;
        this.status = status;
        this.notes = notes;
        this.pre_en = pre_en;
        this.pre_ar = pre_ar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPre_ar() {
        return pre_ar;
    }

    public void setPre_ar(String pre_ar) {
        this.pre_ar = pre_ar;
    }

    public String getPre_en() {
        return pre_en;
    }

    public void setPre_en(String pre_en) {
        this.pre_en = pre_en;
    }
}
