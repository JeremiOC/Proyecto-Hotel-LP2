package com.cibertec.hotel.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cibertec.hotel.entities.Configuracion;
import com.cibertec.hotel.repositories.ConfiguracionRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    @Autowired
    private ConfiguracionRepository configuracionRepository;

    @Bean
     Cloudinary cloudinary() {
        List<Configuracion> lista = configuracionRepository.findByRecurso("Cloudinary");
        Map<String, String> config = lista.stream()
                .collect(Collectors.toMap(Configuracion::getPropiedad, Configuracion::getValor));

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", config.get("cloud_name"),
                "api_key", config.get("api_key"),
                "api_secret", config.get("api_secret") 
        ));
    }
}
