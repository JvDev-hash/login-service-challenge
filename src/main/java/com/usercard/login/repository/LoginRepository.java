package com.usercard.login.repository;

import com.usercard.login.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(@Param("email") String email);
}
