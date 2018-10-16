import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class Solver {
    double[][] H_local;             //obecna macierz H
    double[][] H_global;            //globalna macierz H
    double[] P_local;        //obecny wektor P
    double[] P_global;        //globalny wektor P

    GlobalData gd;
    Element_U el;

    public Solver() throws FileNotFoundException {
        gd = GlobalData.getInstance();
        el = new Element_U();

        H_local = new double[4][4];
        H_global = new double[gd.nh][gd.nh];

        P_local = new double[4];
        P_global = new double[gd.nh];
    }

    public void solve() throws FileNotFoundException {
        for (int i = 0; i < gd.nh; i++) {
            for (int j = 0; j < gd.nh; j++) {
                H_global[i][j] = 0;
            }
            P_global[i] = 0;
        }

        //tworzymy saitkę i jakobian
        Gride grid = Gride.getInstance();
        Jakobian jakobian;

        double[] dNdx = new double[4];          //wektor pochodnych funkcji kształtu po x
        double[] dNdy = new double[4];          //wektor pochodnych funkcji kształtu po y
        double[] x = new double[4];             //współrzędne x-ksowe węzłów z elementu
        double[] y = new double[4];             //współrzędne y-kowe węzłów z elementu
        double[] temp_el = new double[4];        //wektor temperatur z węzłów elementu
        double temp_interpol;                    //temperatura zinterpolowana z rogu elementu do punktu całkowania
        double C_local;                         // obecna C
        int id;                                 //wyciągamy id noda globalnego
        double detj = 0;                        //zerujemy wyznacznik

        for (int num_el = 0; num_el < gd.ne; num_el++) {
            //zerowanie macierzy dla obecnego elementu
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    H_local[i][j] = 0;
                }
                P_local[i] = 0;
            }

            //wyciągamy dane z elementu z siatki
            for (int i = 0; i < 4; i++) {

                id = grid.elements[num_el].globalNodeID[i];
                x[i] = grid.nodes[id].x;
                y[i] = grid.nodes[id].y;
                temp_el[i] = grid.nodes[id].t;
            }

            for (int punktycal = 0; punktycal < 4; punktycal++) { // 4 - liczba punktow calkowania po powierzchni w elemencie
                temp_interpol = 0;
                jakobian = new Jakobian(x, y, punktycal); //jakobian dla danego punktu całkowania

                //pętla po węzłach
                for (int j = 0; j < 4; j++) { // 4 - liczba wezlow w wykorzystywanym elemencie skonczonym
                    temp_interpol += temp_el[j] * el.dN[punktycal][j]; //interpolacja temperatury z rogu elementu do punktu całkowania

                    dNdx[j] = 1.0 / jakobian.detJ * (jakobian.J_odw[0][0] * el.dNdE[punktycal][j] + jakobian.J_odw[0][1] * el.dNdn[punktycal][j]);
                    dNdy[j] = 1.0 / jakobian.detJ * (jakobian.J_odw[1][0] * el.dNdE[punktycal][j] + jakobian.J_odw[1][1] * el.dNdn[punktycal][j]);
                }

//                System.out.println("w punkcie " + punktycal + "dla emenentu " + num_el);
//                for (int k = 0; k < 4; k++) {
//                    System.out.println("macierz dNdx" + dNdx[k]);
//                    System.out.println("macierz dNdy" + dNdy[k]);
//
//                }

                detj = jakobian.detJ;

                //mamy macierz 4x4 wiec musimy do kazdego elementu dopisac
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        C_local = gd.specificHeat * gd.density * el.dN[punktycal][i] * el.dN[punktycal][j] * detj;
                        H_local[i][j] += gd.conductivity * (dNdx[i] * dNdx[j] + dNdy[i] * dNdy[j]) * detj + C_local / gd.simulationStepTime;// obliczanie macierzy H w oparcu o wczesniejsze 2 macierze
                        P_local[i] += C_local / gd.simulationStepTime * temp_interpol;
                    }
                }

//                for (int j = 0; j < 4; j++) {
//                    System.out.println("\nmacierz H dla elemeny " + num_el);
//                    for (int k = 0; k < 4; k++) {
//                        System.out.print(H_local[j][k] + " ");
//                    }
//                }
            }

            //warunki brzegowe
            for (int ipow = 0; ipow < grid.elements[num_el].liczbaPowierzchni; ipow++) {    // poruszamy sie po powierzchniach kontaktowych z otoczeniem
                id = grid.elements[num_el].lokalLiczbaPow[ipow]; // pobieramy z elementu id lokalnej powierzchni (0-3);

                // wybieramy ktora to jest powierzchnia
                // i obliczamy detj = delta x /2
                if (id == 0)
                    detj = Math.sqrt(Math.pow(grid.elements[num_el].nodeID[3].x - grid.elements[num_el].nodeID[0].x, 2) + Math.pow(grid.elements[num_el].nodeID[3].y - grid.elements[num_el].nodeID[0].y, 2)) / 2.0;
                else if (id == 1)
                    detj = Math.sqrt(Math.pow(grid.elements[num_el].nodeID[0].x - grid.elements[num_el].nodeID[1].x, 2) + Math.pow(grid.elements[num_el].nodeID[0].y - grid.elements[num_el].nodeID[1].y, 2)) / 2.0;
                else if (id == 2)
                    detj = Math.sqrt(Math.pow(grid.elements[num_el].nodeID[1].x - grid.elements[num_el].nodeID[2].x, 2) + Math.pow(grid.elements[num_el].nodeID[1].y - grid.elements[num_el].nodeID[2].y, 2)) / 2.0;
                else if (id == 3)
                    detj = Math.sqrt(Math.pow(grid.elements[num_el].nodeID[2].x - grid.elements[num_el].nodeID[3].x, 2) + Math.pow(grid.elements[num_el].nodeID[2].y - grid.elements[num_el].nodeID[3].y, 2)) / 2.0;


                // poniewaz dwa punkty calkowania na powierzchni
                for (int p = 0; p < 2; p++) {
                    // dla macierzy ktora ma 4x4 cos podobnego do wczescniejszej macierzy
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            H_local[i][j] += gd.alfa * el.powierzchnieLokalne[id].N[p][i] * el.powierzchnieLokalne[id].N[p][j] * detj;
                        }
                        P_local[i] += gd.alfa * gd.ambientTemperature * el.powierzchnieLokalne[id].N[p][i] * detj;
                    }
                }
            }
            System.out.println();
            //agregacja macierzy H i P
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    H_global[grid.elements[num_el].globalNodeID[i]][grid.elements[num_el].globalNodeID[j]] += H_local[i][j];
                }
                P_global[grid.elements[num_el].globalNodeID[i]] += P_local[i];
            }
//            System.out.println("\nmacierz H globalna");
//            for (int j = 0; j < gd.nh; j++) {
//                System.out.println();
//                DecimalFormat dec = new DecimalFormat("#0.000");
//                for (int k = 0; k < gd.nh; k++) {
//                    System.out.print(dec.format(H_global[j][k]) + "\t\t");
//                }
//            }
//            System.out.println("\nWektor P globalna");
//            DecimalFormat dec = new DecimalFormat("#0.000");
//            for (int j = 0; j < gd.nh; j++) {
//
//                System.out.print(dec.format(P_global[j]) + "\t\t");
//
//            }
        }
    }
}
