package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Banco banco = new Banco();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        precargarDatosPrueba();
        ejecutarMenu();
    }

    private static void precargarDatosPrueba() {
    	
        Cliente cliente1 = new Cliente(1);
        cliente1.agregarCuenta(new CuentaCorriente(101, 50000, "CLP"));
        cliente1.agregarCuenta(new CuentaAhorro(102, "USD"));
        banco.agregarCliente(cliente1);

        Cliente cliente2 = new Cliente(2);
        cliente2.agregarCuenta(new CuentaAhorro(201, "CLP"));
        banco.agregarCliente(cliente2);
        
        // Damos un saldo inicial al cliente 1 para que pueda transferir
        banco.ingresarDinero(1, 101, 200000);
        
        System.out.println("--- Sistema Inicializado ---");
        System.out.println("Datos de prueba cargados:");
        System.out.println("Cliente 1 | Cuentas: 101 (Corriente CLP), 102 (Ahorro USD)");
        System.out.println("Cliente 2 | Cuentas: 201 (Ahorro CLP)");
        System.out.println("----------------------------\n");
    }

    private static void ejecutarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== ALKE WALLET - MENÚ PRINCIPAL ===");
            System.out.println("1. Revisar Saldo");
            System.out.println("2. Ingresar Dinero");
            System.out.println("3. Transferir Dinero");
            System.out.println("4. Simulador de Conversión de Moneda");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        manejarRevisarSaldo();
                        break;
                    case 2:
                        manejarIngresoDinero();
                        break;
                    case 3:
                        manejarTransferencia();
                        break;
                    case 4:
                        manejarConversion();
                        break;
                    case 5:
                        salir = true;
                        System.out.println("Gracias por usar Alke Wallet. ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor ingrese un número válido.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Operación denegada: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
    }

    private static void manejarRevisarSaldo() {
        System.out.print("Ingrese número de cliente: ");
        int numCliente = scanner.nextInt();
        System.out.print("Ingrese número de cuenta: ");
        int numCuenta = scanner.nextInt();

        double saldo = banco.revisarSaldo(numCliente, numCuenta);
        System.out.println("=> El saldo actual es: " + saldo);
    }

    private static void manejarIngresoDinero() {
        System.out.print("Ingrese número de cliente: ");
        int numCliente = scanner.nextInt();
        System.out.print("Ingrese número de cuenta: ");
        int numCuenta = scanner.nextInt();
        System.out.print("Ingrese el monto a depositar: ");
        double monto = scanner.nextDouble();

        boolean exito = banco.ingresarDinero(numCliente, numCuenta, monto);
        
        if (exito) {
            System.out.println("=> Depósito realizado con éxito.");
        } else {
            System.out.println("=> Fallo al realizar el depósito (verifique que el monto sea mayor a 0).");
        }
    }

    private static void manejarTransferencia() {
        System.out.println("--- Origen ---");
        System.out.print("Número de cliente origen: ");
        int clienteOrigen = scanner.nextInt();
        System.out.print("Número de cuenta origen: ");
        int cuentaOrigen = scanner.nextInt();

        System.out.println("--- Destino ---");
        System.out.print("Número de cliente destino: ");
        int clienteDestino = scanner.nextInt();
        System.out.print("Número de cuenta destino: ");
        int cuentaDestino = scanner.nextInt();

        System.out.print("Monto a transferir: ");
        double monto = scanner.nextDouble();

        boolean exito = banco.transferirDinero(clienteOrigen, cuentaOrigen, clienteDestino, cuentaDestino, monto);
        if (exito) {
            System.out.println("=> Transferencia completada exitosamente (Impuestos aplicados si hubo conversión).");
        } else {
            System.out.println("=> La transferencia falló. Verifique sus fondos y límites de descubierto.");
        }
    }

    private static void manejarConversion() {
        System.out.print("Moneda de origen (ej. USD, CLP, EUR): ");
        String origen = scanner.next();
        System.out.print("Moneda de destino (ej. USD, CLP, EUR): ");
        String destino = scanner.next();
        System.out.print("Monto a calcular: ");
        double monto = scanner.nextDouble();

        double resultado = banco.convertirMoneda(origen, destino, monto);
        System.out.println("=> El monto convertido (con tax de 1% aplicado si aplica) es: " + resultado);
    }
}