package com.cibertec.hotel.services;

import java.io.IOException;
import java.io.InputStream;



public interface CloudinaryService {
	String subirArchivo(InputStream archivo, String carpeta, String nombreArchivo)throws IOException;
	boolean eliminarArchivo(String carpeta, String nombreArchivo);
}
