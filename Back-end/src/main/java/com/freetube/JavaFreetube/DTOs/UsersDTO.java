package com.freetube.JavaFreetube.DTOs;

public class UsersDTO
{
    public int id_user;
    public String usern;
    public String passwordu;
    public String rol;

    public UsersDTO(int id_user, String usern, String passwordu, String rol) {
        this.id_user = id_user;
        this.usern = usern;
        this.passwordu = passwordu;
        this.rol = rol;
    }

    public UsersDTO() {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsern() {
        return usern;
    }

    public void setUsern(String usern) {
        this.usern = usern;
    }

    public String getPasswordu() {
        return passwordu;
    }

    public void setPasswordu(String passwordu) {
        this.passwordu = passwordu;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
