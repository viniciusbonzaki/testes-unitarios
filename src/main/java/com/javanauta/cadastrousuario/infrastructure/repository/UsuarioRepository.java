package com.javanauta.cadastrousuario.infrastructure.repository;

import com.javanauta.cadastrousuario.infrastructure.entities.Usuario;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Encontrar um Usuário pelo Email
    Usuario findByEmail(String email);

    // Deletar um Usuário por Email
    @Transactional
    void deleteByEmail(String email);


}
