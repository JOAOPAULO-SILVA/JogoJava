public class Main {

    public static void main(String[] args) {
        // Limpa os logs de jogos anteriores e inicia um novo registro
        Logger.limparLogs();
        Logger.log("Iniciando modo de teste...");
        System.out.println("--- MODO DE TESTE ---");

        // --- 1. CONFIGURAÇÃO DO CENÁRIO ---
        Battlefield campo = new Battlefield(6, 5);
        Hero paladino = new Hero("Arthur", "PALADIN");
        Hero mago = new Hero("Gandalf", "WIZARD");
        Monster orc = new Monster("Shrek", "ORC");
        Monster bruxa = new Monster("Bruxa má do oeste", "WITCH");

        campo.adicionarPersonagem(paladino, 1, 2);
        campo.adicionarPersonagem(mago, 1, 3);
        campo.adicionarPersonagem(orc, 5, 2);
        campo.adicionarPersonagem(bruxa, 4, 3);
        Logger.log("Personagens posicionados no campo de batalha.");

        // --- 2. EXIBE O ESTADO INICIAL ---
        System.out.println("\n--- ESTADO INICIAL DO CAMPO ---");
        campo.exibirTabuleiro();

        // --- 3. EXECUÇÃO DE AÇÕES ESPECÍFICAS ---
        System.out.println("\n--- EXECUTANDO AÇÕES DE TESTE ---");

        // Ação 1: Herói ataca Monstro
        Logger.log("AÇÃO 1: Arthur (Paladino) ataca Shrek (Orc).");
        paladino.realizarAtaque(orc);

        // Ação 2: Monstro contra-ataca outro Herói
        Logger.log("AÇÃO 2: Morwen (Bruxa) ataca Gandalf (Mago).");
        bruxa.realizarAtaque(mago);

        // Ação 3: O outro Herói também ataca
        Logger.log("AÇÃO 3: Gandalf (Mago) também foca em Shrek (Orc).");
        mago.realizarAtaque(orc);

        // Ação 4: O monstro ferido ataca de volta
        if (orc.getHP() > 0) {
            Logger.log("AÇÃO 4: Shrek (Orc), ferido, ataca Arthur (Paladino).");
            orc.realizarAtaque(paladino);
        }

        // Ação 5: Herói tenta finalizar o monstro
        Logger.log("AÇÃO 5: Arthur (Paladino) ataca Shrek (Orc) novamente para tentar finalizá-lo.");
        paladino.realizarAtaque(orc);

        // Verificando se o alvo foi derrotado
        if (orc.getHP() <= 0) {
            Logger.log("SUCESSO! Shrek foi derrotado!");
            System.out.println("\n!!! Shrek foi derrotado e removido do campo !!!");
            campo.removerPersonagem(orc);
        }


        // --- 4. EXIBE O ESTADO FINAL E OS LOGS ---
        System.out.println("\n--- ESTADO FINAL DO CAMPO ---");
        campo.exibirTabuleiro();

        Logger.exibirLogs();
    }
}