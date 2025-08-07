package model;

public class cliente {
public int idCliente;
public int idMaster;
public int keyPapa;

public int getKeyPapa() {
	return keyPapa;
}
public void setKeyPapa(int keyPapa) {
	this.keyPapa = keyPapa;
}
public int getIdCliente() {
	return idCliente;
}
public void setIdCliente(int idCliente) {
	this.idCliente = idCliente;
}
public int getIdMaster() {
	return idMaster;
}
public void setIdMaster(int idMaster) {
	this.idMaster = idMaster;
}
@Override
public String toString() {
	return "cliente [idCliente=" + idCliente + ", idMaster=" + idMaster + ", keyPapa=" + keyPapa + "]";
}


}
