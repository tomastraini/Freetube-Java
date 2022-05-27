package com.freetube.JavaFreetube.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comments
{
    @Id
    @Column(name="id_comment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id_comment;
    public String comment;
    public int id_user;
    public int id_video;
    public Date fecha_carga;

    public Comments(int id_comment, String comment, int id_user, int id_video, Date fecha_carga)
    {
        this.id_comment = id_comment;
        this.comment = comment;
        this.id_user = id_user;
        this.id_video = id_video;
        this.fecha_carga = fecha_carga;
    }

    public Comments() {
    }

    public int getId_comment() {
        return id_comment;
    }

    public void setId_comment(int id_comment) {
        this.id_comment = id_comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_video() {
        return id_video;
    }

    public void setId_video(int id_video) {
        this.id_video = id_video;
    }

    public Date getFecha_carga() {
        return fecha_carga;
    }

    public void setFecha_carga(Date fecha_carga) {
        this.fecha_carga = fecha_carga;
    }
}
