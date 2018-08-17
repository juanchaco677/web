package util;

import org.json.JSONException;
import org.json.JSONObject;

public class ValerianUtil {
	public static boolean validarRegistro(Registro registro,String llave){
	
		return registro.getCampos().get(llave) == null 
				||
				"".equals(registro.getCampos().get(llave).toString())
				||
				"null".equals(registro.getCampos().get(llave).toString());		
		
	}
	
	public static Object valor(Registro registro,String llave){
		
		return registro.getCampos().get(llave) == null 
				||
				"".equals(registro.getCampos().get(llave).toString())
				||
				"null".equals(registro.getCampos().get(llave).toString())?"":registro.getCampos().get(llave);		
		
	}
	
	public static Registro convertirARegistro(String content) throws JSONException {
		JSONObject json=new JSONObject(content);
		Registro registro=new Registro();
		for (int j = 0; j < json.length(); j++) {					
			registro.add(json.names().getString(j),"null".equals(json.get(json.names().getString(j)))?null:json.get(json.names().getString(j)));					
		}
		return registro;
		
	}
	
}
