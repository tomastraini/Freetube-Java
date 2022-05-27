package com.freetube.JavaFreetube.Models;


import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles
{
    @Id
    @Column(name="id_rol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id_rol;
    public String nombre_rol;

    public Roles(int id_rol, String nombre_rol)
    {
        this.id_rol = id_rol;
        this.nombre_rol = nombre_rol;
    }

    public Roles() {
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }
}
