package model;

public class UnidadConductor {
public int id;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int idCliente;
public int trackerId;
public String label;
public String asignacion;
public String conductor;
public String llaveConductor;
public int posicion;
public String disponibilidad;
public int getIdCliente() {
	return idCliente;
}
public void setIdCliente(int idCliente) {
	this.idCliente = idCliente;
}
public int getTrackerId() {
	return trackerId;
}
public void setTrackerId(int trackerId) {
	this.trackerId = trackerId;
}
public String getLabel() {
	return label;
}
public void setLabel(String label) {
	this.label = label;
}
public String getAsignacion() {
	return asignacion;
}
public void setAsignacion(String asignacion) {
	this.asignacion = asignacion;
}
public String getConductor() {
	return conductor;
}
public void setConductor(String conductor) {
	this.conductor = conductor;
}
public String getLlaveConductor() {
	return llaveConductor;
}
public void setLlaveConductor(String llaveConductor) {
	this.llaveConductor = llaveConductor;
}
public int getPosicion() {
	return posicion;
}
public void setPosicion(int posicion) {
	this.posicion = posicion;
}
public String getDisponibilidad() {
	return disponibilidad;
}
public void setDisponibilidad(String disponibilidad) {
	this.disponibilidad = disponibilidad;
}
@Override
public String toString() {
	return "UnidadConductor [id=" + id + ", idCliente=" + idCliente + ", trackerId=" + trackerId + ", label=" + label
			+ ", asignacion=" + asignacion + ", conductor=" + conductor + ", llaveConductor=" + llaveConductor
			+ ", posicion=" + posicion + ", disponibilidad=" + disponibilidad + "]";
}

}
