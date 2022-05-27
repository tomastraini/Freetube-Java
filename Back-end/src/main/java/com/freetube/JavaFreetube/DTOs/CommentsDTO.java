package com.freetube.JavaFreetube.DTOs;

import java.util.Date;

public class CommentsDTO
{
    public int id_comment;
    public String comment;
    public String usern;
    public Date fecha_carga;

    public CommentsDTO(int id_comment, String comment, String usern, Date fecha_carga) {
        this.id_comment = id_comment;
        this.comment = comment;
        this.usern = usern;
        this.fecha_carga = fecha_carga;
    }

    public CommentsDTO() {
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

    public String getUsern() {
        return usern;
    }

    public void setUsern(String usern) {
        this.usern = usern;
    }

    public Date getFecha_carga() {
        return fecha_carga;
    }

    public void setFecha_carga(Date fecha_carga) {
        this.fecha_carga = fecha_carga;
    }
}
