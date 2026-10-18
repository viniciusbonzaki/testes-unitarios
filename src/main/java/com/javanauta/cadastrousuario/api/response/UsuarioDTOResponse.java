package com.javanauta.cadastrousuario.api.response;

public record UsuarioDTOResponse(Long id,
                                 String nome,
                                 String email,
                                 String documento,
                                 EnderecoDTOResponse endereco) {
}
