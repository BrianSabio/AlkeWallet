package main;

import java.util.ArrayList;

public class Cliente {
	
	private Integer numCliente;
	private ArrayList<Cuenta> listaCuentas;
	
	public Cliente(Integer numCliente) {
		this.numCliente = numCliente;
		listaCuentas = new ArrayList<Cuenta>();
	}

	public Integer getNumCliente() {
		return numCliente;
	}
	
	public boolean agregarCuenta(Cuenta cuenta) {
		if (cuenta != null) {
			listaCuentas.add(cuenta);
			return true;
		}
		return false;
	}
	
	public ArrayList<Cuenta> getListaCuentas() {
		return listaCuentas;
	}
	
	public Cuenta getCuenta(Integer numCuenta) {
		for (Cuenta cuenta : listaCuentas) {
		    if (cuenta.getNumCuenta().equals(numCuenta)) {
		        return cuenta;
		    }
		}
		return null; 
	}

}
