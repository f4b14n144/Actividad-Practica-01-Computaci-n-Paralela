public class AnalizadorHilos extends Thread{

    private final String texto;
    private Resultado resultado;
    private final int inicio;
    private final int fin;
    private final String palabra;
    private final String secuencia;

    public AnalizadorHilos(String texto, int inicio, int fin, String palabra, String secuencia) {
        this.texto = texto;
        this.inicio = inicio;
        this.fin = fin;
        this.palabra = palabra;
        this.secuencia = secuencia;
    }
    @Override
    public void run() {
        resultado = Analizador.analizar(texto, inicio, fin, palabra, secuencia);
    }
    public Resultado getResultado() { return resultado; }

}
