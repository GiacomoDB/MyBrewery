package brewery.giacomo.com.mybrewery.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Giacomo on 31/05/2017.
 */

public class MainRequest {
    @SerializedName("currentPage")
    int currentPage;
    @SerializedName("numberOfPages")
    int numberOfPages;
    @SerializedName("totalResult")
    int totalResult;
    @SerializedName("data")
    private ArrayList<Beer> beers;

    public MainRequest() {}

    public MainRequest(int currentPage, int numberOfPages, int totalResult, ArrayList<Beer> beers) {
        this.currentPage = currentPage;
        this.numberOfPages = numberOfPages;
        this.totalResult = totalResult;
        this.beers = beers;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public void setBeers(ArrayList<Beer> beers) {
        this.beers = beers;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getNumberOfPages() {
        return this.numberOfPages;
    }

    public int getTotalResult() {
        return this.totalResult;
    }

    public ArrayList<Beer> getBeers() {
        return this.beers;
    }


}
