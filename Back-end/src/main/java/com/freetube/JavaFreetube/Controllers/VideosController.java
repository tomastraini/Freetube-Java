package com.freetube.JavaFreetube.Controllers;

import com.freetube.JavaFreetube.DTOs.VideosDTO;
import com.freetube.JavaFreetube.Models.DriveModels.DriveVideos;
import com.freetube.JavaFreetube.Models.Likes;
import com.freetube.JavaFreetube.Models.Videos;
import com.freetube.JavaFreetube.Services.Interfaces.IVideosService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Videos")
public class VideosController
{
    public IVideosService srv;

    public VideosController(IVideosService srv)
    {
        this.srv = srv;
    }

    @GetMapping
    public List<VideosDTO> getallVideos()
    {
        return srv.getallVideos();
    }

    @GetMapping("/{id}")
    public VideosDTO getVideoById(@PathVariable @NotNull Long id)
    {
        return srv.getVideoById(id);
    }

    @GetMapping("driveFolder")
    public DriveVideos getVideosFromDrive()
            throws URISyntaxException, GeneralSecurityException, IOException
    {
        return srv.getallVideosFromDrive();
    }

    @GetMapping("physical")
    public byte[] getPhysicalVideo(@RequestParam String id)
            throws URISyntaxException, IOException, GeneralSecurityException
    {
        return srv.getPhysicalVideo(id);
    }

    @GetMapping("user")
    public List<Videos> getVideosFromDBbyUser(@RequestParam int id_user)
    {
        return srv.getVideosFromDBbyUser(id_user);
    }

    @PostMapping
    public Videos insertVideo(@RequestParam("files")MultipartFile file,
                              @RequestParam String title,
                              @RequestParam String description,
                              @RequestParam int id_user)
            throws URISyntaxException, IOException, GeneralSecurityException {
        return srv.insertVideo(file,title,description,id_user);
    }

    @PutMapping
    public Videos modifyVideo(@RequestBody Videos video)
    {
        return srv.modifyVideo(video);
    }

    @DeleteMapping
    public void deleteVideo(@RequestParam String id)
            throws URISyntaxException, IOException, GeneralSecurityException
    {
        srv.deleteVideo(id);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("getIfLiked")
    public int getIfLiked(@RequestParam int id_video, @RequestParam int id_user)
    {
        return srv.getIfLiked(id_video, id_user);
    }

    @PostMapping("like")
    public void likeVideo(@RequestBody Likes like)
    {
        srv.likeVideo(like);
    }

    @DeleteMapping("like")
    public void deletelike(@RequestBody Likes like)
    {
        srv.deletelike(like);
    }

    @PostMapping("views")
    public void AddViews(int id_video, int id_user)
    {
        srv.AddViews(id_video, id_user);
    }

    @GetMapping("top")
    public List<VideosDTO> ListTopVideos()
    {
        return srv.ListTopVideos();
    }
}
