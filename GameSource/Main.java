import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        Logger.limparLogs();
        Logger.log("Iniciando novo jogo dinâmico...");
        imprimirDevagar("BEM-VINDO A UMA NOVA BATALHA!", 50);

        // --- CONFIGURAÇÃO DINÂMICA DO CENÁRIO ---
        final int LARGURA_CAMPO = 10;
        final int ALTURA_CAMPO = 5;
        final int NUMERO_DE_HEROIS = 2;
        final int NUMERO_DE_MONSTROS = 3;

        Battlefield campo = new Battlefield(LARGURA_CAMPO, ALTURA_CAMPO);

        // Gera e posiciona os heróis aleatoriamente
        for (int i = 0; i < NUMERO_DE_HEROIS; i++) {
            Hero heroi = CharacterGenerator.gerarHeroiAleatorio();
            posicionarAleatoriamente(heroi, campo, LARGURA_CAMPO, ALTURA_CAMPO);
        }

        // Gera e posiciona os monstros aleatoriamente
        for (int i = 0; i < NUMERO_DE_MONSTROS; i++) {
            Monster monstro = CharacterGenerator.gerarMonstroAleatorio();
            posicionarAleatoriamente(monstro, campo, LARGURA_CAMPO, ALTURA_CAMPO);
        }

        // --- INÍCIO DA BATALHA ---
        TurnManager turnManager = new TurnManager(campo);
        Logger.log("A Batalha Começou!");
        System.out.println();
        imprimirDevagar("======= A BATALHA COMEÇOU! =======", 30);

        // O loop interativo de batalha
        while (!turnManager.isBattleOver()) {
            campo.exibirTabuleiro();
            Character personagemAtual = turnManager.getCurrentCharacter();

            if (personagemAtual.getHP() <= 0) {
                turnManager.nextTurn();
                continue;
            }

            if (personagemAtual instanceof Hero) {
                executarTurnoHeroi((Hero) personagemAtual, campo, turnManager);
            } else if (personagemAtual instanceof Monster) {
                executarTurnoMonstro((Monster) personagemAtual, campo, turnManager);
            }

            if (turnManager.isBattleOver()) {
                break;
            }

            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();

            turnManager.nextTurn();
        }

        // --- FIM DA BATALHA ---
        System.out.println();
        imprimirDevagar("======= A BATALHA TERMINOU! =======", 30);

        if (campo.getNumeroDeHerois() > 0) {
            Logger.log("Os Heróis venceram a batalha!");
            imprimirDevagar("OS HERÓIS VENCERAM!", 100);
        } else {
            Logger.log("Os Monstros venceram a batalha!");
            imprimirDevagar("OS MONSTROS VENCERAM!", 100);
        }

        campo.exibirTabuleiro();
        Logger.exibirLogs();
        scanner.close();
    }

    /**
     * Método auxiliar para posicionar um personagem em um local vago do campo.
     */
    private static void posicionarAleatoriamente(Character p, Battlefield campo, int largura, int altura) {
        boolean posicionado = false;
        while (!posicionado) {
            int x = random.nextInt(largura);
            int y = random.nextInt(altura);
            posicionado = campo.adicionarPersonagem(p, x, y);
        }
    }

    private static void imprimirDevagar(String mensagem, int milissegundos) {
        for (char caractere : mensagem.toCharArray()) {
            System.out.print(caractere);
            try {
                Thread.sleep(milissegundos);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    /**
     * Lida com o turno de um Herói, exibindo um menu de ações para o jogador.
     */
    private static void executarTurnoHeroi(Hero heroi, Battlefield campo, TurnManager turnManager) {
        String cabecalho = String.format("\n--- Turno de %s (%s | HP: %d/%d) ---", heroi.getName(), heroi.getType(), heroi.getHP(), heroi.getMaxHP());
        imprimirDevagar(cabecalho, 20);

        System.out.println("O que você quer fazer?");
        System.out.println("1. Atacar");
        System.out.println("2. Passar o turno");
        System.out.print("Sua escolha: ");

        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do scanner

        switch (escolha) {
            case 1:
                List<Monster> monstrosVivos = campo.getMonsters();
                if (monstrosVivos.isEmpty()) {
                    System.out.println("Não há alvos para atacar!");
                    break;
                }

                System.out.println("Escolha um alvo:");
                for (int i = 0; i < monstrosVivos.size(); i++) {
                    Monster monstro = monstrosVivos.get(i);
                    System.out.printf("%d. %s (HP: %d/%d)\n", i + 1, monstro.getName(), monstro.getHP(), monstro.getMaxHP());
                }
                System.out.print("Alvo: ");
                int escolhaAlvo = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer do scanner

                if (escolhaAlvo > 0 && escolhaAlvo <= monstrosVivos.size()) {
                    Monster alvo = monstrosVivos.get(escolhaAlvo - 1);
                    heroi.realizarAtaque(alvo);
                    if (alvo.getHP() <= 0) {
                        Logger.log("!!! " + alvo.getName() + " foi derrotado! !!!");
                        campo.removerPersonagem(alvo);
                        turnManager.removeCharacter(alvo);
                    }
                } else {
                    System.out.println("Escolha de alvo inválida. O herói perdeu o turno.");
                    Logger.log(heroi.getName() + " se confundiu e perdeu o turno.");
                }
                break;

            case 2:
                System.out.println(heroi.getName() + " está esperando...");
                Logger.log(heroi.getName() + " esperou e passou o turno.");
                break;

            default:
                System.out.println("Opção inválida. O herói perdeu o turno.");
                Logger.log(heroi.getName() + " se confundiu e perdeu o turno.");
                break;
        }
    }

    /**
     * Lida com o turno de um Monstro, executando uma ação de IA simples.
     */
    private static void executarTurnoMonstro(Monster monstro, Battlefield campo, TurnManager turnManager) {
        String cabecalho = String.format("\n--- Turno de %s (%s | HP: %d/%d) ---", monstro.getName(), monstro.getType(), monstro.getHP(), monstro.getMaxHP());
        imprimirDevagar(cabecalho, 20);

        List<Hero> heroisVivos = campo.getHeroes();
        if (!heroisVivos.isEmpty()) {
            Hero alvo = heroisVivos.get(random.nextInt(heroisVivos.size()));
            String acaoMonstro = String.format("%s decide atacar %s!", monstro.getName(), alvo.getName());
            imprimirDevagar(acaoMonstro, 30);

            monstro.realizarAtaque(alvo);

            if (alvo.getHP() <= 0) {
                Logger.log("!!! " + alvo.getName() + " foi derrotado! !!!");
                campo.removerPersonagem(alvo);
                turnManager.removeCharacter(alvo);
            }
        } else {
            Logger.log(monstro.getName() + " não tem alvos para atacar.");
        }
    }
}