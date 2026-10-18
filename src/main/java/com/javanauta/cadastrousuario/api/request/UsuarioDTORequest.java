package com.javanauta.cadastrousuario.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UsuarioDTORequest {
    private String nome;
    @JsonProperty(required = true)
    private String email;
    private String documento;
    private EnderecoDTORequest endereco;
}
