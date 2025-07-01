package com.cibertec.hotel.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

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
	private final BCryptPasswordEncoder passwordEncoder;
	private final CloudinaryService cloudinaryService;
	private final CorreoService correoService;
	private final RolRepository rolRepository;
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper,
			CloudinaryService cloudinaryService, CorreoService correoService,RolRepository rolRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.cloudinaryService = cloudinaryService;
		this.correoService = correoService;
		this.rolRepository = rolRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> encontrarPorId(int id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario registrarUsuario(UsuarioDTO dto, String urlPlantillaCorreo) throws IOException {
		if(usuarioRepository.existsByCorreo(dto.getCorreo())) {
			throw new IllegalArgumentException("El correo ya esta registrado");
		}
		Usuario usuario = new Usuario();
		usuario.setUsername(dto.getUsername());
	    usuario.setCorreo(dto.getCorreo());
	    
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

			Context context = new Context();
			context.setVariable("correo", guardado.getCorreo());
			context.setVariable("clave", claveOriginal);
			context.setVariable("url", urlPlantillaCorreo);

			String contenido = templateEngine.process("correo/registro", context);
			correoService.enviarCorreo(guardado.getCorreo(), "Bienvenido", contenido);
		}

		return guardado;
		
	}

	@Override
	public Optional<Usuario> actualizarUsuario(int id, UsuarioDTO dto) throws IOException {		
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		if(usuarioOpt.isEmpty()) {
			return Optional.empty();
		}
		Usuario usuario  = usuarioOpt.get();
		
		boolean correoExistente = usuarioRepository.existsByCorreo(dto.getCorreo())&&
									!usuario.getCorreo().equals(dto.getCorreo());
		if(correoExistente) {
			throw new IllegalArgumentException("El correo ya esta registrado");
		}
		
		usuario.setUsername(dto.getUsername());
		usuario.setCorreo(dto.getCorreo());
		
		if(!dto.getClave().equals(usuario.getClave())) {
			String claveEncrip = passwordEncoder.encode(dto.getClave());
			usuario.setClave(claveEncrip);
		}
		
		
		MultipartFile imagen = dto.getImagen();
		if(imagen != null && !imagen.isEmpty()) {
			if(usuario.getNombreFoto() == null || usuario.getNombreFoto().isBlank()) {
				String nombreFoto = UUID.randomUUID().toString()+"_"+imagen.getOriginalFilename();
				usuario.setNombreFoto(nombreFoto);
			}
			String urlFoto = cloudinaryService.subirArchivo(imagen.getInputStream(), "usuarios", usuario.getNombreFoto());
			usuario.setUrlFoto(urlFoto);
		}
		
		Rol rol = rolRepository.findById(dto.getIdRol())
					.orElseThrow(()-> new RuntimeException("ROL NO EXISTE"));
		usuario.setIdRol(rol);
		
		usuarioRepository.save(usuario);
		return Optional.of(usuario);
	}

	@Override
	public Optional<Boolean> eliminarUsuario(int id) {
		Optional<Usuario> usuOpt = usuarioRepository.findById(id);
		if(usuOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuario con id : "+id+" no encontrado");
		}
		Usuario usu = usuOpt.get();
		String nombreFoto = usu.getNombreFoto();
		
		if (nombreFoto != null && !nombreFoto.isBlank()) {
		    cloudinaryService.eliminarArchivo("usuarios", nombreFoto);
		}
		usuarioRepository.deleteById(id);
		return Optional.of(true);
		
	}


}
