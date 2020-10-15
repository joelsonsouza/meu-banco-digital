package com.nossobancodigital.nossobancodigital.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Builder
@Getter
@Setter
public class ClientePropostaDTOout extends RepresentationModel<ClientePropostaDTOout> {

    private String nome;
    private String sobrenome;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    private String cpf;
    private String tipoPessoa;

    private String cep;
    private String rua;
    private String bairro;
    private String complemento;
    private String cidade;
    private String estado;

    private String descricaoCpfFoto;

}
