public class Main {
    public static void main(String[] args) {
        System.out.println("--- Testando Criação de Heróis via Classe Hero ---");

        Hero paladino = new Hero("Sir Arthur", "PALADIN");
        Hero mago = new Hero("Merlin", "WIZARD");
        Hero furtivo = new Hero("Corvo", "STEALTH");

        System.out.println("Paladino:");
        System.out.println(paladino.toString()); // O toString() de Hero já mostra todos os atributos
        System.out.println("\n-----------------------------------\n");

        System.out.println("Mago:");
        System.out.println(mago.toString());
        System.out.println("\n-----------------------------------\n");

        System.out.println("Furtivo:");
        System.out.println(furtivo.toString());
        System.out.println("\n-----------------------------------\n");


        System.out.println("\n Testando Monstros (Falta definir e tal ) ");
        Monster monstroGenerico = new Monster("Monstro Genérico", 6, 8, 70, 7, 5);
        System.out.println("Monstro Genérico:");
        System.out.println("  Nome: " + monstroGenerico.getName());
        System.out.println("  HP: " + monstroGenerico.getHP());
        System.out.println("  Resistência: " + monstroGenerico.getResistance());
        System.out.println("  Força: " + monstroGenerico.getStrength());
        System.out.println("  Destreza: " + monstroGenerico.getDexterity());
        System.out.println("  Velocidade: " + monstroGenerico.getSpeed());
        System.out.println("\n-----------------------------------\n");

    }
}