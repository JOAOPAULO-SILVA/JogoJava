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
