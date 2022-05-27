package com.freetube.JavaFreetube.Services.Interfaces;

import com.freetube.JavaFreetube.DTOs.VideosDTO;
import com.freetube.JavaFreetube.Models.DriveModels.DriveVideos;
import com.freetube.JavaFreetube.Models.Likes;
import com.freetube.JavaFreetube.Models.Videos;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface IVideosService
{
    public List<VideosDTO> getallVideos();
    public VideosDTO getVideoById(Long id);
    public DriveVideos getallVideosFromDrive()
            throws URISyntaxException, GeneralSecurityException, IOException;
    public byte[] getPhysicalVideo(String id)
            throws URISyntaxException, IOException, GeneralSecurityException;
    public List<Videos> getVideosFromDBbyUser(int id_user);
    Videos insertVideo(MultipartFile file, String title, String description, int id_user)
            throws URISyntaxException, IOException, GeneralSecurityException;
    public Videos modifyVideo(Videos video);
    public void deleteVideo(String id)
            throws URISyntaxException, IOException, GeneralSecurityException;

    //////////////////////////////////////////////////////////////////////////////
    public int getIfLiked(int id_video, int id_user);
    public void likeVideo(Likes like);
    public void deletelike(Likes like);
    public void AddViews(int id_video, int id_user);
    public List<VideosDTO> ListTopVideos();

}
