package com.freetube.JavaFreetube.Repositories;

import com.freetube.JavaFreetube.Models.Videos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosRepo extends JpaRepository<Videos, Long> {
}
