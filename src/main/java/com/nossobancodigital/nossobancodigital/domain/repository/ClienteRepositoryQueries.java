package com.nossobancodigital.nossobancodigital.domain.repository;

import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import com.nossobancodigital.nossobancodigital.domain.model.ClienteCpfFoto;

import java.util.List;

public interface ClienteRepositoryQueries {
    List<Cliente> findByEmail(Long idCliente, String email);
    List<Cliente> findByCpf(Long idCliente, String email);
    ClienteCpfFoto save(ClienteCpfFoto clienteCpfFoto);
}
