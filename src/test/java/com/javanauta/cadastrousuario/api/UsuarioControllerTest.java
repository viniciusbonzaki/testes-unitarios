package com.javanauta.cadastrousuario.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.javanauta.cadastrousuario.api.request.EnderecoDTORequest;
import com.javanauta.cadastrousuario.api.request.EnderecoDTORequestFixture;
import com.javanauta.cadastrousuario.api.request.UsuarioDTORequest;
import com.javanauta.cadastrousuario.api.request.UsuarioDTORequestFixture;
import com.javanauta.cadastrousuario.api.response.EnderecoDTOResponse;
import com.javanauta.cadastrousuario.api.response.EnderecoDTOResponseFixture;
import com.javanauta.cadastrousuario.api.response.UsuarioDTOResponse;
import com.javanauta.cadastrousuario.api.response.UsuarioDTOResponseFixture;
import com.javanauta.cadastrousuario.business.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
    // Injetar a classe que será Testada
    @InjectMocks
    UsuarioController usuarioController;

    // Injetar as outras classes que serão utilizadas
    @Mock
    UsuarioService usuarioService;
    EnderecoDTORequest enderecoDTORequest;
    UsuarioDTORequest usuarioDTORequest;
    EnderecoDTOResponse enderecoDTOResponse;
    UsuarioDTOResponse usuarioDTOResponse;

    // Mock MVC para poder simular as Requisições HTTP nos Endpoints
    private MockMvc mockMvc;

    // ObjectMapper para transformar o DTO em um JSON para Testar as Entradas
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Variáveis Fixas para Testes
    private String url;
    private String json;
    private String email;

    // Preparar um Setup com Dados para Testes
    @BeforeEach
    public void setup() throws JsonProcessingException {
        // Configuração do MockMVC
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).alwaysDo(print()).build();
        // Definir a URL da Controller
        url = "/user";
        // Definir um Email para Testes
        email = "leandrosilva@email.com";

        // Preparar um EnderecoDTORequest para Testes usando a Fixture
        enderecoDTORequest = EnderecoDTORequestFixture.build(
                "Rua das Palmeiras",
                1872L,
                "Jardim das Flores",
                "Casa",
                "São Paulo",
                "17425-000"
        );

        // Preparar um UsuarioDTORequest para Testes usando a Fixture
        usuarioDTORequest = UsuarioDTORequestFixture.build(
                "Leandro da Silva",
                "leandrosilva@email.com",
                "971281",
                enderecoDTORequest
        );

        // Preparar um EnderecoDTOResponse para Testes usando a Fixture
        enderecoDTOResponse = EnderecoDTOResponseFixture.build(
                "Rua das Palmeiras",
                1872L,
                "Jardim das Flores",
                "Casa",
                "São Paulo",
                "17425-000"
        );

        // Preparar um UsuarioDTOResponse para Testes usando a Fixture
        usuarioDTOResponse = UsuarioDTOResponseFixture.build(
                124L,
                "Leandro da Silva",
                "leandrosilva@email.com",
                "971281",
                enderecoDTOResponse
        );

        // Transformar o usuarioDTORequest em JSON com ObjectMapper
        json = objectMapper.writeValueAsString(usuarioDTORequest);
    }

    // Preparar os Casos de Teste
    @Test
    void deveGravarDadosDoUsuarioComSucesso() throws Exception {
        // Chamar o Método para gravar dados do Usuário da usuarioService
        when(usuarioService.gravarUsuarios(usuarioDTORequest)).thenReturn(usuarioDTOResponse);

        // Realizar a requisição do Endpoint com MockMVC passando a URL da Controller e o DTO convertido em JSON
        mockMvc.perform(post(url)
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(json)
        ).andExpect(status().isOk());

        // Verificar os possíveis casos de Teste
        verify(usuarioService).gravarUsuarios(usuarioDTORequest);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveGravarDadosDeUsuarioCasoJsonSejaNull() throws Exception {
        // Realizar a requisição do Endpoint com MockMVC sem passar conteúdo (null)
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        // Verificar os possíveis casos de Teste
        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveAtualizarDadosDoUsuarioComSucesso() throws Exception {
        // Chamar o Método para atualizar dados do Usuário da usuarioService
        when(usuarioService.atualizaCadastro(usuarioDTORequest)).thenReturn(usuarioDTOResponse);

        // Realizar a requisição do Endpoint com MockMVC passando a URL da Controller e o DTO convertido em JSON
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        // Verificar os possíveis casos de Teste
        verify(usuarioService).atualizaCadastro(usuarioDTORequest);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveAtualizarDadosDeUsuarioCasoJsonSejaNull() throws Exception {
        // Realizar a requisição do Endpoint com MockMVC sem passar conteúdo (null)
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        // Verificar os possíveis casos de Teste
        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveBuscarDadosDoUsuarioComSucesso() throws Exception {
        // Chamar o Método para buscar dados do Usuário por Email da usuarioService
        when(usuarioService.buscaDadosUsuario(email)).thenReturn(usuarioDTOResponse);

        // Realizar a requisição do Endpoint com MockMVC passando a URL da Controller e o Email
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("email", email)
        ).andExpect(status().isOk());

        // Verificar os possíveis casos de Teste
        verify(usuarioService).buscaDadosUsuario(email);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveBuscarDadosDoUsuarioCasoParametroSejaNull() throws Exception {
        // Realizar a requisição do Endpoint com MockMVC sem passar o Parâmetro de Email (null)
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        // Verificar os possíveis casos de Teste
        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveDeletarDadosDoUsuarioComSucesso() throws Exception {
        // Chamar o Método para deletar dados do Usuário por Email da usuarioService
        doNothing().when(usuarioService).deletaDadosUsuario(email);

        // Realizar a requisição do Endpoint com MockMVC passando a URL da Controller e o Email
        mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("email", email)
        ).andExpect(status().isAccepted());

        // Verificar os possíveis casos de Teste
        verify(usuarioService).deletaDadosUsuario(email);
        verifyNoMoreInteractions(usuarioService);
    }
}
