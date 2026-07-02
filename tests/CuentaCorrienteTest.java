package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Cuenta;
import main.CuentaCorriente;

class CuentaCorrienteTest {
	
	Cuenta cuenta;

	@BeforeEach
	void setUpBeforeEachTest() throws Exception {
		cuenta = new CuentaCorriente(123456, 10000, "USD");
	}
	
	@AfterEach
	void tearDownAfterEachTest() throws Exception {
		cuenta = null;
	}

	@Test
	void alCrearCuenta_LaMonedaDebeSerLaAsignada() {
		// Act
		String monedaActual = cuenta.getMoneda();

		// Assert
		assertEquals("USD", monedaActual, "La moneda de la cuenta debe ser USD");
	}

	@Test
    void alRetirarMonto_MostrarSaldoNegativoYdevuelveBool() {
    	
    			// Arrange
    			cuenta.ingresar(5000);
    	
    			// Act
    			boolean resultado = cuenta.retirar(10000);
    			double saldoActual = cuenta.getSaldo();
    		
    			// Assert
    			assertTrue(resultado, "El valor de retorno debe ser 'true'");
    			assertEquals(-5000.0, saldoActual, "El saldo de la cuenta debe ser -5000");
    }
	
	@Test
    void retirarMontoExcedidoYdevuelveBool() {
    	
    			// Arrange
    			cuenta.ingresar(1000);
    	
    			// Act
    			boolean resultado = cuenta.retirar(15000);
    			double saldoActual = cuenta.getSaldo();
    		
    			// Assert
    			assertFalse(resultado, "El valor de retorno debe ser 'false'");
    			assertEquals(1000.0, saldoActual, "El saldo de la cuenta debe ser 1000");
    }
}