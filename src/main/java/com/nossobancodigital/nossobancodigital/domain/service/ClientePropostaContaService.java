package com.nossobancodigital.nossobancodigital.domain.service;

import com.nossobancodigital.nossobancodigital.domain.model.ClientePropostaConta;
import com.nossobancodigital.nossobancodigital.domain.repository.ClientePropostaContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientePropostaContaService {
    @Autowired
    private ClientePropostaContaRepository clientePropostaContaRepository;

    @Transactional
    public ClientePropostaConta salvar(ClientePropostaConta clientePropostaConta){
        return clientePropostaContaRepository.save(clientePropostaConta);
    }
}
