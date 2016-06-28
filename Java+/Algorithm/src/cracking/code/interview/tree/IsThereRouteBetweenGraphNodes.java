package cracking.code.interview.tree;

/**
 * Given a directed graph, design an algorithm to find out whether there is a route
 * between two nodes.
 */
class IsThereRouteBetweenGraphNodes {

    static boolean hasPath(Node from, Node to) {
        if (from == to) {
            throw new IllegalArgumentException();
        }
        if (from.visited) {
            return false;
        }
        from.visited = true;
        if (from.paths == null) {
            return false;
        }
        for (Node path : from.paths) {
            boolean has = path == to || hasPath(path, to);
            if (has) {
                return true;
            }
        }
        return false;
    }

    static class Node {
        Node() {
        }

        Node[] paths;
        boolean visited;
    }
}
