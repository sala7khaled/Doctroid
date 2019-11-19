package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medicine {

    @SerializedName("med_id")
    @Expose
    private String med_id;
    @SerializedName("medicineName")
    @Expose
    private String name;
    @SerializedName("medicinePrice")
    @Expose
    private String price;
    @SerializedName("medicineDescription")
    @Expose
    private String Description;
    @SerializedName("medicineQuantity")
    @Expose
    private String quantity;

    public Medicine() {

    }

    public Medicine(String med_id, String name, String price, String description, String quantity) {
        this.med_id = med_id;
        this.name = name;
        this.price = price;
        Description = description;
        this.quantity = quantity;
    }

    public String getMed_id() {
        return med_id;
    }

    public void setMed_id(String med_id) {
        this.med_id = med_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
