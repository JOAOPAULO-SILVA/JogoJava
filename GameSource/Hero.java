public class Hero extends Character {

    private CharacterType tipo;

    public Hero(String nome, String tipoHeroiString) {

        super(nome, 0, 0, 0, 0, 0);

        try{
            this.tipo = CharacterType.valueOf(tipoHeroiString.toUpperCase());
        } catch (IllegalArgumentException e) {

            System.err.println("Erro: Tipo de herói inválido \"" + tipoHeroiString + "\". Usando tipo Paladin como padrão.");
            this.tipo = CharacterType.PALADIN; // Define um tipo padrão para evitar null
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
    public CharacterType getType() {
        return tipo;
    }

    public String toString() {
        return String.format("%s %s\n  HP: %d\n  Defesa: %d\n  Força: %d\n  Destreza: %d\n  Velocidade: %d",
                tipo.toString(), getName(), getHP(), getResistance(), getStrength(), getDexterity(), getSpeed());

    }
}