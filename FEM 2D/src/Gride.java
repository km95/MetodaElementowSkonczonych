import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class Gride {

    Node[] nodes;
    Element[] elements;
    GlobalData gd;
    private static Gride grid = null;

    public Gride () throws FileNotFoundException {  // tworzenie siatki
        gd = GlobalData.getInstance();

        //tworzenie elementów
        elements = new Element[gd.ne];
        int k = 0;
        for ( int i = 0; i < ( gd.nB - 1 ); i++ ) {
            for ( int j = 0; j < ( gd.nH - 1 ); j++ ) {
                elements[k++] = new Element( i , j );
            }
        }

        //tworzenie węzłów
        nodes = new Node[gd.nh];
        k = 0;
        for ( int i = 0; i < gd.nB; i++ ) {
            for ( int j = 0; j < gd.nH; j++ ) {
                nodes[k++] = new Node( i * gd.dB, j * gd.dH);
            }
        }
    }

    public void showElements () {
        System.out.println("\n\nELEMENTY\n\n");

        DecimalFormat dec = new DecimalFormat( "#0.0000" );
        Node[] n;
        for (int i = 0; i < 4 ; i++) {
            n = elements[i].nodeID;
            for (int j = 0; j < 4; j++) {
                System.out.println(dec.format(n[j].x) + "\t" + dec.format(n[j].y) + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void showNodes () {
        System.out.println("\n\nWEZLY\n\n");
        DecimalFormat dec = new DecimalFormat( "#0.0000" );
        int k = 0;
        for ( int i = gd.nB; i >0; i-- ) {
            for ( int j = gd.nH; j > 0; j-- ) {
                System.out.print(dec.format( nodes[k].x ) + "" + dec.format( nodes[k].y ) + "\t\t" );
                k++;
            }
            System.out.println( "\n" );
        }

    }

    public static Gride getInstance() throws FileNotFoundException { // singleton  - zagwarantowanie ze bedzie tylko jedna instancja danej klasy
        if (grid == null)
            grid = new Gride();
        return grid;
    }

}
