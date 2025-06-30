public class Battlefield {
    private final Character[][] board;
    private final int largura;
    private final int altura;

    public Battlefield(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.board = new Character[altura][largura];
    }


    public boolean adicionarPersonagem(Character personagem, int x, int y) {
        if (x < 0 || x >= largura || y < 0 || y >= altura) {
            System.err.println("Erro: Posição (" + x + "," + y + ") está fora do tabuleiro.");
            return false;
        }
        if (board[y][x] != null) {
            System.err.println("Erro: Posição (" + x + "," + y + ") já está ocupada.");
            return false;
        }

        board[y][x] = personagem;
        personagem.setPosX(x);
        personagem.setPosY(y);
        return true;
    }


    public boolean moverPersonagem(Character personagem, int novoX, int novoY) {
        int antigoX = personagem.getPosX();
        int antigoY = personagem.getPosY();

       board[antigoY][antigoX] = null;

        if (adicionarPersonagem(personagem, novoX, novoY)) {
            return true;
        } else {
            board[antigoY][antigoX] = personagem;
            return false;
        }
    }


    public void removerPersonagem(Character personagem) {
        int x = personagem.getPosX();
        int y = personagem.getPosY();

        if (x >= 0 && y >= 0) {
            board[y][x] = null;

            personagem.setPosX(-1);
            personagem.setPosY(-1);
        }
    }


    public void exibirTabuleiro() {
        System.out.println("\n--- CAMPO DE BATALHA ---");
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                if (board[y][x] == null) {
                    System.out.print("[ . ]");
                } else {
                     char sigla = board[y][x] instanceof Hero ? 'H' : 'M';
                    System.out.print("[ " + sigla + " ]");
                }
            }
            System.out.println();
        }
    }
}