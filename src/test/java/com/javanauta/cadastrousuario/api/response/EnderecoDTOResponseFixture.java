package com.javanauta.cadastrousuario.api.response;

public class EnderecoDTOResponseFixture {
    // Método para chamar e construir o DTO que iremos Testar
    public static EnderecoDTOResponse build(String rua,
                                            Long numero,
                                            String bairro,
                                            String complemento,
                                            String cidade,
                                            String cep) {
        return new EnderecoDTOResponse(rua, numero, bairro, complemento, cidade, cep);
    }
}
