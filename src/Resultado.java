public class Resultado {

    private final long palabras;
    private final long apariciones;
    private final long secuencias;

    public Resultado(long palabras, long apariciones, long secuencias) {
        this.palabras = palabras;
        this.apariciones = apariciones;
        this.secuencias = secuencias;
    }

    public long getPalabras() {
        return palabras;
    }

    public long getApariciones() {
        return apariciones;
    }

    public long getSecuencias() {
        return secuencias;
    }

    public Resultado sumar(Resultado resultado) {
        return new Resultado(palabras + resultado.palabras, apariciones + resultado.apariciones, secuencias + resultado.secuencias);
    }

    @Override
    public String toString() {
        return "Resultado{" + "palabras=" + palabras + ", apariciones=" + apariciones + ", secuencias=" + secuencias + '}';
    }
}
