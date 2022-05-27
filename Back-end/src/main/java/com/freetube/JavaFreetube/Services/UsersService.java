package com.freetube.JavaFreetube.Services;

import com.freetube.JavaFreetube.Models.DriveModels.DriveAuthGetter;
import com.freetube.JavaFreetube.Models.DriveModels.DriveAuthResponse;
import com.freetube.JavaFreetube.Repositories.RolesRepo;
import com.freetube.JavaFreetube.Repositories.UsuariosRepo;
import com.freetube.JavaFreetube.Models.Usuarios;
import com.freetube.JavaFreetube.Services.Interfaces.IUsersService;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import com.google.api.services.drive.model.File;

@Service
public class UsersService implements IUsersService
{
    
    @Value("${auth-url}")
    String urlToAuth;

    @Value("${token-uri}")
    public String token_uri;

    @Value("${refresh-token}")
    public String refresh_token;

    @Value("${client-id}")
    public String clientid;

    @Value("${client-secret}")
    public String clientsecret;

    @Autowired
    public UsuariosRepo repo;

    @Autowired
    public RolesRepo rolesRepo;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    static String path = System.getProperty("user.dir");
    static String tmpdir = System.getProperty("java.io.tmpdir");
    private static final String CREDENTIALS_FILE_PATH = path + "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        return credential;
    }

    public String driveAuthenticate() throws URISyntaxException
    {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_JSON);

        URI tokenUri = new URI(this.urlToAuth);

        DriveAuthGetter obj = new DriveAuthGetter(token_uri, refresh_token, clientid, clientsecret);

        HttpEntity<DriveAuthGetter> httptokenEntity = new HttpEntity<>(obj, tokenHeaders);

        RestTemplate restTemplate = new RestTemplate();
        DriveAuthResponse token = restTemplate.postForObject(tokenUri, httptokenEntity, DriveAuthResponse.class);
        return token.access_token;
    }

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Value("${encrypt-secret}")
    public String secret;
    @Override
    public String encryptPass(String pass) {
        Base64.Encoder urlEncoder = java.util.Base64.getUrlEncoder().withoutPadding();
        byte[] string = pass.getBytes();
        String encoded = urlEncoder.encodeToString(string);
        return encoded;
    }

    @Override
    public String decryptPass(String pass) {
        Base64.Decoder urlDecoder = java.util.Base64.getUrlDecoder();
        byte[] decoded = urlDecoder.decode(pass);
        String decodedString = new String(decoded, StandardCharsets.UTF_8);
        return decodedString;
    }

    @Override
    public Usuarios register(MultipartFile file, String username, String password, String correo, String nombreyapellido, String telefono)
        throws URISyntaxException, IOException, GeneralSecurityException
    {
        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                .setApplicationName(APPLICATION_NAME)
                .build();
        Usuarios user = new Usuarios();
        user.imagepath = "1wiwkVMRL1QaZojACRlGYbURjnqroQ9Wn";
        if (!file.isEmpty()) {
            File fileMetadata = new File();
            fileMetadata.setName(file.getOriginalFilename());

            java.io.File newFile = new java.io.File(tmpdir + "/" + file.getOriginalFilename());
            file.transferTo(newFile);

            FileContent mediaContent = new FileContent("image/jpeg", newFile);

            File result = drive.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            newFile.delete();
            user.setImagepath(result.getId());
        }
        

        user.setUsuario(username);
        user.setPass(hash(password));
        user.setPermiso(2);
        user.setCorreo(correo);
        user.setNombreyapellido(nombreyapellido);
        user.setTelefono(telefono);

        repo.save(user);

        return user;
    }

    @Override
    public Usuarios changePassword(int id_user, String password, String oldpassword)
    {
        Usuarios user = repo.findAll()
                .stream()
                .filter(u -> u.id_usuario == id_user)
                .findFirst().orElse(null);
        if(BCrypt.checkpw(oldpassword, user.pass) && user != null)
        {
            user.setPass(hash(password));
            repo.save(user);
            return user;
        }
        return null;
    }

    @Override
    public Usuarios changeImage(int id_user, MultipartFile file) throws URISyntaxException, IOException, GeneralSecurityException
    {
        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());

        java.io.File newFile = new java.io.File(tmpdir + "/" + file.getOriginalFilename());
        file.transferTo(newFile);

        FileContent mediaContent = new FileContent("image/jpeg", newFile);

        File result = drive.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        newFile.delete();

        Usuarios user = repo.findAll().stream().filter(u -> u.getId_usuario() == id_user).findFirst().get();
        if(user.imagepath != "1wiwkVMRL1QaZojACRlGYbURjnqroQ9Wn")
        {
            drive.files().delete(user.imagepath).execute();
        }

        user.setImagepath(result.getId());
        repo.save(user);
        return user;
    }

    @Override
    public byte[] getImage(int id_user) throws IOException, GeneralSecurityException
    {
        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File file = drive.files()
            .get(repo.findAll().stream().filter(u -> u.getId_usuario() == id_user).findFirst().get().imagepath).execute();
        java.io.File newFile = new java.io.File(tmpdir + "/" + file.getName());
        OutputStream out = new FileOutputStream(newFile);
        drive.files().get(file.getId()).executeMediaAndDownloadTo(out);
        out.close();
        byte[] bytes = java.nio.file.Files.readAllBytes(newFile.toPath());
        newFile.delete();
        return bytes;
    }
}
