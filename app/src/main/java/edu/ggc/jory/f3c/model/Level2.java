
package edu.ggc.jory.f3c.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Level2 {

    @SerializedName("paid")
    @Expose
    private Paid paid;

    public Paid getPaid() {
        return paid;
    }

    public void setPaid(Paid paid) {
        this.paid = paid;
    }

}
