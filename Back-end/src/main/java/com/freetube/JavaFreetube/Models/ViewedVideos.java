package com.freetube.JavaFreetube.Models;

import javax.persistence.*;

@Entity
@Table(name = "viewedVideos")
public class ViewedVideos
{
    @Id
    @Column(name="id_view")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id_view;
    public int id_video;
    public int id_user;

    public ViewedVideos(int id_view, int id_video, int id_user)
    {
        this.id_view = id_view;
        this.id_video = id_video;
        this.id_user = id_user;
    }

    public ViewedVideos()
    {
    }

    public int getId_view() {
        return id_view;
    }

    public void setId_view(int id_view) {
        this.id_view = id_view;
    }

    public int getId_video() {
        return id_video;
    }

    public void setId_video(int id_video) {
        this.id_video = id_video;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
