public class Character {
    String name;
    int speed;
    int strength;
    int HP;
    int resistance;
    int dexterity;

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
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    public int getHP() {
        return HP;
    }

    public int getResistance() {
        return resistance;
    }

    public int getDexterity() {
        return dexterity;
    }
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }


    //Setters
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void receberDano(int dano) {
        int danoReal = Math.max(0, dano - this.resistance);
        this.HP -= danoReal;
        if (this.HP < 0) {
            this.HP = 0;
        }
    }


}