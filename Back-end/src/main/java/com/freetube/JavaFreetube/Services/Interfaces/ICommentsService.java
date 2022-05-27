package com.freetube.JavaFreetube.Services.Interfaces;

import com.freetube.JavaFreetube.DTOs.CommentsDTO;
import com.freetube.JavaFreetube.Models.Comments;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ICommentsService
{
    public List<CommentsDTO> getCommentsbyVideo(int id_video);
    public Comments insertComment(Comments com);
    public Comments modifyComment(Comments com);
    public Comments deleteComment(int id);
}
