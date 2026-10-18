package com.javanauta.cadastrousuario.api.request;

public class EnderecoDTORequestFixture {
    // Método para chamar e construir o DTO que iremos Testar
    public static EnderecoDTORequest build(String rua,
                                           Long numero,
                                           String bairro,
                                           String complemento,
                                           String cidade,
                                           String cep) {
        return new EnderecoDTORequest(rua, numero, bairro, complemento, cidade, cep);
    }
}
