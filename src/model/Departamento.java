package model;

import java.util.Date;

public class Departamento {
	private int id;
	private  String nombre;
	private Date created_at;
	private Date updated_at;

	public Departamento(int id, String nombre, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.created_at = created_at;
		this.updated_at = updated_at;
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
