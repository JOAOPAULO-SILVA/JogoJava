public class Main {
    public static void main(String[] args) {
        // Não precisamos mais de: Logger logger = Logger.getInstance();

        // 1. Cria o campo de batalha
        Battlefield campo = new Battlefield(10, 5);
        Logger.log("Campo de batalha 10x5 criado."); // Chamada direta

        // 2. Cria os personagens
        Hero paladino = new Hero("Arthur", "PALADIN");
        Hero mago = new Hero("Gandalf", "WIZARD");
        Monster orc1 = new Monster("Ugluk", "ORC");
        Monster orc2 = new Monster("Grishnakh", "ORC");
        Monster bruxa = new Monster("Morwen", "WITCH");
        Logger.log("Personagens criados: Arthur, Gandalf, Ugluk, Grishnakh, Morwen.");

        // 3. Adiciona os personagens ao campo de batalha
        Logger.log("Adicionando personagens ao campo..."); // Chamada direta
        campo.adicionarPersonagem(paladino, 1, 2);
        campo.adicionarPersonagem(mago, 1, 3);
        campo.adicionarPersonagem(orc1, 8, 1);
        campo.adicionarPersonagem(orc2, 8, 3);
        campo.adicionarPersonagem(bruxa, 7, 2);

        // 4. Mostra o estado inicial do tabuleiro
        campo.exibirTabuleiro();

        // 5. Simula um ataque
        Logger.log(String.format(">>> %s inicia um ataque contra %s! <<<", paladino.getName(), orc1.getName()));
        paladino.realizarAtaque(orc1);

        // 6. Mostra o estado final
        campo.exibirTabuleiro();

        // 7. Exibe todos os logs registrados no final da execução
        Logger.exibirLogs(); // Chamada direta
    }
}