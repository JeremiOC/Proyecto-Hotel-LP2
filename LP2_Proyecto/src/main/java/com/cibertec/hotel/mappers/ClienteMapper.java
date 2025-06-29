package com.cibertec.hotel.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cibertec.hotel.dto.ClienteDTO;
import com.cibertec.hotel.entities.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	@Mapping(target =  "id",ignore = true)
	@Mapping(target =  "tipoDocumento",ignore = true)
	Cliente toEntity(ClienteDTO dto);
	
	@Mapping(target = "tipoDocumentoId",ignore  = true)
	ClienteDTO toDto(Cliente cliente);
}
