package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Contract;

public class MedicalCategory {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("medical_name")
    @Expose
    private String name;
    @SerializedName("medical_image")
    @Expose
    private String image;

    public MedicalCategory() {
    }

    public MedicalCategory(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
