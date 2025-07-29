import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharacterGenerator {

    private static final Random random = new Random();

    // 1. Voltamos a usar arrays simples e fixos para os nomes
    private static final String[] nomesDeHerois = {"Arion", "Lira", "Kael", "Seraphina", "Roric"};
    private static final String[] nomesDeMonstros = {"Grak", "Zorg", "Malpha", "Krag'nar", "Viletongue"};

    // 2. Criamos uma lista simples para guardar os nomes que já saíram
    private static final List<String> nomesUsados = new ArrayList<>();

    // Contadores para o caso de todos os nomes se esgotarem
    private static int defaultCounter = 1;

    private CharacterGenerator() {}

    public static Hero gerarHeroiAleatorio() {
        String nomeEscolhido;

        if (nomesUsados.size() >= nomesDeHerois.length + nomesDeMonstros.length) {
            nomeEscolhido = "Herói #" + defaultCounter++;
        } else {
            do {
                nomeEscolhido = nomesDeHerois[random.nextInt(nomesDeHerois.length)];
            } while (nomesUsados.contains(nomeEscolhido));
        }

        nomesUsados.add(nomeEscolhido);

        CharacterType[] tiposDeHerois = {CharacterType.PALADIN, CharacterType.WIZARD, CharacterType.ARCHER, CharacterType.STEALTH};
        CharacterType tipoEscolhido = tiposDeHerois[random.nextInt(tiposDeHerois.length)];

        Logger.log(String.format("Um herói (%s) chamado %s se junta à batalha!", tipoEscolhido, nomeEscolhido));
        return new Hero(nomeEscolhido, tipoEscolhido.toString());
    }

    public static Monster gerarMonstroAleatorio() {
        String nomeEscolhido;

        if (nomesUsados.size() >= nomesDeHerois.length + nomesDeMonstros.length) {
            nomeEscolhido = "Monstro #" + defaultCounter++;
        } else {
            do {
                nomeEscolhido = nomesDeMonstros[random.nextInt(nomesDeMonstros.length)];
            } while (nomesUsados.contains(nomeEscolhido));
        }

        nomesUsados.add(nomeEscolhido);

        CharacterType[] tiposDeMonstros = {CharacterType.ORC, CharacterType.WITCH};
        CharacterType tipoEscolhido = tiposDeMonstros[random.nextInt(tiposDeMonstros.length)];

        Logger.log(String.format("Uma criatura inimiga (%s) chamada %s surge no campo!", tipoEscolhido, nomeEscolhido));
        return new Monster(nomeEscolhido, tipoEscolhido.toString());
    }
}