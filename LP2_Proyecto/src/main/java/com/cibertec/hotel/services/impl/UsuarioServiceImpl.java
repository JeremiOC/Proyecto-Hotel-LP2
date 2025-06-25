package com.cibertec.hotel.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cibertec.hotel.dto.UsuarioDTO;
import com.cibertec.hotel.entities.Rol;
import com.cibertec.hotel.entities.Usuario;
import com.cibertec.hotel.mappers.UsuarioMapper;
import com.cibertec.hotel.repositories.RolRepository;
import com.cibertec.hotel.repositories.UsuarioRepository;
import com.cibertec.hotel.services.CloudinaryService;
import com.cibertec.hotel.services.CorreoService;
import com.cibertec.hotel.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	private final UsuarioRepository usuarioRepository;
	private final UsuarioMapper usuarioMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	private final CloudinaryService cloudinaryService;
	private final CorreoService correoService;
	private final RolRepository rolRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper,
			CloudinaryService cloudinaryService, CorreoService correoService,RolRepository rolRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioMapper = usuarioMapper;
		this.cloudinaryService = cloudinaryService;
		this.correoService = correoService;
		this.rolRepository = rolRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> encontrarPorId(int id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario registrarUsuario(UsuarioDTO dto, String urlPlantillaCorreo) throws IOException {
		if(usuarioRepository.existsByCorreo(dto.getCorreo())) {
			throw new IllegalArgumentException("El correo ya esta registrado");
		}
		Usuario usuario = usuarioMapper.toEntity(dto);
		
		String claveOriginal = dto.getClave();
		String claveEncriptada = passwordEncoder.encode(claveOriginal);
		
		usuario.setClave(claveEncriptada);
		
		MultipartFile imagen = dto.getImagen();
		if(imagen!=null && !imagen.isEmpty()) {
			String nombreFoto = UUID.randomUUID().toString()+"_"+imagen.getOriginalFilename();
			InputStream fotoStream = imagen.getInputStream();
			String urlFoto = cloudinaryService.subirArchivo(fotoStream, "usuarios", nombreFoto);
			
			usuario.setNombreFoto(nombreFoto);
			usuario.setUrlFoto(urlFoto);
		}
		Rol rol = rolRepository.findById(dto.getIdRol())
				.orElseThrow(()-> new RuntimeException("Rol NO VALIDO"));
		
		usuario.setIdRol(rol);
		
				
		Usuario guardado = usuarioRepository.save(usuario);
		
		if(urlPlantillaCorreo != null && !urlPlantillaCorreo.isBlank()) {
			
			String correo = URLEncoder.encode(guardado.getCorreo(), "UTF-8");
			String clave = URLEncoder.encode(claveOriginal, "UTF-8");

			urlPlantillaCorreo = "http://localhost:8080/plantilla/cuenta?correo=" + correo + "&clave=" + clave;

			String contenido = "<b>Usuario creado correctamente</b><br>" +
	                   "Correo: " + guardado.getCorreo() + "<br>" +
	                   "Clave: " + claveOriginal + "<br>" +
	                   "<a href='" + urlPlantillaCorreo + "'>Accede a tu cuenta</a>";
			correoService.enviarCorreo(guardado.getCorreo(), "Bienvenido", contenido);
		}
		
		return guardado;
		
	}

	@Override
	public Optional<Usuario> actualizarUsuario(int id, UsuarioDTO dto) throws IOException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Boolean> eliminarUsuario(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}


}
