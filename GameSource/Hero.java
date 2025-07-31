import java.util.Random;

public class Hero extends Character {

    private static final Random random = new Random();

    public Hero(String nome, String tipoHeroiString) {
        super(nome, 0, 0, 0, 0, 0);

        CharacterType tipoHeroi;
        try {
            tipoHeroi = CharacterType.valueOf(tipoHeroiString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: Tipo de herói inválido \"" + tipoHeroiString + "\". Usando tipo Paladin como padrão.");
            tipoHeroi = CharacterType.PALADIN;
        }

        this.setTipo(tipoHeroi);
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

    @Override
    public AttackResult realizarAtaque(Character alvo, AttackType tipoAtaque) {
        int chanceAcertoBase = 0;
        int danoBase = 0;
        double multiplicadorCritico = 1.5;
        String nomeAtaque = "";

        // Lógica para definir o tipo de ataque (fraco vs forte)
        switch (this.tipo) {
            case PALADIN:
                if (tipoAtaque == AttackType.FORTE) {
                    nomeAtaque = "Golpe Esmagador";
                    chanceAcertoBase = 70;
                    danoBase = (int) (this.getStrength() * 1.5);
                } else {
                    nomeAtaque = "Estocada Rápida";
                    chanceAcertoBase = 85;
                    danoBase = this.getStrength();
                }
                break;
            case WIZARD:
                if (tipoAtaque == AttackType.FORTE) {
                    nomeAtaque = "Bola de Fogo";
                    chanceAcertoBase = 65;
                    danoBase = (int) (this.getDexterity() * 2.5);
                    multiplicadorCritico = 2.0;
                } else {
                    nomeAtaque = "Raio Místico";
                    chanceAcertoBase = 75;
                    danoBase = (int) (this.getDexterity() * 1.8);
                }
                break;
            case ARCHER:
                if (tipoAtaque == AttackType.FORTE) {
                    nomeAtaque = "Tiro Preciso";
                    chanceAcertoBase = 80;
                    danoBase = this.getStrength() + this.getDexterity();
                    multiplicadorCritico = 2.5;
                } else {
                    nomeAtaque = "Tiro Rápido";
                    chanceAcertoBase = 90;
                    danoBase = this.getStrength() + (this.getDexterity() / 2);
                }
                break;
            case STEALTH:
                if (tipoAtaque == AttackType.FORTE) {
                    nomeAtaque = "Ataque Surpresa";
                    chanceAcertoBase = 75;
                    danoBase = this.getStrength() + this.getDexterity();
                    multiplicadorCritico = 3.0;
                } else {
                    nomeAtaque = "Corte Veloz";
                    chanceAcertoBase = 85;
                    danoBase = this.getStrength() + (this.getDexterity() / 2);
                }
                break;
        }

        int modificadorDestreza = (this.getDexterity() - alvo.getDexterity());
        int chanceFinalAcerto = chanceAcertoBase + modificadorDestreza;
        chanceFinalAcerto = Math.max(10, Math.min(95, chanceFinalAcerto));
        int rolagem = random.nextInt(100) + 1;

        if (rolagem <= 5) {
            Logger.log(String.format("%s usou %s, mas errou criticamente!", this.getName(), nomeAtaque));
            return AttackResult.ERROU;
        } else if (rolagem > 95) {
            int danoCritico = (int) (danoBase * multiplicadorCritico);
            int danoAplicado = alvo.receberDano(danoCritico);
            if (danoAplicado == 0 && danoCritico > 0) {
                String mensagem = String.format("CRITICAL HIT! %s investe com força total, mas %s bloqueia o golpe! (Vida restante: %s %d/%d, %s %d/%d)",
                        this.getName(), alvo.getName(), this.getName(), this.getHP(), this.getMaxHP(), alvo.getName(), alvo.getHP(), alvo.getMaxHP());
                Logger.log(mensagem);
            } else {
                String mensagem = String.format("CRITICAL HIT! Com %s, %s causou %d de dano em %s. (Vida restante: %s %d/%d, %s %d/%d)",
                        nomeAtaque, this.getName(), danoAplicado, alvo.getName(), this.getName(), this.getHP(), this.getMaxHP(), alvo.getName(), alvo.getHP(), alvo.getMaxHP());
                Logger.log(mensagem);
            }
            return AttackResult.CRITICAL_HIT;
        } else if (rolagem <= chanceFinalAcerto) {
            int danoAplicado = alvo.receberDano(danoBase);
            if (danoAplicado == 0 && danoBase > 0) {
                String mensagem = String.format("%s usa %s, mas a defesa de %s absorve o impacto! (Vida restante: %s %d/%d, %s %d/%d)",
                        this.getName(), nomeAtaque, alvo.getName(), this.getName(), this.getHP(), this.getMaxHP(), alvo.getName(), alvo.getHP(), alvo.getMaxHP());
                Logger.log(mensagem);
            } else {
                String mensagem = String.format("%s usou %s e causou %d de dano em %s. (Vida restante: %s %d/%d, %s %d/%d)",
                        this.getName(), nomeAtaque, danoAplicado, alvo.getName(), this.getName(), this.getHP(), this.getMaxHP(), alvo.getName(), alvo.getHP(), alvo.getMaxHP());
                Logger.log(mensagem);
            }
            return AttackResult.ACERTOU;
        } else {
            Logger.log(String.format("%s usou %s, mas errou o ataque.", this.getName(), nomeAtaque));
            return AttackResult.ERROU;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s\n  HP: %d\n  Defesa: %d\n  Força: %d\n  Destreza: %d\n  Velocidade: %d",
                tipo.toString(), getName(), getHP(), getResistance(), getStrength(), getDexterity(), getSpeed());
    }
}