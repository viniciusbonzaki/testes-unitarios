package com.javanauta.cadastrousuario.business;

import com.javanauta.cadastrousuario.api.converter.UsuarioConverter;
import com.javanauta.cadastrousuario.api.converter.UsuarioMapper;
import com.javanauta.cadastrousuario.api.converter.UsuarioUpdateMapper;
import com.javanauta.cadastrousuario.api.request.EnderecoDTORequest;
import com.javanauta.cadastrousuario.api.request.EnderecoDTORequestFixture;
import com.javanauta.cadastrousuario.api.request.UsuarioDTORequest;
import com.javanauta.cadastrousuario.api.request.UsuarioDTORequestFixture;
import com.javanauta.cadastrousuario.api.response.EnderecoDTOResponse;
import com.javanauta.cadastrousuario.api.response.EnderecoDTOResponseFixture;
import com.javanauta.cadastrousuario.api.response.UsuarioDTOResponse;
import com.javanauta.cadastrousuario.api.response.UsuarioDTOResponseFixture;
import com.javanauta.cadastrousuario.infrastructure.entities.Endereco;
import com.javanauta.cadastrousuario.infrastructure.entities.Usuario;
import com.javanauta.cadastrousuario.infrastructure.exceptions.BusinessException;
import com.javanauta.cadastrousuario.infrastructure.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    // Injetar a classe que será Testada
    @InjectMocks
    private UsuarioService usuarioService;

    // Injetar as outras classes que serão utilizadas
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioConverter usuarioConverter;
    @Mock
    private UsuarioUpdateMapper usuarioUpdateMapper;
    @Mock
    private UsuarioMapper usuarioMapper;

    // Injetar as classes para criar um Setup de Dados
    Endereco endereco;
    Usuario usuario;
    EnderecoDTORequest enderecoDTORequest;
    UsuarioDTORequest usuarioDTORequest;
    EnderecoDTOResponse enderecoDTOResponse;
    UsuarioDTOResponse usuarioDTOResponse;

    // Criar variáveis Fixas para Testes
    LocalDateTime dataHoraFixa;
    String email;

    // Preparar um Setup com Dados para Testes
    @BeforeEach
    public void setup() {
        // Definir uma Data e Hora fixa para não dar problemas nos Testes
        dataHoraFixa = LocalDateTime.of(2026, 10, 18, 14, 12, 15);
        // Definir um Email para Testes
        email = "leandrosilva@email.com";

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
                .dataCadastro(dataHoraFixa)
                .endereco(endereco)
                .build();

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
    }

    // Preparar os Casos de Teste
    @Test
    void deveSalvarUsuarioComSucesso() {
        // Dado que o usuarioRepository.saveAndFlush() é chamado - GIVEN
        when(usuarioRepository.saveAndFlush(usuario)).thenReturn(usuario);

        // Quando chamar o usuarioService.salvaUsuario() - WHEN
        Usuario usuarioEntity = usuarioService.salvaUsuario(usuario);

        // Então o Sistema deve salvar o Usuário - THEN
        assertEquals(usuario, usuarioEntity);

        // Verificar os possíveis casos de Teste
        verify(usuarioRepository).saveAndFlush(usuario);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveGravarUsuarioComSucesso() {
        // Dado que esses Métodos foram chamados para Gravar um Usuário - GIVEN
        when(usuarioConverter.paraUsuarioEntity(usuarioDTORequest)).thenReturn(usuario);
        when(usuarioRepository.saveAndFlush(usuario)).thenReturn(usuario);
        when(usuarioMapper.paraUsuarioResponseDTO(usuario)).thenReturn(usuarioDTOResponse);

        // Quando chamar o usuarioService.gravarUsuarios() - WHEN
        UsuarioDTOResponse usuarioDTO = usuarioService.gravarUsuarios(usuarioDTORequest);

        // Então o Sistema deve gravar o Usuário - THEN
        assertEquals(usuarioDTOResponse, usuarioDTO);

        // Verificar os possíveis casos de Teste
        verify(usuarioConverter).paraUsuarioEntity(usuarioDTORequest);
        verify(usuarioRepository).saveAndFlush(usuario);
        verify(usuarioMapper).paraUsuarioResponseDTO(usuario);
        verifyNoMoreInteractions(usuarioConverter, usuarioRepository, usuarioMapper);
    }

    @Test
    void naoDeveGravarUsuarioCasoUsuarioDTORequestForNull() {
        // Verificar uma BusinessException será lançada quando UsuarioDTORequest for Null
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> usuarioService.gravarUsuarios(null)
        );

        // Verificar os possíveis casos de Teste
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), is("Erro ao gravar dados de usuário"));
        assertThat(exception.getCause(), notNullValue());
        assertThat(exception.getCause().getMessage(), is("Os dados do usuário são obrigatórios"));
        verifyNoInteractions(usuarioConverter, usuarioRepository, usuarioMapper);
    }

    @Test
    void deveGerarExcecaoCasoOcorraErroAoGravarUsuario() {
        // Gerar uma RuntimeException se ocorrer algum Erro na hora de gravar um Usuário
        when(usuarioConverter.paraUsuarioEntity(usuarioDTORequest)).thenReturn(usuario);
        when(usuarioRepository.saveAndFlush(usuario)).thenThrow(
                new RuntimeException("Falha ao gravar os dados do Usuário")
        );

        // Verificar se a Exceção será lançada na hora de gravar um Usuário
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> usuarioService.gravarUsuarios(usuarioDTORequest)
        );

        // Verificar os possíveis casos de Teste
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), is("Erro ao gravar dados de usuário"));
        assertThat(exception.getCause().getClass(), is(RuntimeException.class));
        assertThat(exception.getCause().getMessage(), is("Falha ao gravar os dados do Usuário"));
        verify(usuarioConverter).paraUsuarioEntity(usuarioDTORequest);
        verify(usuarioRepository).saveAndFlush(usuario);
        verifyNoInteractions(usuarioMapper);
        verifyNoMoreInteractions(usuarioConverter, usuarioRepository);
    }

    @Test
    void deveAtualizarDadosDoUsuarioComSucesso() {
        // Dado que esses Métodos foram chamados para Atualizar um Usuário - GIVEN
        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);
        when(usuarioUpdateMapper.updateUsuarioFromDTO(usuarioDTORequest, usuario)).thenReturn(usuario);
        when(usuarioRepository.saveAndFlush(usuario)).thenReturn(usuario);
        when(usuarioMapper.paraUsuarioResponseDTO(usuario)).thenReturn(usuarioDTOResponse);

        // Quando chamar o usuarioService.atualizaCadastro() - WHEN
        UsuarioDTOResponse usuarioDTO = usuarioService.atualizaCadastro(usuarioDTORequest);

        // Então o Sistema deve alterar os daods do Usuário - THEN
        assertEquals(usuarioDTOResponse, usuarioDTO);

        // Verificar os possíveis casos de Teste
        verify(usuarioRepository).findByEmail(email);
        verify(usuarioUpdateMapper).updateUsuarioFromDTO(usuarioDTORequest, usuario);
        verify(usuarioRepository).saveAndFlush(usuario);
        verify(usuarioMapper).paraUsuarioResponseDTO(usuario);
        verifyNoMoreInteractions(usuarioRepository, usuarioUpdateMapper, usuarioMapper);
    }

    @Test
    void naoDeveAtualizarUsuarioCasoUsuarioDTORequestForNull() {
        // Verificar uma BusinessException será lançada quando UsuarioDTORequest for Null
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> usuarioService.atualizaCadastro(null)
        );

        // Verificar os possíveis casos de Teste
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), is("Erro ao atualizar dados de usuário"));
        assertThat(exception.getCause(), notNullValue());
        assertThat(exception.getCause().getMessage(), is("Os dados do usuário são obrigatórios"));
        verifyNoInteractions(usuarioConverter, usuarioRepository, usuarioMapper);
    }

    @Test
    void deveGeraExcecaoCasoOcorraErroAoBuscarUsuarioPorEmail() {
        // Gerar uma RuntimeException se ocorrer algum Erro na hora de buscar um Usuário por Email
        when(usuarioRepository.findByEmail(email)).thenThrow(
                new RuntimeException("Falha ao buscar dados do Usuário")
        );

        // Verificar se a Exceção será lançada na hora de buscar um Usuário por Email
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> usuarioService.atualizaCadastro(usuarioDTORequest)
        );

        // Verificar os possíveis casos de Teste
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), is("Erro ao atualizar dados de usuário"));
        assertThat(exception.getCause().getClass(), is(RuntimeException.class));
        assertThat(exception.getCause().getMessage(), is("Falha ao buscar dados do Usuário"));
        verify(usuarioRepository).findByEmail(email);
        verifyNoInteractions(usuarioUpdateMapper, usuarioMapper);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveBuscarDadosDoUsuarioComSucesso() {
        // Dado que esses Métodos foram chamados para Gravar um Usuário - GIVEN
        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);
        when(usuarioMapper.paraUsuarioResponseDTO(usuario)).thenReturn(usuarioDTOResponse);

        // Quando chamar o usuarioService.buscaDadosUsuario() - WHEN
        UsuarioDTOResponse usuarioDTO = usuarioService.buscaDadosUsuario(email);

        // Então o Sistema deve buscar o Usuário - THEN
        assertEquals(usuarioDTOResponse, usuarioDTO);

        // Verificar os possíveis casos de Teste
        verify(usuarioRepository).findByEmail(email);
        verify(usuarioMapper).paraUsuarioResponseDTO(usuario);
    }

    @Test
    void deveRetornarNullCasoUsuarioNaoEncontrado() {
        // Trazer Null caso o não haja um Usuário com o Email que foi passado
        when(usuarioRepository.findByEmail(email)).thenReturn(null);

        // Deve trazer o DTO como Null
        UsuarioDTOResponse usuarioDTO = usuarioService.buscaDadosUsuario(email);

        // Verificar se o DTO está como Null se o Email não foi encontrado
        assertEquals(null, usuarioDTO);

        // Verificar os possíveis casos de Teste
        verify(usuarioRepository).findByEmail(email);
        verifyNoInteractions(usuarioMapper);
    }

    @Test
    void deveDeletarDadosDoUsuarioComSucesso() {
        // Dado que esses Método foi chamado para Deletar um Usuário - GIVEN
        doNothing().when(usuarioRepository).deleteByEmail(email);

        // Quando chamar o usuarioService.deletaDadosUsuario() - WHEN
        usuarioService.deletaDadosUsuario(email);

        // Então o Sistema deve deletar o Usuário - THEN
        verify(usuarioRepository).deleteByEmail(email);
    }
}
