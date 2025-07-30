import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Battlefield battlefield;
    private final TurnManager turnManager;
    private final Scanner scanner;
    private final Random random = new Random();
    private String modoDeJogo; // Guarda o modo escolhido

    public Game() {
        Logger.limparLogs();
        Logger.log("Novo objeto de Jogo criado.");
        this.scanner = new Scanner(System.in);
        this.battlefield = configurarBatalha();
        this.turnManager = new TurnManager(this.battlefield);
    }

    public void iniciar() {
        Logger.log("A Batalha Começou!");
        System.out.println();
        imprimirDevagar("======= A BATALHA COMEÇOU! =======", 30);

        while (!turnManager.isBattleOver()) {
            battlefield.exibirTabuleiro();
            Character personagemAtual = turnManager.getCurrentCharacter();

            if (personagemAtual.getHP() <= 0) {
                turnManager.nextTurn();
                continue;
            }

            if (personagemAtual instanceof Hero) {
                if ("INTERATIVO".equals(this.modoDeJogo)) {
                    // Chama o método de objeto (não-static)
                    executarTurnoHeroi((Hero) personagemAtual);
                } else { // MODO AUTOMÁTICO
                    executarTurnoHeroiAI((Hero) personagemAtual);
                }
            } else if (personagemAtual instanceof Monster) {
                // Chama o método de objeto (não-static)
                executarTurnoMonstro((Monster) personagemAtual);
            }

            if (turnManager.isBattleOver()) {
                break;
            }

            if ("INTERATIVO".equals(this.modoDeJogo)) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            } else {
                try { Thread.sleep(1500); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            turnManager.nextTurn();
        }
        finalizarBatalha();
    }

    private Battlefield configurarBatalha() {
        final int LARGURA_CAMPO = 10;
        final int ALTURA_CAMPO = 5;
        final int NUMERO_DE_HEROIS = 2;
        Battlefield campo = new Battlefield(LARGURA_CAMPO, ALTURA_CAMPO);

        for (int i = 0; i < NUMERO_DE_HEROIS; i++) {
            // Chama o método de objeto (não-static)
            posicionarAleatoriamente(CharacterGenerator.gerarHeroiAleatorio(), campo, LARGURA_CAMPO, ALTURA_CAMPO);
        }

        imprimirDevagar("Escolha o modo de jogo:", 30);
        System.out.println("1. Interativo (Você controla os heróis)");
        System.out.println("2. Simulação Automática (IA vs IA)");
        System.out.print("Sua escolha: ");
        int escolhaModo = scanner.nextInt();
        scanner.nextLine();
        this.modoDeJogo = (escolhaModo == 2) ? "AUTOMATICO" : "INTERATIVO";
        Logger.log("Modo de jogo selecionado: " + this.modoDeJogo);

        imprimirDevagar("Escolha a dificuldade:", 30);
        System.out.println("1. Fácil");
        System.out.println("2. Médio");
        System.out.println("3. Difícil");
        System.out.print("Sua escolha: ");
        int escolhaDif = scanner.nextInt();
        scanner.nextLine();
        switch (escolhaDif) {
            case 1:
                Logger.log("Dificuldade selecionada: Fácil.");
                for (int i = 0; i < 3; i++) posicionarAleatoriamente(CharacterGenerator.gerarMonstroFacil(), campo, LARGURA_CAMPO, ALTURA_CAMPO);
                break;
            case 3:
                Logger.log("Dificuldade selecionada: Difícil.");
                for (int i = 0; i < 2; i++) posicionarAleatoriamente(CharacterGenerator.gerarMonstroMedio(), campo, LARGURA_CAMPO, ALTURA_CAMPO);
                posicionarAleatoriamente(CharacterGenerator.gerarMonstroDificil(), campo, LARGURA_CAMPO, ALTURA_CAMPO);
                break;
            case 2:
            default:
                Logger.log("Dificuldade selecionada: Médio.");
                for (int i = 0; i < 3; i++) posicionarAleatoriamente(CharacterGenerator.gerarMonstroMedio(), campo, LARGURA_CAMPO, ALTURA_CAMPO);
                break;
        }
        return campo;
    }

    private void finalizarBatalha() {
        System.out.println();
        imprimirDevagar("======= A BATALHA TERMINOU! =======", 30);

        if (battlefield.getNumeroDeHerois() > 0) {
            Logger.log("Os Heróis venceram a batalha!");
            imprimirDevagar("OS HERÓIS VENCERAM!", 100);
        } else {
            Logger.log("Os Monstros venceram a batalha!");
            imprimirDevagar("OS MONSTROS VENCERAM!", 100);
        }

        battlefield.exibirTabuleiro();
        Logger.exibirLogs();
        scanner.close();
    }

    // --- MÉTODOS AUXILIARES CORRIGIDOS (NÃO SÃO MAIS STATIC) ---

    private void executarTurnoHeroi(Hero heroi) {
        String cabecalho = String.format("\n--- Turno de %s (%s | HP: %d/%d) ---", heroi.getName(), heroi.getType(), heroi.getHP(), heroi.getMaxHP());
        imprimirDevagar(cabecalho, 20);

        System.out.println("O que você quer fazer?");
        System.out.println("1. Atacar");
        System.out.println("2. Mover");
        System.out.println("3. Passar o turno");
        System.out.print("Sua escolha: ");
        int escolha = this.scanner.nextInt();
        this.scanner.nextLine();

        switch (escolha) {
            case 1:
                List<Monster> monstrosVivos = this.battlefield.getMonsters();
                if (monstrosVivos.isEmpty()) { System.out.println("Não há alvos para atacar!"); break; }
                System.out.println("Escolha um alvo:");
                for (int i = 0; i < monstrosVivos.size(); i++) {
                    Monster monstro = monstrosVivos.get(i);
                    System.out.printf("%d. %s (HP: %d/%d)\n", i + 1, monstro.getName(), monstro.getHP(), monstro.getMaxHP());
                }
                System.out.print("Alvo: ");
                int escolhaAlvo = this.scanner.nextInt();
                this.scanner.nextLine();
                if (escolhaAlvo > 0 && escolhaAlvo <= monstrosVivos.size()) {
                    Monster alvo = monstrosVivos.get(escolhaAlvo - 1);
                    heroi.realizarAtaque(alvo);
                    if (alvo.getHP() <= 0) {
                        Logger.log("!!! " + alvo.getName() + " foi derrotado! !!!");
                        this.battlefield.removerPersonagem(alvo);
                        this.turnManager.removeCharacter(alvo);
                    }
                } else {
                    System.out.println("Escolha de alvo inválida.");
                    Logger.log(heroi.getName() + " perdeu o turno.");
                }
                break;
            case 2:
                System.out.println("Digite as coordenadas para mover (X Y):");
                System.out.print("Coordenadas: ");
                int novoX = this.scanner.nextInt();
                int novoY = this.scanner.nextInt();
                this.scanner.nextLine();
                if (!this.battlefield.moverPersonagem(heroi, novoX, novoY)) {
                    Logger.log(heroi.getName() + " tentou se mover, mas falhou.");
                }
                break;
            case 3:
                System.out.println(heroi.getName() + " está esperando...");
                Logger.log(heroi.getName() + " esperou e passou o turno.");
                break;
            default:
                System.out.println("Opção inválida. O herói perdeu o turno.");
                Logger.log(heroi.getName() + " se confundiu e perdeu o turno.");
                break;
        }
    }

    private void executarTurnoHeroiAI(Hero heroi) {
        String cabecalho = String.format("\n--- Turno de %s (%s | HP: %d/%d) ---", heroi.getName(), heroi.getType(), heroi.getHP(), heroi.getMaxHP());
        System.out.println(cabecalho);
        List<Monster> monstrosVivos = this.battlefield.getMonsters();
        if (!monstrosVivos.isEmpty()) {
            Monster alvo = monstrosVivos.get(0);
            int menorHP = alvo.getHP();
            for (Monster monstro : monstrosVivos) {
                if (monstro.getHP() < menorHP) {
                    menorHP = monstro.getHP();
                    alvo = monstro;
                }
            }
            System.out.printf("%s decide atacar %s!\n", heroi.getName(), alvo.getName());
            heroi.realizarAtaque(alvo);
            if (alvo.getHP() <= 0) {
                Logger.log("!!! " + alvo.getName() + " foi derrotado! !!!");
                this.battlefield.removerPersonagem(alvo);
                this.turnManager.removeCharacter(alvo);
            }
        } else {
            Logger.log(heroi.getName() + " não tem alvos para atacar.");
        }
    }

    private void executarTurnoMonstro(Monster monstro) {
        String cabecalho = String.format("\n--- Turno de %s (%s | HP: %d/%d) ---", monstro.getName(), monstro.getType(), monstro.getHP(), monstro.getMaxHP());
        imprimirDevagar(cabecalho, 20);

        List<Hero> heroisVivos = this.battlefield.getHeroes();
        if (!heroisVivos.isEmpty()) {
            Hero alvo = heroisVivos.get(random.nextInt(heroisVivos.size()));
            String acaoMonstro = String.format("%s decide atacar %s!", monstro.getName(), alvo.getName());
            imprimirDevagar(acaoMonstro, 30);
            monstro.realizarAtaque(alvo);
            if (alvo.getHP() <= 0) {
                Logger.log("!!! " + alvo.getName() + " foi derrotado! !!!");
                this.battlefield.removerPersonagem(alvo);
                this.turnManager.removeCharacter(alvo);
            }
        } else {
            Logger.log(monstro.getName() + " não tem alvos para atacar.");
        }
    }

    private void posicionarAleatoriamente(Character p, Battlefield campo, int largura, int altura) {
        boolean posicionado = false;
        while (!posicionado) {
            int x = random.nextInt(largura);
            int y = random.nextInt(altura);
            posicionado = campo.adicionarPersonagem(p, x, y);
        }
    }

    private void imprimirDevagar(String mensagem, int milissegundos) {
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
}