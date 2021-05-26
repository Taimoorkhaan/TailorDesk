package com.digiconvalley.tailordesk.Model.Albums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AlbumDesignsMain implements Serializable {
    @SerializedName("albumDirectoryId")
    @Expose
    private Integer albumDirectoryId;
    @SerializedName("albumId")
    @Expose
    private Integer albumId;
    @SerializedName("albumName")
    @Expose
    private String albumName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("albumDesign")
    @Expose
    private List<AlbumDesigns> albumDesign = null;

    public Integer getAlbumDirectoryId() {
        return albumDirectoryId;
    }

    public void setAlbumDirectoryId(Integer albumDirectoryId) {
        this.albumDirectoryId = albumDirectoryId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<AlbumDesigns> getAlbumDesign() {
        return albumDesign;
    }

    public void setAlbumDesign(List<AlbumDesigns> albumDesign) {
        this.albumDesign = albumDesign;
    }
}
