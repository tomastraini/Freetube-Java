package com.freetube.JavaFreetube.DTOs;

import java.util.Date;

public class VideosDTO
{
    public int id_video;
    public String id_drive;
    public String title;
    public String description;
    public String usern;
    public long likes;
    public long dislikes;
    public long views;
    public Date fecha_carga;
    public int id_user;
    public String videoWeight;
    public String id_thumbnail;

    public VideosDTO(int id_video, String id_drive,
    String title, String description, String usern,
    long likes, long dislikes,
    long views, Date fecha_carga, int id_user, String videoWeight,
                     String id_thumbnail) {
        this.id_video = id_video;
        this.id_drive = id_drive;
        this.title = title;
        this.description = description;
        this.usern = usern;
        this.likes = likes;
        this.dislikes = dislikes;
        this.views = views;
        this.fecha_carga = fecha_carga;
        this.id_user = id_user;
        this.videoWeight = videoWeight;
        this.id_thumbnail = id_thumbnail;
    }

    public VideosDTO() {
    }

    public String getId_thumbnail() {
        return id_thumbnail;
    }

    public void setId_thumbnail(String id_thumbnail) {
        this.id_thumbnail = id_thumbnail;
    }

    public String getVideoWeight() {
        return videoWeight;
    }

    public void setVideoWeight(String videoWeight) {
        this.videoWeight = videoWeight;
    }

    public int getId_video() {
        return id_video;
    }

    public void setId_video(int id_video) {
        this.id_video = id_video;
    }

    public String getId_drive() {
        return id_drive;
    }

    public void setId_drive(String id_drive) {
        this.id_drive = id_drive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsern() {
        return usern;
    }

    public void setUsern(String usern) {
        this.usern = usern;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public Date getFecha_carga() {
        return fecha_carga;
    }

    public void setFecha_carga(Date fecha_carga) {
        this.fecha_carga = fecha_carga;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
