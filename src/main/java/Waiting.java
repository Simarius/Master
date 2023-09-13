import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Waiting {
    private List<Fahrzeug> Fahrzeuge = new ArrayList<>();
    private Knoten Depot;
    List<Knoten> aktiveKnoten;
    private Knoten Depot2;

    public Waiting(Knoten depot) {
        Depot = depot;
    }

    public void waiting(List<Knoten> knotenList, List<Knoten> dynamicList) throws IOException {
        long startTime = System.nanoTime();

        File fiel = new File("D:\\Users\\Jonas\\Desktop\\Uni\\Master\\Auswertung\\auswertung.xlsx");
        FileInputStream file = new FileInputStream(fiel);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet("Waiting");
        Row row = sheet.createRow(sheet.getLastRowNum()+1);

        Random random = new Random();
        List<ServiceArea> Areas = findServiceArea.find(knotenList, dynamicList);
        aktiveKnoten = new ArrayList<>(knotenList);
        int nichtEingefügteKnoten = 0;
        List<Knoten> außerhalbDerZeit = new ArrayList<>();
        System.out.println("WAITING");
        Depot2 = new Knoten(0d,0d,100, 0,0,0,12);
        Fahrzeuge.add(new Fahrzeug(Depot,Depot2,  Fahrzeuge.size()));
        Fahrzeug aktuellesFahrzeug = Fahrzeuge.get(Fahrzeuge.size()-1);
        aktuellesFahrzeug.getPuffer().add(Depot);
        aktuellesFahrzeug.getPufferSave().add(Depot);
        for (ServiceArea Area : Areas) {
            //Versuche alle bestehenden Knoten aus dem Area hinzuzufügen
            Area.getAreaKnoten().sort(new KnotenStartzeitComparator());
            Area.calcKapa(knotenList,dynamicList);
            if (aktuellesFahrzeug.getKapa() + Area.getBedarfBekannt() >= aktuellesFahrzeug.getKapazitätsMaximum()) {
                Fahrzeuge.add(new Fahrzeug(Depot,Depot2, Fahrzeuge.size()));
                aktuellesFahrzeug = Fahrzeuge.get(Fahrzeuge.size()-1);
                aktuellesFahrzeug.getPuffer().add(Depot);
                aktuellesFahrzeug.getPufferSave().add(Depot);
            }
            fügeAreaDemFahrzeugHinzu(knotenList, Area);
            aktuellesFahrzeug.getPuffer().add(aktuellesFahrzeug.getKnoten().get(aktuellesFahrzeug.getKnoten().size()-2));
            aktuellesFahrzeug.getPufferSave().add(aktuellesFahrzeug.getKnoten().get(aktuellesFahrzeug.getKnoten().size()-2));
        }
        //Zeit test
        for(Fahrzeug aktuell: Fahrzeuge){
            Besuchseitzuweisung(aktuell);
            boolean ret = true;
            do{
                ret = Puffererhöung(aktuell, 0);
            }
            while(ret);
            Besuchseitzuweisung(aktuell);
        }

        for(Fahrzeug f : Fahrzeuge){
            System.out.println("Fahrzeug Nr: " + f.getID());
            System.out.print("Puffer ID: ");
            for(Knoten pu : f.getPufferSave()){
                System.out.print(pu.getID() + " - ");
            }
            System.out.println();
            System.out.print("Knoten: ");
            for(Knoten ka : f.getKnoten()){
                System.out.print(ka.getID() + " - ");
            }
            System.out.println();
            System.out.print("Puffer: ");
            for(Knoten ka : f.getKnoten()){
                System.out.print(ka.getPuffer() + " - ");
            }
            System.out.println();
        }
        System.out.println();
        for(Knoten k : dynamicList){
            System.out.print(k.getID() + " - ");
        }
        for(int zeit = 0; zeit < 12; zeit++) {
            for (Knoten k : dynamicList) {
                if (random.nextInt(100) < k.getWahrscheinlichkeit() && random.nextInt(100) < 25) {
                    if(k.getEndZeit() >= zeit + 1){
                        k.setStartZeit(utils.maximum(zeit + 1, k.getStartZeit()));
                    }else{
                        System.out.println("Die Anfrage bezieht sich auf ein bereits überschrittenes Zeitfenster");
                        System.out.println("Es ist: " + zeit);
                        System.out.println("Anfrage geht von " + k.getStartZeit() + " bis " + k.getEndZeit());
                        außerhalbDerZeit.add(k);
                        nichtEingefügteKnoten++;
                        continue;
                    }
                    aktiveKnoten.add(k);
                    System.out.println("Um " + zeit + " wurden neue Knoten eingefügt");

                }
            }
            dynamicList.removeAll(außerhalbDerZeit);
            dynamicList.removeAll(aktiveKnoten);
            for (ServiceArea area : Areas) {
                double[] puffer = new double[area.getAreaKnoten().size()];
                for (int i = 0; i < puffer.length; i++) {
                    try {
                        puffer[i] = area.getAreaKnoten().get(i).getPuffer();
                    } catch (NullPointerException e) {
                        puffer[i] = 100;
                    }
                }
                area.setMöglicherPuffer((int) utils.findMinIndex(puffer));
            }
            List<Knoten> eingefügteAktiveKnoten = new ArrayList<>();
            for (Knoten aktiv : aktiveKnoten) { //für jeden neuen Knoten
                for (ServiceArea area : Areas) { //für jedes Service Area
                    if (area.getAreaKnoten().contains(aktiv)) { // der aktive Knoten ist teil der Area
                        if (area.getMöglicherPuffer() > 0) { // Wir haben genug Puffer im area um einen Knoten einzufügen
                            for (Fahrzeug f : Fahrzeuge) { //für jedes fahrzeug
                                if (f.getServiceAreas().contains(area)) {
                                    //versuche aktiv einzufügen.
                                    for (Knoten i : area.getAreaKnoten()) {// für jeden Knoten der Service area
                                        if (f.getKnoten().contains(i)) {
                                            int offset = (area.getAreaKnoten().indexOf(aktiv) - area.getAreaKnoten().indexOf(i));
                                            if(offset < 0){
                                                offset++;
                                            }
                                            int newIndex = f.getKnoten().indexOf(i) + offset;
                                            newIndex = utils.minimum(newIndex, f.getKnoten().size()-1);
                                            // Check das nur in der Zukunft eingefügt wird

                                            if(f.getKnoten().get(newIndex).getBesuchsZeit() < zeit){
                                                System.out.println("Der Knoten " + aktiv.getID() + " kann nicht eingefügt werden ");
                                            }else{
                                                if(f.getKapa() + aktiv.getBedarf() < f.getKapazitätsMaximum()) {
                                                    aktiv.setBesuchsZeit(aktiv.getStartZeit());
                                                    aktiv.setPuffer(0);
                                                    f.getKnoten().add(newIndex, aktiv);
                                                    f.setKapa(f.getKapa() + aktiv.getBedarf());
                                                    area.setMöglicherPuffer(area.getMöglicherPuffer()-1);
                                                    eingefügteAktiveKnoten.add(aktiv);
                                                } else{
                                                    System.out.println("kapazitäts Restriktion misachtet");
                                                    System.out.println(f.getKapa() + "  " + aktiv.getBedarf());
                                                }

                                            }
                                            break;
                                        }
                                    }

                                }
                            }
                        }else {
                            System.out.println("Kein Puffer in  " + area.getUrsprung().getID() + " " + area.getMöglicherPuffer() + " für " + aktiv.getID());
                        }
                    }
                }
            }
            aktiveKnoten.removeAll(eingefügteAktiveKnoten);
            if(!aktiveKnoten.isEmpty()){
                for(Knoten k : aktiveKnoten) {
                    double[][] posDis = new double[Fahrzeuge.size()][3];

                    boolean vehicleFound = false;
                    for (int f = 0; f < Fahrzeuge.size(); f++) {
                        if (Fahrzeuge.get(f).getKapazitätsMaximum() < Fahrzeuge.get(f).getKapa() + k.getBedarf()) {
                            //prüfe insertion von Knoten
                            double[] distanzen = new double[Fahrzeuge.get(f).getKnoten().size()];
                            double[] davor = new double[Fahrzeuge.get(f).getKnoten().size()];
                            distanzen[0] = Double.MAX_VALUE;
                            for (int i = 1; i < Fahrzeuge.get(f).getKnoten().size(); i++) {
                                if(k.getStartZeit() <= Fahrzeuge.get(f).getKnoten().get(i).getBesuchsZeit() && Fahrzeuge.get(f).getKnoten().get(i).getBesuchsZeit() < k.getEndZeit() && Fahrzeuge.get(f).getKnoten().get(i).getPuffer() > 0) {
                                    k.setBesuchsZeit(Fahrzeuge.get(f).getKnoten().get(i).getBesuchsZeit());
                                    davor[i] = Fahrzeuge.get(f).längeBerechnen();
                                    Fahrzeuge.get(f).getKnoten().add(i, k);
                                    distanzen[i] = Fahrzeuge.get(f).längeBerechnen() - davor[i];
                                    Fahrzeuge.get(f).getKnoten().remove(k);
                                    vehicleFound = true;

                                }else{
                                    distanzen[i] = Double.MAX_VALUE;
                                }

                            }
                            posDis[f][0] = utils.findMinIndex(distanzen);
                            posDis[f][1] = distanzen[(int) posDis[f][0]];
                            posDis[f][2] = davor[(int) posDis[f][0]];
                        }else{
                            posDis[f][0] = Double.MAX_VALUE;
                            posDis[f][1] = Double.MAX_VALUE;
                            posDis[f][2] = Double.MAX_VALUE;
                        }
                    }
                    if(vehicleFound) {
                        int dIndex = 0;
                        int minIndex = 0;
                        double min = Double.MAX_VALUE;
                        double d = 0;
                        for (int i = 0; i < posDis.length; i++) {
                            if (posDis[i][1] < min) {
                                min = posDis[i][1];
                                minIndex = (int) posDis[i][0];
                                dIndex = i;
                                d = posDis[i][2];
                            }
                        }
                        Fahrzeug g = new Fahrzeug(Depot,Depot2, Fahrzeuge.size());
                        g.getKnoten().add(1,k);
                        if(min - d < g.längeBerechnen() && Fahrzeuge.get(dIndex).getKapa() + k.getBedarf() <= Fahrzeuge.get(dIndex).getKapazitätsMaximum()){
                            k.setBesuchsZeit(Fahrzeuge.get(dIndex).getKnoten().get(minIndex).getBesuchsZeit());
                            k.setPuffer(0);
                            Fahrzeuge.get(dIndex).getKnoten().add(minIndex,k);
                            Fahrzeuge.get(dIndex).setKapa(Fahrzeuge.get(dIndex).getKapa() + k.getBedarf());
                            eingefügteAktiveKnoten.add(k);
                        } else{
                            g.getPuffer().add(k);
                            g.setKapa(k.getBedarf());
                            Fahrzeuge.add(g);
                            eingefügteAktiveKnoten.add(k);
                        }

                    }
                    for(Fahrzeug aktuell: Fahrzeuge){
                        Besuchseitzuweisung(aktuell);
                    }
                }
            }
            aktiveKnoten.removeAll(eingefügteAktiveKnoten);
            //prüfe nächsten Knoten
            for(Fahrzeug f: Fahrzeuge){
                //finde position
                Knoten position = null;
                for(Knoten k : f.getKnoten()){
                    if(k.getBesuchsZeit() >= zeit){
                        position = k;
                        break;
                    }else if(k.equals(f.getKnoten().get(f.getKnoten().size()-1))){
                        position = f.getKnoten().get(f.getKnoten().size()-1);
                    }
                }
                if(position.equals(Depot)|| position.equals(Depot2)){
                    continue;
                }
                if(position.getServiceArea() != f.getKnoten().get(f.getKnoten().indexOf(position) + 1).getServiceArea()){
                    //wenn außerhalb des areas ggf warten
                    if (position.getPuffer() > 0) { // wenn Puffer da ist
                        for(int i = f.getKnoten().indexOf(position); i < f.getKnoten().size(); i++){
                            //ggf. start erhöhen
                            if(f.getKnoten().get(i).getBesuchsZeit() == zeit + 1 || f.getKnoten().get(i).getBesuchsZeit() == f.getKnoten().get(i-1).getBesuchsZeit()){
                                f.getKnoten().get(i).setBesuchsZeit(f.getKnoten().get(i).getBesuchsZeit() + 1);
                            }
                            f.getKnoten().get(i).setPuffer(f.getKnoten().get(i).getPuffer() - 1);


                        }
                        //ggf puffer senken
                        for (ServiceArea area : Areas) {
                            double[] puffer = new double[area.getAreaKnoten().size()];
                            for (int i = 0; i < puffer.length; i++) {
                                try {
                                    puffer[i] = area.getAreaKnoten().get(i).getPuffer();
                                } catch (NullPointerException e) {
                                    puffer[i] = 100;
                                }
                            }
                            area.setMöglicherPuffer((int) utils.findMinIndex(puffer));
                        }
                    }


                }else{
                    //wenn innerhalb keine Änderung
                }

            }
        }
        nichtEingefügteKnoten = nichtEingefügteKnoten + aktiveKnoten.size();
        System.out.println();
        double gesammtLänge = 0d;
        for(Fahrzeug f : Fahrzeuge){
            System.out.println("Fahrzeug Nr: " + f.getID());
            System.out.print("Puffer ID: ");
            for(Knoten pu : f.getPufferSave()){
                System.out.print(pu.getID() + " - ");
            }
            System.out.println();
            System.out.print("Knoten: ");
            for(Knoten ka : f.getKnoten()){
                System.out.print(ka.getID() + " - ");
            }
            System.out.println();
            System.out.print("Puffer: ");
            for(Knoten ka : f.getKnoten()){
                System.out.print(ka.getPuffer() + " - ");
            }
            System.out.println( "Kapa = " + f.getKapa());
            System.out.println();
            gesammtLänge += f.längeBerechnen();
        }
        System.out.println("Nicht erschienene Knoten");
        for(Knoten k : dynamicList){
            System.out.print(k.getID() + " - ");
        }
        System.out.println();
        System.out.println("aktive Knoten:");
        for(Knoten k : aktiveKnoten){
            System.out.print(k.getID() + " - ");
        }
        System.out.println();
        System.out.println("Außerhalb der Zeit");
        for(Knoten k : außerhalbDerZeit){
            System.out.print(k.getID() + " - ");
        }
        System.out.println("Gesammtlänge der Lösung: " + gesammtLänge);
        System.out.println("Anzahl nicht eingefügter Knoten: " + nichtEingefügteKnoten);
        System.out.println("Anzahl Knoten die Außerhalb der Zeit aufgetaucht sind: " + außerhalbDerZeit.size());
        System.out.println("Funktionswert der Lösung: " + (gesammtLänge + (nichtEingefügteKnoten - außerhalbDerZeit.size()) * 1000));

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;


        row.createCell(0).setCellValue(gesammtLänge);
        row.createCell(1).setCellValue(nichtEingefügteKnoten);
        row.createCell(2).setCellValue( außerhalbDerZeit.size());
        row.createCell(3).setCellValue((gesammtLänge + (nichtEingefügteKnoten - außerhalbDerZeit.size()) * 1000));
        row.createCell(4).setCellValue(totalTime);

        for(int i = 0; i < Fahrzeuge.size(); i++){
            row.createCell(i + 5).setCellValue(Fahrzeuge.get(i).toString());
        }
        FileOutputStream outputStream = new FileOutputStream(fiel);
        workbook.write(outputStream);
        workbook.close();



        // Neue Route wenn aufgrund von Puffer oder Kapa voll
        //Funktionswert bestimmen



    }
    private boolean Puffererhöung(Fahrzeug aktuell, int pufferIndex){
        boolean ret = true;
        Knoten indexKnoten = aktuell.getPuffer().get(pufferIndex);
        int index = aktuell.getKnoten().indexOf(indexKnoten);
        for (int i = index + 1; i < aktuell.getKnoten().size(); i++) {
            aktuell.getKnoten().get(i).setBesuchsZeit(aktuell.getKnoten().get(i).getBesuchsZeit() + 1);
            try {
                aktuell.getKnoten().get(i).setPuffer(aktuell.getKnoten().get(i).getPuffer() + 1);
            }catch (NullPointerException e){
                aktuell.getKnoten().get(i).setPuffer(1);
            }
        }
        if (!Zeittest(aktuell)) {
            aktuell.getPuffer().remove(pufferIndex);
            for (int i = index + 1; i < aktuell.getKnoten().size(); i++) {
                aktuell.getKnoten().get(i).setBesuchsZeit(aktuell.getKnoten().get(i).getBesuchsZeit() - 1);
                aktuell.getKnoten().get(i).setPuffer(aktuell.getKnoten().get(i).getPuffer() - 1);
            }
            if(aktuell.getPuffer().isEmpty()){
                ret = false;
            }
        }
        return ret;
    }

    private boolean Zeittest(Fahrzeug aktuell) {
        boolean legalität = true;
        for(Knoten k : aktuell.getKnoten()){
            if(k.getBesuchsZeit() > k.getEndZeit()){
                legalität = false;
            }
        }
        return legalität;
    }

    private void Besuchseitzuweisung(Fahrzeug aktuell) {
        boolean legalität = true;
        int checker = 0;
        for(int a = 1; a < aktuell.getKnoten().size(); a++){
            checker = utils.maximum(aktuell.getKnoten().get(a).getStartZeit(),checker+1);
            aktuell.getKnoten().get(a).setBesuchsZeit(checker);
            if(checker > aktuell.getKnoten().get(a).getEndZeit()) {
                legalität = false;
                //break
                a = aktuell.getKnoten().size();
            }
        }
        if(!legalität)  {
            System.out.println("ZEIT FEHLER");
        }
    }

    private void fügeAreaDemFahrzeugHinzu(List<Knoten> knotenList, ServiceArea Area) {
        for (Knoten k : Area.getAreaKnoten()) {
            if (knotenList.contains(k)) {
                Fahrzeuge.get(Fahrzeuge.size()-1).getKnoten().add(Fahrzeuge.get(Fahrzeuge.size()-1).getKnoten().size() - 1, k);
                aktiveKnoten.remove(k);
                Fahrzeuge.get(Fahrzeuge.size()-1).getServiceAreas().add(Area);
            }
        }
        Fahrzeuge.get(Fahrzeuge.size()-1).setKapa(Fahrzeuge.get(Fahrzeuge.size()-1).getKapa() + Area.getBedarfBekannt());
    }
}