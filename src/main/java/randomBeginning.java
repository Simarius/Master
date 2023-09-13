import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class randomBeginning {
    private Knoten Depot;
    public randomBeginning(Knoten Depot) {
        this.Depot = Depot;
    }

    public List<Fahrzeug> beginn(List<Knoten> knotenList){
        Random rnd = new Random();
        ArrayList<Fahrzeug> fahrzeuge = new ArrayList<>();
        fahrzeuge.add(new Fahrzeug(Depot,0));
        for(Knoten knoten : knotenList){
            int fahrzeugNummer = rnd.nextInt(fahrzeuge.size()+1);
            if(fahrzeugNummer < fahrzeuge.size()){
                //füge Knoten diesem fahrzeug zu
                int knotenPosition = 1;
                if ( fahrzeuge.get(fahrzeugNummer).getKnoten().size() > 2){
                  knotenPosition = rnd.nextInt(fahrzeuge.get(fahrzeugNummer).getKnoten().size()-2)+1;
                }
                fahrzeuge.get(fahrzeugNummer).getKnoten().add(knotenPosition, knoten);
            }else{
                //füge Knoten neuem Fahrzeug zu
                fahrzeuge.add(new Fahrzeug(Depot,fahrzeuge.size()));
                fahrzeuge.get(fahrzeugNummer).getKnoten().add(1, knoten);
            }
        }
        for (Fahrzeug i : fahrzeuge){
            System.out.println("ID: " + i.getID());
            System.out.println("Kapa: " + i.getKapa());
            System.out.println("Knoten: ");
            for (Knoten k : i.getKnoten()) {
                System.out.print(k.getID() + " - ");
            }
            System.out.println();
        }
        return fahrzeuge;

    }
}
