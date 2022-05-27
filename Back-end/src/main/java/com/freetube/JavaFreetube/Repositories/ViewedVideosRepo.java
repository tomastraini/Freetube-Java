package com.freetube.JavaFreetube.Repositories;

import com.freetube.JavaFreetube.Models.ViewedVideos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewedVideosRepo extends JpaRepository<ViewedVideos, Long> {
}
