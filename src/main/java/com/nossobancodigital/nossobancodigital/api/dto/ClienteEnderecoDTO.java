package com.nossobancodigital.nossobancodigital.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteEnderecoDTO extends RepresentationModel<ClienteEnderecoDTO> {

    @Valid
    @NotNull
    private ClienteDTO clienteDTO;

    @NotBlank
    @Size(max = 9, min = 9)
    private String cep;

    @NotBlank
    @Size(max = 255)
    private String rua;

    @NotBlank
    @Size(max = 255)
    private String bairro;

    @NotBlank
    @Size(max = 255)
    private String complemento;

    @NotBlank
    @Size(max = 255)
    private String cidade;

    @NotBlank
    @Size(max = 100)
    private String estado;
}
