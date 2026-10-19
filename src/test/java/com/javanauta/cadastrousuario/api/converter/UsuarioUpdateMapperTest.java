package com.javanauta.cadastrousuario.api.converter;

import com.javanauta.cadastrousuario.api.request.EnderecoDTORequest;
import com.javanauta.cadastrousuario.api.request.EnderecoDTORequestFixture;
import com.javanauta.cadastrousuario.api.request.UsuarioDTORequest;
import com.javanauta.cadastrousuario.api.request.UsuarioDTORequestFixture;
import com.javanauta.cadastrousuario.infrastructure.entities.Endereco;
import com.javanauta.cadastrousuario.infrastructure.entities.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mapstruct.factory.Mappers;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioUpdateMapperTest {
    // Injetar a classe que será Testada
    UsuarioUpdateMapper usuarioUpdateMapper;

    // Injetar as outras classes que serão utilizadas
    Endereco endereco;
    Usuario usuario;
    EnderecoDTORequest enderecoDTO;
    UsuarioDTORequest usuarioDTO;

    // Variável de Data e Hora apenas para Testes
    LocalDateTime dataHora;

    // Classe para poder Simular uma alteração de Dados (PUT)
    Usuario usuarioEsperado;

    // Preparar um Setup com Dados para Testes
    @BeforeEach
    public void setup() {
        // Utilizar a classe de Teste que foi injetada - Exclusivo para Test com MapStruct
        usuarioUpdateMapper = Mappers.getMapper(UsuarioUpdateMapper.class);

        // Preparar um Endereço para Testes
        endereco = Endereco.builder()
                .rua("Rua das Palmeiras")
                .numero(1872L)
                .bairro("Jardim das Flores")
                .complemento("Casa")
                .cidade("São Paulo")
                .cep("17425-000")
                .build();

        // Preparar um Usuario para Testes
        usuario = Usuario.builder()
                .id(124L)
                .nome("Leandro da Silva")
                .email("leandrosilva@email.com")
                .documento("971281")
                .dataCadastro(dataHora)
                .endereco(endereco)
                .build();

        // Preparar um EnderecoDTORequest para Testes usando a Fixture
        enderecoDTO = EnderecoDTORequestFixture.build(
                "Rua das Palmeiras",
                1872L,
                "Jardim das Flores",
                "Casa",
                "São Paulo",
                "17425-000"
        );

        // Preparar um UsuarioDTORequest para Testes usando a Fixture
        usuarioDTO = UsuarioDTORequestFixture.build(
                "Leandro Teste",
                null,  // Como o Email está como "null", deve trazer do Original
                "9712812",
                enderecoDTO
        );

        // Preparar um Usuário esperado após a alteração
        usuarioEsperado = Usuario.builder()
                                 .id(124L)
                                 .nome("Leandro Teste")
                                 .email("leandrosilva@email.com")
                                 .documento("9712812")
                                 .dataCadastro(dataHora)
                                 .endereco(endereco)
                                 .build();
    }

    // Preparar os Casos de Teste
    @Test
    void deveAtualizarDadosDoUsuario() {
        Usuario usuarioEntity = usuarioUpdateMapper.updateUsuarioFromDTO(usuarioDTO, usuario);
        assertEquals(usuarioEsperado, usuarioEntity);
    }
}
