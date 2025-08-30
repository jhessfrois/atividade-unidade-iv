package projeto;

import java.util.List;

public class Pizza {
    private List<String> sabores;
    private double preco;
    private tamanhoPizza tamanho;

    public enum tamanhoPizza {
        PEQUENA, MEDIA, GRANDE;

        public static tamanhoPizza getByIndex(int index) {
            switch (index) {
                case 1:
                    return PEQUENA;
                case 2:
                    return MEDIA;
                case 3:
                    return GRANDE;
                default:
                    throw new IllegalArgumentException("Índice inválido para tamanho de pizza.");
            }
        }
    }

    public Pizza(List<String> sabores, double preco, tamanhoPizza tamanho) {
        this.sabores = sabores;
        this.preco = preco;
        this.tamanho = tamanho;
    }

    public List<String> getSabores() {
        return sabores;
    }

    public double getPreco() {
        return preco;
    }

    public tamanhoPizza getTamanho() {
        return tamanho;
    }

    public void setSabores(List<String> sabores) {
        this.sabores = sabores;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTamanho(tamanhoPizza tamanho) {
        this.tamanho = tamanho;
    }
}
