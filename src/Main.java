import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    static final String PALABRA = "quijote";
    static final String SECUENCIA = "ción";
    static final int REPETICIONES = 5;
    static final int CALENTAMIENTO = 2;

    public static void main(String[] args) throws Exception {
        // Se carga el archivo completo en memoria (fuera de cualquier medición)
        String original = Files.readString(Path.of("src/quijote.txt"));

        // Texto ampliado: el original repetido 100 veces, armado ANTES de medir
        StringBuilder sb = new StringBuilder(original.length() * 100);
        for (int i = 0; i < 100; i++) {
            sb.append(original);
        }
        String ampliado = sb.toString();
        sb = null; // ya no se usa; así el recolector de basura puede liberar esa memoria

        // Comprobación: la versión paralela da lo mismo que la secuencial
        System.out.println("Original (secuencial): " + Analizador.analizar(original, 0, original.length(), PALABRA, SECUENCIA));
        System.out.println("Original (4 hilos):    " + Analizador.analizarParalelo(original, 4, PALABRA, SECUENCIA));
        System.out.println("Ampliado (secuencial): " + Analizador.analizar(ampliado, 0, ampliado.length(), PALABRA, SECUENCIA));
        System.out.println("Ampliado (4 hilos):    " + Analizador.analizarParalelo(ampliado, 4, PALABRA, SECUENCIA));
        System.out.println();

        // Tabla de tiempos
        System.out.printf("%-18s | %5s | %22s | %11s%n", "Conjunto de datos", "Hilos", "Tiempo promedio (ms)", "Aceleración");
        System.out.println("-------------------+-------+------------------------+------------");
        imprimirFilas("Original", original);
        imprimirFilas("Ampliado (x100)", ampliado);
    }

    // Mide 1, 2 y 4 hilos sobre un texto e imprime una fila por cada uno
    static void imprimirFilas(String nombre, String texto) throws InterruptedException {
        double ts = medir(texto, 1); // tiempo secuencial: la base para la aceleración
        int[] hilos = {1, 2, 4};
        for (int n : hilos) {
            double tp = (n == 1) ? ts : medir(texto, n);
            double aceleracion = ts / tp;
            System.out.printf("%-18s | %5d | %22.2f | %11.2f%n", nombre, n, tp, aceleracion);
        }
    }

    // Devuelve el tiempo promedio en ms de analizar el texto con numHilos hilos.
    // Con 1 hilo usa la versión secuencial.
    static double medir(String texto, int numHilos) throws InterruptedException {
        // Calentamiento: ejecuciones que NO se cuentan, para que el JIT optimice el código
        for (int i = 0; i < CALENTAMIENTO; i++) {
            ejecutar(texto, numHilos);
        }

        double totalMs = 0;
        for (int i = 0; i < REPETICIONES; i++) {
            long inicio = System.nanoTime();
            ejecutar(texto, numHilos);
            long fin = System.nanoTime();
            totalMs += (fin - inicio) / 1_000_000.0;
        }
        return totalMs / REPETICIONES;
    }

    static Resultado ejecutar(String texto, int numHilos) throws InterruptedException {
        if (numHilos == 1) {
            return Analizador.analizar(texto, 0, texto.length(), PALABRA, SECUENCIA);
        }
        return Analizador.analizarParalelo(texto, numHilos, PALABRA, SECUENCIA);
    }
}
