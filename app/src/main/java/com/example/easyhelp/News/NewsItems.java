package com.example.easyhelp.News;

public class NewsItems
{
     private int imageID;
     private String headline;
     private String description;

    public NewsItems(int imageID, String headline, String description)
    {
        this.imageID = imageID;
        this.headline = headline;
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
