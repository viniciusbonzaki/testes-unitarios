package com.javanauta.cadastrousuario.api.converter;

import com.javanauta.cadastrousuario.api.response.EnderecoDTOResponse;
import com.javanauta.cadastrousuario.api.response.EnderecoDTOResponseFixture;
import com.javanauta.cadastrousuario.api.response.UsuarioDTOResponse;
import com.javanauta.cadastrousuario.api.response.UsuarioDTOResponseFixture;
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
public class UsuarioMapperTest {
    // Injetar a classe que será Testada
    UsuarioMapper usuarioMapper;

    // Injetar as outras classes que serão utilizadas
    Endereco endereco;
    Usuario usuario;
    EnderecoDTOResponse enderecoDTO;
    UsuarioDTOResponse usuarioDTO;

    // Variável de Data e Hora apenas para Testes
    LocalDateTime dataHora;

    // Preparar um Setup com Dados para Testes
    @BeforeEach
    public void setup() {
        // Utilizar a classe de Teste que foi injetada - Exclusivo para Test com MapStruct
        usuarioMapper = Mappers.getMapper(UsuarioMapper.class);

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

        // Preparar um EnderecoDTOResponse para Testes usando a Fixture
        enderecoDTO = EnderecoDTOResponseFixture.build(
                "Rua das Palmeiras",
                1872L,
                "Jardim das Flores",
                "Casa",
                "São Paulo",
                "17425-000"
        );

        // Preparar um UsuarioDTOResponse para Testes usando a Fixture
        usuarioDTO = UsuarioDTOResponseFixture.build(
                124L,
                "Leandro da Silva",
                "leandrosilva@email.com",
                "971281",
                enderecoDTO
        );
    }

    // Preparar os Casos de Teste
    @Test
    void deveConverterParaUsuarioDTOResponse() {
        UsuarioDTOResponse usuarioDTOResponse = usuarioMapper.paraUsuarioResponseDTO(usuario);
        assertEquals(usuarioDTO, usuarioDTOResponse);
    }
}
