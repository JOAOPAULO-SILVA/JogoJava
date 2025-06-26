import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Hero extends SerVivo{
    //[(MinVida,maxVida),(minDefesa,maxDefesa),(minForça,maxForça),(minDestreza,maxDestreza),(minVelocidade,maxVelocidade)]
    //destreza é a chance de desviar e velocidade define quem ataca primeiro
    final int[][] modGuerreiro = {/*vida*/{14,20},/*defesa*/{3,5},/*força*/{4,7},/*Destreza*/{1,3},/*velociade*/{1,4}};
    final int[][] modMago = {/*vida*/{8,14},/*defesa*/{1,4},/*força*/{5,9},/*Destreza*/{2,4},/*velociade*/{2,5}};
    final int[][] modLadino = {/*vida*/{7,13},/*defesa*/{2,4},/*força*/{4,7},/*Destreza*/{3,6},/*velociade*/{3,6}};
    static int herois = 0;

    Classes ladino = Classes.LADINO;

    public Hero(String classe){
        switch (classe.toLowerCase(Locale.ROOT)) {
            case "guerreiro":
                setStatus(modGuerreiro);
                break;
            case "mago":
                setStatus(modMago);
                break;
            case "ladino":
                setStatus(modLadino);
                break;
        }

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
    private void setStatus(int[][] modClasse){
        Random aleatorio = new Random();
        //gera um numero entre (maximo - minimo + 1) e adiciona o minimo
        setVida(aleatorio.nextInt(modClasse[0][1]-modClasse[0][0]+1)+modClasse[0][0]);
        setDefesa(aleatorio.nextInt(modClasse[1][1]-modClasse[1][0]+1)+modClasse[1][0]);
        setForca(aleatorio.nextInt(modClasse[2][1]-modClasse[2][0]+1)+modClasse[2][0]);
        setDestreza(aleatorio.nextInt(modClasse[3][1]-modClasse[3][0]+1)+modClasse[3][0]);
        setVelocidade(aleatorio.nextInt(modClasse[4][1]-modClasse[4][0]+1)+modClasse[4][0]);
    }

    private int diferanca(String atributo){
        Map<String, int[]> mapAtributos = ladino.getAtributos();
        int diferenca = mapAtributos.get(atributo)[1]-mapAtributos.get(atributo)[0]; //Recebe um Map e pega o campo vida, o campo vida é um array de duas posições e faz a difernça do segundo - o primeiro
        return diferenca;
    }






    //Outros metodos
    public String toString(){
        return String.format(" VidaMax: %s \n Defesa: %s \n Força: %s \n Destreza: %s \n Velocidade: %s", vida, defesa, forca, destreza, velocidade);
    }
}
