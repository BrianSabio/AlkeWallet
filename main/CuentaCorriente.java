package main;

public class CuentaCorriente extends Cuenta {
	
	private double limiteDescubierto;

	public CuentaCorriente(int numCuenta, double limiteDescubierto, String moneda) {
		super(numCuenta, moneda);
		this.limiteDescubierto = limiteDescubierto;
	}

	@Override
	public boolean retirar(double monto) {
		if (monto <= 0 || saldo + limiteDescubierto < monto) {
			return false;
		}
		saldo -= monto;
		return true;
	}

}