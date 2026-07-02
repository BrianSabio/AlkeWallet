package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Banco;
import main.Cliente;
import main.Cuenta;
import main.CuentaAhorro;
import main.CuentaCorriente;

class BancoTest {

	Banco banco;
	
	@BeforeEach
	void setUpBeforeEachTest() throws Exception {
		banco = new Banco();
	}

	@AfterEach
	void tearDownAfterEachTest() throws Exception {
		banco = null;
	}

	@Test
	void alCrearBanco_laListaDeClientesDebeEstarVacia() {
		
        // Act
        ArrayList<Cliente> listaClientes = banco.buscarClientes();

        // Assert
        assertNotNull(listaClientes, "La lista de clientes debe estar inicializada");
        assertTrue(listaClientes.isEmpty(), "La lista de clientes debe nacer vacía");
    }
	
	@Test
	void alBuscarClienteExistente_debeRetornarCliente()
	{
		// Arrange
		Cliente cliente = new Cliente(123);
		banco.agregarCliente(cliente);
		
		// Act
        Cliente resultado = banco.buscarCliente(123);

        // Assert
        assertNotNull(resultado, "No debe retornar null");
        assertEquals(cliente, resultado, "Debe retornar el cliente instanciado");
	}
	
	@Test
	void alBuscarClienteInexistente_debeRetornarFalse()
	{
		// Act
        Cliente resultado = banco.buscarCliente(123);

        // Assert
        assertNull(resultado, "Debe retornar null");
	}
	
	@Test
    void alIngresarMontoPositivo_debeDevolverTrue() {
		
		// Arrange
		Cliente cliente = new Cliente(123);
		Cuenta cuenta = new CuentaCorriente(1, 100000, "CLP"); 
		cliente.agregarCuenta(cuenta);
		banco.agregarCliente(cliente);
    		
    		// Act
    		boolean resultado = banco.ingresarDinero(123, 1, 150000);
    		double saldoActual = banco.revisarSaldo(123, 1);
    	
    		// Assert
    		assertTrue(resultado, "El valor de retorno debe ser 'true'");
    		assertEquals(150000.0, saldoActual, "El saldo de la cuenta debe ser 150000.0");
    }
	
	@Test
    void alIngresarMontoInvalido_debeDevolverFalse() {
		
		// Arrange
		Cliente cliente = new Cliente(123);
		Cuenta cuenta1 = new CuentaCorriente(1, 100000, "CLP"); 
		Cuenta cuenta2 = new CuentaAhorro(2, "CLP");
		cliente.agregarCuenta(cuenta1);
		cliente.agregarCuenta(cuenta2);
		banco.agregarCliente(cliente);
    		
    		// Act
    		boolean resultado1 = banco.ingresarDinero(123, 1, 0);          // Se ingresa un monto de $0
    		boolean resultado2 = banco.ingresarDinero(123, 2, -150000);    // Se ingresa un monto negativo
    		double saldoActual1 = banco.revisarSaldo(123, 1);
    		double saldoActual2 = banco.revisarSaldo(123, 2);
    	
    		// Assert
    		assertFalse(resultado1, "El valor de retorno debe ser 'false'");
    		assertFalse(resultado2, "El valor de retorno debe ser 'false'");
    		assertEquals(0.0, saldoActual1, "El saldo de la cuenta debe ser 0.0");
    		assertEquals(0.0, saldoActual2, "El saldo de la cuenta debe ser 0.0");
    }
	
	@Test
	void alRetirarMontoValido_debeDevolverTrue() {
		
		// Arrange
		Cliente cliente1 = new Cliente(123);
		Cliente cliente2 = new Cliente(456);
		Cuenta cuenta1 = new CuentaAhorro(1, "CLP");
		Cuenta cuenta2 = new CuentaAhorro(2, "CLP");
		cliente1.agregarCuenta(cuenta1);
		cliente2.agregarCuenta(cuenta2);
		banco.agregarCliente(cliente1);
		banco.agregarCliente(cliente2);
		banco.ingresarDinero(123, 1, 100000);
		
		// Act
		boolean resultado = banco.transferirDinero(123, 1, 456, 2, 50000);
		double saldoCliente1 = banco.revisarSaldo(123, 1);
		double saldoCliente2 = banco.revisarSaldo(456, 2);
		
		// Assert
		assertTrue(resultado, "El valor de retorno debe ser 'true'");
		assertEquals(50000.0, saldoCliente1, "El saldo de la cuenta del cliente1 debe ser 50000.0");
		assertEquals(50000.0, saldoCliente2, "El saldo de la cuenta del cliente2 debe ser 50000.0");
	}
	
	@Test
	void alTransferirConFondosInsuficientes_debeDevolverFalseYNoAlterarSaldos() {
		
		// Arrange
		Cliente cliente1 = new Cliente(123);
		Cliente cliente2 = new Cliente(456);
		Cuenta cuenta1 = new CuentaAhorro(1, "CLP");
		Cuenta cuenta2 = new CuentaAhorro(2, "CLP");
		cliente1.agregarCuenta(cuenta1);
		cliente2.agregarCuenta(cuenta2);
		banco.agregarCliente(cliente1);
		banco.agregarCliente(cliente2);
		banco.ingresarDinero(123, 1, 100000);
				
		// Act
		boolean resultado = banco.transferirDinero(123, 1, 456, 2, 150000);
		double saldoCliente1 = banco.revisarSaldo(123, 1);
		double saldoCliente2 = banco.revisarSaldo(456, 2);
		
		// Assert
		assertFalse(resultado, "El valor de retorno debe ser 'false'");
		assertEquals(100000.0, saldoCliente1, "El saldo de la cuenta del cliente1 debe ser 100000.0");
		assertEquals(0.0, saldoCliente2, "El saldo de la cuenta del cliente2 debe ser 0.0");
	}
	
	@Test
	void alConvertirMoneda_DeUSDaCLP_debeCalcularMontoCorrecto() {
		
		// Act
		double resultado = banco.convertirMoneda("USD", "CLP", 50.0);
		
		// Assert
		assertEquals(49500.0, resultado, "50 USD (50000) menos 1% de impuesto deben ser 49500.0 CLP");
	}
	
	@Test
	void alConvertirMoneda_DeEURaCLP_debeCalcularMontoCorrecto() {
		
		// Act
		double resultado = banco.convertirMoneda("EUR", "CLP", 10.0);
		
		// Assert
		assertEquals(10890.0, resultado, "10 EUR (11000) menos 1% de impuesto deben ser 10890.0 CLP");
	}
	
	@Test
	void alConvertirMoneda_DeEURaUSD_debeCalcularMontoCorrecto() {
		
		// Act
		double resultado = banco.convertirMoneda("EUR", "USD", 100.0);
		
		// Assert
		assertEquals(108.9, resultado, 0.001, "100 EUR (110) menos 1% de impuesto deben ser 108.9 USD");
	}
	
	@Test
	void alConvertirMoneda_MismaMoneda_debeRetornarMismoMonto() {
		
		// Act
		double resultado = banco.convertirMoneda("CLP", "CLP", 25000.0);
		
		// Assert
		assertEquals(25000.0, resultado, "Si la moneda origen y destino son iguales, el monto no debe cambiar");
	}
}