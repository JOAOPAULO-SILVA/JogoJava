
public class Hero extends Character {

    public Hero(String nome, int velocidade, int forca, int vida, int defesa, int destreza) {
        super(nome, velocidade, forca, vida, defesa, destreza);
    }

    //getters and setters
    public void setVida(int vida){
        this.vida = vida;
    }
    public void setDefesa(int defesa){
        this.defesa = defesa;
    }
    private void setForca(int forca){
        this.forca = forca;
    }
    private void setDestreza(int destreza){
        this.destreza = destreza;
    }
    private void setVelocidade(int velocidade){
        this.velocidade = velocidade;
    }


    //Outros metodos
    public String toString(){
        return String.format(" VidaMax: %s \n Defesa: %s \n Força: %s \n Destreza: %s \n Velocidade: %s", vida, defesa, forca, destreza, velocidade);
    }
}
