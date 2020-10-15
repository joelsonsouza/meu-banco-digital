package com.nossobancodigital.nossobancodigital.infrastructure.repository;

import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import com.nossobancodigital.nossobancodigital.domain.model.ClienteCpfFoto;
import com.nossobancodigital.nossobancodigital.domain.repository.ClienteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;

@Repository
public class ClienteRepositoryImpl implements ClienteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cliente> findByEmail(Long idCliente, String email) {
        var jpql = new StringBuilder();
        jpql.append("from Cliente where 0 = 0 ");

        var parametros = new HashMap<String, Object>();

        if (idCliente != null) {
            jpql.append("and idCliente <> :idCliente ");
            parametros.put("idCliente", idCliente);
        }

        jpql.append("and email = :email ");
        parametros.put("email", email);

        TypedQuery<Cliente> query = manager
                .createQuery(jpql.toString(), Cliente.class);

        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();
    }

    @Override
    public List<Cliente> findByCpf(Long idCliente, String cpf) {
        var jpql = new StringBuilder();
        jpql.append("from Cliente where 0 = 0 ");

        var parametros = new HashMap<String, Object>();

        if (idCliente != null) {
            jpql.append("and idCliente <> :idCliente ");
            parametros.put("idCliente", idCliente);
        }

        jpql.append("and cpf = :cpf ");
        parametros.put("cpf", cpf);

        TypedQuery<Cliente> query = manager
                .createQuery(jpql.toString(), Cliente.class);

        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();
    }

    @Transactional
    @Override
    public ClienteCpfFoto save(ClienteCpfFoto fotoCpf) {
        return manager.merge(fotoCpf);
    }

}
