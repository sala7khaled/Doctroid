package network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Precautions {

    @SerializedName("english")
    @Expose
    private String english;

    @SerializedName("arabic")
    @Expose
    private String arabic;

    public Precautions(String english, String arabic) {
        this.english = english;
        this.arabic = arabic;
    }

    public String getEnglish() {
        return english;
    }

    public String getArabic() {
        return arabic;
    }
}
