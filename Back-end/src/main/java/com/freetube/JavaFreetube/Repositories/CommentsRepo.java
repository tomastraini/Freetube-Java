package com.freetube.JavaFreetube.Repositories;

import com.freetube.JavaFreetube.Models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepo extends JpaRepository<Comments, Long> {
}
