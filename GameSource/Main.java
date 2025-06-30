public class Main {
    public static void main(String[] args) {
        // 1. Cria o campo de batalha
        Battlefield campo = new Battlefield(5, 5); // Um tabuleiro 5x5

        // 2. Cria alguns personagens
        // Lembre-se de corrigir o bug nos setters da classe Character!
        Hero paladino = new Hero("Sir Arthur", "PALADIN");
        Monster sla = new Monster("Ugluk", "ORC");

        // 3. Adiciona os personagens ao campo de batalha
        System.out.println("Adicionando personagens ao campo...");
        campo.adicionarPersonagem(paladino, 0, 2); // Coluna 0, Linha 2
        campo.adicionarPersonagem(sla, 4, 2);     // Coluna 4, Linha 2

        // 4. Mostra o estado inicial do tabuleiro
        campo.exibirTabuleiro();

        // 5. Simula um movimento
        System.out.println("Movendo o Paladino...");
        campo.moverPersonagem(paladino, 1, 2);

        // 6. Mostra o estado final
        campo.exibirTabuleiro();
        campo.removerPersonagem(sla);
        campo.exibirTabuleiro();

    }
}