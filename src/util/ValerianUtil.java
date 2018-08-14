package util;

public class ValerianUtil {
	public static boolean validarRegistro(Registro registro,String llave){
	
		return registro.getCampos().get(llave) == null || "".equals(registro.getCampos().get(llave).toString());		
		
	}
	
	public static Object valor(Registro registro,String llave){
		
		return registro.getCampos().get(llave) == null || "".equals(registro.getCampos().get(llave).toString())?"":registro.getCampos().get(llave);		
		
	}
	
}
