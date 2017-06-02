package brewery.giacomo.com.mybrewery.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giacomo on 31/05/2017.
 */

public class Lables {

    @SerializedName("icon")
    String icon;
    @SerializedName("medium")
    String medium;
    String path;
    String pathMed;
    public Lables(String icon, String medium, String path, String pathMed) {
        this.icon = icon;
        this.medium = medium;
        this.path = path;
        this.pathMed = pathMed;
    }

    public Lables(Lables icon) {}

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setMedium(String medium) {
        this.medium= medium;
    }

    public void setPath(String path){
        this.path = path;
    }

    public void setPathMed(String pathMed){
        this.pathMed = pathMed;
    }

    public String getIcon(){
        return this.icon;
    }

    public String getMedium(){
        return this.medium;
    }

    public String getPath(){
        return this.path;
    }
    public String getPathMed(){
        return this.pathMed;
    }
}
