public class Monster extends Character{
private CharacterType tipo;

public Monster(String nome, String tipoCharacterString) {

        super(nome, 0, 0, 0, 0, 0);

        try{
        this.tipo = CharacterType.valueOf(tipoCharacterString.toUpperCase());
        } catch (IllegalArgumentException e) {

        System.err.println("Erro: Tipo de herói inválido \"" + tipoCharacterString + "\". Usando tipo Orc como padrão.");
        this.tipo = CharacterType.ORC; // Define um tipo padrão para evitar null
        }

        setAttributes();
        }

        private void setAttributes() {
                switch (this.tipo) {
                case WITCH:
                     this.setSpeed(CharacterAttributes.WITCH_SPEED);
                     this.setStrength(CharacterAttributes.WITCH_STRENGTH);
                     this.setHP(CharacterAttributes.WITCH_HP);
                     this.setResistance(CharacterAttributes.WITCH_RESISTANCE);
                     this.setDexterity(CharacterAttributes.WITCH_DEXTERITY);
                     break;
                case ORC:
                     this.setSpeed(CharacterAttributes.ORC_SPEED);
                     this.setStrength(CharacterAttributes.ORC_STRENGTH);
                     this.setHP(CharacterAttributes.ORC_HP);
                     this.setResistance(CharacterAttributes.ORC_RESISTANCE);
                     this.setDexterity(CharacterAttributes.ORC_DEXTERITY);
                     break;
                case DRAGON:
                     this.setSpeed(CharacterAttributes.DRAGON_SPEED);
                     this.setStrength(CharacterAttributes.DRAGON_STRENGTH);
                     this.setHP(CharacterAttributes.DRAGON_HP);
                     this.setResistance(CharacterAttributes.DRAGON_RESISTANCE);
                     this.setDexterity(CharacterAttributes.DRAGON_DEXTERITY);
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