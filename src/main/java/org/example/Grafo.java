package org.example;

// Aluno: Felipe Babel

import java.util.Arrays;

public class Grafo {
    private final int[][] matrizAdj;
    private final int numVertices;

    public Grafo(int[][] matrizAdj) {
        this.numVertices = matrizAdj.length;
        this.matrizAdj = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            this.matrizAdj[i] = Arrays.copyOf(matrizAdj[i], numVertices);
        }
    }

    public String tipoDoGrafo() {
        boolean direcionado = false;
        boolean multigrafo = false;
        boolean possuiLaco = false;
        boolean vazio = true;

        // Checa matriz
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdj[i][j] > 0) vazio = false;
                if (i == j && matrizAdj[i][j] > 0) possuiLaco = true;
                if (matrizAdj[i][j] > 1) multigrafo = true;
                if (matrizAdj[i][j] != matrizAdj[j][i]) direcionado = true;
            }
        }

        if (vazio) return "Grafo nulo";

        StringBuilder tipo = new StringBuilder();

        if (direcionado) tipo.append("Grafo direcionado");
        else tipo.append("Grafo não dirigido");

        if (possuiLaco) tipo.append(", Pseudografo");
        else if (multigrafo) tipo.append(", Multigrafo");
        else tipo.append(", Simples");

        return tipo.toString();
    }

    public String arestasDoGrafo() {
        StringBuilder sb = new StringBuilder("E = { ");
        boolean vazio = true;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdj[i][j] > 0) {
                    sb.append("(").append(i).append(",").append(j).append(") ");
                    vazio = false;
                }
            }
        }
        sb.append("}");
        return vazio ? "Sem arestas" : sb.toString();
    }

    public String grausDoVertice() {
        StringBuilder sb = new StringBuilder();

        boolean dirigido = tipoDoGrafo().contains("direcionado");

        int[] graus = new int[numVertices];
        int grauGrafo = 0;

        for (int i = 0; i < numVertices; i++) {
            int grau = 0;

            if (dirigido) {
                // Grafo dirigido: soma 1 + 1
                for (int j = 0; j < numVertices; j++) {
                    grau += matrizAdj[i][j];
                    grau += matrizAdj[j][i];
                }
            } else {
                // Grafo não dirigido ou pseudografo: soma linha, laços contam duas vezes
                for (int j = 0; j < numVertices; j++) {
                    grau += matrizAdj[i][j];
                }
                grau += matrizAdj[i][i]; // laço conta duas vezes
            }

            graus[i] = grau;
            grauGrafo += grau;
        }

        sb.append("Grau do grafo = ").append(grauGrafo).append("\n");

        sb.append("Graus dos vértices: ");
        for (int i = 0; i < numVertices; i++) {
            sb.append("v").append(i).append("=").append(graus[i]).append(" ");
        }
        sb.append("\n");

        sb.append("Sequência de graus: ");
        int[] grausOrdenados = Arrays.copyOf(graus, graus.length);
        Arrays.sort(grausOrdenados); // adiciona nova pra ordenar e nao alterar a lista original
        for (int i = grausOrdenados.length - 1; i >= 0; i--) {
            sb.append(grausOrdenados[i]).append(" ");
        }

        return sb.toString();
    }
}
