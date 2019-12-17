package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmSignUpForm {

    @SerializedName("p_id")
    @Expose
    private String p_id;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("confirm")
    @Expose
    private String confirm;
    @SerializedName("snn")
    @Expose
    private String snn;
    @SerializedName("medicines")
    @Expose
    private String[] medicines;

    public ConfirmSignUpForm(){

    }

    public ConfirmSignUpForm(String p_id, String location, String date, String confirm, String snn, String[] medicines) {
        this.p_id = p_id;
        this.location = location;
        this.date = date;
        this.confirm = confirm;
        this.snn = snn;
        this.medicines = medicines;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getSnn() {
        return snn;
    }

    public void setSnn(String snn) {
        this.snn = snn;
    }

    public String[] getMedicines() {
        return medicines;
    }

    public void setMedicines(String[] medicines) {
        this.medicines = medicines;
    }
}
