package brewery.giacomo.com.mybrewery.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giacomo on 31/05/2017.
 */

public class Available {

    @SerializedName("description")
    String description;

    public Available(String description) {
        this.description = description;
    }

    public Available(Available ava) {}

    public void setdescription(String description) {
        this.description= description;
    }

    public String getDescription(){ return this.description;}}
