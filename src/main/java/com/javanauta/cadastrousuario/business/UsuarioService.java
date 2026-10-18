package com.javanauta.cadastrousuario.business;

import com.javanauta.cadastrousuario.api.converter.UsuarioConverter;
import com.javanauta.cadastrousuario.api.converter.UsuarioMapper;
import com.javanauta.cadastrousuario.api.converter.UsuarioUpdateMapper;
import com.javanauta.cadastrousuario.api.request.UsuarioDTORequest;
import com.javanauta.cadastrousuario.api.response.UsuarioDTOResponse;
import com.javanauta.cadastrousuario.infrastructure.entities.Usuario;
import com.javanauta.cadastrousuario.infrastructure.exceptions.BusinessException;
import com.javanauta.cadastrousuario.infrastructure.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final UsuarioUpdateMapper usuarioUpdateMapper;
    private final UsuarioMapper usuarioMapper;


    public Usuario salvaUsuario(Usuario usuario) {
        return usuarioRepository.saveAndFlush(usuario);
    }

    public UsuarioDTOResponse gravarUsuarios(UsuarioDTORequest usuarioDTORequest) {
        try {
            notNull(usuarioDTORequest, "Os dados do usuário são obrigatórios");
            Usuario usuario = salvaUsuario(usuarioConverter.paraUsuarioEntity(usuarioDTORequest));
            return usuarioMapper.paraUsuarioResponseDTO(usuario);
        } catch (Exception e) {
            throw new BusinessException("Erro ao gravar dados de usuário", e);
        }
    }

    public UsuarioDTOResponse atualizaCadastro(UsuarioDTORequest usuarioDTORequest){
        try {
            notNull(usuarioDTORequest, "Os dados do usuário são obrigatórios");
            Usuario usuario = usuarioRepository.findByEmail(usuarioDTORequest.getEmail());
            Usuario entity = usuarioUpdateMapper.updateUsuarioFromDTO(usuarioDTORequest, usuario);
            return usuarioMapper.paraUsuarioResponseDTO(salvaUsuario(entity));
        } catch (Exception e) {
            throw new BusinessException("Erro ao gravar dados de usuário", e);
        }
    }

    public UsuarioDTOResponse buscaDadosUsuario(String email) {
            Usuario entity = usuarioRepository.findByEmail(email);
            return entity != null ? usuarioMapper.paraUsuarioResponseDTO(entity) : null;
    }

    public void deletaDadosUsuario(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}
