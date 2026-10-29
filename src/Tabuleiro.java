public class Tabuleiro {
    private Carta[][] cartas;
    private Jogador[][] donos;

    public Tabuleiro() {
        cartas = new Carta[3][3];
        donos = new Jogador[3][3];
    }

    public boolean posicaoLivre(int linha, int coluna) {
        if (linha < 0 || linha >= 3
                || coluna < 0 || coluna >= 3) {
            return false;
        }

        if (cartas[linha][coluna] == null) {
            return true;
        } else {
            return false;
        }
    }

    public void colocarCarta(
            int linha, int coluna, Carta carta, Jogador jogador) {

        if (posicaoLivre(linha, coluna) == false) {
            throw new IllegalArgumentException(
                "Posicao invalida ou ocupada."
            );
        }

        cartas[linha][coluna] = carta;
        donos[linha][coluna] = jogador;
    }

    public Carta getCarta(int linha, int coluna) {
        return cartas[linha][coluna];
    }

    public Jogador getDono(int linha, int coluna) {
        return donos[linha][coluna];
    }

    public void alterarDono(int linha, int coluna, Jogador novoDono) {
        donos[linha][coluna] = novoDono;
    }

    public int contarCartasDoJogador(Jogador jogador) {
        int total = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (donos[i][j] == jogador) {
                    total++;
                }
            }
        }

        return total;
    }

    
   
   
      
    public void exibir() {
        System.out.println("\n========== TABULEIRO 1==========\n");

        for (int i = 0; i < 3; i++) {
            System.out.println("+---------+---------+---------+");

            // 1ª LINHA: Dono e valor superior
            for (int j = 0; j < 3; j++) {
                Carta carta = cartas[i][j];

                if (carta == null) {
                    System.out.print("|         ");
                } else {
                    String dono = "?";

                    if (donos[i][j] != null) {
                        if (donos[i][j].getNome().equals("Jogador 1")) {
                            dono = "1";
                        } else if (donos[i][j].getNome().equals("Jogador 2")) {
                            dono = "2";
                        }
                    }

                    String cima = valor(carta.getCima());

                    System.out.printf("| P%s %s    ", dono, cima);
                }
            }
            System.out.println("|");

            // 2ª LINHA: Valores esquerdo e direito
            for (int j = 0; j < 3; j++) {
                Carta carta = cartas[i][j];

                if (carta == null) {
                    System.out.print("|         ");
                } else {
                    String esq = valor(carta.getEsquerda());
                    String dir = valor(carta.getDireita());

                    System.out.printf("|  %s   %s  ", esq, dir);
                }
            }
            System.out.println("|");

            // 3ª LINHA: Valor inferior
            for (int j = 0; j < 3; j++) {
                Carta carta = cartas[i][j];

                if (carta == null) {
                    System.out.print("|         ");
                } else {
                    String baixo = valor(carta.getBaixo());

                    System.out.printf("|    %s    ", baixo);
                }
            }
            System.out.println("|");
        }

        System.out.println("+---------+---------+---------+");
        System.out.println("Legenda: P1 = Jogador 1 | P2 = Jogador 2");
    }
    private String valor(int valor) {
        if (valor == 10) {
            return "A";
        } else {
            return String.valueOf(valor);
        }
    }
}
