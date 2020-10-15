package com.nossobancodigital.nossobancodigital.domain.repository;

import com.nossobancodigital.nossobancodigital.domain.model.ClientePropostaConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientePropostaContaRepository extends JpaRepository<ClientePropostaConta, Long> {
}
