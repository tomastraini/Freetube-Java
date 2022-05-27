package com.freetube.JavaFreetube.Repositories;

import com.freetube.JavaFreetube.Models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepo extends JpaRepository<Usuarios, Long> {
}
