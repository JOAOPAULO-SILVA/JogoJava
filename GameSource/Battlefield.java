public class Battlefield {
    private Character[][] Board;
    private int Largura;
    private int Altura;

    public Battlefield(int largura, int altura) {
        this.Largura = largura;
        this.Altura = altura;
        this.Board = new Character[altura][largura];
    }

    public int getLargura() { return Largura; }
    public int getAltura() { return Altura; }






}
