# Análisis de Don Quijote: secuencial vs. paralelo

Cuenta las palabras del Quijote, cuántas veces aparece "quijote" y cuántas palabras contienen "ción". Lo hace de forma secuencial y con hilos (`Thread`), y compara los tiempos con 1, 2 y 4 hilos.

## Requisitos

- JDK 11 o superior

## Cómo ejecutarlo

El texto ya está en `src/quijote.txt`. Desde la carpeta del proyecto:

```
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

También se puede abrir en IntelliJ y ejecutar `Main`.

El programa imprime los conteos y una tabla con el tiempo promedio y la aceleración, tanto para el texto original como para el texto repetido 100 veces. Tarda unos 20 segundos.
