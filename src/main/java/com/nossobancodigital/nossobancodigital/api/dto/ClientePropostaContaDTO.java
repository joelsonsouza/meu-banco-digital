package com.nossobancodigital.nossobancodigital.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nossobancodigital.nossobancodigital.domain.model.AbrirConta;
import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
public class ClientePropostaContaDTO {
    private ClienteDTO clienteDTO;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataProposta;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataAceitacaoProposta;
    @Enumerated(EnumType.STRING)
    private AbrirConta status;

}
