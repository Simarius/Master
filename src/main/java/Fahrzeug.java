import java.util.ArrayList;
import java.util.List;

public class Fahrzeug{
    private int ID;
    private List<Knoten> Knoten;
    private int Kapa;
    private int KapazitätsMaximum = 250;
    private boolean verfügbar;
    private List<Knoten> Puffer = new ArrayList<>();
    private List<Knoten> PufferSave = new ArrayList<>();
    private List<ServiceArea> ServiceAreas = new ArrayList<>();

    public Fahrzeug(Knoten Depot, Knoten Depot2, int id) {
        Knoten = new ArrayList<>();
        Knoten.add(Depot);
        Knoten.add(Depot2);
        ID = id;
        verfügbar = true;
    }
    public Fahrzeug(Knoten Depot, int id) {
        Knoten = new ArrayList<>();
        Knoten.add(Depot);
        Knoten.add(Depot);
        ID = id;
        verfügbar = true;
    }

    public List<Knoten> getPufferSave() {
        return PufferSave;
    }

    public List<ServiceArea> getServiceAreas() {
        return ServiceAreas;
    }

    public List<Knoten> getPuffer() {
        return Puffer;
    }

    public void setKapa(int kapa) {
        Kapa = kapa;
    }

    public int getID() {
        return ID;
    }

    public List<Knoten> getKnoten() {
        return Knoten;
    }

    public int getKapa() {
        return Kapa;
    }

    public boolean isVerfügbar() {
        return verfügbar;
    }

    public int getKapazitätsMaximum() {
        return KapazitätsMaximum;
    }

    public double längeBerechnen(){
        double länge = 0d;
        for(int i = 0; i < Knoten.size()-1; i++){
            double deltaX = Knoten.get(i+1).getX_Koordinate() - Knoten.get(i).getX_Koordinate();
            double deltaY = Knoten.get(i+1).getY_Koordinate() - Knoten.get(i).getY_Koordinate();

            länge += Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        }
        return länge;

    }
    public String toString(){
        String ret = "";
        for(Knoten k : Knoten){
           ret += k.getID() + " - ";
        }
        ret = ret.substring(0, ret.length() -3);
        return ret;
    }
    public void printKnoten(){
        System.out.println("Fahrzeug Id: " + ID);
        for(Knoten k : Knoten){
            System.out.print(k.getID() + " - ");
        }
        System.out.println();
    }
}
