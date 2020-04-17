package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MedicalAnalysis implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("test_title")
    @Expose
    private String title;
    @SerializedName("test_period")
    @Expose
    private String period;
    @SerializedName("test_price")
    @Expose
    private String price;
    @SerializedName("test_description")
    @Expose
    private String description;
    @SerializedName("test_precautions_en")
    @Expose
    private String precautions_en;
    @SerializedName("test_precautions_ar")
    @Expose
    private String precautions_ar;

    public MedicalAnalysis(String id, String title, String period, String price, String description, String precautions_en, String precautions_ar) {
        this.id = id;
        this.title = title;
        this.period = period;
        this.price = price;
        this.description = description;
        this.precautions_en = precautions_en;
        this.precautions_ar = precautions_ar;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrecautions_en() {
        return precautions_en;
    }

    public void setPrecautions_en(String precautions_en) {
        this.precautions_en = precautions_en;
    }

    public String getPrecautions_ar() {
        return precautions_ar;
    }

    public void setPrecautions_ar(String precautions_ar) {
        this.precautions_ar = precautions_ar;
    }
}
