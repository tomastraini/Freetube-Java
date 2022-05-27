package com.freetube.JavaFreetube.Services;

import com.freetube.JavaFreetube.DTOs.CommentsDTO;
import com.freetube.JavaFreetube.Models.Comments;
import com.freetube.JavaFreetube.Models.Usuarios;
import com.freetube.JavaFreetube.Repositories.CommentsRepo;
import com.freetube.JavaFreetube.Repositories.UsuariosRepo;
import com.freetube.JavaFreetube.Services.Interfaces.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class CommentsService implements ICommentsService
{
    @Autowired
    public CommentsRepo repo;
    @Autowired
    public UsuariosRepo usersRepo;
    @Override
    public List<CommentsDTO> getCommentsbyVideo(int id_video) {
        Long id = Long.parseLong(String.valueOf(id_video));
        List<Comments> comments = repo.findAll()
                .stream().filter(x -> x.id_video == id_video)
                .collect(Collectors.toList());
        List<Usuarios> usuarios = usersRepo.findAll();
        List<CommentsDTO> response = new ArrayList<>();
        comments.forEach(c ->{
            response.add(new CommentsDTO(
                    c.id_comment,
                    c.comment,
                    usuarios.stream().filter(u -> u.id_usuario == c.id_user).findFirst().orElseGet(new Supplier<Usuarios>() {
                        @Override
                        public Usuarios get() {
                            return new Usuarios(0,"","",0,"","","","");
                        }
                    }).usuario,
                    c.fecha_carga
            ));
        });

        return response;
    }

    @Override
    public Comments insertComment(Comments com)
    {
        com.id_comment = 0;
        Calendar cal = Calendar.getInstance();
        com.fecha_carga = cal.getTime();
        repo.save(com);
        return com;
    }

    @Override
    public Comments modifyComment(Comments com)
    {
        if(com.id_comment == 0){ return null; }
        Long id = Long.parseLong(String.valueOf(com.id_comment));
        Comments comToChange = repo.findById(id).get();
        if(comToChange.id_comment != 0)
        {
            repo.save(comToChange);
            return comToChange;
        }
        return null;
    }

    public Comments deleteComment(int id)
    {
        if(id != 0)
        {
            Long realid = Long.parseLong(String.valueOf(id));
            Comments com = repo.findById(realid).get();
            if(com.id_comment == 0){ return null; }
            repo.delete(com);
            return com;
        }
        return null;
    }
}
