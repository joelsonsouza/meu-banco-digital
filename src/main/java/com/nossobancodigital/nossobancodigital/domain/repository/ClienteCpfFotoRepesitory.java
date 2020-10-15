package com.nossobancodigital.nossobancodigital.domain.repository;

import com.nossobancodigital.nossobancodigital.domain.model.ClienteCpfFoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteCpfFotoRepesitory extends JpaRepository<ClienteCpfFoto, Long> {
}
