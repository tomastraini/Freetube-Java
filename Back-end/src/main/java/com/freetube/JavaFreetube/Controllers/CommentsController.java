package com.freetube.JavaFreetube.Controllers;

import com.freetube.JavaFreetube.DTOs.CommentsDTO;
import com.freetube.JavaFreetube.Models.Comments;
import com.freetube.JavaFreetube.Services.Interfaces.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Comments")
public class CommentsController
{
    @Autowired
    public ICommentsService srv;

    @GetMapping
    public List<CommentsDTO> getCommentsbyVideo(@RequestParam int id_video)
    {
        return srv.getCommentsbyVideo(id_video);
    }

    @PostMapping
    public Comments insertComment(@RequestBody Comments com)
    {
        return srv.insertComment(com);
    }

    @PutMapping
    public Comments modifyComment(@RequestBody Comments com)
    {
        return srv.modifyComment(com);
    }

    @DeleteMapping
    public Comments deleteComment(@RequestParam int id)
    {
        return srv.deleteComment(id);
    }
}
