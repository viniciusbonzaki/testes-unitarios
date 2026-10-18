package com.javanauta.cadastrousuario.api.converter;

import com.javanauta.cadastrousuario.api.response.UsuarioDTOResponse;
import com.javanauta.cadastrousuario.infrastructure.entities.Usuario;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    // Converter um Usuario em UsuarioDTO
    UsuarioDTOResponse paraUsuarioResponseDTO(Usuario usuario);
}
