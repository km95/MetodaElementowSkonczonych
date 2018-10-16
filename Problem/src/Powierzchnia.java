public class Powierzchnia {
    Node nodes[];

    public Powierzchnia(Node n1, Node n2) {     // na jednej powierzchni zanjduja sie 2 wezly
        this.nodes = new Node[2];
        nodes[0] = n1;
        nodes[1] = n2;
    }
}
