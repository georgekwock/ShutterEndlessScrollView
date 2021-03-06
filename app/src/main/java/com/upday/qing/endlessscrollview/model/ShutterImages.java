package com.upday.qing.endlessscrollview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Qing Guo
 * @Date 2018/7/10
 * @Description Shutter Image Data Model
 */
public class ShutterImages {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("search_id")
    @Expose
    private String searchId;
    @SerializedName("data")
    @Expose
    private List<DataDetail> data = new ArrayList<>();

    /**
     * @return The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return The perPage
     */
    public Integer getPerPage() {
        return perPage;
    }

    /**
     * @param perPage The per_page
     */
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    /**
     * @return The totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount The total_count
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return The searchId
     */
    public String getSearchId() {
        return searchId;
    }

    /**
     * @param searchId The search_id
     */
    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    /**
     * @return The data
     */
    public List<DataDetail> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<DataDetail> data) {
        this.data = data;
    }

}
