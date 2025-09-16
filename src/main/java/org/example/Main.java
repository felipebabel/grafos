package org.example;

// Aluno: Felipe Babel

public class Main {
    public static void main(String[] args) {

        // ========================
        // 1) Grafo simples nao dirigido
        // ========================
        int[][] matrizSimples = {
              // a  b  c  d
                {0, 1, 1, 0}, // a
                {1, 0, 1, 1}, // b
                {1, 1, 0, 1}, // c
                {0, 1, 1, 0}  // d
        };
        Grafo g1 = new Grafo(matrizSimples);
        System.out.println("=== GRAFO SIMPLES ===");
        imprimirAnalise(g1);

        // ========================
        // 2) Grafo dirigido
        // ========================
        int[][] matrizDirigido = {
              // a  b  c  d
                {0, 1, 0, 0},  // a
                {0, 0, 1, 0},  // b
                {0, 0, 0, 1},  // c
                {1, 0, 0, 0}   // d
        };
        Grafo g2 = new Grafo(matrizDirigido);
        System.out.println("\n=== GRAFO DIRIGIDO ===");
        imprimirAnalise(g2);

        // ========================
        // 3) Pseudografo (com laço)
        // ========================
        int[][] matrizPseudo = {
              // a  b  c
                {1, 1, 0}, // a
                {1, 0, 1}, // b
                {0, 1, 0} // c
        };
        Grafo g3 = new Grafo(matrizPseudo);
        System.out.println("\n=== PSEUDOGRAFO ===");
        imprimirAnalise(g3);

        // ========================
        // 4) Multigrafo (arestas múltiplas)
        // ========================
        int[][] matrizMulti = {
              // a  b  c
                {0, 2, 0},  // a
                {2, 0, 1}, // b
                {0, 1, 0} // c
        };
        Grafo g4 = new Grafo(matrizMulti);
        System.out.println("\n=== MULTIGRAFO ===");
        imprimirAnalise(g4);

        // ========================
        // 5) Grafo nulo (sem arestas)
        // ========================
        int[][] matrizNulo = {
              // a  b  c
                {0, 0, 0},  // a
                {0, 0, 0},  // b
                {0, 0, 0}   // c
        };
        Grafo g5 = new Grafo(matrizNulo);
        System.out.println("\n=== GRAFO NULO ===");
        imprimirAnalise(g5);
    }

    private static void imprimirAnalise(Grafo g) {
        System.out.println("Classificacao: " + g.tipoDoGrafo());
        System.out.println(g.arestasDoGrafo());
        System.out.println(g.grausDoVertice());
        //System.out.println(g.buscaEmProfundidade());
    }
}