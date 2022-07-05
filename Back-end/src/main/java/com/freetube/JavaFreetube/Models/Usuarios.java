package com.freetube.JavaFreetube.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class Usuarios {
    @Id
    @Column(name="id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id_usuario;
    @Column(name="usern")
    public String usuario;
    @Column(name="passwordu")
    public String pass;
    @Column(name="id_rol")
    public int permiso;

    public String imagepath;
    public String correo;
    public String nombreyapellido;
    public String telefono;
    public boolean hasdefaultimg;
    public String description;
    public Date fechaCreation;
    public Usuarios() {
    }

    public Usuarios(int id_usuario, String usuario,
                    String pass, int permiso, String imagepath, String correo,
                    String nombreyapellido, String telefono, boolean hasdefaultimg,
                    String description, Date fechaCreation)
    {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.pass = pass;
        this.permiso = permiso;
        this.imagepath = imagepath;
        this.correo = correo;
        this.nombreyapellido = nombreyapellido;
        this.telefono = telefono;
        this.hasdefaultimg = hasdefaultimg;
        this.description = description;
        this.fechaCreation = fechaCreation;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPermiso() {
        return permiso;
    }

    public void setPermiso(int permiso) {
        this.permiso = permiso;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreyapellido() {
        return nombreyapellido;
    }

    public void setNombreyapellido(String nombreyapellido) {
        this.nombreyapellido = nombreyapellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isHasdefaultimg() {
        return hasdefaultimg;
    }

    public void setHasdefaultimg(boolean hasdefaultimg) {
        this.hasdefaultimg = hasdefaultimg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFechaCreation() {
        return fechaCreation;
    }

    public void setFechaCreation(Date fechaCreation) {
        this.fechaCreation = fechaCreation;
    }
}
