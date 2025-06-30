import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Hero extends SerVivo{
    static int herois = 0; //contagem de herois
    private Classes heroClass;
    private Map<String, int[]> mapAtributos = heroClass.getAtributos();

    public Hero(String choosedClass){
        heroClass = Classes.valueOf(choosedClass);
        setStatus();
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
        setVida(aleatorio.nextInt(attrubuteGap("VIDA")));
        setDefesa(aleatorio.nextInt(attrubuteGap("DEFESA")));
        setForca(aleatorio.nextInt(attrubuteGap("FORCA")));
        setDestreza(aleatorio.nextInt(attrubuteGap("DESTREZA")));
        setVelocidade(aleatorio.nextInt(attrubuteGap("VELOCIDADE")));
    }
    
    private int diferenca(String atributo){
        //Recebe um Map e pega o campo vida, o campo vida é um array de duas posições e faz a difernça do segundo - o primeiro
        int minAtributo = mapAtributos.get(atributo)[0];
        int maxAtributo = mapAtributos.get(atributo)[1];
        int diferenca = maxAtributo-minAtributo;
        return diferenca+1; //recebe a diferença mais 1 para ja ser usado no calculo
    }
    private int attrubuteGap(String atributo){
        int minAtributo = mapAtributos.get(atributo)[1];
        return diferenca(atributo) + minAtributo;
    }
    





    //Outros metodos
    public String toString(){
        return String.format(" VidaMax: %s \n Defesa: %s \n Força: %s \n Destreza: %s \n Velocidade: %s", vida, defesa, forca, destreza, velocidade);
    }
}
