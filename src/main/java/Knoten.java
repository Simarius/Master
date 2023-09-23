public class Knoten {
    private double X_Koordinate; //-100 bis 100
    private double Y_Koordinate; // -100 bis 100
    private int ID;// id
    private double Wahrscheinlichkeit; // 0 bis 100
    private int Bedarf; // 0 bis 100
    private int startZeit; // 1 bis 12
    private int endZeit; // 1 bis 12
    private int besuchsZeit = 0;
    private double Winkel;
    private Integer Puffer = 0;
    private ServiceArea ServiceArea;


    public Knoten(double x_Koordinate, double y_Koordinate, int ID, double wahrscheinlichkeit, int bedarf, int startZeit, int endZeit, int besuchsZeit, double winkel, Integer puffer, ServiceArea serviceArea) {
        X_Koordinate = x_Koordinate;
        Y_Koordinate = y_Koordinate;
        this.ID = ID;
        Wahrscheinlichkeit = wahrscheinlichkeit;
        Bedarf = bedarf;
        this.startZeit = startZeit;
        this.endZeit = endZeit;
        this.besuchsZeit = besuchsZeit;
        Winkel = winkel;
        Puffer = puffer;
        ServiceArea = serviceArea;
    }

    public Knoten(double x_Koordinate, double y_Koordinate, int ID, double wahrscheinlichkeit, int bedarf, int startZeit, int endZeit) {
        X_Koordinate = x_Koordinate;
        Y_Koordinate = y_Koordinate;
        this.ID = ID;
        Wahrscheinlichkeit = wahrscheinlichkeit;
        Bedarf = bedarf;
        this.startZeit = startZeit;
        this.endZeit = endZeit;
    }

    public ServiceArea getServiceArea() {
        return ServiceArea;
    }

    public void setServiceArea(ServiceArea serviceArea) {
        ServiceArea = serviceArea;
    }

    public Integer getPuffer() {
        return Puffer;
    }

    public void setPuffer(Integer puffer) {
        Puffer = puffer;
    }

    public double getWinkel() {
        return Winkel;
    }

    public void setWinkel(double winkel) {
        Winkel = winkel;
    }

    public int getBesuchsZeit() {
        return besuchsZeit;
    }

    public void setBesuchsZeit(int besuchsZeit) {
        this.besuchsZeit = besuchsZeit;
    }

    public double getX_Koordinate() {
        return X_Koordinate;
    }

    public double getY_Koordinate() {
        return Y_Koordinate;
    }

    public int getID() {
        return ID;
    }

    public double getWahrscheinlichkeit() {
        return Wahrscheinlichkeit;
    }

    public int getBedarf() {
        return Bedarf;
    }

    public int getStartZeit() {
        return startZeit;
    }

    public void setStartZeit(int startZeit) {
        this.startZeit = startZeit;
    }

    public int getEndZeit() {
        return endZeit;
    }
}
