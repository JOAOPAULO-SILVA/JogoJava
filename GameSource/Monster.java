import java.util.Random;

public class Monster extends Character {
    private CharacterType tipo;
    private static final Random random = new Random();

    public Monster(String nome, String tipoCharacterString) {
        super(nome, 0, 0, 0, 0, 0);

        try {
            this.tipo = CharacterType.valueOf(tipoCharacterString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: Tipo de monstro inválido \"" + tipoCharacterString + "\". Usando tipo Orc como padrão.");
            this.tipo = CharacterType.ORC;
        }

        setAttributes();
    }

    private int getRandom(int min, int max) {
        if (min > max) {
            return max;
        }
        return random.nextInt((max - min) + 1) + min;
    }

    private void setAttributes() {
        switch (this.tipo) {
            case WITCH:
                this.setHP(getRandom(CharacterAttributes.WITCH_HP_MIN, CharacterAttributes.WITCH_HP_MAX));
                this.setStrength(getRandom(CharacterAttributes.WITCH_STRENGTH_MIN, CharacterAttributes.WITCH_STRENGTH_MAX));
                this.setSpeed(getRandom(CharacterAttributes.WITCH_SPEED_MIN, CharacterAttributes.WITCH_SPEED_MAX));
                this.setResistance(getRandom(CharacterAttributes.WITCH_RESISTANCE_MIN, CharacterAttributes.WITCH_RESISTANCE_MAX));
                this.setDexterity(getRandom(CharacterAttributes.WITCH_DEXTERITY_MIN, CharacterAttributes.WITCH_DEXTERITY_MAX));
                break;
            case ORC:
                this.setHP(getRandom(CharacterAttributes.ORC_HP_MIN, CharacterAttributes.ORC_HP_MAX));
                this.setStrength(getRandom(CharacterAttributes.ORC_STRENGTH_MIN, CharacterAttributes.ORC_STRENGTH_MAX));
                this.setSpeed(getRandom(CharacterAttributes.ORC_SPEED_MIN, CharacterAttributes.ORC_SPEED_MAX));
                this.setResistance(getRandom(CharacterAttributes.ORC_RESISTANCE_MIN, CharacterAttributes.ORC_RESISTANCE_MAX));
                this.setDexterity(getRandom(CharacterAttributes.ORC_DEXTERITY_MIN, CharacterAttributes.ORC_DEXTERITY_MAX));
                break;
            case DRAGON:
                this.setHP(getRandom(CharacterAttributes.DRAGON_HP_MIN, CharacterAttributes.DRAGON_HP_MAX));
                this.setStrength(getRandom(CharacterAttributes.DRAGON_STRENGTH_MIN, CharacterAttributes.DRAGON_STRENGTH_MAX));
                this.setSpeed(getRandom(CharacterAttributes.DRAGON_SPEED_MIN, CharacterAttributes.DRAGON_SPEED_MAX));
                this.setResistance(getRandom(CharacterAttributes.DRAGON_RESISTANCE_MIN, CharacterAttributes.DRAGON_RESISTANCE_MAX));
                this.setDexterity(getRandom(CharacterAttributes.DRAGON_DEXTERITY_MIN, CharacterAttributes.DRAGON_DEXTERITY_MAX));
                break;
        }
    }

    public CharacterType getType() {
        return tipo;
    }

    public String toString() {
        return String.format("%s %s\n  HP: %d\n  Defesa: %d\n  Força: %d\n  Destreza: %d\n  Velocidade: %d",
                tipo.toString(), getName(), getHP(), getResistance(), getStrength(), getDexterity(), getSpeed());
    }
}