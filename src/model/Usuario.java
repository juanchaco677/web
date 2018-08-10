package model;

public class Usuario {
	private String nombreUno;

	private String nombreDos;

	private String apellidoUno;

	private String apellidoDos;

	private String celular;


	public Usuario(String nombreUno, String nombreDos, String apellidoUno, String apellidoDos, String celular) {
		super();
		this.nombreUno = nombreUno;
		this.nombreDos = nombreDos;
		this.apellidoUno = apellidoUno;
		this.apellidoDos = apellidoDos;
		this.celular = celular;
	}
	public Usuario(String nombreCompleto, String celular) {
		
		String [] arrayNombre=nombreCompleto.split(" ");		
		this.nombreUno = recursivo(arrayNombre, 0);
		this.nombreDos = recursivo(arrayNombre, 1);
		this.apellidoUno = recursivo(arrayNombre, 2);
		this.apellidoDos = recursivo(arrayNombre, 3);
		this.celular = celular;
	}
	
	private String recursivo(String [] arrayNombre,int i){
		
		if(!"".equals(arrayNombre[i]) && arrayNombre[i]!=null){
			return arrayNombre[i];
		}else if(arrayNombre.length-1==i && ("".equals(arrayNombre[i]) || arrayNombre[i]==null)){
			return "";
		}		
		
		return recursivo(arrayNombre,i+1);
	}
	
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNombreUno() {
		return nombreUno;
	}

	public void setNombreUno(String nombreUno) {
		this.nombreUno = nombreUno;
	}

	public String getNombreDos() {
		return nombreDos;
	}

	public void setNombreDos(String nombreDos) {
		this.nombreDos = nombreDos;
	}

	public String getApellidoUno() {
		return apellidoUno;
	}

	public void setApellidoUno(String apellidoUno) {
		this.apellidoUno = apellidoUno;
	}

	public String getApellidoDos() {
		return apellidoDos;
	}

	public void setApellidoDos(String apellidoDos) {
		this.apellidoDos = apellidoDos;
	}
	
	
	
}
