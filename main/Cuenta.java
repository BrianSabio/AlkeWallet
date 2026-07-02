package main;

public abstract class Cuenta {

	protected int numCuenta;
	protected double saldo;
	protected String moneda;
	
	Cuenta(int numCuenta, String moneda) {
		this.numCuenta = numCuenta;
		this.moneda = moneda;
		this.saldo = 0.0;
	}
	
	public double getSaldo() {
		return this.saldo;
	}
	
	public Integer getNumCuenta() {
		return this.numCuenta;
	}
	
	public String getMoneda() {
		return this.moneda;
	}
	
	public boolean ingresar(double monto) {
		if (monto > 0) {
			this.saldo += monto;
			return true;
		}
		return false;
	}
	
	public abstract boolean retirar(double monto);
	
}