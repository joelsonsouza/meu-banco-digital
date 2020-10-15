package com.nossobancodigital.nossobancodigital.domain.repository;

import com.nossobancodigital.nossobancodigital.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQueries {

}
