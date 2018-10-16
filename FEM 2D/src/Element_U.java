public class Element_U {
    NodeLokal[] punktyCalkowania;				//cztery punkty calkowania
    PowierzchniaLokalna[] powierzchnieLokalne;	//cztery powierzchnie elementu
    double [][] dN = new double[4][4] ;
    double [][] dNdE = new double[4][4] ;
    double [][] dNdn = new double[4][4] ;

    public Element_U() {
        // ksi i eta dla czterech punktów
        punktyCalkowania = new NodeLokal[4];
        punktyCalkowania[0] = new NodeLokal(-1.0 / Math.sqrt(3.0), -1.0 / Math.sqrt(3.0));
        punktyCalkowania[1] = new NodeLokal(1.0 / Math.sqrt(3.0), -1.0 / Math.sqrt(3.0));
        punktyCalkowania[2] = new NodeLokal(-1.0 / Math.sqrt(3.0), 1.0 / Math.sqrt(3.0));
        punktyCalkowania[3] = new NodeLokal(1.0 / Math.sqrt(3.0), 1.0 / Math.sqrt(3.0));
            // obliczanie punktow calkowania macierz dN
        for (int i = 0; i <4; i++) {
            dN[i][0] = (1./4.)*(1-punktyCalkowania[i].ksi)*(1-punktyCalkowania[i].eta);    // funkcje krztaltu
            dN[i][1] = (1./4.)*(1+punktyCalkowania[i].ksi)*(1-punktyCalkowania[i].eta);
            dN[i][2] = (1./4.)*(1+punktyCalkowania[i].ksi)*(1+punktyCalkowania[i].eta);
            dN[i][3] = (1./4.)*(1-punktyCalkowania[i].ksi)*(1+punktyCalkowania[i].eta);
        }
        // obliczanie punktow calkowania macierz dNdE
        for (int i = 0; i <4; i++) {
            dNdE[i][0] = -(1./4.)*(1-punktyCalkowania[i].ksi);
            dNdE[i][1] = (1./4.)*(1-punktyCalkowania[i].ksi);
            dNdE[i][2] = (1./4.)*(1+punktyCalkowania[i].ksi);
            dNdE[i][3] = -(1./4.)*(1+punktyCalkowania[i].ksi);
        }
        // obliczanie punktow calkowania macierz dNdn
        for (int i = 0; i <4; i++) {
            dNdn[i][0] = -(1./4.)*(1-punktyCalkowania[i].eta);
            dNdn[i][1] = -(1./4.)*(1+punktyCalkowania[i].eta);
            dNdn[i][2] = (1./4.)*(1+punktyCalkowania[i].eta);
            dNdn[i][3] = (1./4.)*(1-punktyCalkowania[i].eta);
        }
            // dwa punktycałkowania na powierzchni
        powierzchnieLokalne = new PowierzchniaLokalna[4];
        powierzchnieLokalne[0] = new  PowierzchniaLokalna(new NodeLokal(-1.0,1.0/Math.sqrt(3.0)), new NodeLokal(-1.0,-1.0/Math.sqrt(3.0)));
        powierzchnieLokalne[1] = new  PowierzchniaLokalna(new NodeLokal(-1.0/Math.sqrt(3.0),-1.0), new NodeLokal(1.0/Math.sqrt(3.0),-1.0));
        powierzchnieLokalne[2] = new  PowierzchniaLokalna(new NodeLokal(1.0,-1.0/Math.sqrt(3.0)), new NodeLokal(1.0,1.0/Math.sqrt(3.0)));
        powierzchnieLokalne[3] = new  PowierzchniaLokalna(new NodeLokal(1.0/Math.sqrt(3.0),1.0), new NodeLokal(-1.0/Math.sqrt(3.0),1.0));
        // obliczanie punktow calkowania na powierzchniach
        for ( int i = 0; i < 4; i++ ) {
            for (int j = 0; j < 2; j++) {
                powierzchnieLokalne[i].N[j][0] = (1./4.)*(1-powierzchnieLokalne[i].nodes[j].ksi)*(1-powierzchnieLokalne[i].nodes[j].eta);
                powierzchnieLokalne[i].N[j][1] = (1./4.)*(1+powierzchnieLokalne[i].nodes[j].ksi)*(1-powierzchnieLokalne[i].nodes[j].eta);
                powierzchnieLokalne[i].N[j][2] = (1./4.)*(1+powierzchnieLokalne[i].nodes[j].ksi)*(1+powierzchnieLokalne[i].nodes[j].eta);
                powierzchnieLokalne[i].N[j][3] = (1./4.)*(1-powierzchnieLokalne[i].nodes[j].ksi)*(1+powierzchnieLokalne[i].nodes[j].eta);
            }
        }
    }
}
