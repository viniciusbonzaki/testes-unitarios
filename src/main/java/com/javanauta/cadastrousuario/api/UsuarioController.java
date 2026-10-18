package com.javanauta.cadastrousuario.api;

import com.javanauta.cadastrousuario.api.request.UsuarioDTORequest;
import com.javanauta.cadastrousuario.api.response.UsuarioDTOResponse;
import com.javanauta.cadastrousuario.business.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<UsuarioDTOResponse> gravaDadosUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest) {
        return ResponseEntity.ok(usuarioService.gravarUsuarios(usuarioDTORequest));
    }

    @PutMapping()
    public ResponseEntity<UsuarioDTOResponse> atualizaDadosUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest) {
        return ResponseEntity.ok(usuarioService.atualizaCadastro(usuarioDTORequest));
    }

    @GetMapping()
    public ResponseEntity<UsuarioDTOResponse> buscaUsuarioPorEmail(@RequestParam ("email") String email) {
        return ResponseEntity.ok(usuarioService.buscaDadosUsuario(email));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaDadosUsuario(@RequestParam ("email") String email) {
        usuarioService.deletaDadosUsuario(email);
        return ResponseEntity.accepted().build();
    }
}
