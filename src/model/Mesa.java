package model;

import java.util.Date;

public class Mesa {
	private int id;
	private int numero;
	private PuntoVotacion puntoVotacion;
	private Date created_at;
	private Date updated_at;
	
	
	public Mesa(int id, int numero, PuntoVotacion puntoVotacion, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.numero = numero;
		this.puntoVotacion = puntoVotacion;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public PuntoVotacion getPuntoVotacion() {
		return puntoVotacion;
	}
	public void setPuntoVotacion(PuntoVotacion puntoVotacion) {
		this.puntoVotacion = puntoVotacion;
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
