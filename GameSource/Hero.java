import java.util.Random;

public class Hero extends Character {

    private CharacterType tipo;
    private static final Random random = new Random();
    public Hero(String nome, String tipoHeroiString) {

        super(nome, 0, 0, 0, 0, 0);

        try{
            this.tipo = CharacterType.valueOf(tipoHeroiString.toUpperCase());
        } catch (IllegalArgumentException e) {

            System.err.println("Erro: Tipo de herói inválido \"" + tipoHeroiString + "\". Usando tipo Paladin como padrão.");
            this.tipo = CharacterType.PALADIN;
        }

        setAttributes();
    }

    private void setAttributes() {
        switch (this.tipo) {
            case PALADIN:
                this.setSpeed(CharacterAttributes.PALADIN_SPEED);
                this.setStrength(CharacterAttributes.PALADIN_STRENGTH);
                this.setHP(CharacterAttributes.PALADIN_HP);
                this.setResistance(CharacterAttributes.PALADIN_RESISTANCE);
                this.setDexterity(CharacterAttributes.PALADIN_DEXTERITY);
                break;
            case WIZARD:
                this.setSpeed(CharacterAttributes.WIZARD_SPEED);
                this.setStrength(CharacterAttributes.WIZARD_STRENGTH);
                this.setHP(CharacterAttributes.WIZARD_HP);
                this.setResistance(CharacterAttributes.WIZARD_RESISTANCE);
                this.setDexterity(CharacterAttributes.WIZARD_DEXTERITY);
                break;
            case ARCHER:
                this.setSpeed(CharacterAttributes.ARCHER_SPEED);
                this.setStrength(CharacterAttributes.ARCHER_STRENGTH);
                this.setHP(CharacterAttributes.ARCHER_HP);
                this.setResistance(CharacterAttributes.ARCHER_RESISTANCE);
                this.setDexterity(CharacterAttributes.ARCHER_DEXTERITY);
                break;
            case STEALTH:
                this.setSpeed(CharacterAttributes.STEALTH_SPEED);
                this.setStrength(CharacterAttributes.STEALTH_STRENGTH);
                this.setHP(CharacterAttributes.STEALTH_HP);
                this.setResistance(CharacterAttributes.STEALTH_RESISTANCE);
                this.setDexterity(CharacterAttributes.STEALTH_DEXTERITY);
                break;
        }
    }

    public AttackResult realizarAtaque(Character alvo) {
        int chanceAcertoBase;
        int danoBase;
        double multiplicadorCritico;

        switch (this.tipo) {
            case PALADIN:
                chanceAcertoBase = 75;
                danoBase = this.getStrength();
                multiplicadorCritico = 1.6; 
                break;
            case WIZARD:
                chanceAcertoBase = 60;
                danoBase = this.getDexterity() * 2;
                multiplicadorCritico = 1.8;
                break;
            case ARCHER:
                chanceAcertoBase = 80;
                danoBase = this.getStrength() + (this.getDexterity() / 2);
                multiplicadorCritico = 1.7;
                break;
            case STEALTH:
                chanceAcertoBase = 70;
                danoBase = this.getStrength() + this.getDexterity();
                multiplicadorCritico = 2.0;
                break;
            default:
                chanceAcertoBase = 70;
                danoBase = this.getStrength();
                multiplicadorCritico = 1.5;
                break;
        }

        int modificadorDestreza = (this.getDexterity() - alvo.getDexterity());
        int chanceFinalAcerto = chanceAcertoBase + modificadorDestreza;

        chanceFinalAcerto = Math.max(10, Math.min(95, chanceFinalAcerto));

        int rolagem = random.nextInt(100) + 1;

        if (rolagem <= 5) {
            System.out.println(this.getName() + " errou o ataque criticamente contra " + alvo.getName() + "!");
            return AttackResult.ERROU;
        } else if (rolagem > 95) {
            int dano = (int) (danoBase * multiplicadorCritico);
            alvo.receberDano(dano);
            System.out.println(this.getName() + " acertou um CRITICAL HIT em " + alvo.getName() + " causando " + dano + " de dano!");
            return AttackResult.CRITICAL_HIT;
        } else if (rolagem <= chanceFinalAcerto) {
            alvo.receberDano(danoBase);
            System.out.println(this.getName() + " acertou " + alvo.getName() + " causando " + danoBase + " de dano.");
            return AttackResult.ACERTOU;
        } else {
            System.out.println(this.getName() + " errou o ataque contra " + alvo.getName() + ".");
            return AttackResult.ERROU;
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