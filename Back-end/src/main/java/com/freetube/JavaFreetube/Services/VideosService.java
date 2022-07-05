package com.freetube.JavaFreetube.Services;

import com.freetube.JavaFreetube.DTOs.VideosDTO;
import com.freetube.JavaFreetube.Models.*;
import com.freetube.JavaFreetube.Models.DriveModels.DriveAuthGetter;
import com.freetube.JavaFreetube.Models.DriveModels.DriveAuthResponse;
import com.freetube.JavaFreetube.Models.DriveModels.DriveVideos;
import com.freetube.JavaFreetube.Models.DriveModels.Files;
import com.freetube.JavaFreetube.Repositories.CommentsRepo;
import com.freetube.JavaFreetube.Repositories.LikesRepo;
import com.freetube.JavaFreetube.Repositories.UsuariosRepo;
import com.freetube.JavaFreetube.Repositories.VideosRepo;
import com.freetube.JavaFreetube.Repositories.ViewedVideosRepo;
import com.freetube.JavaFreetube.Services.Interfaces.IVideosService;
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

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.google.api.services.drive.model.File;

@Service
public class VideosService implements IVideosService
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
    public VideosRepo repo;

    @Autowired
    public UsuariosRepo usuariosRepo;

    @Autowired
    public LikesRepo likesRepo;

    @Autowired
    public ViewedVideosRepo viewsRepo;

    @Autowired
    public CommentsRepo commentsRepo;

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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<VideosDTO> getallVideos()
    {
        List<VideosDTO> response = new ArrayList<>();
        List<Videos> videos = repo.findAll();
        List<Usuarios> usuarios = usuariosRepo.findAll();
        List<Likes> likes = likesRepo.findAll();
        List<ViewedVideos> views = viewsRepo.findAll();
        for(Videos video : videos)
        {
            try
            {
                Drive drive = new Drive.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

                List<File> result = drive.files().list()
                        .setFields("nextPageToken, files(id, name, kind, mimeType, size)")
                        .execute().getFiles();

                if (result.isEmpty()) {
                    System.out.println("No files found.");
                } else {
                    System.out.println("Files:");
                    for (File file : result) {
                        if(file.getMimeType().equals("video/mp4"))
                        {
                            if(file.getId().equals(video.id_drive))
                            {
                                response.add(new VideosDTO(video.id_video,
                                        video.id_drive,
                                        video.title,
                                        video.description,
                                        usuarios.stream().filter(u -> u.id_usuario == video.id_user)
                                                .findFirst().get().usuario,
                                        likes.stream().filter(l -> l.id_video == video.id_video
                                                             && l.liked)
                                                             .count(),
                                        likes.stream().filter(l -> l.id_video == video.id_video
                                                                && !l.liked)
                                                                .count(),
                                        views.stream().filter(v -> v.id_video == video.id_video)
                                                .count(),
                                        video.fecha_carga,
                                        video.id_user,
                                        file.getSize().toString(),
                                        video.portray_id_drive));
                            }
                        }
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        Collections.sort(response, new Comparator<VideosDTO>() {
            @Override
            public int compare(VideosDTO o1, VideosDTO o2) {
                if(o1.likes - o1.dislikes > o2.likes - o2.dislikes)
                {
                    return -1;
                }
                else if(o1.likes - o1.dislikes < o2.likes - o2.dislikes)
                {
                    return 1;
                }
                else
                {
                    if(o1.views > o2.views)
                    {
                        return -1;
                    }
                    else if(o1.views < o2.views)
                    {
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
            }
        });

        return response;
    }

    @Override
    public VideosDTO getVideoById(Long id)
    {
        VideosDTO response = new VideosDTO();
        Videos video = repo.findAll().stream().filter(x -> x.id_video == id).findFirst().orElse(null);
        System.out.println(video.id_video);
        List<Usuarios> usuarios = usuariosRepo.findAll();
        List<Likes> likes = likesRepo.findAll();
        List<ViewedVideos> views = viewsRepo.findAll();
        try
        {
            Drive drive = new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            List<File> result = drive.files().list()
                    .setFields("nextPageToken, files(id, name, kind, mimeType, size)")
                    .execute().getFiles();

            if (result.isEmpty()) {
                System.out.println("No files found.");
            } else {
                System.out.println("Files:");
                for (File file : result) {
                    if(file.getMimeType().equals("video/mp4"))
                    {
                        if(file.getId().equals(video.id_drive))
                        {
                            response = new VideosDTO(video.id_video,
                                    video.id_drive,
                                    video.title,
                                    video.description,
                                    usuarios.stream().filter(u -> u.id_usuario == video.id_user)
                                            .findFirst().get().usuario,
                                    likes.stream().filter(l -> l.id_video == video.id_video
                                                         && l.liked)
                                                         .count(),
                                    likes.stream().filter(l -> l.id_video == video.id_video
                                                            && !l.liked)
                                                            .count(),
                                    views.stream().filter(v -> v.id_video == video.id_video)
                                            .count(),
                                    video.fecha_carga,
                                    video.id_user,
                                    file.getSize().toString(),
                                    video.portray_id_drive);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        return response;
    }


    @Override
    public DriveVideos getallVideosFromDrive()
            throws URISyntaxException, GeneralSecurityException, IOException
    {
        DriveVideos videos = new DriveVideos();
        videos.files = new ArrayList<>();
        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                .setApplicationName(APPLICATION_NAME)
                .build();

        List<File> result = drive.files().list()
                .setFields("nextPageToken, files(id, name, kind, mimeType)")
                .execute().getFiles();

        if (result.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : result) {
                if(file.getMimeType().equals("video/mp4"))
                {
                    Files video = new Files();
                    video.id = file.getId();
                    video.kind = file.getKind();
                    video.name = file.getName();
                    video.mimeType = file.getMimeType();
                    videos.files.add(video);
                }
            }
        }
        return videos;
    }

    @Override
    public byte[] getPhysicalVideo(String id)
            throws URISyntaxException, IOException, GeneralSecurityException
    {
        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File file = drive.files().get(id).execute();
        java.io.File newFile = new java.io.File(tmpdir + "/" + file.getName());
        OutputStream out = new FileOutputStream(newFile);
        drive.files().get(id).executeMediaAndDownloadTo(out);
        out.close();
        byte[] bytes = java.nio.file.Files.readAllBytes(newFile.toPath());
        newFile.delete();

        return bytes;
    }

    @Override
    public List<Videos> getVideosFromDBbyUser(int id_user)
    {
        List<Videos> videos = repo.findAll()
                .stream().filter(x -> x.id_user == id_user).collect(Collectors.toList());
        List<Videos> videos_to_return = new ArrayList<>();
        for(Videos video : videos)
        {
            try
            {
                Drive drive = new Drive.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

                List<File> result = drive.files().list()
                        .setFields("nextPageToken, files(id, name, kind, mimeType)")
                        .execute().getFiles();

                if (result.isEmpty()) {
                    System.out.println("No files found.");
                } else {
                    System.out.println("Files:");
                    for (File file : result) {
                        if(file.getMimeType().equals("video/mp4"))
                        {
                            if(file.getId().equals(video.id_drive))
                            {
                                videos_to_return.add(video);
                            }
                        }
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return videos_to_return;
    }

    @Override
    public Videos insertVideo(MultipartFile file, String title, String description, int id_user)
            throws URISyntaxException, IOException, GeneralSecurityException, FrameGrabber.Exception {
        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());

        java.io.File newFile = new java.io.File(tmpdir + "/" + file.getOriginalFilename());
        file.transferTo(newFile);

        FileContent mediaContent = new FileContent("video/mp4", newFile);

        File result = drive.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        Java2DFrameConverter converter = new Java2DFrameConverter();
        FFmpegFrameGrabber g = new FFmpegFrameGrabber(newFile);
        g.setFormat("mp4");
        g.start();

        java.io.File thumb = new java.io.File(tmpdir + "/thumb.jpg");

        for(int i = 0; i < 50; i++)
        {
            BufferedImage bufferedImage = converter.getBufferedImage(g.grab());
            if(bufferedImage != null)
                ImageIO.write(bufferedImage, "jpg", thumb);
        }
        
        g.stop();

        FileContent mediaContent2 = new FileContent("image/jpeg", thumb);
        fileMetadata.setName(file.getOriginalFilename() + ".jpg");

        File result2 = drive.files().create(fileMetadata, mediaContent2)
                .setFields("id")
                .execute();

        thumb.delete();
        newFile.delete();

        Calendar cal = Calendar.getInstance();
        Videos video = new Videos();

        video.title = title;
        video.description = description;
        video.id_user = id_user;
        video.fecha_carga = cal.getTime();
        video.id_drive = result.getId();
        video.portray_id_drive = result2.getId();

        repo.save(video);
        return video;
    }

    @Override
    public Videos modifyVideo(Videos video)
    {
        Videos videoToModify = repo.findAll()
                .stream().filter(x -> x.id_drive.equals(video.id_drive)).findFirst().get();
        if(videoToModify.id_user != 0)
        {
            videoToModify.title = video.title;
            videoToModify.description = video.description;

            repo.save(videoToModify);
            return videoToModify;
        }
        return null;
    }

    @Override
    public void deleteVideo(String id)
            throws URISyntaxException, IOException, GeneralSecurityException
    {
        Videos videoToDelete = repo.findAll()
                .stream().filter(x -> x.id_drive.contains(id)).findFirst().orElse(null);
        
        if(videoToDelete == null){ return; }
        List<Comments> commentsRelated = commentsRepo.findAll()
                .stream().filter(x -> x.id_video == videoToDelete.id_video).collect(Collectors.toList());
        List<ViewedVideos> viewsRelated = viewsRepo.findAll()
                .stream().filter(x -> x.id_video == videoToDelete.id_video).collect(Collectors.toList());
        List<Likes> likesRelated = likesRepo.findAll().stream().filter(
                x -> x.id_video == videoToDelete.id_video).collect(Collectors.toList());
        
        

        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                .setApplicationName(APPLICATION_NAME)
                .build();
        for(Comments comment : commentsRelated)
        {
            commentsRepo.delete(comment);
        }
        for(ViewedVideos view : viewsRelated)
        {
            viewsRepo.delete(view);
        }
        for(Likes like : likesRelated)
        {
            likesRepo.delete(like);
        }
        repo.delete(videoToDelete);
        repo.flush();

        if(videoToDelete.id_drive != null)
        {
            List<File> result = drive.files().list()
                    .setFields("nextPageToken, files(id, name, kind, mimeType)")
                    .execute().getFiles();
            
            if (!result.isEmpty()) {
                for (File file : result) {
                    if(file.getId().equals(videoToDelete.id_drive))
                    {
                        drive.files().delete(videoToDelete.id_drive).execute();
                    }
                }
            }
        }
        if(videoToDelete.portray_id_drive != null)
        {
            List<File> result = drive.files().list()
                    .setFields("nextPageToken, files(id, name, kind, mimeType)")
                    .execute().getFiles();
            if (!result.isEmpty()) {
                for (File file : result) {
                    if(file.getId().equals(videoToDelete.portray_id_drive))
                    {
                        drive.files().delete(videoToDelete.portray_id_drive).execute();
                    }
                }
            }
        }


    }

    public int getIfLiked(int id_video, int id_user)
    {
        Likes lik = likesRepo.findAll()
                .stream().filter(x -> x.id_video == id_video && x.id_user == id_user)
                .findFirst().orElseGet(new Supplier<Likes>() {
                    @Override
                    public Likes get() {
                        return new Likes(0,0,0,false);
                    }
                });
        if(lik.id_like == 0){ return 3; }
        if(lik.liked){ return 1; } else{ return 0; }
    }
    public void likeVideo(Likes like)
    {
        Likes lik = likesRepo.findAll()
                .stream()
                .filter(x -> x.id_video == like.id_video && x.id_user == like.id_user)
                .findFirst().orElseGet(new Supplier<Likes>() {
                    @Override
                    public Likes get() {
                        return new Likes(0,like.id_video,like.id_user,like.liked);
                    }
                });
        if(lik.id_like == 0){
            Likes newLike = new Likes();
            newLike.id_user = like.id_user;
            newLike.id_video = like.id_video;
            newLike.liked = like.liked;
            likesRepo.save(lik);
        }
        else
        {
            lik.liked = like.liked;
            likesRepo.save(lik);
        }
    }

    public void deletelike(Likes like)
    {
        Likes lik = likesRepo.findAll()
                .stream().filter(x -> x.id_video == like.id_video && x.id_user == like.id_user).findFirst().get();
        if(lik.id_like != 0){
            likesRepo.delete(lik);
        }
    }

    public void AddViews(int id_video, int id_user)
    {
        ViewedVideos viewedVideos = new ViewedVideos();
        viewedVideos.id_user = id_user;
        viewedVideos.id_video = id_video;
        viewsRepo.save(viewedVideos);
    }

    public List<VideosDTO> ListTopVideos()
    {
        List<VideosDTO> response = new ArrayList<>();
        List<Videos> videos = repo.findAll();
        List<Usuarios> usuarios = usuariosRepo.findAll();
        List<Likes> likes = likesRepo.findAll();
        List<ViewedVideos> views = viewsRepo.findAll();
        for(Videos video : videos)
        {
            try
            {
                Drive drive = new Drive.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

                List<File> result = drive.files().list()
                        .setFields("nextPageToken, files(id, name, kind, mimeType, size)")
                        .execute().getFiles();

                if (result.isEmpty()) {
                    System.out.println("No files found.");
                } else {
                    System.out.println("Files:");
                    for (File file : result) {
                        if(file.getMimeType().equals("video/mp4"))
                        {
                            if(file.getId().equals(video.id_drive))
                            {
                                response.add(new VideosDTO(video.id_video,
                                        video.id_drive,
                                        video.title,
                                        video.description,
                                        usuarios.stream().filter(u -> u.id_usuario == video.id_user)
                                                .findFirst().get().usuario,
                                        likes.stream().filter(l -> l.id_video == video.id_video 
                                                             && l.liked)
                                                             .count(),
                                        likes.stream().filter(l -> l.id_video == video.id_video
                                                                && !l.liked)
                                                                .count(),
                                        views.stream().filter(v -> v.id_video == video.id_video)
                                                .count(),
                                        video.fecha_carga,
                                        video.id_user,
                                        file.getSize().toString(),
                                        video.portray_id_drive));
                            }
                        }
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        Collections.sort(response, new Comparator<VideosDTO>() {
            @Override
            public int compare(VideosDTO o1, VideosDTO o2) {
                if(o1.likes - o1.dislikes > o2.likes - o2.dislikes)
                {
                    return -1;
                }
                else if(o1.likes - o1.dislikes < o2.likes - o2.dislikes)
                {
                    return 1;
                }
                else
                {
                    if(o1.views > o2.views)
                    {
                        return -1;
                    }
                    else if(o1.views < o2.views)
                    {
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
            }
        });
        if(response.size() < 8)
        {
            return response;
        }
        else
        {
            return response.subList(0, 8);
        }
    }
}
