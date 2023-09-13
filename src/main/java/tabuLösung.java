import java.util.List;

public class tabuLösung {

    private int veränderung;
    private int fahrzeug1;
    private int index1;
    private int fahrzeug2;
    private int index2;
    private int knoten;
    private double funktionswert;
    private boolean legalität;
    private List<Fahrzeug> fahrzeuge;
    private List<Knoten> einzufügendeKnoten;


    public String toString(){
        return String.format("%d-%d.%d-%d.%d-%d--%f-",veränderung,fahrzeug1,index1,fahrzeug2,index2,knoten,funktionswert) + legalität;
    }



    public void setVeränderung(int veränderung) {
        this.veränderung = veränderung;
    }


    public void setFahrzeug1(int fahrzeug1) {
        this.fahrzeug1 = fahrzeug1;
    }


    public void setIndex1(int index1) {
        this.index1 = index1;
    }


    public void setFahrzeug2(int fahrzeug2) {
        this.fahrzeug2 = fahrzeug2;
    }


    public void setIndex2(int index2) {
        this.index2 = index2;
    }


    public void setKnoten(int knoten) {
        this.knoten = knoten;
    }

    public double getFunktionswert() {
        return funktionswert;
    }

    public void setFunktionswert(double funktionswert) {
        this.funktionswert = funktionswert;
    }

    public boolean isLegalität() {
        return legalität;
    }

    public void setLegalität(boolean legalität) {
        this.legalität = legalität;
    }

    public List<Fahrzeug> getFahrzeuge() {
        return fahrzeuge;
    }

    public void setFahrzeuge(List<Fahrzeug> fahrzeuge) {
        this.fahrzeuge = fahrzeuge;
    }

    public List<Knoten> getEinzufügendeKnoten() {
        return einzufügendeKnoten;
    }

    public void setEinzufügendeKnoten(List<Knoten> einzufügendeKnoten) {
        this.einzufügendeKnoten = einzufügendeKnoten;
    }
}
