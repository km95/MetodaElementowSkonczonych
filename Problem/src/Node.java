import java.io.FileNotFoundException;

public class Node {

    double x, y;  // wspołrzedne wezla
    double t;       // temperatura w wezle
    int status;     // status wykorzystywany do warunku brzegowego
    GlobalData gd;

    public Node(double x, double y) throws FileNotFoundException {
        gd = GlobalData.getInstance();
        this.x = x;
        this.y = y;
        this.t = gd.initialTemperature;
        if (this.x == 0.0 || this.x >= gd.B) {       // ustawiamy status elementu. Czy dany elment znajduje sie na brzegu. 1 - na brzegu 0 - w srodku
            status = 1;
        } else {
            status = 0;
        }
    }

    public void setT(double t) {
        this.t = t;
    }
}
