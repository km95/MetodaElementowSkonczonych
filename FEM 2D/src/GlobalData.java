import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GlobalData {

    double H;                       //wysokość
    double B;                       //szerokość
    int nH;                         //liczba węzłów po wysokości
    int nB;                         //liczba węzłów po szerokości
    double dB;                      //liczba elementów po szerokości
    double dH;                      //liczba elementów po wysokości
    int nh;                         //liczba węzłów
    int ne;                         //liczba elementów

    double alfa;                    //wspołczynnik wymiany ciepła
    double specificHeat;			//ciepło właściwe
    double conductivity;            //współczynnik przewodzenia ciepła k
    double density;					//gęstość
    double initialTemperature;		//temperatura poczatkowa T
    double simulationTime;		    //czas symulacji
    double simulationStepTime;		    //czas kroku symulacji
    double ambientTemperature;		//temperatura otoczenia

    static GlobalData gd = null;

    public GlobalData ( String name )throws FileNotFoundException{
        try {
            Scanner file = new Scanner( new File( name ) );
            H = file.nextDouble();              //wysokosc przekroju, m
            file.nextLine();
            B = file.nextDouble();              //szerokosc przekroju, m
            file.nextLine();
            nH = file.nextInt();                //liczba wezlow po wysokosci
            file.nextLine();
            nB = file.nextInt();                //liczba wezlow po szerokosci
            file.nextLine();
            alfa = file.nextDouble();           //wspołczynnik wymiany ciepła
            file.nextLine();
            specificHeat = file.nextDouble();           //wspołczynnik wymiany ciepła
            file.nextLine();
            conductivity = file.nextDouble();           //współczynnik przewodzenia ciepła k
            file.nextLine();
            density = file.nextDouble();                //gęstość
            file.nextLine();
            initialTemperature = file.nextDouble();     //temperatura poczatkowa T
            file.nextLine();
            simulationTime = file.nextDouble();          //czas symulacji
            file.nextLine();
            simulationStepTime = file.nextDouble();         //czas kroku symulacji
            file.nextLine();
            ambientTemperature = file.nextDouble();     //temperatura otoczenia
            file.close();
            dB = B / ( nB - 1 );                        //długość jednej scianki elementu
            dH = H / ( nH - 1 );

            ne = ( nH - 1 ) * ( nB - 1 );       // liczba elementow
            nh = nH * nB;                       // liczba wszystkich wezłow
        }catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }

    }
    public static GlobalData getInstance () throws FileNotFoundException { // singleton  - zagwarantowanie ze bedzie tylko jedna instancja danej klasy
        if ( gd == null )
            gd = new GlobalData( "dane.txt" );
        return gd;
    }


}