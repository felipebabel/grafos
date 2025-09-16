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

        isCompleto(direcionado, possuiLaco, multigrafo, tipo);
        boolean isRegular = isRegular(direcionado);
        if (isRegular) tipo.append(", Regular");
        return tipo.toString();
    }

    private boolean isRegular(boolean direcionado) {
        //Um grafo é regular se todos os vértices têm o mesmo grau.
        int[] graus = new int[numVertices];
        boolean isRegular = true;

        for (int i = 0; i < numVertices; i++) {
            int grau = 0;
            if (direcionado) {
                // soma entrada e saida
                for (int j = 0; j < numVertices; j++) {
                    grau += matrizAdj[i][j];
                    grau += matrizAdj[j][i];
                }
            } else {
                // Grafo não dirigido: soma a linha (arestas) + laços
                for (int j = 0; j < numVertices; j++) grau += matrizAdj[i][j];
                grau += matrizAdj[i][i]; // cada laço conta duas vezes
            }
            graus[i] = grau; // armazena grau do vértice i
        }

        // Verifica se todos os vértices têm o mesmo grau
        for (int i = 1; i < numVertices; i++) {
            if (graus[i] != graus[0]) {
                return false;
            }
        }
        return isRegular;
    }


    private void isCompleto(boolean direcionado, boolean possuiLaco, boolean multigrafo, StringBuilder tipo) {
        //Um grafo completo (não dirigido, simples) é aquele em que todos os vértices estão conectados a todos os outros vértices, sem laços ou arestas múltiplas.
        if (!direcionado && !possuiLaco && !multigrafo) {
            // Número máximo de arestas possíveis em um grafo simples não dirigido
            int maxArestas = numVertices * (numVertices - 1) / 2; //multiplica coluna x linhas /2

            int contArestas = 0;
            for (int i = 0; i < numVertices; i++) {
                for (int j = i + 1; j < numVertices; j++) { // j > i para não contar duas vezes
                    if (matrizAdj[i][j] > 0) contArestas++;
                }
            }

            // Se todas as arestas possíveis existem, é completo
            if (contArestas == maxArestas) {
                tipo.append(", Completo");
            }
        }
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

    public String buscaEmProfundidade() {
        boolean[] visitado = new boolean[numVertices]; // marca os vértices já visitados, inicalmente tudo como false
        StringBuilder ordem = new StringBuilder("Ordem de visita: ");

        for (int v = 0; v < numVertices; v++) {
            if (!visitado[v]) { // se nao foi visitado vai verificar os vizinhos
                buscaEmProfundidade(v, visitado, ordem);
            }
        }

        return ordem.toString();
    }

    private void buscaEmProfundidade(int v, boolean[] visitado, StringBuilder ordem) {
        visitado[v] = true;
        ordem.append(v).append(" ");  // adiciona na string

        for (int j = 0; j < numVertices; j++) {
            if (matrizAdj[v][j] > 0 && !visitado[j]) { // se tiver aresta e nao foi visitado entra no metodo de novo
                buscaEmProfundidade(j, visitado, ordem);
            }
        }
    }

}
