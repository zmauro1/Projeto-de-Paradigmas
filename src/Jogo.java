
import java.util.Random;
import java.util.Scanner;

public class Jogo {
    private Jogador jogador1;
    private Jogador jogador2;
    private Tabuleiro tabuleiro;
    private Scanner scanner;

    public Jogo() {
        jogador1 = new Jogador("Jogador 1");
        jogador2 = new Jogador("Jogador 2");
        tabuleiro = new Tabuleiro();
        scanner = new Scanner(System.in);

        distribuirCartas();
    }

    private void distribuirCartas() {
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            jogador1.adicionarCarta(criarCarta(random));
            jogador2.adicionarCarta(criarCarta(random));
        }
    }

    private Carta criarCarta(Random random) {
        return new Carta(
                random.nextInt(11),
                random.nextInt(11),
                random.nextInt(11),
                random.nextInt(11)
        );
    }

    public void iniciar() {
        Jogador atual = jogador1;

        while (!jogador1.getMao().isEmpty()
                || !jogador2.getMao().isEmpty()) {

            tabuleiro.exibir();
            mostrarMaos();

            System.out.println("\nVez de: " + atual.getNome());
            System.out.println("Pontuação: " + atual.getPontuacao());

            System.out.print("Linha (0 a 2): ");
            int linha = scanner.nextInt();

            System.out.print("Coluna (0 a 2): ");
            int coluna = scanner.nextInt();

            if (linha < 0 || linha > 2
                    || coluna < 0 || coluna > 2) {
                System.out.println("Posição inválida!");
                continue;
            }

            if (!tabuleiro.posicaoLivre(linha, coluna)) {
                System.out.println("Posição ocupada!");
                continue;
            }

            Carta carta = atual.jogarCarta(0);

            tabuleiro.colocarCarta(linha, coluna, carta, atual);

            capturarVizinhos(linha, coluna, atual, carta);

            atual = (atual == jogador1) ? jogador2 : jogador1;
        }

        mostrarResultado();
        scanner.close();
    }

    private void mostrarMaos() {
        System.out.println("\nCartas do " + jogador1.getNome() + ":");
        for (Carta carta : jogador1.getMao()) {
            System.out.println(carta);
        }

        System.out.println("\nCartas do " + jogador2.getNome() + ":");
        for (Carta carta : jogador2.getMao()) {
            System.out.println(carta);
        }
    }

    private void capturarVizinhos(
            int linha, int coluna,
            Jogador atual, Carta carta) {

        comparar(linha - 1, coluna, carta.getCima(),
                1, 0, atual);

        comparar(linha + 1, coluna, carta.getBaixo(),
                0, 1, atual);

        comparar(linha, coluna - 1, carta.getEsquerda(),
                3, 2, atual);

        comparar(linha, coluna + 1, carta.getDireita(),
                2, 3, atual);
    }

    private void comparar(
            int linha, int coluna, int valorNovo,
            int ladoNovo, int ladoVizinho, Jogador atual) {

        if (linha < 0 || linha > 2
                || coluna < 0 || coluna > 2) {
            return;
        }

        Carta vizinha = tabuleiro.getCarta(linha, coluna);
        Jogador dono = tabuleiro.getDono(linha, coluna);

        if (vizinha == null || dono == null || dono == atual) {
            return;
        }

        int valorVizinho;

        switch (ladoVizinho) {
            case 0:
                valorVizinho = vizinha.getCima();
                break;
            case 1:
                valorVizinho = vizinha.getBaixo();
                break;
            case 2:
                valorVizinho = vizinha.getEsquerda();
                break;
            default:
                valorVizinho = vizinha.getDireita();
        }

        if (valorNovo > valorVizinho) {
            tabuleiro.alterarDono(linha, coluna, atual);
            atual.ganharPonto();
            dono.perderPonto();

            System.out.println("Carta capturada!");
        }
    }

    private void mostrarResultado() {
        System.out.println("\nFim da partida!");
        System.out.println(jogador1.getNome() + ": "
                + jogador1.getPontuacao() + " pontos");
        System.out.println(jogador2.getNome() + ": "
                + jogador2.getPontuacao() + " pontos");

        if (jogador1.getPontuacao() > jogador2.getPontuacao()) {
            System.out.println("Jogador 1 venceu!");
        } else if (jogador2.getPontuacao() > jogador1.getPontuacao()) {
            System.out.println("Jogador 2 venceu!");
        } else {
            System.out.println("Empate!");
        }
    }
}

