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
    double alfa2;                    //wspołczynnik wymiany ciepła
    double specificHeat;			//ciepło właściwe szkla
    double specificHeat2;			//ciepło właściwe powietrza
    double conductivity;            //współczynnik przewodzenia ciepła k
    double conductivity2;            //współczynnik przewodzenia ciepła k
    double density;					//gęstość
    double density2;					//gęstość
    double initialTemperature;		//temperatura poczatkowa T
    double simulationTime;		    //czas symulacji
    double simulationStepTime;		    //czas kroku symulacji
    double ambientTemperature;		//temperatura otoczenia
    double ambientTemperature2;		//temperatura otoczenia

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
            initialTemperature = file.nextDouble();     //temperatura poczatkowa T
            file.nextLine();
            simulationTime = file.nextDouble();          //czas symulacji
            file.nextLine();
            ambientTemperature = file.nextDouble();     //temperatura otoczenia wewnatrz
            file.nextLine();
            ambientTemperature2 = file.nextDouble();     //temperatura otoczenia zewnatrz
            file.nextLine();
            alfa = file.nextDouble();           //wspołczynnik wymiany ciepła
            file.nextLine();
            alfa2 = file.nextDouble();           //wspołczynnik wymiany ciepła
            file.nextLine();
            specificHeat = file.nextDouble();           //cieplo wlasciwe  szkla
            file.nextLine();
            specificHeat2 = file.nextDouble();           //cieplo wlasciwe  powietrza
            file.nextLine();
            conductivity = file.nextDouble();           //współczynnik przewodzenia ciepła k szkla
            file.nextLine();
            conductivity2 = file.nextDouble();           //współczynnik przewodzenia ciepła k powietrza
            file.nextLine();
            density = file.nextDouble();                //gęstość szkla
            file.nextLine();
            density2 = file.nextDouble();                //gęstość powietrza
            file.close();
            dB = B / ( nB - 1 );                        //długość jednej scianki elementu
            dH = H / ( nH - 1 );

            ne = ( nH - 1 ) * ( nB - 1 );       // liczba elementow
            nh = nH * nB;                       // liczba wszystkich wezłow

            //optymalny krok czasowy
            double Asr1 = conductivity / ( specificHeat * density );
            double Asr2 = conductivity2 / ( specificHeat2 * density2 );
            double Asr = ( Asr1 + Asr2 ) / 2.0;
            simulationStepTime = Math.pow( B / nB, 2 ) / ( 0.5 * Asr );

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