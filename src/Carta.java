public class Carta {
    private int cima;
    private int baixo;
    private int esquerda;
    private int direita;

    public Carta(int cima, int baixo, int esquerda, int direita) {
        if (valorValido(cima) == false
                || valorValido(baixo) == false
                || valorValido(esquerda) == false
                || valorValido(direita) == false) {
            throw new IllegalArgumentException(
                "Os valores devem estar entre 0 e 10."
            );
        }

        this.cima = cima;
        this.baixo = baixo;
        this.esquerda = esquerda;
        this.direita = direita;
    }

    private boolean valorValido(int valor) {
        if (valor >= 0 && valor <= 10) {
            return true;
        } else {
            return false;
        }
    }

    private String formatar(int valor) {
        if (valor == 10) {
            return "A";
        } else {
            return String.valueOf(valor);
        }
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
        return "   " + formatar(cima) + "   \n"
             + formatar(esquerda) + "     " + formatar(direita) + "\n"
             + "   " + formatar(baixo) + "   ";
    }
}
