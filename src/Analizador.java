public class Analizador {
    public static Resultado analizar(String texto, int inicio, int fin, String palabra, String secuencia) {
        long palabras = 0;
        long apariciones = 0;
        long secuencias = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = inicio; i < fin; i++) {
            char c = texto.charAt(i);

            if (Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            } else if (sb.length() > 0) {

                String n = sb.toString();
                palabras++;
                if (n.equals(palabra)) {
                    apariciones++;
                }
                if (n.contains(secuencia)) {
                    secuencias++;
                }
                sb.setLength(0);
            }
        }
        if (sb.length() > 0) {
            String n = sb.toString();
            palabras++;
            if (n.equals(palabra)) {
                apariciones++;
            }
            if (n.contains(secuencia)) {
                secuencias++;
            }
        }
        return new Resultado(palabras, apariciones, secuencias);
    }

    public static Resultado analizarParalelo(String texto, int numHilos, String palabra, String secuencia) throws InterruptedException {

        int[] posiciones = new int[numHilos + 1];
        int tamanoTexto = texto.length() / numHilos;
        posiciones[0] = 0;
        posiciones[numHilos] = texto.length();
        for (int k = 1; k < numHilos; k++) {
            int corte = k * tamanoTexto;
            while (corte < texto.length() && Character.isLetter(texto.charAt(corte))) {
                corte++;
            }
            posiciones[k] = corte;
        }
        AnalizadorHilos[] hilos = new AnalizadorHilos[numHilos];
        for (int i = 0; i < numHilos; i++) {
            hilos[i] = new AnalizadorHilos(texto, posiciones[i], posiciones[i + 1], palabra, secuencia);
            hilos[i].start();
        }
        Resultado total = new Resultado(0, 0, 0);
        for (int i = 0; i < numHilos; i++) {
            hilos[i].join();
            total = total.sumar(hilos[i].getResultado());
        }


        return total;
    }
}

