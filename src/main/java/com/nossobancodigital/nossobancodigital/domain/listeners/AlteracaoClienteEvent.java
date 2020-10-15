package com.nossobancodigital.nossobancodigital.domain.listeners;

import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlteracaoClienteEvent {
    private Cliente cliente;
}
