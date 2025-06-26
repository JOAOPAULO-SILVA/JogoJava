public class Character {
    String Name;
    int Speed;
    int Strength;
    int HP;
    int Resistance;
    int dexterity;

    public Character(String Name, int Speed, int Strength, int HP, int Resistance, int dexterity) {
        this.Name = Name;
        this.Speed = Speed;
        this.Strength = Strength;
        this.HP = HP;
        this.Resistance = Resistance;
        this.dexterity = dexterity;
    }

    public String getName() {
        return Name;
    }

    public int getSpeed() {
        return Speed;
    }

    public int getStrength() {
        return Strength;
    }

    public int getHP() {
        return HP;
    }

    public int getResistance() {
        return Resistance;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public void setStrength(int strength) {
        Strength = strength;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setResistance(int resistance) {
        Resistance = resistance;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
}