package com.cibertec.hotel.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cibertec.hotel.services.CloudinaryService;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
	@Autowired
	private Cloudinary cloudinary;
	
	private static final Logger logger = LoggerFactory.getLogger(CloudinaryServiceImpl.class);

	@Override
	public String subirArchivo(InputStream archivo, String carpeta, String nombreArchivo) throws IOException {
	    try {
	        byte[] bytes = IOUtils.toByteArray(archivo);

	        Map<String, Object> options = new HashMap<>();
	        options.put("public_id", nombreArchivo);     
	        options.put("folder", carpeta);              

	        Map<?, ?> result = cloudinary.uploader().upload(bytes, options);

	        return result.get("secure_url").toString();  
	    } catch (Exception e) {
	        System.err.println("Error al subir la imagen: " + e.getMessage());
	        throw new IOException("Error al subir imagen", e);
	    }
	}


	@Override
	@SuppressWarnings("rawtypes")
	public boolean eliminarArchivo(String carpeta, String nombreArchivo) {
	    try {
	        String publicId = carpeta + "/" + nombreArchivo; 

	        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

	        return "ok".equals(result.get("result"));
	    } catch (Exception e) {
	        logger.error("Error al eliminar la imagen del storage: ", e);
	        return false;
	    }
	}



}
