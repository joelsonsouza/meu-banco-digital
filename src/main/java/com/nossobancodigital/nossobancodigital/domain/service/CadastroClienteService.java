package com.nossobancodigital.nossobancodigital.domain.service;

import com.nossobancodigital.nossobancodigital.domain.exceptions.RegraNegocioException;
import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) throws RegraNegocioException {

        if (cliente.getDataNascimento() == null)
            throw new RegraNegocioException("Informe a data de nascimento");

        if (dataNascimentoInvalida(cliente.getDataNascimento()))
            throw new RegraNegocioException("A data de nascimento não pode ser informada no futuro");

        if (idadeInvalida(cliente.getDataNascimento()))
            throw new RegraNegocioException("A conta só pode ser criada para maiores de 18 anos");

        if (cpfJaCadastrado(cliente.getIdCliente(), cliente.getCpf()))
            throw new RegraNegocioException("Já existe uma conta para o cpf informado");

        if (emailJaCadastrado(cliente.getIdCliente(), cliente.getEmail()))
            throw new RegraNegocioException("Já existe uma conta para o email informado");


        return clienteRepository.save(cliente);
    }

    private boolean idadeInvalida(Date dataNascimento) {

        Calendar dtNascimento = new GregorianCalendar();
        dtNascimento.setTime(dataNascimento);

        Calendar hoje = Calendar.getInstance();

        int idade = hoje.get(Calendar.YEAR) - dtNascimento.get(Calendar.YEAR);

        return idade < 18 ? true : false;
    }

    private boolean dataNascimentoInvalida(Date dataNascimento) {
        return dataNascimento.after(new Date()) ? true : false;
    }

    private boolean emailJaCadastrado(Long idCliente, String email) {
        List<Cliente> cliente = clienteRepository.findByEmail(idCliente, email);
        return cliente.isEmpty() ? false : true;
    }

    private boolean cpfJaCadastrado(Long idCliente, String cpf) {
        List<Cliente> cliente = clienteRepository.findByCpf(idCliente, cpf);
        return cliente.isEmpty() ? false : true;
    }

}
