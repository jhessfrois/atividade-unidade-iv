package projeto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cardapio {
    private Map<String, Double> cardapio;

    public Cardapio() {
        this.cardapio = new HashMap<>();
        inicializarCardapio();
    }

    private void inicializarCardapio() {
        cardapio.put("Margherita", 25.0);
        cardapio.put("Pepperoni", 30.0);
        cardapio.put("Quatro Queijos", 35.0);
        cardapio.put("Calabresa", 28.0);
        cardapio.put("Frango com Catupiry", 32.50);
        cardapio.put("Vegetariana", 27.0);
        cardapio.put("Portuguesa", 33.0);
        cardapio.put("Atum", 29.0);
        cardapio.put("Chocolate", 22.0);
    }

    public Map<String, Double> getCardapio() {
        return cardapio;
    }

    public double getPrecoJusto(List<String> pizzas) {
        List<String> saboresDisponiveis = new ArrayList<>();
        double precoTotal = 0.0;
        int totalSabores = 0;
        for (String sabor : pizzas) {
            if (cardapio.containsKey(sabor)) {
                saboresDisponiveis.add(sabor);
                totalSabores++;
            }else{
                System.out.println("Sabor indisponível: " + sabor);
            }
        }

        for (String sabor : saboresDisponiveis) {
            precoTotal += cardapio.get(sabor)/totalSabores;
        }

        return precoTotal;
    }
}
