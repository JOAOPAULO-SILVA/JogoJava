import java.util.ArrayList;
import java.util.List;

public class Battlefield {
    private final Character[][] board;
    private final int largura;
    private final int altura;

    //Lista para saber quantidade de personagens
    private final List<Hero> heroes;
    private final List<Monster> monsters;

    public Battlefield(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.board = new Character[altura][largura];

        this.heroes = new ArrayList<>();
        this.monsters = new ArrayList<>();
    }

    public boolean adicionarPersonagem(Character personagem, int x, int y) {
        if (x < 0 || x >= largura || y < 0 || y >= altura) {
            System.err.printf("Erro: Posição (%d,%d) está fora do tabuleiro.%n", x, y);
            return false;
        }
        if (board[y][x] != null) {
            System.err.printf("Erro: Posição (%d,%d) já está ocupada.%n", x, y);
            return false;
        }

        board[y][x] = personagem;
        personagem.setPosX(x);
        personagem.setPosY(y);

        if (personagem instanceof Hero) {
            heroes.add((Hero) personagem);
        } else if (personagem instanceof Monster) {
            monsters.add((Monster) personagem);
        }

        return true;
    }

    public void removerPersonagem(Character personagem) {
        int x = personagem.getPosX();
        int y = personagem.getPosY();

        if (x >= 0 && y >= 0) {
            board[y][x] = null;
            personagem.setPosX(-1);
            personagem.setPosY(-1);

            if (personagem instanceof Hero) {
                heroes.remove(personagem);
            } else if (personagem instanceof Monster) {
                monsters.remove(personagem);
            }
        }
    }

    public boolean moverPersonagem(Character personagem, int novoX, int novoY) {
        int antigoX = personagem.getPosX();
        int antigoY = personagem.getPosY();
        board[antigoY][antigoX] = null;
        if (adicionarPersonagem(personagem, novoX, novoY)) {
            if (personagem instanceof Hero) {
                heroes.remove(personagem);
            } else if (personagem instanceof Monster) {
                monsters.remove(personagem);
            }
            return true;
        } else {
            board[antigoY][antigoX] = personagem;
            return false;
        }
    }


    public void exibirTabuleiro() {
        System.out.println("\n--- CAMPO DE BATALHA ---");
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                if (board[y][x] == null) {
                    System.out.print("[ . ]");
                } else {
                    char inicial = board[y][x].getName().charAt(0);
                    System.out.printf("[ %c ]",inicial);
                }
            }
            System.out.println();
        }

        System.out.println("\n--- UNIDADES EM CAMPO ---");
        System.out.println("Heróis:");
        if (heroes.isEmpty()) {
            System.out.println("  Nenhum herói em campo.");
        } else {
            for (Hero hero : heroes) {
                System.out.printf("  [%c] %s (HP: %d/%d)\n",
                        hero.getName().charAt(0),
                        hero.getName(),
                        hero.getHP(),
                        hero.getMaxHP()); // Mostra o HP atual e o máximo
            }
        }

        System.out.println("\nMonstros:");
        if (monsters.isEmpty()) {
            System.out.println("  Nenhum monstro em campo.");
        } else {
            for (Monster monstro : monsters) {
                System.out.printf("  [%c] %s (HP: %d)\n",
                        monstro.getName().charAt(0),
                        monstro.getName(),
                        monstro.getHP());
            }
        }
        System.out.println("-------------------------");
    }

    public List<Hero> getHeroes() {
        return this.heroes;
    }
    public int getNumeroDeHerois() {
        return this.heroes.size();
    }
    public List<Monster> getMonsters() {
        return this.monsters;
    }
    public int getNumeroDeMonstros() {
        return this.monsters.size();
    }

}