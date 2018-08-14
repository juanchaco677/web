package model;

import java.util.Date;

public class Ciudad {
	private int id;
	private  String nombre;
	private Departamento departamento;
	private Date created_at;
	private Date updated_at;
	
	
	public Ciudad(int id, String nombre, Departamento departamento, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.departamento = departamento;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}


	public Ciudad(String nombre, Departamento departamento) {
		super();
		this.nombre = nombre;
		this.departamento = departamento;
	}


	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
