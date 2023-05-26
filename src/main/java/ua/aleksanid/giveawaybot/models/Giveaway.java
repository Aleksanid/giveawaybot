package ua.aleksanid.giveawaybot.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.crypto.Data;
import java.util.Date;

public class Giveaway {
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private int id;
    private String title;
    private String worth;
    private String thumbnail;
    private String image;
    private String description;
    private String instructions;
    private String openGiveawayUrl;
    private String publishedDate;
    private String type;
    private String platforms;
    private String endDate;
    private int users;
    private String status;
    private String gamerpowerUrl;
    private String openGiveaway;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getOpenGiveawayUrl() {
        return openGiveawayUrl;
    }

    public void setOpenGiveawayUrl(String openGiveawayUrl) {
        this.openGiveawayUrl = openGiveawayUrl;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGamerpowerUrl() {
        return gamerpowerUrl;
    }

    public void setGamerpowerUrl(String gamerpowerUrl) {
        this.gamerpowerUrl = gamerpowerUrl;
    }

    public String getOpenGiveaway() {
        return openGiveaway;
    }

    public void setOpenGiveaway(String openGiveaway) {
        this.openGiveaway = openGiveaway;
    }
}

