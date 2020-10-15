package com.nossobancodigital.nossobancodigital.domain.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cliente_cpf_foto")
public class ClienteCpfFoto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "idcliente")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Cliente cliente;

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

}