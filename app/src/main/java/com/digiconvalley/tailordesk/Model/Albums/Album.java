package com.digiconvalley.tailordesk.Model.Albums;

import android.net.Uri;

public class Album {

    private Uri albumImage;
    private String albumImageName;
    private String albumImageDecoded;

    public Album(Uri albumImage, String albumImageName) {
        this.albumImage = albumImage;
        this.albumImageName = albumImageName;
    }

    public Album() {
    }

    public Uri getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(Uri albumImage) {
        this.albumImage = albumImage;
    }

    public String getAlbumImageName() {
        return albumImageName;
    }

    public void setAlbumImageName(String albumImageName) {
        this.albumImageName = albumImageName;
    }

    public String getAlbumImageDecoded() {
        return albumImageDecoded;
    }

    public void setAlbumImageDecoded(String albumImageDecoded) {
        this.albumImageDecoded = albumImageDecoded;
    }
}
