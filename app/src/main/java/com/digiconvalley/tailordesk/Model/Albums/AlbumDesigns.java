package com.digiconvalley.tailordesk.Model.Albums;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlbumDesigns implements Serializable {
    @SerializedName("albumDesignId")
    @Expose
    private Integer albumDesignId;
    @SerializedName("albumPic")
    @Expose
    private String albumPic;
    @SerializedName("date")
    @Expose
    private String date;
    private Boolean isNew=false;
    private Uri designImage;
    private String designImagee;
    private String designImageName;

    public AlbumDesigns(Boolean isNew) {
        this.isNew = isNew;
    }

    public Integer getAlbumDesignId() {
        return albumDesignId;
    }

    public void setAlbumDesignId(Integer albumDesignId) {
        this.albumDesignId = albumDesignId;
    }

    public String getAlbumPic() {
        return albumPic;
    }

    public void setAlbumPic(String albumPic) {
        this.albumPic = albumPic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Uri getDesignImage() {
        return designImage;
    }

    public void setDesignImage(Uri designImage) {
        this.designImage = designImage;
    }

    public String getDesignImagee() {
        return designImagee;
    }

    public void setDesignImagee(String designImagee) {
        this.designImagee = designImagee;
    }

    public String getDesignImageName() {
        return designImageName;
    }

    public void setDesignImageName(String designImageName) {
        this.designImageName = designImageName;
    }
}
