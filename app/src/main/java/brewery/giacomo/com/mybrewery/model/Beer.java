package brewery.giacomo.com.mybrewery.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giacomo on 31/05/2017.
 */

public class Beer {
    @SerializedName("idsdjakSDNKJASDN")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("nameDisplay")
    String nameDisplay;
    @SerializedName("description")
    String description;
    @SerializedName("abv")
    double abv;
    @SerializedName("IBU")
    int IBU;
    @SerializedName("glasswareld")
    int glasswareld;
    @SerializedName("availabled")
    int availabled;
    @SerializedName("styled")
    int styled;
    @SerializedName("isOrganic")
    String isOrganic;
    @SerializedName("labels")
    Lables icon;
    @SerializedName("status")
    String status;
    @SerializedName("createDate")
    String createDate;
    @SerializedName("updateDate")
    String updateDate;
    @SerializedName("available")
    Available available;
    @SerializedName("availableDesc")
    String availableDesc;
    String iconPath;
    String mediumPath;

    public Beer() {}

    public Beer(int id, String name, String nameDisplay, String description, double abv,
                int IBU, int glasswareld, int availabled, int styled, String isOrganic, Lables icon,
                String status, String createDate, String updateDate, Available available, String availableDesc, String iconPaht, String mediumPath) {
        this.id = id;
        this.name = name;
        this.nameDisplay = nameDisplay;
        this.description = description;
        this.abv = abv;
        this.IBU = IBU;
        this.glasswareld = glasswareld;
        this.availabled = availabled;
        this.styled = styled;
        this.isOrganic = isOrganic;
        this.icon = icon;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.available = available;
        this.availableDesc = availableDesc;
        this.iconPath = iconPaht;
        this.mediumPath = mediumPath;
    }

    public Beer(Beer beer) {}

    // setter
    public void setId(int id) {
        this.id= id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameDisplay(String nameDisplay) {
        this.nameDisplay = nameDisplay;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public void setIBU(int IBU) {
        this.IBU = IBU;
    }

    public void setGlasswareld(int glasswareld) {
        this.glasswareld = glasswareld;
    }

    public void setAvailabled(int availabled) {
        this.availabled = availabled;
    }

    public void setStyled(int styled) {
        this.styled = styled;
    }

    public void setIsOrganic(String isOrganic) {
        this.isOrganic = isOrganic;
    }

    public void setIcon(Lables icon) {
        this.icon = icon;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setAvailable(Available available) {
        this.available = available;
    }

    public void setAvailableDesc(String availableDesc) {
        this.availableDesc = availableDesc;
    }
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
    public void setMediumPath(String mediumPath) {
        this.mediumPath = mediumPath;
    }
    // getter
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getNameDisplay() {
        return this.nameDisplay;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getAbv() {
        return this.abv;
    }

    public int getIBU() {
        return this.IBU;
    }

    public int getGlasswareld() {
        return this.glasswareld;
    }

    public int getAvailabled() {
        return this.availabled;
    }

    public int getStyled() {
        return this.styled;
    }

    public String getIsOrganic() {
        return this.isOrganic;
    }

    public Lables getIcon() {
        return this.icon;
    }

    public String getStatus() {
        return this.status;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public String getUpdateDate() {
        return this.updateDate;
    }

    public Available getAvailable() {
        return this.available;
    }

    public String getAvailableDesc() {
        return this.availableDesc;
    }
    public String getIconPath() {
        return this.iconPath;
    }
    public String getMediumPath() {
        return this.mediumPath;
    }
}
