package com.javanauta.cadastrousuario.api.converter;

import com.javanauta.cadastrousuario.api.request.EnderecoDTORequest;
import com.javanauta.cadastrousuario.api.request.UsuarioDTORequest;
import com.javanauta.cadastrousuario.infrastructure.entities.Endereco;
import com.javanauta.cadastrousuario.infrastructure.entities.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UsuarioConverter {

    private final Clock clock;

    // Converter um UsuarioDTO em Usuario
    public Usuario paraUsuarioEntity(UsuarioDTORequest usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .documento(usuarioDTO.getDocumento())
                .email(usuarioDTO.getEmail())
                .dataCadastro(LocalDateTime.now(clock))
                .endereco(paraEnderecoEntity(usuarioDTO.getEndereco()))
                .build();

    }

    // Converter um EnderecoDTO em Endereco
    private Endereco paraEnderecoEntity(EnderecoDTORequest enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .bairro(enderecoDTO.getBairro())
                .cep(enderecoDTO.getCep())
                .cidade(enderecoDTO.getCidade())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .build();
    }
}
