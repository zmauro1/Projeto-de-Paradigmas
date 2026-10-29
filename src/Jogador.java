import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private List<Carta> mao;
    private int pontuacao;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<Carta>();
        this.pontuacao = 5;
    }

    public String getNome() {
        return nome;
    }

    public List<Carta> getMao() {
        return mao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void adicionarCarta(Carta carta) {
        mao.add(carta);
    }

    public Carta jogarCarta(int indice) {
        if (indice < 0 || indice >= mao.size()) {
            throw new IllegalArgumentException("Indice invalido.");
        }

        return mao.remove(indice);
    }

    public void substituirCarta(int indice, Carta carta) {
        mao.set(indice, carta);
    }

    public void ganharPonto() {
        pontuacao++;
    }

    public void perderPonto() {
        pontuacao--;
    }
}
