public class Arc {
    private Nod startNode; // Nodul de început al arcului
    private Nod endNode; // Nodul de sfârșit al arcului
    private int cost; // Costul de parcurs al arcului (poate fi 1 pentru fiecare drum)

    // Constructor
    public Arc(Nod startNode, Nod endNode, int cost) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.cost = cost;
    }

    public Nod getStartNode() {
        return startNode;
    }

    public Nod getEndNode() {
        return endNode;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "start=" + startNode +
                ", end=" + endNode +
                ", cost=" + cost +
                '}';
    }
}
