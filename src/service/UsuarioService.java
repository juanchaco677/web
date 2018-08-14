package service;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import model.Usuario;
import util.Registro;
import util.Request;


public class UsuarioService {

	public Registro crearUsuario(Usuario usuario) throws URISyntaxException{
		Map<String,Object>parametros=new HashMap<>();				
		parametros.put("departamento", usuario.getMesa().getPuntoVotacion().getLocalizacion().getCiudad().getDepartamento().getNombre());
		parametros.put("ciudad", usuario.getMesa().getPuntoVotacion().getLocalizacion().getCiudad().getNombre());
		parametros.put("latitud", usuario.getMesa().getPuntoVotacion().getLocalizacion().getLatitud());
		parametros.put("longitud", usuario.getMesa().getPuntoVotacion().getLocalizacion().getLongitud());
		parametros.put("direccion", usuario.getMesa().getPuntoVotacion().getLocalizacion().getDireccion());
		parametros.put("mesa", usuario.getMesa().getId());		
		parametros.put("email", usuario.getCorreo());
		parametros.put("passsword", usuario.getContrasena());
		parametros.put("mesa", usuario.getMesa().getId());	

		return Request.get("http://localhost:8000/api/usuario/storeJX", parametros);
	}

	public Registro sesion(Usuario usuario) throws URISyntaxException{
		Map<String,Object>parametros=new HashMap<>();				
		parametros.put("email", usuario.getCorreo());
		parametros.put("password", usuario.getContrasena());	

		return Request.post("http://localhost:8000/api/usuario/sesion", parametros);
	}
	

	public Registro getUsuario(String token) throws URISyntaxException{
		Map<String,Object>parametros=new HashMap<>();				
		parametros.put("token", token);
		return Request.get("http://localhost:8000/api/usuario/getUsuario", parametros);
	}
}
