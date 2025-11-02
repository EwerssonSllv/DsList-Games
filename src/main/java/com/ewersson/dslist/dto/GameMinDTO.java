package com.ewersson.dslist.dto;

import com.ewersson.dslist.entities.Game;

public class GameMinDTO {
    private Long id;
    private String title;
    private Integer year;
    private String cover;
    private String shortDescription;

    public GameMinDTO(){}

    public GameMinDTO(Game entity) {
        id = entity.getId();
        title = entity.getTitle();
        year = entity.getYear();
        cover = entity.getCover();
        shortDescription = entity.getShortDescription();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public String getCover() {
        return cover;
    }

    public String getShortDescription() {
        return shortDescription;
    }
}
