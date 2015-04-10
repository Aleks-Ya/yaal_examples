package graph;

/**
 * Вершина графа.
 * Из книги "Структуры данных и алгоритмы Java" (Лафоре).
 */
public class Vertex {
    public char label;
    public boolean wasVisited;

    public Vertex(char label) {
        this.label = label;
    }
}
