# Alke Wallet - Documentación Técnica

## Descripción del Proyecto
Alke Wallet es una billetera digital desarrollada en Java orientada a la gestión segura de activos financieros. Permite a los usuarios crear cuentas, revisar saldos, realizar depósitos, retiros y transferencias, incluyendo un sistema de conversión de divisas (CLP, USD, EUR).

## Tecnologías Empleadas
* **Lenguaje:** Java (Paradigma Orientado a Objetos)
* Testing:** JUnit 5 (Desarrollo guiado por pruebas - TDD)
* Estructura de Datos:** Colecciones de Java (`ArrayList`)

## Arquitectura y Patrones de Diseño
El sistema fue diseñado con un fuerte enfoque en la integridad de los datos y buenas prácticas de ingeniería backend:
1. **Domain-Driven Design (DDD):** Las reglas de negocio pertenecen a las entidades. Cada `Cuenta` (Ahorro o Corriente) es responsable de autorizar o denegar sus propios retiros e ingresos según su naturaleza (ej. límites de descubierto).
2. **Inversión de Dependencias (SOLID):** La clase orquestadora (`Banco`) implementa la interfaz `IConversorMoneda` para las operaciones de divisas, desacoplando la lógica financiera global de las cuentas individuales.
3. **Fail-Fast (Falla Rápida):** Se validan parámetros y existencias al inicio de los métodos. Si un cliente o cuenta no existe, el sistema lanza una `IllegalArgumentException` inmediata, evitando colapsos por `NullPointerException` en cascada.
4. **Patrón Rollback:** Las transacciones multi-cuenta están protegidas. Si un retiro es exitoso pero el ingreso al destino falla, el sistema realiza una reversión automática (rollback) para devolver los fondos al origen y evitar dinero "en el limbo".

## Estructura del Proyecto
* `/main`: Contiene las entidades del dominio (`Cliente`, `Cuenta`, `CuentaAhorro`, `CuentaCorriente`), el orquestador (`Banco`), la interfaz (`IConversorMoneda`) y el controlador de interfaz de usuario (`Main`).
* `/tests`: Contiene la suite de pruebas unitarias implementadas con JUnit 5 (`BancoTest`, `CuentaAhorroTest`, `CuentaCorrienteTest`).

## Instrucciones de Ejecución
1. Clonar el repositorio en un entorno local.
2. Abrir el proyecto en un IDE compatible con Java (Eclipse, IntelliJ IDEA, NetBeans).
3. Asegurarse de tener configurado el JDK 11 o superior.
4. Navegar hasta el paquete `main` y ejecutar la clase `Main.java`.
5. El sistema iniciará en la consola con datos precargados para facilitar las pruebas de flujo (Clientes 1 y 2 con cuentas en CLP y USD).