package model;

public class Jobs {
	public int id;
	public int idTicket;
	public int idCliente;
	public int trackerId;
	public int estado;
	public String deviceId;
	public String label;
	public String conductores;
	public String tipo;
	public Boolean tieneJob;
	public String tieneJobConductor;
	
	
	
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}
	public String getTieneJobConductor() {
		return tieneJobConductor;
	}
	public void setTieneJobConductor(String tieneJobConductor) {
		this.tieneJobConductor = tieneJobConductor;
	}
	public Boolean getTieneJob() {
		return tieneJob;
	}
	public void setTieneJob(Boolean tieneJob) {
		this.tieneJob = tieneJob;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getConductores() {
		return conductores;
	}
	public void setConductores(String conductores) {
		this.conductores = conductores;
	}
	@Override
	public String toString() {
		return "Jobs [id=" + id + ", idTicket=" + idTicket + ", idCliente=" + idCliente + ", trackerId=" + trackerId
				+ ", estado=" + estado + ", deviceId=" + deviceId + ", label=" + label + ", conductores=" + conductores
				+ ", tipo=" + tipo + ", tieneJob=" + tieneJob + ", tieneJobConductor=" + tieneJobConductor + "]";
	}




}
