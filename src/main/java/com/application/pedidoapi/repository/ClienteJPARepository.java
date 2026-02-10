package com.application.pedidoapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.pedidoapi.model.Cliente;

@Repository
public interface ClienteJPARepository extends JpaRepository<Cliente, UUID> {

}
