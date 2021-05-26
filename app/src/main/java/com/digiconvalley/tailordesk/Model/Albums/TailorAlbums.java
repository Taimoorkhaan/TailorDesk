package com.digiconvalley.tailordesk.Model.Albums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TailorAlbums implements Serializable {
    @SerializedName("albumData")
    @Expose
    private List<AlbumDesignsMain> albumDesignsMains = null;

    public List<AlbumDesignsMain> getAlbumDesignsMains() {
        return albumDesignsMains;
    }

    public void setAlbumDesignsMains(List<AlbumDesignsMain> albumData) {
        this.albumDesignsMains = albumData;
    }
}
