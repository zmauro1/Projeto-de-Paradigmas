
public class Carta {
    private int cima;
    private int baixo;
    private int esquerda;
    private int direita;

    public Carta(int cima, int baixo, int esquerda, int direita) {
        this.cima = cima;
        this.baixo = baixo;
        this.esquerda = esquerda;
        this.direita = direita;
    }

    public int getCima() {
        return cima;
    }

    public int getBaixo() {
        return baixo;
    }

    public int getEsquerda() {
        return esquerda;
    }

    public int getDireita() {
        return direita;
    }

    @Override
    public String toString() {
        return "   " + cima + "   \n"
                + esquerda + "   " + direita + "\n"
                + "   " + baixo + "   ";
    }
}
