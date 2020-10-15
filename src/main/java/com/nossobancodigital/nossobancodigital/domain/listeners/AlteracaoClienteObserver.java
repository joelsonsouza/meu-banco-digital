package com.nossobancodigital.nossobancodigital.domain.listeners;

import com.nossobancodigital.nossobancodigital.domain.model.Cliente;

public interface AlteracaoClienteObserver {

    public boolean alterouCliente(Cliente oldCliente, Cliente newCliente);
    public void salvarCliente(AlteracaoClienteEvent event);

}
