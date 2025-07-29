import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    public List<Character> turnOrder;
    private int currentTurnIndex;
    private final Battlefield battlefield;

    public TurnManager(Battlefield battlefield) {
        this.battlefield = battlefield;
        this.currentTurnIndex = 0;
        initializeTurnOrder();
    }

    private void initializeTurnOrder() {
        this.turnOrder = new ArrayList<>();
        turnOrder.addAll(battlefield.getHeroes());
        turnOrder.addAll(battlefield.getMonsters());

        int size = turnOrder.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                Character personagemEsquerda = turnOrder.get(j);
                Character personagemDireita = turnOrder.get(j + 1);

                if (personagemEsquerda.getSpeed() < personagemDireita.getSpeed()) {
                    turnOrder.set(j, personagemDireita);
                    turnOrder.set(j + 1, personagemEsquerda);
                }
            }
        }

        System.out.println("Ordem de turno definida:");
        for (Character c : turnOrder) {
            System.out.printf("- %s (Velocidade: %d)\n", c.getName(), c.getSpeed());
        }
    }

    public Character getCurrentCharacter() {
        if (turnOrder.isEmpty()) {
            return null;
        }
        return turnOrder.get(currentTurnIndex);
    }

    public void nextTurn() {
        if (turnOrder.isEmpty()) return;

        currentTurnIndex = (currentTurnIndex + 1) % turnOrder.size();
    }

    public boolean isBattleOver() {
        return battlefield.getNumeroDeHerois() == 0 || battlefield.getNumeroDeMonstros() == 0;
    }

    public void removeCharacter(Character character) {
        turnOrder.remove(character);
        if (currentTurnIndex >= turnOrder.size()) {
            currentTurnIndex = 0;
        }
    }
}