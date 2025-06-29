package com.cibertec.hotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.hotel.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	List<Cliente> findByActivoTrue();
	
}
