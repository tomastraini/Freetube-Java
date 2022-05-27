package com.freetube.JavaFreetube.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="videos")
public class Videos
{
    @Id
    @Column(name="id_video")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id_video;
    public String title;
    public String description;
    public int id_user;
    public Date fecha_carga;
    public String id_drive;

    public Videos(int id_video, String paths, String title, String description, int id_user, Date fecha_carga, String id_drive) {
        this.id_video = id_video;
        this.title = title;
        this.description = description;
        this.id_user = id_user;
        this.fecha_carga = fecha_carga;
        this.id_drive = id_drive;
    }

    public Videos() {
    }

    public String getId_drive() {
        return id_drive;
    }

    public void setId_drive(String id_drive) {
        this.id_drive = id_drive;
    }

    public int getId_video() {
        return id_video;
    }

    public void setId_video(int id_video) {
        this.id_video = id_video;
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getFecha_carga() {
        return fecha_carga;
    }

    public void setFecha_carga(Date fecha_carga) {
        this.fecha_carga = fecha_carga;
    }
}
