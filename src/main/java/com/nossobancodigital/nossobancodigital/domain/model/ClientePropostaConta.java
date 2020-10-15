package com.nossobancodigital.nossobancodigital.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class ClientePropostaConta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPropostaAberturaConta;
    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;
    @Column(columnDefinition = "datetime")
    private Date dataProposta;
    @Column(columnDefinition = "datetime")
    private Date dataAceitacaoProposta;
    @Enumerated(EnumType.STRING)
    private AbrirConta status;

    public void confirmar() {
        setStatus(AbrirConta.ACEITAR);
        setDataProposta(new Date());
        setDataAceitacaoProposta(new Date());
    }

    public void recusar() {
        setStatus(AbrirConta.RECUSAR);
        setDataProposta(new Date());
    }
}
