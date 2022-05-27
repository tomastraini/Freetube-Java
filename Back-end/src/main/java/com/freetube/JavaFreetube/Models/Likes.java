package com.freetube.JavaFreetube.Models;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Likes
{
    @Id
    @Column(name="id_like")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id_like;
    public int id_video;
    public int id_user;
    public Boolean liked;

    public Likes(int id_like, int id_video, int id_user, Boolean liked)
    {
        this.id_like = id_like;
        this.id_video = id_video;
        this.id_user = id_user;
        this.liked = liked;
    }

    public Likes()
    {
    }

    public int getId_like() {
        return id_like;
    }

    public void setId_like(int id_like) {
        this.id_like = id_like;
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

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
