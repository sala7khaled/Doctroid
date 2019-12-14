package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medicine {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("medicine_Name")
    @Expose
    private String name;
    @SerializedName("medicine_Price")
    @Expose
    private String price;
    @SerializedName("medicine_Description")
    @Expose
    private String Description;
    @SerializedName("medicine_Quantity")
    @Expose
    private String quantity;


    @Override
    public String toString() {
        return name;
    }

    public Medicine() {

    }

    public Medicine(String name) {
        this.name = name;
    }

    public Medicine(String id, String name, String price, String description, String quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        Description = description;
        this.quantity = quantity;
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
