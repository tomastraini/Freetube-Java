package com.freetube.JavaFreetube.Repositories;

import com.freetube.JavaFreetube.Models.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepo extends JpaRepository<Likes, Long> {
}
