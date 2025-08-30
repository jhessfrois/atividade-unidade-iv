import java.util.Arrays;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<Pizza> pizzas;
    private double valorTotal;

    public Pedido(int id, Cliente cliente, List<Pizza> pizzas, double valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.pizzas = pizzas;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters (boas práticas)
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    // =============================
    // 🔹 Métodos para alterar pedido
    // =============================

    // Adicionar pizza
    public void adicionarPizza(Pizza p) {
        pizzas.add(p);
        recalcularValorTotal();
    }

    // Remover pizza pelo índice
    public void removerPizza(int index) {
        if (index >= 0 && index < pizzas.size()) {
            pizzas.remove(index);
            recalcularValorTotal();
        } else {
            System.out.println("Índice inválido. Pizza não removida.");
        }
    }

    // Alterar sabor da pizza pelo índice
    public void alterarSaborPizza(int index, String novoSabor) {
        if (index >= 0 && index < pizzas.size()) {
            pizzas.get(index).setSabores(Arrays.asList(novoSabor));
        } else {
            System.out.println("Índice inválido. Pizza não alterada.");
        }
    }

    // Recalcular valor total
    private void recalcularValorTotal() {
        valorTotal = pizzas.stream().mapToDouble(Pizza::getPreco).sum();
    }

    @Override
    public String toString() {
        return "Pedido ID: " + id +
                ", Cliente: " + cliente.getNome() +
                ", Pizzas: " + pizzas +
                ", Valor Total: R$ " + valorTotal;
    }
}
