public class Hero extends Character {

    private HeroType tipo;

    public Hero(String nome, String tipoHeroiString) {

        super(nome, 0, 0, 0, 0, 0);

        try{
            this.tipo = HeroType.valueOf(tipoHeroiString.toUpperCase());
        } catch (IllegalArgumentException e) {

            System.err.println("Erro: Tipo de herói inválido \"" + tipoHeroiString + "\". Usando tipo Paladin como padrão.");
            this.tipo = HeroType.PALADIN; // Define um tipo padrão para evitar null
        }

        setAttributes();
    }

    private void setAttributes() {
        switch (this.tipo) {
            case PALADIN:
                this.setSpeed(8);
                this.setStrength(15);
                this.setHP(120);
                this.setResistance(12);
                this.setDexterity(7);
                break;
            case WIZARD:
                this.setSpeed(7);
                this.setStrength(5);
                this.setHP(70);
                this.setResistance(4);
                this.setDexterity(15);
                break;
            case ARCHER:
                this.setSpeed(12);
                this.setStrength(10);
                this.setHP(80);
                this.setResistance(6);
                this.setDexterity(12);
                break;
            case STEALTH:
                this.setSpeed(15);
                this.setStrength(9);
                this.setHP(75);
                this.setResistance(5);
                this.setDexterity(15);
                break;
        }
    }

    public HeroType getType() {
        return tipo;
    }

    public String toString() {
        return String.format("%s %s\n  HP: %d\n  Defesa: %d\n  Força: %d\n  Destreza: %d\n  Velocidade: %d",
                tipo.toString(), getName(), getHP(), getResistance(), getStrength(), getDexterity(), getSpeed());

    }
}