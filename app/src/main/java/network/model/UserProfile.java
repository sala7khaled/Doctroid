package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("confirm")
    @Expose
    private String confirm;
    @SerializedName("medicines")
    @Expose
    private String[] medicines;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private boolean password;
    @SerializedName("phone")
    @Expose
    private boolean phone;
    @SerializedName("gender")
    @Expose
    private boolean gender;
    @SerializedName("date")
    @Expose
    private boolean date;
    @SerializedName("location")
    @Expose
    private boolean location;
    @SerializedName("snn")
    @Expose
    private boolean snn;

    public UserProfile() {

    }

    public UserProfile(String confirm, String[] medicines, String _id, String firstName, String lastName, String email, boolean password, boolean phone, boolean gender, boolean date, boolean location, boolean snn) {
        this.confirm = confirm;
        this.medicines = medicines;
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.date = date;
        this.location = location;
        this.snn = snn;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String[] getMedicines() {
        return medicines;
    }

    public void setMedicines(String[] medicines) {
        this.medicines = medicines;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPassword() {
        return password;
    }

    public void setPassword(boolean password) {
        this.password = password;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isDate() {
        return date;
    }

    public void setDate(boolean date) {
        this.date = date;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    public boolean isSnn() {
        return snn;
    }

    public void setSnn(boolean snn) {
        this.snn = snn;
    }
}
