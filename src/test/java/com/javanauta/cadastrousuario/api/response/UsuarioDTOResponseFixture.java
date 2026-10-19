package com.javanauta.cadastrousuario.api.response;

public class UsuarioDTOResponseFixture {
    // Método para chamar e construir o DTO que iremos Testar
    public static UsuarioDTOResponse build(Long id,
                                           String nome,
                                           String email,
                                           String documento,
                                           EnderecoDTOResponse endereco) {
        return new UsuarioDTOResponse(id, nome, email, documento, endereco);
    }
}
