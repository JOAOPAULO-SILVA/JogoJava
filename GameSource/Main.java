public class Main {
    public static void main(String[] args) {
        // 1. Cria o campo de batalha
        Battlefield campo = new Battlefield(10, 5); // Um tabuleiro maior 10x5

        // 2. Cria os personagens
        Hero paladino = new Hero("Arthur", "PALADIN");
        Hero mago = new Hero("Gandalf", "WIZARD");

        // Adiciona múltiplos monstros!
        Monster orc1 = new Monster("Ugluk", "ORC");
        Monster orc2 = new Monster("Grishnakh", "ORC");
        Monster bruxa = new Monster("Morwen", "WITCH");

        // 3. Adiciona os personagens ao campo de batalha
        System.out.println("Adicionando personagens ao campo...");
        campo.adicionarPersonagem(paladino, 1, 2);
        campo.adicionarPersonagem(mago, 1, 3);

        campo.adicionarPersonagem(orc1, 8, 1);
        campo.adicionarPersonagem(orc2, 8, 3);
        campo.adicionarPersonagem(bruxa, 7, 2);

        // 4. Mostra o estado inicial do tabuleiro
        campo.exibirTabuleiro();

        // 5. Simula um ataque
        System.out.println("\n>>> Arthur ataca Ugluk! <<<\n");
        paladino.realizarAtaque(orc1);

        // 6. Mostra o estado final
        campo.exibirTabuleiro();
    }
}