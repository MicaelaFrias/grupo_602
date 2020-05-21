package com.example.midiendodistanciasmobile.model;

public class Card {

    private String picture;
    private String titleCard;
    private String descriptionCard;
    private String labelButtonCard;

    public Card(String picture, String titleCard, String descriptionCard, String labelButtonCard) {
        this.picture = picture;
        this.titleCard = titleCard;
        this.descriptionCard = descriptionCard;
        this.labelButtonCard = labelButtonCard;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitleCard() {
        return titleCard;
    }

    public void setTitleCard(String titleCard) {
        this.titleCard = titleCard;
    }

    public String getDescriptionCard() {
        return descriptionCard;
    }

    public void setDescriptionCard(String descriptionCard) {
        this.descriptionCard = descriptionCard;
    }

    public String getLabelButtonCard() {
        return labelButtonCard;
    }

    public void setLabelButtonCard(String labelButtonCard) {
        this.labelButtonCard = labelButtonCard;
    }
}
