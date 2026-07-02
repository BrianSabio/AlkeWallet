# Informe de Pruebas y Aseguramiento de Calidad (QA)

## Resumen
El desarrollo de Alke Wallet se guio por la metodología **TDD (Test-Driven Development)**. Se escribió una suite de pruebas unitarias utilizando JUnit 5 antes de implementar la lógica de producción. Todas las pruebas actuales pasan exitosamente (100% de cobertura de casos críticos), asegurando que el software cumple con los requerimientos funcionales solicitados.

## Alcance de las Pruebas
Se validaron tanto los "caminos felices" como los casos extremos y lógicas de falla para las siguientes operativas:
* Inicialización de entidades y asignación correcta de divisas.
* Validaciones de saldos negativos y límites de descubierto (`CuentaCorriente`).
* Depósitos con montos inválidos (menores o iguales a cero).
* Transferencias con fondos insuficientes.
* Cálculos matemáticos de conversión de divisas y aplicación de impuestos transaccionales.

## Problemas Críticos Identificados y Solucionados

Durante el desarrollo impulsado por pruebas, se identificaron y mitigaron los siguientes riesgos arquitectónicos:

### 1. Falla de Concurrencia (dinero infinito)
* **Problema:** En la primera iteración de `transferirDinero`, el sistema intentaba ingresar el dinero al destino antes de retirarlo del origen. Si el retiro fallaba (por fondos insuficientes), el destino ya había recibido el dinero.
* **Solución:** Se invirtió la lógica matemática. Ahora el sistema valida obligatoriamente la salida exitosa del origen (`retiroExitoso == true`) antes de intentar cualquier inyección de saldo en el destino.

### 2. Estado Inconsistente en Conversión de Divisas (clonación en Rollback)
* **Problema:** Al implementar transferencias multidivisa (ej. USD a CLP), si la inyección de saldo final fallaba, el sistema de contingencia devolvía el dinero a la cuenta origen usando la variable `montoFinal` (ya convertida y multiplicada). Esto generaba la inyección de fondos masivos incorrectos al revertir la operación.
* **Solución:** Se refactorizó el bloque de Rollback. El orquestador ahora almacena el `monto` original inmutable y es estrictamente ese valor el que se devuelve a la cuenta de origen en caso de abortar la transacción.

### 3. Ambigüedad de Retorno en Búsquedas
* **Problema:** Los métodos como `revisarSaldo` retornaban `0` si la cuenta no existía, dificultando saber si el cliente estaba sin fondos o si la entrada era errónea.
* **Solución:** Implementación de *Defensive Programming*. Se eliminaron los retornos silenciosos y se reemplazaron por `throw new IllegalArgumentException(...)`, obligando al controlador UI a manejar el error explícitamente y mostrar el feedback correcto al usuario final.