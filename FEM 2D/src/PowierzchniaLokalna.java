public class PowierzchniaLokalna {
    double[][] N;
    NodeLokal nodes[];

    public PowierzchniaLokalna(NodeLokal n1, NodeLokal n2) {
        N = new double[2][4];       // wartości funkci krztaltu ktora jest nam potrzebna do warunku brzegowego
        nodes = new NodeLokal[2];
        nodes[0] = n1;
        nodes[1] = n2;
    }

}
