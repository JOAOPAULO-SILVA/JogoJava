import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharacterGenerator {

    private static final Random random = new Random();
    private static final String[] nomesHerois = {"Arion", "Lira", "Gandalf", "Seraphina", "Roric"};
    private static final String[] nomesMonstros = {"Grak", "Zorg", "Malpha", "Krag'nar", "Viletongue", "Snarl"};
    private static final List<String> nomesUsados = new ArrayList<>();

    private static int defaultCounter = 1;

    private CharacterGenerator() {}

    public static Hero gerarHeroiAleatorio() {

        String nomeEscolhido = getUniqueName(nomesHerois);

        CharacterType[] tiposDeHerois = {CharacterType.PALADIN, CharacterType.WIZARD, CharacterType.ARCHER, CharacterType.STEALTH};
        CharacterType tipoEscolhido = tiposDeHerois[random.nextInt(tiposDeHerois.length)];

        Logger.log(String.format("Um herói (%s) chamado %s se junta à batalha!", tipoEscolhido, nomeEscolhido));
        return new Hero(nomeEscolhido, tipoEscolhido.toString());
    }

    public static Monster gerarMonstroFacil() {
        return gerarMonstroPorTipo(new CharacterType[]{CharacterType.ORC});
    }

    public static Monster gerarMonstroMedio() {
        return gerarMonstroPorTipo(new CharacterType[]{CharacterType.ORC, CharacterType.WITCH});
    }

    public static Monster gerarMonstroDificil() {
        return gerarMonstroPorTipo(new CharacterType[]{CharacterType.WITCH, CharacterType.DRAGON});
    }

    private static Monster gerarMonstroPorTipo(CharacterType[] tiposPossiveis) {

        String nomeEscolhido = getUniqueName(nomesMonstros);

        CharacterType tipoEscolhido = tiposPossiveis[random.nextInt(tiposPossiveis.length)];

        Logger.log(String.format("Uma criatura inimiga (%s) chamada %s surge no campo!", tipoEscolhido, nomeEscolhido));
        return new Monster(nomeEscolhido, tipoEscolhido.toString());
    }

    private static String getUniqueName(String[] Nomes) {

        if (nomesUsados.size() >= Nomes.length) {
            return "Inimigo #" + defaultCounter++;
        }

        String nomeEscolhido;
        // Fica em loop até encontrar um nome que não esteja na lista de "nomesUsados"
        do {
            nomeEscolhido = Nomes[random.nextInt(Nomes.length)];
        } while (nomesUsados.contains(nomeEscolhido));

        // Adiciona o nome encontrado à lista de usados e o retorna
        nomesUsados.add(nomeEscolhido);
        return nomeEscolhido;
    }
}