package model;

import org.json.JSONException;
import org.json.JSONObject;

import util.Registro;
import util.ValerianUtil;

public class Usuario {	

	private int id;

	private String correo;

	private String contrasena;

	private String nombreUno;

	private String nombreDos;

	private String apellidoUno;

	private String apellidoDos;

	private String celular;

	private Mesa mesa;	

	private String type;

	private Usuario candidato;

	private Usuario referido;

	public Usuario(int id, String correo, String contrasena, String nombreUno, String nombreDos, String apellidoUno,
			String apellidoDos, String celular, Mesa mesa) {
		super();
		this.id = id;
		this.correo = correo;
		this.contrasena = contrasena;
		this.nombreUno = nombreUno;
		this.nombreDos = nombreDos;
		this.apellidoUno = apellidoUno;
		this.apellidoDos = apellidoDos;
		this.celular = celular;
		this.mesa = mesa;
	}
	
	
	



	public Usuario(int id) {
		super();
		this.id = id;
	}



	public Usuario(String nombreCompleto, String celular,Mesa mesa) {

		String [] arrayNombre=nombreCompleto.split(" ");		
		this.nombreUno = recursivo(arrayNombre, 0);
		this.nombreDos = recursivo(arrayNombre, 1);
		this.apellidoUno = recursivo(arrayNombre, 2);
		this.apellidoDos = recursivo(arrayNombre, 3);
		this.celular = celular;
		this.mesa=mesa;
	}

	public Usuario(Registro registro){

			this.id=ValerianUtil.validarRegistro(registro, "id")?0:Integer.parseInt(ValerianUtil.valor(registro, "id").toString());
			this.nombreUno=ValerianUtil.valor(registro, "name").toString();
			this.nombreDos=ValerianUtil.valor(registro, "name").toString();
			this.apellidoUno=ValerianUtil.valor(registro, "lastname").toString();
			this.apellidoDos=ValerianUtil.valor(registro, "lastname2").toString();
			this.type=ValerianUtil.valor(registro, "type").toString();
			this.correo=ValerianUtil.valor(registro, "email").toString();		
			this.mesa=new Mesa(ValerianUtil.validarRegistro(registro, "id_mesa")?0:Integer.parseInt(ValerianUtil.valor(registro, "id_mesa").toString()));
			this.candidato=new Usuario(ValerianUtil.validarRegistro(registro, "id_candidato")?0:Integer.parseInt(ValerianUtil.valor(registro, "id_candidato").toString()));
			this.referido=new Usuario(ValerianUtil.validarRegistro(registro, "id_referido")?0:Integer.parseInt(ValerianUtil.valor(registro, "id_candidato").toString()));
			
	
	}
	



	public Usuario(String correo, String contrasena) {
		super();
		this.correo = correo;
		this.contrasena = contrasena;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Usuario getCandidato() {
		return candidato;
	}

	public void setCandidato(Usuario candidato) {
		this.candidato = candidato;
	}

	public Usuario getReferido() {
		return referido;
	}

	public void setReferido(Usuario referido) {
		this.referido = referido;
	}	

}
