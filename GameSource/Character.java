public abstract class Character {
    protected String name;
    protected int speed;
    protected int strength;
    protected int HP;
    protected int resistance;
    protected int dexterity;
    protected CharacterType tipo;
    protected int maxHP;

    private int posX;
    private int posY;

    public Character(String name, int speed, int strength, int HP, int resistance, int dexterity) {
        this.name = name;
        this.speed = speed;
        this.strength = strength;
        this.HP = HP;
        this.resistance = resistance;
        this.dexterity = dexterity;
        this.posX = -1;
        this.posY = -1;
        this.tipo = null;
        this.maxHP = HP;
    }

    protected void setTipo(CharacterType tipo) {
        this.tipo = tipo;
    }

     public int getMaxHP() {
        return this.maxHP;
    }

    public void setHP(int HP) {
        this.HP = HP;
        this.maxHP = HP;
    }

    // Getters
    public String getName() { return name; }
    public int getSpeed() { return speed; }
    public int getStrength() { return strength; }
    public int getHP() { return HP; }
    public int getResistance() { return resistance; }
    public int getDexterity() { return dexterity; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public CharacterType getType() { return tipo; }

    //Setters
    public void setPosX(int posX) { this.posX = posX; }
    public void setPosY(int posY) { this.posY = posY; }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setStrength(int strength) { this.strength = strength; }
    public void setResistance(int resistance) { this.resistance = resistance; }
    public void setDexterity(int dexterity) { this.dexterity = dexterity; }

    public abstract AttackResult realizarAtaque(Character alvo);

    public int receberDano(int dano) {
        int danoReal = Math.max(0, dano - this.resistance);
        this.HP -= danoReal;
        if (this.HP < 0) { this.HP = 0; }
        return danoReal;
    }
}