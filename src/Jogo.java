
import java.util.Random;
import java.util.Scanner;

public class Jogo {
    private Jogador jogador1;
    private Jogador jogador2;
    private Tabuleiro tabuleiro;
    private Scanner scanner;
    private Random random;

    public Jogo() {
        jogador1 = new Jogador("Jogador 1");
        jogador2 = new Jogador("Jogador 2");
        tabuleiro = new Tabuleiro();
        scanner = new Scanner(System.in);
        random = new Random();

        distribuirCartas();
        aplicarSwap();
    }

    private void distribuirCartas() {
        for (int i = 0; i < 5; i++) {
            jogador1.adicionarCarta(criarCarta());
            jogador2.adicionarCarta(criarCarta());
        }
    }

    private Carta criarCarta() {
        int cima = random.nextInt(11);
        int baixo = random.nextInt(11);
        int esquerda = random.nextInt(11);
        int direita = random.nextInt(11);

        return new Carta(cima, baixo, esquerda, direita);
    }

    private void aplicarSwap() {
        int indice1 = random.nextInt(jogador1.getMao().size());
        int indice2 = random.nextInt(jogador2.getMao().size());

        Carta carta1 = jogador1.getMao().get(indice1);
        Carta carta2 = jogador2.getMao().get(indice2);

        jogador1.substituirCarta(indice1, carta2);
        jogador2.substituirCarta(indice2, carta1);

        System.out.println(
            "Regra Swap: uma carta foi trocada entre os jogadores."
        );
    }

    public void iniciar() {
        for (int turno = 0; turno < 9; turno++) {
            Jogador atual;

            if (turno % 2 == 0) {
                atual = jogador1;
            } else {
                atual = jogador2;
            }

            if (turno > 0) {
                System.out.println();
                System.out.println("Passe o controle para " + atual.getNome() + ".");
                System.out.println("Pressione ENTER para continuar...");
                scanner.nextLine();
            }

            System.out.println();
            tabuleiro.exibir();
            System.out.println();

            mostrarMaos();

            System.out.println("Vez de: " + atual.getNome());
            System.out.println("Pontuacao: " + atual.getPontuacao());

            int linha;
            int coluna;

            while (true) {
                linha = lerInteiro("Linha (1 a 3): ") - 1;
                coluna = lerInteiro("Coluna (1 a 3): ") - 1;

                if (linha < 0 || linha > 2
                        || coluna < 0 || coluna > 2) {
                    System.out.println(
                        "Escolha uma linha e coluna de 1 a 3."
                    );
                } else if (tabuleiro.posicaoLivre(linha, coluna) == false) {
                    System.out.println("Essa posicao ja esta ocupada.");
                } else {
                    break;
                }
            }

            // Regra Order: jogar sempre a primeira carta da mao.
            Carta carta = atual.jogarCarta(0);

            tabuleiro.colocarCarta(linha, coluna, carta, atual);

            capturarVizinhos(linha, coluna, atual, carta);
        }

        System.out.println();
        System.out.println("========== RESULTADO FINAL ==========");
        tabuleiro.exibir();

        mostrarResultado();
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private void mostrarMaos() {
        mostrarMao(jogador1);
        mostrarMao(jogador2);
    }

    private void mostrarMao(Jogador jogador) {
        System.out.println();
        System.out.println("Cartas de " + jogador.getNome() + ":");

        int numero = 1;

        for (Carta carta : jogador.getMao()) {
            System.out.println("Carta " + numero + ":");
            System.out.println(carta);
            numero++;
        }
    }

    private void capturarVizinhos(
            int linha, int coluna, Jogador atual, Carta carta) {

        int iguais = 0;

        // Conta quantas bordas iguais existem ao redor da carta.
        if (ehIgual(linha - 1, coluna, atual,
                carta.getCima(), 1)) {
            iguais++;
        }

        if (ehIgual(linha + 1, coluna, atual,
                carta.getBaixo(), 0)) {
            iguais++;
        }

        if (ehIgual(linha, coluna - 1, atual,
                carta.getEsquerda(), 3)) {
            iguais++;
        }

        if (ehIgual(linha, coluna + 1, atual,
                carta.getDireita(), 2)) {
            iguais++;
        }

        // Verifica a carta que esta acima.
        verificarCaptura(
            linha - 1, coluna, atual, carta.getCima(), 1, iguais
        );

        // Verifica a carta que esta abaixo.
        verificarCaptura(
            linha + 1, coluna, atual, carta.getBaixo(), 0, iguais
        );

        // Verifica a carta que esta a esquerda.
        verificarCaptura(
            linha, coluna - 1, atual, carta.getEsquerda(), 3, iguais
        );

        // Verifica a carta que esta a direita.
        verificarCaptura(
            linha, coluna + 1, atual, carta.getDireita(), 2, iguais
        );
    }

    private boolean ehIgual(
            int linha, int coluna, Jogador atual,
            int valorNovo, int ladoVizinho) {

        if (linha >= 0 && linha < 3
                && coluna >= 0 && coluna < 3) {

            Carta vizinha = tabuleiro.getCarta(linha, coluna);
            Jogador dono = tabuleiro.getDono(linha, coluna);

            if (vizinha != null && dono != null) {
                int valorVizinho = obterValor(vizinha, ladoVizinho);

                if (valorNovo == valorVizinho) {
                    return true;
                }
            }
        }

        return false;
    }

    private void verificarCaptura(
            int linha, int coluna, Jogador atual,
            int valorNovo, int ladoVizinho, int iguais) {

        if (linha >= 0 && linha < 3
                && coluna >= 0 && coluna < 3) {

            Carta vizinha = tabuleiro.getCarta(linha, coluna);
            Jogador dono = tabuleiro.getDono(linha, coluna);

            if (vizinha != null && dono != null && dono != atual) {
                int valorVizinho = obterValor(vizinha, ladoVizinho);

                boolean maior = valorNovo > valorVizinho;
                boolean mesmaBorda = valorNovo == valorVizinho;
                System.out.println("Posicao: " + linha + ", " + coluna);
                System.out.println("Valor novo: " + valorNovo);
                System.out.println("Valor vizinho: " + valorVizinho);
                System.out.println("Iguais: " + iguais);
                System.out.println("Maior: " + maior);
                System.out.println("Mesma borda: " + mesmaBorda);
                if (maior || (iguais >= 2 && mesmaBorda)) {
                    tabuleiro.alterarDono(linha, coluna, atual);

                    atual.ganharPonto();
                    dono.perderPonto();

                    if (iguais >= 2 && mesmaBorda) {
                        System.out.println(
                            "Same! Carta capturada por igualdade."
                        );
                    } else {
                        System.out.println(
                            "Carta capturada por valor maior!"
                        );
                    }
                }
            }
        }
    }

    // 0 = cima, 1 = baixo, 2 = esquerda, 3 = direita.
    private int obterValor(Carta carta, int lado) {
        if (lado == 0) {
            return carta.getCima();
        } else if (lado == 1) {
            return carta.getBaixo();
        } else if (lado == 2) {
            return carta.getEsquerda();
        } else {
            return carta.getDireita();
        }
    }
    public void testarSame() {
        tabuleiro = new Tabuleiro();

        Jogador p1 = new Jogador("Jogador 1");
        Jogador p2 = new Jogador("Jogador 2");

        // Carta adversária acima: borda inferior vale 5.
        Carta acima = new Carta(0, 5, 0, 0);
        tabuleiro.colocarCarta(0, 1, acima, p2);

        // Carta adversária à direita: borda esquerda vale 4.
        Carta direita = new Carta(0, 0, 4, 0);
        tabuleiro.colocarCarta(1, 2, direita, p2);

        // Carta nova: superior vale 5 e direita vale 4.
        Carta nova = new Carta(5, 0, 0, 4);
        tabuleiro.colocarCarta(1, 1, nova, p1);

        capturarVizinhos(1, 1, p1, nova);

        System.out.println("\nResultado do teste Same:");
        tabuleiro.exibir();

        System.out.println(
            "Cartas do Jogador 1: "
            + tabuleiro.contarCartasDoJogador(p1)
        );

        System.out.println(
            "Cartas do Jogador 2: "
            + tabuleiro.contarCartasDoJogador(p2)
        );
    }
    public void testarSameComValorMaior() {
        tabuleiro = new Tabuleiro();

        Jogador p1 = new Jogador("Jogador 1");
        Jogador p2 = new Jogador("Jogador 2");

        // Carta adversária acima: valor inferior = 5.
        Carta acima = new Carta(0, 5, 0, 0);
        tabuleiro.colocarCarta(0, 1, acima, p2);

        // Carta adversária à esquerda: valor direito = 2.
        Carta esquerda = new Carta(0, 0, 0, 2);
        tabuleiro.colocarCarta(1, 0, esquerda, p2);

        // Carta adversária à direita: valor esquerdo = 4.
        Carta direita = new Carta(0, 0, 4, 0);
        tabuleiro.colocarCarta(1, 2, direita, p2);

        // Carta nova: cima = 5, esquerda = 7 e direita = 4.
        Carta nova = new Carta(5, 0, 0, 5);
        tabuleiro.colocarCarta(1, 1, nova, p1);

        capturarVizinhos(1, 1, p1, nova);

        System.out.println("\nResultado do teste Same com valor maior:");
        tabuleiro.exibir();

        System.out.println(
            "Cartas do Jogador 1: "
            + tabuleiro.contarCartasDoJogador(p1)
        );

        System.out.println(
            "Cartas do Jogador 2: "
            + tabuleiro.contarCartasDoJogador(p2)
        );

        System.out.println(
            "Pontuacao do Jogador 1: " + p1.getPontuacao()
        );

        System.out.println(
            "Pontuacao do Jogador 2: " + p2.getPontuacao()
        );
    }
    
    public void testarSameComCartaNossa() {
        tabuleiro = new Tabuleiro();

        Jogador p1 = new Jogador("Jogador 1");
        Jogador p2 = new Jogador("Jogador 2");

        // Carta adversária acima: borda inferior vale 5
        Carta acima = new Carta(0, 5, 0, 0);
        tabuleiro.colocarCarta(0, 1, acima, p2);

        // Nossa carta à esquerda: borda direita vale 5
        Carta esquerda = new Carta(0, 0, 0, 5);
        tabuleiro.colocarCarta(1, 0, esquerda, p1);

        // Carta adversária à direita: borda esquerda vale 5
        Carta direita = new Carta(0, 0, 5, 0);
        tabuleiro.colocarCarta(1, 2, direita, p2);

        // Nova carta no centro: cima 5 e esquerda 5
        Carta nova = new Carta(5, 0, 5, 0);
        tabuleiro.colocarCarta(1, 1, nova, p1);

        capturarVizinhos(1, 1, p1, nova);

        System.out.println("\nResultado do teste Same com carta nossa:");
        tabuleiro.exibir();

        System.out.println("Cartas do Jogador 1: "
                + tabuleiro.contarCartasDoJogador(p1));

        System.out.println("Cartas do Jogador 2: "
                + tabuleiro.contarCartasDoJogador(p2));
    }

    private void mostrarResultado() {
        int casas1 = tabuleiro.contarCartasDoJogador(jogador1);
        int casas2 = tabuleiro.contarCartasDoJogador(jogador2);
        

        System.out.println(
            jogador1.getNome() + " controla " + casas1
            + " posicoes e tem " + jogador1.getPontuacao() + " pontos."
        );

        System.out.println(
            jogador2.getNome() + " controla " + casas2
            + " posicoes e tem " + jogador2.getPontuacao() + " pontos."
        );

        if (casas1 > casas2) {
            System.out.println("Jogador 1 venceu!");
        } else if (casas2 > casas1) {
            System.out.println("Jogador 2 venceu!");
        } else {
            System.out.println("A partida terminou empatada.");
        }
    }
}
