package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointRequest {

    @SerializedName("c_id")
    @Expose
    private String c_id;

    @SerializedName("t_id")
    @Expose
    private String t_id;

    @SerializedName("p_id")
    @Expose
    private String p_id;

    @SerializedName("req_status")
    @Expose
    private String req_status;

    @SerializedName("req_time")
    @Expose
    private String req_time;

    @SerializedName("req_date")
    @Expose
    private String req_date;

    @SerializedName("req_answers")
    @Expose
    private String[] req_answers;

    @SerializedName("req_test")
    @Expose
    private String req_test;

    public AppointRequest(String c_id, String t_id, String p_id, String req_status, String req_time, String req_date, String[] req_answers, String req_test) {
        this.c_id = c_id;
        this.t_id = t_id;
        this.p_id = p_id;
        this.req_status = req_status;
        this.req_time = req_time;
        this.req_date = req_date;
        this.req_answers = req_answers;
        this.req_test = req_test;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getReq_status() {
        return req_status;
    }

    public void setReq_status(String req_status) {
        this.req_status = req_status;
    }

    public String getReq_time() {
        return req_time;
    }

    public void setReq_time(String req_time) {
        this.req_time = req_time;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    public String[] getReq_answers() {
        return req_answers;
    }

    public void setReq_answers(String[] req_answers) {
        this.req_answers = req_answers;
    }

    public String getReq_test() {
        return req_test;
    }

    public void setReq_test(String req_test) {
        this.req_test = req_test;
    }
}
