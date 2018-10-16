public class Jakobian {

    Element_U lokalny = new Element_U();

    double detJ;
    double [][] J;
    double[][] J_odw;

    public Jakobian ( double[] x, double[] y, int punktCalkowania ) {
        J = new double[2][2];
        J_odw = new double[2][2];

        J( x, y, punktCalkowania );
        J_odw();
        detJ = J[0][0] * J[1][1] - J[0][1] * J[1][0];
    }
    // obliczamy macierz J  korzystajac z elementu uniwersalnego (jakobian przeksztalcen)
    private void J ( double[] x, double[] y, int punktCalkowania ) {
        J[0][0] = lokalny.dNdE[punktCalkowania][0] * x[0] + lokalny.dNdE[punktCalkowania][1] * x[1] + lokalny.dNdE[punktCalkowania][2] * x[2] + lokalny.dNdE[punktCalkowania][3] * x[3];
        J[1][0] = lokalny.dNdE[punktCalkowania][0] * y[0] + lokalny.dNdE[punktCalkowania][1] * y[1] + lokalny.dNdE[punktCalkowania][2] * y[2] + lokalny.dNdE[punktCalkowania][3] * y[3];
        J[0][1] = lokalny.dNdn[punktCalkowania][0] * x[0] + lokalny.dNdn[punktCalkowania][1] * x[1] + lokalny.dNdn[punktCalkowania][2] * x[2] + lokalny.dNdn[punktCalkowania][3] * x[3];
        J[1][1] = lokalny.dNdn[punktCalkowania][0] * y[0] + lokalny.dNdn[punktCalkowania][1] * y[1] + lokalny.dNdn[punktCalkowania][2] * y[2] + lokalny.dNdn[punktCalkowania][3] * y[3];
    }
    private void J_odw () {
        J_odw[0][0] = J[1][1];
        J_odw[1][0] = - 1 * J[1][0];
        J_odw[0][1] = - 1 * J[0][1];
        J_odw[1][1] = J[0][0];
    }

}
