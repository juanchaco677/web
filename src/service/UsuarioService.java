package service;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import main.Registro;
import main.Request;
import model.Usuario;


public class UsuarioService {

	public void crearUsuario(Usuario usuario){
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

		Registro registro;
		try {
			registro = Request.get("http://softwarevalerian.tk/api/usuario/crear", parametros);
			if(registro!=null) {
				boolean sucess=(boolean) registro.getCampos().get("sucess");
				if(sucess) {
					System.out.println("true");
				}else {

					System.out.println("false");
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	public void sesion(Usuario usuario){
		Map<String,Object>parametros=new HashMap<>();				
		parametros.put("email", usuario.getCorreo());
		parametros.put("passsword", usuario.getContrasena());

		Registro registro;
		try {
			registro = Request.get("http://softwarevalerian.tk/api/usuario/sesion", parametros);
			boolean sucess=(boolean) registro.getCampos().get("sucess");
			if(sucess) {
				System.out.println("true");
			}else {

				System.out.println("false");
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
