package main;

import java.util.ArrayList;

public class Banco implements IConversorMoneda {
	
	private ArrayList<Cliente> listaClientes;

	public Banco() {
		listaClientes = new ArrayList<Cliente>();
	}
	
	public ArrayList<Cliente> buscarClientes() {
		return listaClientes;
	}
	
	public Cliente buscarCliente(Integer numCliente) {

		for (Cliente cliente : listaClientes) {
		    if (cliente.getNumCliente().equals(numCliente)) {
		        return cliente;
		    }
		}
		
		return null;
	}
	
	public boolean agregarCliente(Cliente cliente) {
		if (cliente != null) {
			listaClientes.add(cliente);
			return true;
		}
		return false;
	}

	public boolean ingresarDinero(int numCliente, int numCuenta, double monto) {
		Cliente cliente = buscarCliente(numCliente);
		
		if (cliente == null) {
			throw new IllegalArgumentException("El cliente no existe");
		}
		Cuenta cuenta = cliente.getCuenta(numCuenta);
		
		if (cuenta == null) {
			throw new IllegalArgumentException("La cuenta no existe");
		}
		return cuenta.ingresar(monto);
	}

	public boolean transferirDinero(int numClienteOrigen, int numCuentaOrigen, int numClienteDestino, int numCuentaDestino, double monto) {
		Cliente clienteOrigen = buscarCliente(numClienteOrigen);
		Cliente clienteDestino = buscarCliente(numClienteDestino);
		
		if (clienteOrigen == null) {
			throw new IllegalArgumentException("El cliente de origen no existe");
		} if (clienteDestino == null) {
			throw new IllegalArgumentException("El cliente de destino no existe");
		}
		
		Cuenta cuentaOrigen = clienteOrigen.getCuenta(numCuentaOrigen);
		Cuenta cuentaDestino = clienteDestino.getCuenta(numCuentaDestino);

		if (cuentaOrigen == null) {
			throw new IllegalArgumentException("La cuenta de origen no existe");
		} if (cuentaDestino == null) {
			throw new IllegalArgumentException("La cuenta de destino no existe");
		}
		boolean retiroExitoso = cuentaOrigen.retirar(monto);
		
	    if (retiroExitoso) {
	    		double montoFinal = convertirMoneda(cuentaOrigen.getMoneda(), cuentaDestino.getMoneda(), monto);
	        boolean ingresoExitoso = cuentaDestino.ingresar(montoFinal);
	        
	        if (ingresoExitoso) {
	            return true;
	        } else {
	            // ROLLBACK
	            cuentaOrigen.ingresar(monto);
	            return false;
	        }
	    }
	    return false;
	}
	
	public double revisarSaldo(int numCliente, int numCuenta) {
		Cliente cliente = buscarCliente(numCliente);
		
		if (cliente == null) {
			throw new IllegalArgumentException("El cliente no existe");
		}
		Cuenta cuenta = cliente.getCuenta(numCuenta);
		
		if (cuenta == null) {
			throw new IllegalArgumentException("La cuenta no existe");
		}
		return cuenta.getSaldo();
	}

	@Override
	public double convertirMoneda(String monedaOrigen, String monedaDestino, double monto) {
		String origen = monedaOrigen.toUpperCase();
		String destino = monedaDestino.toUpperCase();
		
		// Si es la misma moneda, no hay conversión ni impuesto
		if (origen.equals(destino)) {
			return monto;
		}
		double montoConvertido = 0.0;
		String parDeDivisas = origen + "_" + destino;
		
		switch (parDeDivisas) {
			case "USD_CLP": montoConvertido = monto * 1000.0; break;
			case "CLP_USD": montoConvertido = monto / 1000.0; break;
			case "EUR_CLP": montoConvertido = monto * 1100.0; break;
			case "CLP_EUR": montoConvertido = monto / 1100.0; break;
			case "EUR_USD": montoConvertido = monto * 1.10; break;
			case "USD_EUR": montoConvertido = monto / 1.10; break;
			default:
				throw new IllegalArgumentException("Par de divisas no soportado o inválido: " + parDeDivisas);
		}
		
		// El banco cobra un 1% por la conversión
		double porcentajeImpuesto = 0.01;
		double montoFinalConImpuesto = montoConvertido - (montoConvertido * porcentajeImpuesto);
		
		return montoFinalConImpuesto;
	}
}