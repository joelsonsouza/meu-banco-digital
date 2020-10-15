package com.nossobancodigital.nossobancodigital.domain.service;

import com.nossobancodigital.nossobancodigital.domain.model.ClienteCpfFoto;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteCpfFotoRepesitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroClienteCpfFotoService {

    @Autowired
    private ClienteCpfFotoRepesitory clienteCpfFotoRepesitory;

    @Transactional
    public ClienteCpfFoto salvar(ClienteCpfFoto cpfFoto){
        return clienteCpfFotoRepesitory.save(cpfFoto);
    }

}
