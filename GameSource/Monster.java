import java.util.Random;

public class Monster extends Character {
    private static final Random random = new Random();

    public Monster(String nome, String tipoCharacterString) {
        super(nome, 0, 0, 0, 0, 0);

        CharacterType tipoMonstro;
        try {
            tipoMonstro = CharacterType.valueOf(tipoCharacterString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: Tipo de monstro inválido \"" + tipoCharacterString + "\". Usando tipo Orc como padrão.");
            tipoMonstro = CharacterType.ORC;
        }

        this.setTipo(tipoMonstro);
        setAttributes();
    }

    private int getRandom(int min, int max) {
        if (min >= max) {
            return min;
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

    @Override
    public AttackResult realizarAtaque(Character alvo, AttackType tipoAtaque) {
        int danoBase = this.getStrength();
        int danoAplicado = alvo.receberDano(danoBase);

        String mensagem;
        if (danoAplicado == 0 && danoBase > 0) {
            mensagem = String.format("%s ataca %s, mas sua defesa absorve todo o impacto! (Vida restante: %s %d/%d, %s %d/%d)",
                    this.getName(),
                    alvo.getName(),
                    this.getName(), this.getHP(), this.getMaxHP(),
                    alvo.getName(), alvo.getHP(), alvo.getMaxHP()
            );
        } else {
            mensagem = String.format("%s atacou %s, causando %d de dano! (Vida restante: %s %d/%d, %s %d/%d)",
                    this.getName(),
                    alvo.getName(),
                    danoAplicado,
                    this.getName(), this.getHP(), this.getMaxHP(),
                    alvo.getName(), alvo.getHP(), alvo.getMaxHP()
            );
        }
        Logger.log(mensagem);

        return AttackResult.ACERTOU;
    }

    @Override
    public String toString() {
        return String.format("%s %s\n  HP: %d\n  Defesa: %d\n  Força: %d\n  Destreza: %d\n  Velocidade: %d",
                tipo.toString(), getName(), getHP(), getResistance(), getStrength(), getDexterity(), getSpeed());
    }
}