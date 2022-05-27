package com.freetube.JavaFreetube.Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import com.freetube.JavaFreetube.Models.Usuarios;
import com.freetube.JavaFreetube.Services.Interfaces.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/Users")
public class UsersController
{
    @Autowired
    public IUsersService srv;

    @PostMapping("encrypt")
    public String encryptPass(@RequestParam String pass)
    {
        return srv.encryptPass(pass);
    }

    @PostMapping("decrypt")
    public String decryptPass(@RequestParam String pass)
    {
        return srv.decryptPass(pass);
    }

    @GetMapping
    public byte[] getImage(@RequestParam int id_user) throws IOException, GeneralSecurityException
    {
        return srv.getImage(id_user);
    }

    @PostMapping
    public Usuarios register(@RequestParam("files") MultipartFile file,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String correo,
                             @RequestParam String nombreyapellido,
                             @RequestParam String telefono) throws URISyntaxException, IOException, GeneralSecurityException
    {
        return srv.register(file, username, password, correo,nombreyapellido,telefono);
    }

    @PatchMapping
    public Usuarios changePassword(@RequestParam int id_user,
                                   @RequestParam String password,
                                   @RequestParam String oldpassword)
    {
        return srv.changePassword(id_user, password, oldpassword);
    }

    @PatchMapping("/image")
    public Usuarios changeImage(@RequestParam int id_user,
                                @RequestParam("files") MultipartFile file) throws URISyntaxException, IOException, GeneralSecurityException
    {
        return srv.changeImage(id_user, file);
    }
}
