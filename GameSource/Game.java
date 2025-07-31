import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Battlefield battlefield;
    private final TurnManager turnManager;
    private final Scanner scanner;
    private final Random random = new Random();
    private String modoDeJogo;

    public Game() {
        Logger.limparLogs();
        Logger.log("Novo objeto de Jogo criado.");
        this.scanner = new Scanner(System.in);
        this.battlefield = configurarBatalha();
        this.turnManager = new TurnManager(this.battlefield);
    }

    public void iniciar() {
        Logger.log("A Batalha Começou!");
        if (!"LOG_ONLY".equals(this.modoDeJogo)) {
            System.out.println();
            imprimirDevagar("======= A BATALHA COMEÇOU! =======", 30);
        }

        while (!turnManager.isBattleOver()) {
            if (!"LOG_ONLY".equals(this.modoDeJogo)) {
                battlefield.exibirTabuleiro();
            }
            Character personagemAtual = turnManager.getCurrentCharacter();

            if (personagemAtual.getHP() <= 0) {
                turnManager.nextTurn();
                continue;
            }

            if (personagemAtual instanceof Hero) {
                if ("INTERATIVO".equals(this.modoDeJogo)) {
                    executarTurnoHeroi((Hero) personagemAtual);
                } else { // MODO AUTOMÁTICO ou LOG_ONLY
                    executarTurnoHeroiAI((Hero) personagemAtual);
                }
            } else if (personagemAtual instanceof Monster) {
                executarTurnoMonstro((Monster) personagemAtual);
            }

            if (turnManager.isBattleOver()) {
                break;
            }

            if ("INTERATIVO".equals(this.modoDeJogo)) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            } else if ("AUTOMATICO".equals(this.modoDeJogo)) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // No modo LOG_ONLY, não há pausa

            turnManager.nextTurn();
        }
        finalizarBatalha();
    }

    private Battlefield configurarBatalha() {
        final int LARGURA_CAMPO = 5;
        final int ALTURA_CAMPO = 5;
        Battlefield campo = new Battlefield(LARGURA_CAMPO, ALTURA_CAMPO);

        imprimirDevagar("Escolha o modo de jogo:", 30);
        System.out.println("1. Interativo (Você controla os heróis)");
        System.out.println("2. Simulação Automática (IA vs IA com turnos visuais)");
        System.out.println("3. Simulação Rápida (Apenas o resultado final no log)");
        System.out.print("Sua escolha: ");
        int escolhaModo = scanner.nextInt();
        scanner.nextLine();

        int proximoXHeroi = 1;
        int proximoYHeroi = 1;
        int proximoXMonstro = LARGURA_CAMPO - 2;
        int proximoYMonstro = 1;

        if (escolhaModo == 1) {
            this.modoDeJogo = "INTERATIVO";
            Logger.log("Modo de jogo selecionado: JOGADOR VS IA");
            exibirInformacoesPersonagens();

            System.out.println("\nEscolha sua classe de Herói:");
            System.out.println("1. Paladino");
            System.out.println("2. Mago");
            System.out.println("3. Arqueiro");
            System.out.println("4. Ladino");
            System.out.print("Sua escolha: ");
            int escolhaClasse = scanner.nextInt();
            scanner.nextLine();

            String tipoHeroi;
            switch (escolhaClasse) {
                case 2: tipoHeroi = "WIZARD"; break;
                case 3: tipoHeroi = "ARCHER"; break;
                case 4: tipoHeroi = "STEALTH"; break;
                case 1:
                default: tipoHeroi = "PALADIN"; break;
            }
            System.out.print("Digite o nome do seu Herói: ");
            String nomeHeroi = scanner.nextLine();
            Hero heroiEscolhido = new Hero(nomeHeroi, tipoHeroi);
            campo.adicionarPersonagem(heroiEscolhido, proximoXHeroi, proximoYHeroi++);
        } else if (escolhaModo == 3) {
            this.modoDeJogo = "LOG_ONLY";
            Logger.log("Modo de jogo selecionado: " + this.modoDeJogo);
            for (int i = 0; i < 2; i++) {
                Hero heroi = CharacterGenerator.gerarHeroiAleatorio();
                campo.adicionarPersonagem(heroi, proximoXHeroi, proximoYHeroi++);
            }
        } else {
            this.modoDeJogo = "AUTOMATICO";
            Logger.log("Modo de jogo selecionado: EQUIPE INTERATIVA");
            for (int i = 0; i < 2; i++) {
                Hero heroi = CharacterGenerator.gerarHeroiAleatorio();
                campo.adicionarPersonagem(heroi, proximoXHeroi, proximoYHeroi++);
            }
        }

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
                for (int i = 0; i < 2; i++) campo.adicionarPersonagem(CharacterGenerator.gerarMonstroFacil(), proximoXMonstro, proximoYMonstro++);
                break;
            case 3:
                Logger.log("Dificuldade selecionada: Difícil.");
                campo.adicionarPersonagem(CharacterGenerator.gerarMonstroMedio(), proximoXMonstro, proximoYMonstro++);
                campo.adicionarPersonagem(CharacterGenerator.gerarMonstroDificil(), proximoXMonstro, proximoYMonstro++);
                break;
            case 2:
            default:
                Logger.log("Dificuldade selecionada: Médio.");
                for (int i = 0; i < 3; i++) campo.adicionarPersonagem(CharacterGenerator.gerarMonstroMedio(), proximoXMonstro, proximoYMonstro++);
                break;
        }
        return campo;
    }

    private void finalizarBatalha() {
        if (!"LOG_ONLY".equals(this.modoDeJogo)) {
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
        } else {
            if (battlefield.getNumeroDeHerois() > 0) {
                Logger.log("Os Heróis venceram a batalha!");
            } else {
                Logger.log("Os Monstros venceram a batalha!");
            }
            System.out.println("\nSimulação Rápida concluída. Verifique o log de eventos abaixo.");
        }
        Logger.exibirLogs();
        scanner.close();
    }

    private void exibirInformacoesPersonagens() {
        imprimirDevagar("\n--- CLASSES DE HERÓIS DISPONÍVEIS ---", 20);
        System.out.println("-----------------------------------------------------------------");
        System.out.println("PALADINO:");
        imprimirDevagar("Um guerreiro sagrado com alta resistência e força. Perfeito para a linha de frente.", 15);
        System.out.printf(" > HP: %d | Força: %d | Resistência: %d | Destreza: %d | Velocidade: %d\n\n", CharacterAttributes.PALADIN_HP, CharacterAttributes.PALADIN_STRENGTH, CharacterAttributes.PALADIN_RESISTANCE, CharacterAttributes.PALADIN_DEXTERITY, CharacterAttributes.PALADIN_SPEED);
        System.out.println("MAGO:");
        imprimirDevagar("Um mestre das artes arcanas que usa sua destreza para causar dano massivo.", 15);
        System.out.printf(" > HP: %d | Força: %d | Resistência: %d | Destreza: %d | Velocidade: %d\n\n", CharacterAttributes.WIZARD_HP, CharacterAttributes.WIZARD_STRENGTH, CharacterAttributes.WIZARD_RESISTANCE, CharacterAttributes.WIZARD_DEXTERITY, CharacterAttributes.WIZARD_SPEED);
        System.out.println("ARQUEIRO:");
        imprimirDevagar("Um caçador ágil e preciso, com alta velocidade e chance de acerto.", 15);
        System.out.printf(" > HP: %d | Força: %d | Resistência: %d | Destreza: %d | Velocidade: %d\n\n", CharacterAttributes.ARCHER_HP, CharacterAttributes.ARCHER_STRENGTH, CharacterAttributes.ARCHER_RESISTANCE, CharacterAttributes.ARCHER_DEXTERITY, CharacterAttributes.ARCHER_SPEED);
        System.out.println("LADINO:");
        imprimirDevagar("Rápido e mortal, combina força e destreza para ataques críticos devastadores.", 15);
        System.out.printf(" > HP: %d | Força: %d | Resistência: %d | Destreza: %d | Velocidade: %d\n", CharacterAttributes.STEALTH_HP, CharacterAttributes.STEALTH_STRENGTH, CharacterAttributes.STEALTH_RESISTANCE, CharacterAttributes.STEALTH_DEXTERITY, CharacterAttributes.STEALTH_SPEED);
        System.out.println("-----------------------------------------------------------------");
    }

    private void executarTurnoHeroi(Hero heroi) {
        String cabecalho = String.format("\n--- Turno de %s (%s | HP: %d/%d) ---", heroi.getName(), heroi.getType(), heroi.getHP(), heroi.getMaxHP());
        imprimirDevagar(cabecalho, 20);

        System.out.println("O que você quer fazer?");
        System.out.println("1. Ataque Fraco");
        System.out.println("2. Ataque Forte");
        System.out.println("3. Passar o turno");
        System.out.println("4. Desistir da Batalha");
        System.out.print("Sua escolha: ");
        int escolha = this.scanner.nextInt();
        this.scanner.nextLine();

        switch (escolha) {
            case 1:
            case 2:
                List<Monster> monstrosVivos = this.battlefield.getMonsters();
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
                int escolhaAlvo = this.scanner.nextInt();
                this.scanner.nextLine();
                if (escolhaAlvo > 0 && escolhaAlvo <= monstrosVivos.size()) {
                    Monster alvo = monstrosVivos.get(escolhaAlvo - 1);
                    AttackType tipoAtaque = (escolha == 1) ? AttackType.FRACO : AttackType.FORTE;
                    heroi.realizarAtaque(alvo, tipoAtaque);
                    if (alvo.getHP() <= 0) {
                        Logger.log("!!! " + alvo.getName() + " foi derrotado! !!!");
                        this.battlefield.removerPersonagem(alvo);
                        this.turnManager.removeCharacter(alvo);
                    }
                } else {
                    System.out.println("Escolha de alvo inválida. O herói perdeu o turno.");
                    Logger.log(heroi.getName() + " se confundiu e perdeu o turno.");
                }
                break;
            case 3:
                System.out.println(heroi.getName() + " está esperando...");
                Logger.log(heroi.getName() + " esperou e passou o turno.");
                break;
            case 4:
                System.out.println("Os heróis se retiram da batalha...");
                Logger.log("Os heróis desistiram da batalha.");
                this.battlefield.removerTodosHerois();
                break;
            default:
                System.out.println("Opção inválida. O herói perdeu o turno.");
                Logger.log(heroi.getName() + " se confundiu e perdeu o turno.");
                break;
        }
    }

    private void executarTurnoHeroiAI(Hero heroi) {
        if (!"LOG_ONLY".equals(this.modoDeJogo)) {
            String cabecalho = String.format("\n--- Turno de %s (%s | HP: %d/%d) ---", heroi.getName(), heroi.getType(), heroi.getHP(), heroi.getMaxHP());
            System.out.println(cabecalho);
        }

        List<Monster> monstrosVivos = this.battlefield.getMonsters();
        if (!monstrosVivos.isEmpty()) {
            // Lógica para encontrar o alvo com menor HP (sem alteração)
            Monster alvo = monstrosVivos.get(0);
            int menorHP = alvo.getHP();
            for (Monster monstro : monstrosVivos) {
                if (monstro.getHP() < menorHP) {
                    menorHP = monstro.getHP();
                    alvo = monstro;
                }
            }

            // --- NOVA LÓGICA DE DECISÃO DA IA ---
            AttackType ataqueEscolhidoPelaIA;
            if (random.nextInt(100) < 40) { // 40% de chance
                ataqueEscolhidoPelaIA = AttackType.FORTE;
            } else { // 60% de chance
                ataqueEscolhidoPelaIA = AttackType.FRACO;
            }

            if (!"LOG_ONLY".equals(this.modoDeJogo)) {
                System.out.printf("%s decide usar seu ataque %s em %s!\n", heroi.getName(), ataqueEscolhidoPelaIA, alvo.getName());
            }

            heroi.realizarAtaque(alvo, ataqueEscolhidoPelaIA);

            if (alvo.getHP() <= 0) {
                Logger.log("!!! " + alvo.getName() + " foi derrotado! !!!");
                this.battlefield.removerPersonagem(alvo);
                this.turnManager.removeCharacter(alvo);
            }
        } else {
            Logger.log(heroi.getName() + " não tem alvos para atacar.");
        }
    }

    // --- MÉTODO ATUALIZADO ---
    private void executarTurnoMonstro(Monster monstro) {
        if (!"LOG_ONLY".equals(this.modoDeJogo)) {
            String cabecalho = String.format("\n--- Turno de %s (%s | HP: %d/%d) ---", monstro.getName(), monstro.getType(), monstro.getHP(), monstro.getMaxHP());
            imprimirDevagar(cabecalho, 20);
        }

        List<Hero> heroisVivos = this.battlefield.getHeroes();
        if (!heroisVivos.isEmpty()) {
            // Lógica para encontrar o alvo com menor HP (sem alteração)
            Hero alvo = heroisVivos.get(0);
            int menorHP = alvo.getHP();
            for (Hero heroi : heroisVivos) {
                if (heroi.getHP() < menorHP) {
                    menorHP = heroi.getHP();
                    alvo = heroi;
                }
            }

            AttackType ataqueEscolhidoPelaIA = AttackType.FRACO;
            if (!"LOG_ONLY".equals(this.modoDeJogo)) {
                String acaoMonstro = String.format("%s foca seu ataque em %s!", monstro.getName(), alvo.getName());
                imprimirDevagar(acaoMonstro, 30);
            }

            monstro.realizarAtaque(alvo, ataqueEscolhidoPelaIA);

            if (alvo.getHP() <= 0) {
                Logger.log("!!! " + alvo.getName() + " foi derrotado! !!!");
                this.battlefield.removerPersonagem(alvo);
                this.turnManager.removeCharacter(alvo);
            }
        } else {
            Logger.log(monstro.getName() + " não tem ninguém para atacar.");
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