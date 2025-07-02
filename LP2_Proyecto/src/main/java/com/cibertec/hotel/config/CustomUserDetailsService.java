package com.cibertec.hotel.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cibertec.hotel.entities.Usuario;
import com.cibertec.hotel.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Correo no encontrado"));

        String rol = usuario.getIdRol().getNombre(); 


        return User.builder()
                .username(usuario.getCorreo()) 
                .password(usuario.getClave())  
                .roles(rol.toUpperCase().replace(" ", "_")) 
                .build();
    }
}
