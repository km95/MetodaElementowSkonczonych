import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class Gride {

    Node[] nodes;
    Element[] elements;
    GlobalData gd;
    private static Gride grid = null;

    public Gride() throws FileNotFoundException {  // tworzenie siatki
        double alfa;            //wspołczynnik wymiany ciepła
        double specificHeat;    //ciepło właściwe
        double conductivity;    //współczynnik przewodzenia ciepła
        double density;         //gęstość
        gd = GlobalData.getInstance();

        //tworzenie elementów
        elements = new Element[gd.ne];
        int k = 0;
        for (int i = 0; i < (gd.nB - 1); i++) {
            for (int j = 0; j < (gd.nH - 1); j++) {
                // szyba 4-powietrze 12-szyba 4 -powietrze 12-szyba 4    elementow --- 73
//                if (i <  (gd.nB - 1) -64.0){
//                    alfa = gd.alfa;
//                    specificHeat = gd.specificHeat;
//                    conductivity = gd.conductivity;
//                    density = gd.density;
//                    elements[k++] = new Element(i, j,alfa,specificHeat,conductivity,density);
//                }
//
//                if ((i >=  (gd.nB - 1) -64.0) && (i < (gd.nB - 1) - 40.0)){
//                    alfa = gd.alfa;
//                    specificHeat = gd.specificHeat2;
//                    conductivity = gd.conductivity2;
//                    density = gd.density2;
//                    elements[k++] = new Element(i, j,alfa,specificHeat,conductivity,density);
//                }
//
//                if ((i >= (gd.nB - 1) - 40.0) && (i < (gd.nB - 1) - 32.0)){
//                    alfa = gd.alfa;
//                    specificHeat = gd.specificHeat;
//                    conductivity = gd.conductivity;
//                    density = gd.density;
//                    elements[k++] = new Element(i, j,alfa,specificHeat,conductivity,density);
//                }
//
//                if ((i >=  (gd.nB - 1) -32.0) && (i < (gd.nB - 1) - 8.0)){
//                    alfa = gd.alfa;
//                    specificHeat = gd.specificHeat2;
//                    conductivity = gd.conductivity2;
//                    density = gd.density2;
//                    elements[k++] = new Element(i, j,alfa,specificHeat,conductivity,density);
//                }
//
//                if ((i >= (gd.nB - 1) - 8.0)){
//                    alfa = gd.alfa;
//                    specificHeat = gd.specificHeat;
//                    conductivity = gd.conductivity;
//                    density = gd.density;
//                    elements[k++] = new Element(i, j,alfa,specificHeat,conductivity,density);
//                }

                // szyba 4 - powietrze 12-szyba 4 - elementow ---41
                if ((i <  (gd.nB - 1) -8.0) && (i >= (gd.nB - 1) - 32.0)){
                    alfa = gd.alfa2;
                    specificHeat = gd.specificHeat2;
                    conductivity = gd.conductivity2;
                    density = gd.density2;
                }else {
                    alfa = gd.alfa;
                    specificHeat = gd.specificHeat;
                    conductivity = gd.conductivity;
                    density = gd.density;
                }
                    elements[k++] = new Element(i, j,alfa,specificHeat,conductivity,density);
            }
        }

        //tworzenie węzłów
        nodes = new Node[gd.nh];
        k = 0;
        for (int i = 0; i < gd.nB; i++) {
            for (int j = 0; j < gd.nH; j++) {
                nodes[k++] = new Node(i * gd.dB, j * gd.dH);
            }
        }
    }


    public static Gride getInstance() throws FileNotFoundException { // singleton  - zagwarantowanie ze bedzie tylko jedna instancja danej klasy
        if (grid == null)
            grid = new Gride();
        return grid;
    }

}
