package com.freetube.JavaFreetube.Services.Interfaces;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import com.freetube.JavaFreetube.Models.Usuarios;

import org.springframework.web.multipart.MultipartFile;

public interface IUsersService
{
    public String encryptPass(String pass);
    public String decryptPass(String pass);
    public Usuarios register(MultipartFile file, String username, String password, String correo, String nombreyapellido, String telefono) throws URISyntaxException, IOException, GeneralSecurityException;
    public Usuarios changePassword(int id_user, String password, String oldpassword);
    public Usuarios changeImage(int id_user, MultipartFile file) throws URISyntaxException, IOException, GeneralSecurityException;
    public byte[] getImage(int id_user) throws IOException, GeneralSecurityException;

}
