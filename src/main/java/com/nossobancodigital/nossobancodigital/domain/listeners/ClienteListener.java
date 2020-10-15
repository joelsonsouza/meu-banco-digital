package com.nossobancodigital.nossobancodigital.domain.listeners;

import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import com.nossobancodigital.nossobancodigital.domain.service.CadastroClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteListener implements AlteracaoClienteObserver {

    @Autowired
    private CadastroClienteService cadastroClienteService;
    private List<AlteracaoClienteObserver> observers = new ArrayList<AlteracaoClienteObserver>();

    public void addObserver(AlteracaoClienteObserver observer) {
        this.observers.add(observer);
    }

    public void run(Cliente oldCliente, Cliente newCliente) {
        if (alterouCliente(oldCliente, newCliente)) {
            salvarCliente(AlteracaoClienteEvent.builder().cliente(newCliente).build());
        }
    }

    @Override
    public boolean alterouCliente(Cliente oldCliente, Cliente newCliente) {
        return !(newCliente.equals(oldCliente));
    }

    @Override
    public void salvarCliente(AlteracaoClienteEvent event) {
        cadastroClienteService.salvar(event.getCliente());
    }
}
