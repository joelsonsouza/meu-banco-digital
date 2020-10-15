package com.nossobancodigital.nossobancodigital.domain.service;

import com.nossobancodigital.nossobancodigital.domain.exceptions.RegraNegocioException;
import com.nossobancodigital.nossobancodigital.domain.model.ClienteEndereco;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteEnderecoRepository;
import com.nossobancodigital.nossobancodigital.domain.validations.CepValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroClienteEnderecoService {

    @Autowired
    private ClienteEnderecoRepository clienteEnderecoRepository;

    public ClienteEndereco salvar(ClienteEndereco clienteEndereco) throws RegraNegocioException {

        if (CepValidator.builder().cep(clienteEndereco.getCep()).build().cepInvalido())
            throw new RegraNegocioException("Cep inválido");

        return clienteEnderecoRepository.save(clienteEndereco);
    }
}
