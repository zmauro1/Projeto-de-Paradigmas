
public class Tabuleiro {
    private Carta[][] cartas;
    private Jogador[][] donos;

    public Tabuleiro() {
        cartas = new Carta[3][3];
        donos = new Jogador[3][3];
    }

    public boolean posicaoLivre(int linha, int coluna) {
        return cartas[linha][coluna] == null;
    }

    public void alterarDono(int linha, int coluna, Jogador novoDono) {
        donos[linha][coluna] = novoDono;
    }

    public void colocarCarta(int linha, int coluna, Carta carta, Jogador jogador) {
        if (posicaoLivre(linha, coluna)) {
            cartas[linha][coluna] = carta;
            donos[linha][coluna] = jogador;
        }
    }

    public Carta getCarta(int linha, int coluna) {
        return cartas[linha][coluna];
    }

    public Jogador getDono(int linha, int coluna) {
        return donos[linha][coluna];
    }

    public void exibir() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cartas[i][j] == null) {
                    System.out.print("[   ] ");
                } else {
                    System.out.print("[ X ] ");
                }
            }
            System.out.println();
        }
    }
}
