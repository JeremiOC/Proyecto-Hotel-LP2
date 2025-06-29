package com.cibertec.hotel.services;

import java.util.List;
import java.util.Optional;

import com.cibertec.hotel.dto.ClienteDTO;
import com.cibertec.hotel.entities.Cliente;
import com.cibertec.hotel.entities.TipoDocumento;

public interface ClienteService {
	
	List<Cliente> listarClientes();
	Cliente registrarCliente(ClienteDTO dto);
	Optional<Cliente> editarCliente(int id , ClienteDTO dto);
	Optional<Cliente> encontrarPorId(int id);
	boolean eliminar(int id);
	List<TipoDocumento> listarTiposDoc();
}
