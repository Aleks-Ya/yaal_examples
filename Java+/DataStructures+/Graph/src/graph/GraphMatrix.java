package graph;

/**
 * Реализация графа на основе матрицы смежности.
 * Из книги "Структуры данных и алгоритмы Java" (Лафоре).
 */
public class GraphMatrix {
    private final int MAX_VERTS = 20;
    private Vertex vertexList[];// Массив вершин
    private int adjMat[][]; //Матрица смежности
    private int nVerts;

    public GraphMatrix() {
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for (int j = 0; j < MAX_VERTS; j++) {
            for (int k = 0; k < MAX_VERTS; k++) {
                adjMat[j][k] = 0;
            }
        }
    }

    public void addVertex(char label) {
        vertexList[nVerts++] = new Vertex(label);
    }

    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }

    public void displayVertex(int v) {
        System.out.println(vertexList[v].label);
    }
}
