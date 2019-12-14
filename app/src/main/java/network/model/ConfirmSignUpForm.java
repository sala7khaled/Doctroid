package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmSignUpForm {

    @SerializedName("snn")
    @Expose
    private String snn;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("confirm")
    @Expose
    private String confirm;

    public ConfirmSignUpForm(){

    }

    public ConfirmSignUpForm(String snn, String date, String location, String confirm) {
        this.snn = snn;
        this.date = date;
        this.location = location;
        this.confirm = confirm;
    }

    public String getSnn() {
        return snn;
    }

    public void setSnn(String snn) {
        this.snn = snn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
