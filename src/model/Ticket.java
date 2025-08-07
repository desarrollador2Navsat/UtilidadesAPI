package model;

public class Ticket {
	public int idTicket;
	public int idCliente;
	public int cantidadJobs;
	public int jobsBuenos;
	public int jobsMalos;
	public String fechaCreacion;
	public String fechaActualizacion;
	public String usuario;
	public int estado;
	public String estadoMensaje;
	public String jobs;

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getCantidadJobs() {
		return cantidadJobs;
	}

	public void setCantidadJobs(int cantidadJobs) {
		this.cantidadJobs = cantidadJobs;
	}

	public int getJobsBuenos() {
		return jobsBuenos;
	}

	public void setJobsBuenos(int jobsBuenos) {
		this.jobsBuenos = jobsBuenos;
	}

	public int getJobsMalos() {
		return jobsMalos;
	}

	public void setJobsMalos(int jobsMalos) {
		this.jobsMalos = jobsMalos;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getEstadoMensaje() {
		return estadoMensaje;
	}

	public void setEstadoMensaje(String estadoMensaje) {
		this.estadoMensaje = estadoMensaje;
	}

	public String getJobs() {
		return jobs;
	}

	public void setJobs(String jobs) {
		this.jobs = jobs;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", idCliente=" + idCliente + ", cantidadJobs=" + cantidadJobs
				+ ", jobsBuenos=" + jobsBuenos + ", jobsMalos=" + jobsMalos + ", fechaCreacion=" + fechaCreacion
				+ ", fechaActualizacion=" + fechaActualizacion + ", usuario=" + usuario + ", estado=" + estado
				+ ", estadoMensaje=" + estadoMensaje + ", jobs=" + jobs + "]";
	}

}
