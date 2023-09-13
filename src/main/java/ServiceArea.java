import java.util.List;

public class ServiceArea {
    List<Knoten> areaKnoten;
    Knoten ursprung;
    int bedarfGesammt = 0;
    int bedarfBekannt = 0;
    int möglicherPuffer = 0;

    public ServiceArea(List<Knoten> areaKnoten, Knoten ursprung) {
        this.areaKnoten = areaKnoten;
        this.ursprung = ursprung;
        areaKnoten.add(ursprung);
    }


    public Knoten getUrsprung() {
        return ursprung;
    }
    public List<Knoten> getAreaKnoten() {
        return areaKnoten;
    }

    public void setAreaKnoten(List<Knoten> areaKnoten) {
        this.areaKnoten = areaKnoten;
    }

    public int getMöglicherPuffer() {
        return möglicherPuffer;
    }

    public void setMöglicherPuffer(int möglicherPuffer) {
        this.möglicherPuffer = möglicherPuffer;
    }

    public int getBedarfGesammt() {
        return bedarfGesammt;
    }

    public int getBedarfBekannt() {
        return bedarfBekannt;
    }

    public void calcKapa(List<Knoten> bekannt, List<Knoten> dynamic){

        for(Knoten k : areaKnoten){
            if(bekannt.contains(k)){
                bedarfBekannt+= k.getBedarf();
                bedarfGesammt+= k.getBedarf();
            }
            if(dynamic.contains(k)){
                bedarfGesammt+= k.getBedarf();
            }
        }
    }
    public void print(){
        for(Knoten k: areaKnoten){
            System.out.print(k.getID() + " - ");
        }
        System.out.println();
    }


}
