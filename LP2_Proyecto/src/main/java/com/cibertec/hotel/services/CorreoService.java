package com.cibertec.hotel.services;

import java.io.UnsupportedEncodingException;



public interface CorreoService {
	
	 boolean enviarCorreo(String destino, String asunto, String mensajeHtml) throws UnsupportedEncodingException;
	
}
