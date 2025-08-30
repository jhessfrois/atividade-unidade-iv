package projeto;

import java.util.*;
import projeto.Pizza.tamanhoPizza;

public class Pizzaria {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> clientes = new ArrayList<>();
        List<Pedido> pedidos = new ArrayList<>();

        boolean continuar = true;
        while (continuar) {
            System.out.println();
            System.out.println("Escolha uma opção: ");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Fazer um novo pedido");
            System.out.println("3. Alterar um pedido");
            System.out.println("4. Exibir relatório de vendas");
            System.out.println("5. Exibir lista de clientes ");
            System.out.println("6. Calcular frete");
            System.out.println("7. Sair");

            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha
            System.out.println();

            switch (opcao) {
                case 1:
                    clientes.add(adicionarCliente(scanner));
                    System.out.println("Cliente cadastrado com sucesso!");
                    break;
                case 2:
                    fazerPedido(scanner, pedidos, clientes);
                    break;
                case 3:
                    alterarPedido(scanner, pedidos);
                    break;
                case 4:
                    exibirRelatorio(pedidos);
                    break;
                case 5:
                    exibirListaClientes(clientes);
                    break;
                case 6:
                    System.out.print("Digite a distância em Km: ");
                    double distancia = scanner.nextDouble();
                    System.out.print("Digite a quantidade de pizzas: ");
                    int qtdPizzas = scanner.nextInt();
                    double frete = calcularFrete(distancia, qtdPizzas);
                    System.out.println("Frete calculado: R$ " + frete);
                    break;
                case 7:
                    System.out.println("Saindo...");
                    continuar = false;
                    scanner.close();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void fazerPedido(Scanner scanner, List<Pedido> pedidos, List<Cliente> clientes) {
        List<Pizza> pizzas = new ArrayList<>();
        System.out.println("Fazendo um novo pedido...");
        
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }

        int x = 1;
        System.out.println("Escolha o cliente: ");
        for (Cliente cliente : clientes) {
            System.out.println(x + ". " + cliente.getNome());
            x++;
        }

        System.out.print("Opção: ");
        int opcaoCliente = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        boolean continuar = true;
        while (continuar) {
            x = 1;
            System.out.println("Selecione um tamanho: ");
            for (tamanhoPizza t : Pizza.tamanhoPizza.values()) {
                System.out.println(x + ". " + t);
                x++;
            }

            System.out.print("Opção: ");
            int tamanho = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            int quantiSabores = 0;
            while (quantiSabores < 1 || quantiSabores > 4) {
                System.out.print("Digite a quantidade de sabores (1-4): ");
                quantiSabores = scanner.nextInt();
                scanner.nextLine();
            }

            Cardapio cardapio = new Cardapio();
            List<String> saboresSelect = new ArrayList<>();
            List<String> saboresList = new ArrayList<>(cardapio.getCardapio().keySet());

            for (int i = 0; i < quantiSabores; i++) {
                System.out.println("Selecione o sabor: ");
                for (int j = 0; j < saboresList.size(); j++) {
                    System.out.println((j+1) + ". " + saboresList.get(j));
                }

                System.out.print("Opção: ");
                int opcaoSabor = scanner.nextInt();
                scanner.nextLine();
                saboresSelect.add(saboresList.get(opcaoSabor-1));
            }

            Pizza pizza = new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect), tamanhoPizza.getByIndex(tamanho));
            pizzas.add(pizza);

            System.out.print("Deseja cadastrar mais uma pizza? (1-Sim, 2-Não): ");
            String opcaoContinuar = scanner.nextLine();

            if (!opcaoContinuar.equals("1")) {
                continuar = false;
            }
        }
        Pedido pedido = new Pedido(pedidos.size()+1, clientes.get(opcaoCliente-1), pizzas, somarPizzas(pizzas));
        pedidos.add(pedido);
    }

    private static double somarPizzas(List<Pizza> pizzas) {
        double valorTotal = 0;
        for (Pizza pizza : pizzas) {
            valorTotal += pizza.getPreco();
        }
        return valorTotal;
    }

    // 🔹 Alterar pedido
    private static void alterarPedido(Scanner scanner, List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        System.out.print("Digite o ID do pedido: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Pedido pedidoEncontrado = null;
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                pedidoEncontrado = p;
                break;
            }
        }

        if (pedidoEncontrado == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }

        System.out.println("Pedido encontrado: " + pedidoEncontrado);
        System.out.println("1 - Adicionar pizza");
        System.out.println("2 - Remover pizza");
        System.out.println("3 - Alterar sabor de uma pizza");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                System.out.print("Digite o sabor (simples): ");
                String novoSabor = scanner.nextLine();
                List<String> sabores = new ArrayList<>();
                sabores.add(novoSabor);
                Pizza novaPizza = new Pizza(sabores, 30.0, tamanhoPizza.MEDIA);
                pedidoEncontrado.adicionarPizza(novaPizza);
                break;
            case 2:
                System.out.print("Digite o índice da pizza a remover (0..n): ");
                int idx = scanner.nextInt();
                pedidoEncontrado.removerPizza(idx);
                break;
            case 3:
                System.out.print("Digite o índice da pizza a alterar (0..n): ");
                int i = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Novo sabor: ");
                String saborAlterado = scanner.nextLine();
                pedidoEncontrado.getPizzas().get(i).setSabores(Arrays.asList(saborAlterado));
                break;
        }

        System.out.println("Pedido atualizado: " + pedidoEncontrado);
    }

    // 🔹 Relatório
    private static void exibirRelatorio(List<Pedido> pedidos) {
        double faturamento = 0;
        Map<String, Integer> contagemSabores = new HashMap<>();
        Map<String, Set<String>> grafoSabores = new HashMap<>();

        for (Pedido p : pedidos) {
            faturamento += p.getValorTotal();

            for (Pizza pizza : p.getPizzas()) {
                List<String> sabores = pizza.getSabores();

                for (String sabor : sabores) {
                    contagemSabores.put(sabor, contagemSabores.getOrDefault(sabor, 0) + 1);
                    grafoSabores.putIfAbsent(sabor, new HashSet<>());
                }

                for (int i = 0; i < sabores.size(); i++) {
                    for (int j = i+1; j < sabores.size(); j++) {
                        grafoSabores.get(sabores.get(i)).add(sabores.get(j));
                        grafoSabores.get(sabores.get(j)).add(sabores.get(i));
                    }
                }
            }
        }

        System.out.println("\n=== RELATÓRIO DE VENDAS ===");
        System.out.println("Faturamento total: R$ " + faturamento);
        System.out.println("Sabores mais pedidos: " + contagemSabores);
        System.out.println("Conexões entre sabores: ");
        for (var entry : grafoSabores.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    // 🔹 Calcular frete
    private static double calcularFrete(double distanciaKm, int qtdPizzas) {
        double precoPorKm = 2.0;
        double precoPorPizza = 1.5;
        return distanciaKm * precoPorKm + qtdPizzas * precoPorPizza;
    }

    private static Cliente adicionarCliente(Scanner scanner) {
        System.out.println("Adicionar cliente...");
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o endereço do cliente: ");
        String endereco = scanner.nextLine();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();

        return new Cliente(nome, endereco, telefone, email);
    }

    private static void exibirListaClientes(List<Cliente> clientes) {
        int x = 1;
        for (Cliente cliente : clientes) {
            System.out.println("Cliente " + x);
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Endereço: " + cliente.getEndereco());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println();
            x++;
        }
    }
}