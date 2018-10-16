import java.io.FileNotFoundException;

public class Element {
    Node[] nodeID;
    Powierzchnia[] powierzchnie;
    int liczbaPowierzchni;      //liczba powierzchni ktore sa na brzegach
    int[] lokalLiczbaPow;       // lokalne numery powierzchni konkretnych elementow
    GlobalData gd;
    int[] globalNodeID;
    double alfa;                //wspołczynnik wymiany ciepła
    double specificHeat;        //ciepło właściwe
    double conductivity;        //współczynnik przewodzenia ciepła
    double density;            //gęstość

    public Element(double x, double y, double alfa, double specificHeat, double conductivity, double density) throws FileNotFoundException {
        this.alfa = alfa;
        this.specificHeat = specificHeat;
        this.conductivity = conductivity;
        this.density = density;

        gd = GlobalData.getInstance();
        nodeID = new Node[4];

        globalNodeID = new int[4];
        int i = (int) x;
        int j = (int) y;

        //wyznaczamy globalne id wezlow w elemencie
        globalNodeID[0] = gd.nH * i + j;
        globalNodeID[1] = gd.nH * (i + 1) + j;
        globalNodeID[2] = gd.nH * (i + 1) + (j + 1);
        globalNodeID[3] = gd.nH * i + (j + 1);

        x = x * gd.dB;
        y = y * gd.dH;

        //wyznaczamy wspolrzedne lokalnych wezłow
        nodeID[0] = new Node(x, y);
        nodeID[1] = new Node(x + gd.dB, y);
        nodeID[2] = new Node(x + gd.dB, y + gd.dH);
        nodeID[3] = new Node(x, y + gd.dH);

        // tworzymy powierzchnie zaczynajac od lewej strony
        powierzchnie = new Powierzchnia[4];
        powierzchnie[0] = new Powierzchnia(nodeID[3], nodeID[0]);
        powierzchnie[1] = new Powierzchnia(nodeID[0], nodeID[1]);
        powierzchnie[2] = new Powierzchnia(nodeID[1], nodeID[2]);
        powierzchnie[3] = new Powierzchnia(nodeID[2], nodeID[3]);

        // obliczamy liczbe powierzchni na brzegu
        liczbaPowierzchni = 0;
        for (int k = 0; k < 4; k++) {
            if (powierzchnie[k].nodes[0].status == 1 && powierzchnie[k].nodes[1].status == 1) {
                liczbaPowierzchni++;
            }
        }

        // macierz z numerami powierzchni ktore sa na brzegu
        lokalLiczbaPow = new int[liczbaPowierzchni];
        int licznik = 0;
        for (int k = 0; k < 4; k++) {
            if (powierzchnie[k].nodes[0].status == 1 && powierzchnie[k].nodes[1].status == 1) {
                lokalLiczbaPow[licznik++] = k;
            }
        }
    }

}