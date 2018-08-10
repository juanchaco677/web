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
		parametros.put("", "1");

		Registro registro;
		try {
			registro = Request.get("http://softwarevalerian.tk/api/usuario", parametros);
			if(registro!=null) {
//				System.out.println(registro.getCampos().get("NOMBRE"));
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
