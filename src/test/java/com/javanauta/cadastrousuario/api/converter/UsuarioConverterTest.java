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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)  // Permitir o suporte do jUnit com Mockito
public class UsuarioConverterTest {
    // Injetar a classe que será Testada
    @InjectMocks
    UsuarioConverter usuarioConverter;

    // Injetar as outras classes que serão utilizadas
    Endereco endereco;
    Usuario usuario;
    EnderecoDTORequest enderecoDTO;
    UsuarioDTORequest usuarioDTO;

    // Dependencia do Clock para não dar problema nos Testes
    @Mock
    Clock clock;
    LocalDateTime dataHoraFixa;

    // Preparar um Setup com Dados para Testes
    @BeforeEach
    public void setup() {
        // Definir uma Data e Hora fixa para não dar problemas nos Testes
        dataHoraFixa = LocalDateTime.of(2026, 10, 18, 14, 12, 15);

        // Configurações do Clock para não dar Erro nos Testes
        ZoneId zoneId = ZoneId.systemDefault();
        Clock fixedClock = Clock.fixed(dataHoraFixa.atZone(zoneId).toInstant(), zoneId);
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

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
                         .nome("Leandro da Silva")
                         .email("leandrosilva@email.com")
                         .documento("971281")
                         .dataCadastro(dataHoraFixa)
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
                "Leandro da Silva",
                "leandrosilva@email.com",
                "971281",
                enderecoDTO
        );
    }

    // Preparar os Casos de Teste
    @Test
    void deveConverterParaUsuarioEntity() {
        Usuario usuarioEntity = usuarioConverter.paraUsuarioEntity(usuarioDTO);
        assertEquals(usuario, usuarioEntity);
    }
}
