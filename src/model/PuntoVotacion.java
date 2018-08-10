package model;

import java.util.Date;

public class PuntoVotacion {
	private int id;
	private String nombre;
	private Localizacion localizacion;
	private Date created_at;
	private Date updated_at;
	
	
	public PuntoVotacion(int id, String nombre, Localizacion localizacion, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.localizacion = localizacion;
		this.created_at = created_at;
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
	public Localizacion getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
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
	
	

}
