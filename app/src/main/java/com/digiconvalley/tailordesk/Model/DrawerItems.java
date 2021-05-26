package com.digiconvalley.tailordesk.Model;

public class DrawerItems {
    private String itemName;
    private Integer itemImage;

    public DrawerItems(String itemName, Integer itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemImage() {
        return itemImage;
    }

    public void setItemImage(Integer itemImage) {
        this.itemImage = itemImage;
    }
}
