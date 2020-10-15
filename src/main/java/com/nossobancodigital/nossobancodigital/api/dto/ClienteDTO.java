package com.nossobancodigital.nossobancodigital.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class ClienteDTO {

    @NotBlank
    @Size(max = 255)
    private String nome;

    @NotBlank
    @Size(max = 255)
    private String sobrenome;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataNascimento;

    @NotBlank
    @CPF(message="cpf inválido")
    private String cpf;

    @NotBlank
    @Size(max = 14)
    private String tipoPessoa;

}