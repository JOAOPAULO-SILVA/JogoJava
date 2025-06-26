import java.util.Map;

public enum Classes {
    LADINO(Map.of(
            "VIDA", new int[]{14, 20},
            "DEFESA", new int[]{3, 5},
            "FORCA", new int[]{4, 7},
            "DESTREZA", new int[]{1, 3},
            "VELOCIDADE", new int[]{1, 4}
    )),
    MAGO(Map.of(
            "VIDA", new int[]{14, 20},
            "DEFESA", new int[]{3, 5},
            "FORCA", new int[]{4, 7},
            "DESTREZA", new int[]{1, 3},
            "VELOCIDADE", new int[]{1, 4}
    )),
    GUERREIRO(Map.of(
            "VIDA", new int[]{14, 20},
            "DEFESA", new int[]{3, 5},
            "FORCA", new int[]{4, 7},
            "DESTREZA", new int[]{1, 3},
            "VELOCIDADE", new int[]{1, 4}
    ));

    //Atributos
    private final Map<String, int[]> atributos;
    //construtores
   private Classes(Map<String , int[]> atributos) {
       this.atributos = atributos;
   }
   //getters

    public Map<String, int[]> getAtributos() {
        return atributos;
    }
}
//    final int[][] modMago = {/*vida*/{8,14},/*defesa*/{1,4},/*força*/{5,9},/*Destreza*/{2,4},/*velociade*/{2,5}};
//    final int[][] modLadino = {/*vida*/{7,13},/*defesa*/{2,4},/*força*/{4,7},/*Destreza*/{3,6},/*velociade*/{3,6}};
