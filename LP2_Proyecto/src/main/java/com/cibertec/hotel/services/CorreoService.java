package com.cibertec.hotel.services;

import java.io.UnsupportedEncodingException;



public interface CorreoService {
	
	 void enviarCorreo(String destino, String asunto, String mensajeHtml) throws UnsupportedEncodingException;
	
}
