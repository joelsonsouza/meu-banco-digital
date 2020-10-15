package com.nossobancodigital.nossobancodigital.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente")
    private Long idCliente;
    @NotBlank
    private String nome;
    private String sobrenome;
    private String email;
    @Column(name = "data_nascimento", columnDefinition = "datetime")
    private Date dataNascimento;
    private String cpf;
    @Column(name = "tipo_pessoa")
    private String tipoPessoa;
}