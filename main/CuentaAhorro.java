package main;

public class CuentaAhorro extends Cuenta {

	public CuentaAhorro(int numCuenta, String moneda) {
		super(numCuenta, moneda);
	}

	@Override
	public boolean retirar(double monto) {
		if (monto > 0 && saldo >= monto) {
			saldo -= monto;
			return true;
		}
		return false;
	}
	
}