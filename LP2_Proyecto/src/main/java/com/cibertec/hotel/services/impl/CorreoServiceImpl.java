package com.cibertec.hotel.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cibertec.hotel.entities.Configuracion;
import com.cibertec.hotel.repositories.ConfiguracionRepository;
import com.cibertec.hotel.services.CorreoService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CorreoServiceImpl implements CorreoService {
	
	private ConfiguracionRepository configuracionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(CorreoServiceImpl.class);

	public CorreoServiceImpl(ConfiguracionRepository configuracionRepository) {
     this.configuracionRepository = configuracionRepository;
     
	}
	
	private JavaMailSenderImpl crearMailSender(Map<String, String> config) {
		 JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
       mailSender.setHost(config.get("host"));
       mailSender.setPort(Integer.parseInt(config.get("puerto")));
       mailSender.setUsername(config.get("correo"));
       mailSender.setPassword(config.get("clave"));

       Properties props = mailSender.getJavaMailProperties();
       props.put("mail.smtp.auth", "true");
       props.put("mail.smtp.starttls.enable", "true");
       props.put("mail.smtp.ssl.trust", config.get("host"));

       return mailSender;
	}
	
	@Async
	@Override
	public void enviarCorreo(String destino, String asunto, String mensajeHtml) throws UnsupportedEncodingException {
	    try {
	        List<Configuracion> lista = configuracionRepository.findByRecurso("Servicio_Correo");
	        Map<String, String> config = lista.stream()
	            .collect(Collectors.toMap(Configuracion::getPropiedad, Configuracion::getValor));

	        JavaMailSenderImpl mailSender = crearMailSender(config);

	        MimeMessage mensaje = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

	        helper.setFrom(config.get("correo"), config.get("alias"));
	        helper.setTo(destino);
	        helper.setSubject(asunto);
	        helper.setText(mensajeHtml, true);

	        mailSender.send(mensaje);

	    } catch (MessagingException e) {
	        logger.error("Error al enviar correo: ", e);
	    }
	}

}
