package network.model;

public class Appoint {

    private String id;
    private String title;
    private String question1_answer;
    private String question2_answer;
    private String question3_answer;
    private String date;
    private String time;
    private String status;
    private String notes;

    public Appoint(String id, String title, String question1_answer, String question2_answer, String question3_answer, String date, String time, String status, String notes) {
        this.id = id;
        this.title = title;
        this.question1_answer = question1_answer;
        this.question2_answer = question2_answer;
        this.question3_answer = question3_answer;
        this.date = date;
        this.time = time;
        this.status = status;
        this.notes = notes;
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

    public String getQuestion1_answer() {
        return question1_answer;
    }

    public void setQuestion1_answer(String question1_answer) {
        this.question1_answer = question1_answer;
    }

    public String getQuestion2_answer() {
        return question2_answer;
    }

    public void setQuestion2_answer(String question2_answer) {
        this.question2_answer = question2_answer;
    }

    public String getQuestion3_answer() {
        return question3_answer;
    }

    public void setQuestion3_answer(String question3_answer) {
        this.question3_answer = question3_answer;
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
}
