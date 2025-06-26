import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Hero extends SerVivo{
    //[(MinVida,maxVida),(minDefesa,maxDefesa),(minForça,maxForça),(minDestreza,maxDestreza),(minVelocidade,maxVelocidade)]
    //destreza é a chance de desviar e velocidade define quem ataca primeiro
    static int herois = 0; //contagem de herois
    private Classes heroClass;

    public Hero(String choosedClass){
        heroClass = Classes.valueOf(choosedClass);
        herois+=1;
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
    private void setStatus(){
        Random aleatorio = new Random();

        //gera um numero entre (maximo - minimo + 1) e adiciona o minimo
        setVida(aleatorio.nextInt(diferenca("VIDA")));
        setDefesa(aleatorio.nextInt(diferenca("DEFESA")));
        setForca(aleatorio.nextInt(diferenca("FORCA")));
        setDestreza(aleatorio.nextInt(diferenca("DESTREZA")));
        setVelocidade(aleatorio.nextInt(diferenca("VELOCIDADE")));
    }

    private int diferenca(String atributo){
        //Recebe um Map e pega o campo vida, o campo vida é um array de duas posições e faz a difernça do segundo - o primeiro
        Map<String, int[]> mapAtributos = heroClass.getAtributos();
        int minAtributo = mapAtributos.get(atributo)[0];
        int maxAtributo = mapAtributos.get(atributo)[1];
        int diferenca = maxAtributo-minAtributo;
        return diferenca+1; //recebe a diferença mais 1 para ja ser usado no calculo
    }






    //Outros metodos
    public String toString(){
        return String.format(" VidaMax: %s \n Defesa: %s \n Força: %s \n Destreza: %s \n Velocidade: %s", vida, defesa, forca, destreza, velocidade);
    }
}
