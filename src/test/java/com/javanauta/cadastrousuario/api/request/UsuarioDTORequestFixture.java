package com.javanauta.cadastrousuario.api.request;

public class UsuarioDTORequestFixture {
    // Método para chamar e construir o DTO que iremos Testar
    public static UsuarioDTORequest build(String nome,
                                          String email,
                                          String documento,
                                          EnderecoDTORequest endereco) {
        return new UsuarioDTORequest(nome, email, documento, endereco);
    }
}
