package com.cibertec.hotel.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.hotel.dto.ClienteDTO;
import com.cibertec.hotel.entities.Cliente;
import com.cibertec.hotel.entities.TipoDocumento;
import com.cibertec.hotel.mappers.ClienteMapper;
import com.cibertec.hotel.repositories.ClienteRepository;
import com.cibertec.hotel.repositories.TipoDocumentoRepository;
import com.cibertec.hotel.services.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{
	private ClienteRepository clienteRepository;
	private TipoDocumentoRepository tipoDocRepo;
	private ClienteMapper mapper;	

	public ClienteServiceImpl(ClienteRepository clienteRepository, TipoDocumentoRepository tipoDocRepo,
			ClienteMapper mapper) {
		this.clienteRepository = clienteRepository;
		this.tipoDocRepo = tipoDocRepo;
		this.mapper = mapper;
	}

	@Override
	public List<Cliente> listarClientes() {
		return clienteRepository.findByActivoTrue();
	}
	
	public List<TipoDocumento> listarTiposDoc(){
		return tipoDocRepo.findAll();
	}
	
	@Override
	@Transactional
	public Cliente registrarCliente(ClienteDTO dto) {
		Cliente cliente  = mapper.toEntity(dto);
		
		TipoDocumento tipoDoc = tipoDocRepo.findById(dto.getTipoDocumentoId())
									.orElseThrow(()-> new RuntimeException("Tipo documento con id : "+dto.getTipoDocumentoId()+" no encontrado"));
		
		cliente.setTipoDocumento(tipoDoc);
		
		return clienteRepository.save(cliente);
	}
	
    @Override
    @Transactional
    public Optional<Cliente> editarCliente(int id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente con ID " + id + " no encontrado"));

        cliente.setNombres(dto.getNombres());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        cliente.setApellidoMaterno(dto.getApellidoMaterno());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());
        cliente.setNroDocumento(dto.getNroDocumento());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());

        TipoDocumento tipoDoc = tipoDocRepo.findById(dto.getTipoDocumentoId())
            .orElseThrow(() -> new RuntimeException("Tipo de documento con ID " + dto.getTipoDocumentoId() + " no encontrado"));

        cliente.setTipoDocumento(tipoDoc);

        return Optional.of(clienteRepository.save(cliente));
    }
	@Override
	public Optional<Cliente> encontrarPorId(int id) {
		return clienteRepository.findById(id);
	}

	@Override
	@Transactional
	public boolean eliminar(int id) {
		  Cliente cliente = clienteRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Cliente con ID " + id + " no encontrado"));

	      cliente.setActivo(false);
	      clienteRepository.save(cliente);

	      return true;
	}

}
