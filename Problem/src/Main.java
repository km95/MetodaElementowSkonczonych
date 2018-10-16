import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Gride grid = Gride.getInstance();
        //grid.showNodes();
        System.out.println();
        //grid.showElements();
        GlobalData gd = GlobalData.getInstance();
        Solver solver = new Solver();
        double[] t;                    // wektor  temperatur ktorego szukamy
        int timeStep = (int) (gd.simulationTime / gd.simulationStepTime); // czas symulacji / krok czasowy symulacji  - otrzymamy ilosc iteracji

        for (int i = 0; i < timeStep; i++) {
            solver.solve(); // rownanie furiera
            t = GaussElimination.Gauss(gd.nh,solver.H_global,solver.P_global); // znalezienie temperatury za pomoca gaussa

            for (int j = 0; j < gd.nh; j++) {
                grid.nodes[j].setT(t[j]);     // po policzeniu wektora temperatur nalezy nadac nowa temperature kazdemu z wezlow
            }

            double suma = 0.0;
            double srednia = 0;
            double suma2 = 0.0;
            double srednia2 = 0;
            for (int j = 16; j < 66; j++) {
                suma += grid.nodes[j].t;
            }
            srednia = suma /50.0;
            for (int j = 16; j <66 ; j++) {
                grid.nodes[j].setT(srednia);
            }


//            for (int j = 80; j < 130; j++) {
//                suma2 += grid.nodes[j].t;
//            }
//            srednia2 = suma2 /50.0;
//            for (int j = 80; j <130 ; j++) {
//                grid.nodes[j].setT(srednia2);
//            }

            //co jedną dziesiątą całego procesu zapisuję wyniki
            if ( i%(timeStep/5) == 0 ) {
                int iterator = 0;
                for ( int k = 0; k < gd.nB; k++ ) {
                    for ( int j = 0; j < gd.nH; j++ ) {
                        sb.append( String.format( "%.3f", grid.nodes[iterator].t) + "\t" );
                        iterator++;
                    }
                    sb.append( "\n" );
                }
                sb.append( "\n\n" );
            }
        }

        //zapis wyników do pliku
        File file = new File("rezultat.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        int k = 0;
//        for ( int i = 0; i < gd.nB; i++ ) {
//            for ( int j = 0; j < gd.nH; j++ )
//                System.out.printf( "%.3f\t\t", grid.nodes[k++].t );
//            System.out.println();
//        }

    }
}