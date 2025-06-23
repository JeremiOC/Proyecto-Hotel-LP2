package com.cibertec.hotel.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.cibertec.hotel.dto.UsuarioDTO;
import com.cibertec.hotel.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "nombreFoto", ignore = true)
    @Mapping(target = "urlFoto", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    Usuario toEntity(UsuarioDTO dto);

    @Mapping(target = "idRol", source = "idRol.idRol")
    @Mapping(target = "imagen", ignore = true)
    @Mapping(target = "clave", ignore = true)
    UsuarioDTO toDTO(Usuario entity);

//    @Named("mapIdToRol")
//    public static Rol mapIdToRol(Integer idRol) {
//        if (idRol == null) return null;
//        Rol rol = new Rol();
//        rol.setIdRol(idRol);
//        return rol;
//    }
}