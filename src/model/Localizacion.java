package model;

import java.util.Date;

public class Localizacion {
	private int id;
	private Double latitud;
	private Double longitud;
	private String direccion;
	private Ciudad ciudad;	
	private Date created_at;
	private Date updated_at;
	
	public Localizacion(int id, Double latitud, Double longitud, String direccion, Ciudad ciudad, Date created_at,
			Date updated_at) {
		super();
		this.id = id;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	
	
	public Localizacion(Double latitud, Double longitud, String direccion, Ciudad ciudad) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.ciudad = ciudad;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Ciudad getCiudad() {
		return ciudad;
	}
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
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
