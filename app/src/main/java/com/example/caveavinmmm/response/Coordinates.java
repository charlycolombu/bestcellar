package com.example.caveavinmmm.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinates {

    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("xmax")
    @Expose
    private Integer xmax;
    @SerializedName("xmin")
    @Expose
    private Integer xmin;
    @SerializedName("ymax")
    @Expose
    private Integer ymax;
    @SerializedName("ymin")
    @Expose
    private Integer ymin;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getXmax() {
        return xmax;
    }

    public void setXmax(Integer xmax) {
        this.xmax = xmax;
    }

    public Integer getXmin() {
        return xmin;
    }

    public void setXmin(Integer xmin) {
        this.xmin = xmin;
    }

    public Integer getYmax() {
        return ymax;
    }

    public void setYmax(Integer ymax) {
        this.ymax = ymax;
    }

    public Integer getYmin() {
        return ymin;
    }

    public void setYmin(Integer ymin) {
        this.ymin = ymin;
    }

}
