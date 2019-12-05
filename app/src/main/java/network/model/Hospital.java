package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hospital {

    @SerializedName("_id")
    @Expose
    private String hospital_id;
    @SerializedName("hospital_name")
    @Expose
    private String hospital_name;
    @SerializedName("hospital_location")
    @Expose
    private String hospital_location;
    @SerializedName("hospital_phone")
    @Expose
    private String hospital_phone;
    @SerializedName("hospital_website")
    @Expose
    private String hospital_website;
    @SerializedName("hospital_facebook")
    @Expose
    private String hospital_facebook;
    @SerializedName("hospital_email")
    @Expose
    private String hospital_email;
    @SerializedName("hospital_generalManager")
    @Expose
    private String hospital_generalManager;
    @SerializedName("hospital_adminstratonManager")
    @Expose
    private String hospital_adminstratonManager;
    @SerializedName("hospital_itManager")
    @Expose
    private String hospital_itManager;
    @SerializedName("hospital_MarketingManager")
    @Expose
    private String hospital_MarketingManager;
    @SerializedName("hospital_PurchasingManager")
    @Expose
    private String hospital_PurchasingManager;

    public Hospital() {

    }

    public Hospital(String hospital_id, String hospital_name, String hospital_location, String hospital_phone, String hospital_website, String hospital_facebook, String hospital_email, String hospital_generalManager, String hospital_adminstratonManager, String hospital_itManager, String hospital_MarketingManage, String hospital_PurchasingManager) {
        this.hospital_id = hospital_id;
        this.hospital_name = hospital_name;
        this.hospital_location = hospital_location;
        this.hospital_phone = hospital_phone;
        this.hospital_website = hospital_website;
        this.hospital_facebook = hospital_facebook;
        this.hospital_email = hospital_email;
        this.hospital_generalManager = hospital_generalManager;
        this.hospital_adminstratonManager = hospital_adminstratonManager;
        this.hospital_itManager = hospital_itManager;
        this.hospital_MarketingManager = hospital_MarketingManage;
        this.hospital_PurchasingManager = hospital_PurchasingManager;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_location() {
        return hospital_location;
    }

    public void setHospital_location(String hospital_location) {
        this.hospital_location = hospital_location;
    }

    public String getHospital_phone() {
        return hospital_phone;
    }

    public void setHospital_phone(String hospital_phone) {
        this.hospital_phone = hospital_phone;
    }

    public String getHospital_website() {
        return hospital_website;
    }

    public void setHospital_website(String hospital_website) {
        this.hospital_website = hospital_website;
    }

    public String getHospital_facebook() {
        return hospital_facebook;
    }

    public void setHospital_facebook(String hospital_facebook) {
        this.hospital_facebook = hospital_facebook;
    }

    public String getHospital_email() {
        return hospital_email;
    }

    public void setHospital_email(String hospital_email) {
        this.hospital_email = hospital_email;
    }

    public String getHospital_generalManager() {
        return hospital_generalManager;
    }

    public void setHospital_generalManager(String hospital_generalManager) {
        this.hospital_generalManager = hospital_generalManager;
    }

    public String getHospital_adminstratonManager() {
        return hospital_adminstratonManager;
    }

    public void setHospital_adminstratonManager(String hospital_adminstratonManager) {
        this.hospital_adminstratonManager = hospital_adminstratonManager;
    }

    public String getHospital_itManager() {
        return hospital_itManager;
    }

    public void setHospital_itManager(String hospital_itManager) {
        this.hospital_itManager = hospital_itManager;
    }

    public String getHospital_MarketingManager() {
        return hospital_MarketingManager;
    }

    public void setHospital_MarketingManage(String hospital_MarketingManage) {
        this.hospital_MarketingManager = hospital_MarketingManage;
    }

    public String getHospital_PurchasingManager() {
        return hospital_PurchasingManager;
    }

    public void setHospital_PurchasingManager(String hospital_PurchasingManager) {
        this.hospital_PurchasingManager = hospital_PurchasingManager;
    }
}
