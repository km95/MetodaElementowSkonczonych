import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Gride grid = Gride.getInstance();
        //grid.showNodes();
        System.out.println();
        //grid.showElements();
        GlobalData gd = GlobalData.getInstance();
        Solver solver = new Solver();
        double[] t;                    // wektor  temperatur ktorego szukamy
        int timeStep = (int) (gd.simulationTime / gd.simulationStepTime); // czas symulacji / krok czasowy symulacji  - otrzymamy ilosc iteracji
        System.out.println("TIME"+"\t\tMIN"+"\t\t\tMAX");
        for (int i = 0; i < timeStep; i++) {
            solver.solve(); // rownanie furiera
            t = GaussElimination.Gauss(gd.nh,solver.H_global,solver.P_global); // znalezienie temperatury za pomoca gaussa

            for (int j = 0; j < gd.nh; j++) {
                grid.nodes[j].setT(t[j]);     // po policzeniu wektora temperatur nalezy nadac nowa temperature kazdemu z wezlow
             //   System.out.print( grid.nodes[j].t+"\t");
            }
            //System.out.println();

            System.out.print(gd.simulationStepTime*(i+1)+"\t\t");
            System.out.printf("%.3f\t\t",Arrays.stream( t ).min().getAsDouble());
            System.out.printf("%.3f\t\t",Arrays.stream( t ).max().getAsDouble());
            System.out.println();
          //  System.out.println(Arrays.stream( t ).min().getAsDouble());
           // System.out.println(Arrays.stream( t ).max().getAsDouble());
        }
//        int k = 0;
//        for ( int i = 0; i < gd.nB; i++ ) {
//            for ( int j = 0; j < gd.nH; j++ )
//                System.out.printf( "%.3f\t\t", grid.nodes[k++].t );
//            System.out.println();
//        }

    }
}