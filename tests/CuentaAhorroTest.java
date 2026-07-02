package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Cuenta;
import main.CuentaAhorro;

class CuentaAhorroTest {
	
	Cuenta cuenta;
	
	@BeforeEach
	void setUpBeforeEachTest() throws Exception  {
		// Arrange
		cuenta = new CuentaAhorro(12345, "CLP");
	}
	
	@AfterEach
	void tearDownAfterEachTest() throws Exception {
		cuenta = null;
	}
	
	@Test
	void alCrearCuenta_monedaDebeSerLaAsignada() {
		
		// Act
		String monedaActual = cuenta.getMoneda();

		// Assert
		assertEquals("CLP", monedaActual, "La moneda de la cuenta debe ser CLP");
	}
	
    @Test
    void alCrearCuenta_saldoInicialDebeSerCero() {

        // Act
        double saldoActual = cuenta.getSaldo();

        // Assert
        assertEquals(0.0, saldoActual, "El saldo al crear la cuenta debe ser 0.0");
    }
    
    @Test
    void alIngresarMontoPositivo_devuelveBool() {
    		
    		// Act
    		boolean resultado = cuenta.ingresar(100000);
    		double saldoActual = cuenta.getSaldo();
    	
    		// Assert
    		assertTrue(resultado, "El valor de retorno debe ser 'true'");
    		assertEquals(100000.0, saldoActual, "El saldo de la cuenta debe ser 100000.0");
    }
    
    @Test
    void alIngresarMontoNegativo_FallaYNoAlteraSaldo() {
		
		// Act
		boolean resultado = cuenta.ingresar(-1);
		double saldoActual = cuenta.getSaldo();
	
		// Assert
		assertFalse(resultado, "El valor de retorno debe ser 'false'");
		assertEquals(0.0, saldoActual, "El saldo de la cuenta debe ser 0.0");
    }
    
    @Test
    void alRetirarMontoPositivo_devuelveBool() {
    	
    			// Arrange
    			cuenta.ingresar(100000);
    	
    			// Act
    			boolean resultado = cuenta.retirar(100000);
    			double saldoActual = cuenta.getSaldo();
    		
    			// Assert
    			assertTrue(resultado, "El valor de retorno debe ser 'true'");
    			assertEquals(0.0, saldoActual, "El saldo de la cuenta debe ser 0.0");
    }
    
    @Test
    void alRetirarMontoNegativo_devuelveBool() {
    	
    			// Arrange
    			cuenta.ingresar(100000);
    	
    			// Act
    			boolean resultado = cuenta.retirar(-1);
    			double saldoActual = cuenta.getSaldo();
    		
    			// Assert
    			assertFalse(resultado, "El valor de retorno debe ser 'false'");
    			assertEquals(100000.0, saldoActual, "El saldo de la cuenta debe ser 100000");
    }
    
    @Test
    void alRetirarMontoExcedido_devuelveBool() {
    	
    			// Arrange
    			cuenta.ingresar(100000);
    	
    			// Act
    			boolean resultado = cuenta.retirar(100001);
    			double saldoActual = cuenta.getSaldo();
    		
    			// Assert
    			assertFalse(resultado, "El valor de retorno debe ser 'false'");
    			assertEquals(100000.0, saldoActual, "El saldo de la cuenta debe ser 100000");
    }
    
}
