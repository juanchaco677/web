package util;

import java.util.HashMap;
import java.util.Map;

public class Registro {
	private Map<String,Object> campos;

	public Registro() {
		campos=new HashMap<>();
	}
	
	public void add(String llave,Object valor){
		campos.put(llave,valor);
	}

	public Map<String, Object> getCampos() {
		return campos;
	}

	public void setCampos(Map<String, Object> campos) {
		this.campos = campos;
	}

}
