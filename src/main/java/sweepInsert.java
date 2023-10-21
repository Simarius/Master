import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class sweepInsert {

    private List<Fahrzeug> Fahrzeuge;
    private Knoten Depot;
    private Row row;
    private File fiel;
    private Workbook workbook;
    private long startTime;
    private int fahrzeugKapa = 0;
    private int fahrzeugKapaPuffer = 0;

    private int AUFTRITSWAHRSCHEINLICHKEIT = 80;
    private String path;

    public sweepInsert(Knoten depot, int a, String path) {
        Fahrzeuge = new ArrayList<>();
        Depot = depot;
        AUFTRITSWAHRSCHEINLICHKEIT = a;
        this.path = path;
    }

    public void main(List<Knoten> knotenList) throws IOException {
        startTime = System.nanoTime();

        Fahrzeuge.add(new Fahrzeug(Depot, Fahrzeuge.size()));
        fahrzeugKapa = Fahrzeuge.get(0).getKapazitätsMaximum();
        fahrzeugKapaPuffer = fahrzeugKapa - 50;
        Fahrzeuge.get(Fahrzeuge.size()-1).setKapazitätsMaximum(fahrzeugKapaPuffer);

        fiel = new File(path);
        FileInputStream file = new FileInputStream(fiel);
        workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet("Insertion");
        row = sheet.createRow(sheet.getLastRowNum()+1);

            //Wähle Punkt
        for(Knoten neu : knotenList) {
            if (Fahrzeuge.get(Fahrzeuge.size()-1).isVerfügbar()) {
                Fahrzeug aktuell = Fahrzeuge.get(Fahrzeuge.size() - 1);
                sweep(neu, aktuell);
            } else {
                System.out.println("Kein Fahrzeug Verfügbar");
                Fahrzeuge.add(new Fahrzeug(Depot, Fahrzeuge.size()));
                Fahrzeuge.get(Fahrzeuge.size()-1).setKapazitätsMaximum(fahrzeugKapaPuffer);
                Fahrzeug aktuell = Fahrzeuge.get(Fahrzeuge.size() - 1);
                sweep(neu, aktuell);
            }
            for (Fahrzeug i : Fahrzeuge) {
                System.out.println("ID: " + i.getID());
                System.out.println("Kapa: " + i.getKapa());
                System.out.print("Knoten: ");
                for (Knoten k : i.getKnoten()) {
                    System.out.print(k.getID() + " - ");
                }
                System.out.println();
            }

        }
    }



    public void Insertion(List<Knoten> dynamicList) throws IOException {
        Random random = new Random();
        System.out.println("INSERTION");
        for(Fahrzeug f : Fahrzeuge){
            f.setKapazitätsMaximum(fahrzeugKapa);
        }
        List<Knoten> außerhalbDerZeit = new ArrayList<>();
        int nichtEingefügteKnoten = 0;
        for(int Stunde =0; Stunde < 12; Stunde++){

            //Einfügen, dass geprüft wird das ein einfügen an eine bereits vergangenen Position nicht möglich ist

            if(random.nextInt(100) < AUFTRITSWAHRSCHEINLICHKEIT && !dynamicList.isEmpty()){
                Knoten KnotenNeu = dynamicList.get(random.nextInt(dynamicList.size()));
                dynamicList.remove(KnotenNeu);
                System.out.println("Knoten " + KnotenNeu.getID() + " soll eingefügt werden");
                if(KnotenNeu.getEndZeit() >= Stunde + 1){
                    KnotenNeu.setStartZeit(utils.maximum(Stunde + 1, KnotenNeu.getStartZeit()));
                }else{
                    System.out.println("Die Anfrage bezieht sich auf ein bereits überschrittenes Zeitfenster");
                    System.out.println("Es ist: " + Stunde);
                    System.out.println("Anfrage geht von " + KnotenNeu.getStartZeit() + " bis " + KnotenNeu.getEndZeit());
                    nichtEingefügteKnoten++;
                    außerhalbDerZeit.add(KnotenNeu);
                    continue;
                }

                boolean einfügbar = true;
                boolean neuesFahrzeug = false;
                double[] längenDifferenz = new double[Fahrzeuge.size()];
                for(int i = 0; i < längenDifferenz.length; i++){
                    längenDifferenz[i] = Double.MAX_VALUE;
                }
                int[] bestePositon = new int[Fahrzeuge.size()];


                for(int fahrzeugNummer = 0; fahrzeugNummer < Fahrzeuge.size(); fahrzeugNummer++){
                    Fahrzeug aktuell = Fahrzeuge.get(fahrzeugNummer);
                    if(aktuell.getKapa() + KnotenNeu.getBedarf() <= aktuell.getKapazitätsMaximum()) {
                        double[] temp = new double[aktuell.getKnoten().size()];
                        Arrays.fill(temp, Double.MAX_VALUE);

                        insertion(KnotenNeu, aktuell, temp);
                        int minimum = utils.findMinIndex(temp);

                        if (temp[minimum] < Double.MAX_VALUE) {
                            double vorher = aktuell.längeBerechnen();
                            aktuell.getKnoten().add(minimum, KnotenNeu);
                            längenDifferenz [fahrzeugNummer] = aktuell.längeBerechnen() - vorher;
                            bestePositon[fahrzeugNummer] = minimum;
                            aktuell.getKnoten().remove(KnotenNeu);
                            einfügbar = true;
                        } else {
                            System.out.println("Zeitlich nicht okay");
                            //keine zeitlich akzeptierte Einordnung gefunden
                            einfügbar = false;
                        }
                    }else {
                        System.out.println("Kapazitär nicht okay (insert)");
                        System.out.println("Fahrzeugnummer: " + aktuell.getID());
                        System.out.println("Knotennummer: " + KnotenNeu.getID());
                        System.out.println(aktuell.getKapa() + "+" + KnotenNeu.getBedarf() + ">" + aktuell.getKapazitätsMaximum());
                        //keine zeitlich akzeptierte Einordnung gefunden
                        einfügbar = false;
                        neuesFahrzeug = true;
                    }
                }
                //neuesFahrzeug = false;
                if(neuesFahrzeug){
                    Fahrzeug a = (new Fahrzeug(Depot, Fahrzeuge.size()));
                    a.getKnoten().set(1,KnotenNeu);
                    a.setKapa(KnotenNeu.getBedarf());
                    Fahrzeuge.add(a);


                }
                if(neuesFahrzeug && einfügbar) {
                }else if(einfügbar) {
                    int besteEinfügung = utils.findMinIndex(längenDifferenz);
                    Fahrzeuge.get(besteEinfügung).getKnoten().add(bestePositon[besteEinfügung], KnotenNeu);
                    Fahrzeuge.get(besteEinfügung).setKapa(Fahrzeuge.get(besteEinfügung).getKapa() + KnotenNeu.getBedarf());
                }else{
                    System.out.println("Der Knoten " + KnotenNeu.getID() + " konnte nicht eingefügt werden");
                    nichtEingefügteKnoten++;
                }
            }
        }
        double gesammtLänge = 0d;
        for (Fahrzeug i : Fahrzeuge){
            System.out.println("ID: " + i.getID());
            System.out.println("Kapa: " + i.getKapa());
            System.out.println("Knoten: ");
            gesammtLänge += i.längeBerechnen();
            for (Knoten k : i.getKnoten()) {
                System.out.print(k.getID() + " - ");
                 }
            System.out.println();
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
        row.createCell(3).setCellValue(dynamicList.size());
        row.createCell(4).setCellValue((gesammtLänge + (nichtEingefügteKnoten - außerhalbDerZeit.size()) * 1000));
        row.createCell(5).setCellValue(totalTime);

        for(int i = 0; i < Fahrzeuge.size(); i++){
            row.createCell(i + 6).setCellValue(Fahrzeuge.get(i).toString());
        }
        FileOutputStream outputStream = new FileOutputStream(fiel);
        workbook.write(outputStream);
        workbook.close();


    }

    public void sweep(Knoten neu, Fahrzeug aktuell) {
        if(aktuell.getKapa() + neu.getBedarf() <= aktuell.getKapazitätsMaximum()){
            //add Knoten an beste Stelle
            double[] temp = new double[aktuell.getKnoten().size()];
            Arrays.fill(temp, Double.MAX_VALUE);
            insertion(neu, aktuell, temp);
            int minimum = utils.findMinIndex(temp);
            if(temp[minimum] < Double.MAX_VALUE) {
                aktuell.getKnoten().add(minimum, neu);
                aktuell.setKapa(aktuell.getKapa() + neu.getBedarf());
            }else{
                System.out.println("Zeitlich nicht okay");
                //keine zeitlich akzeptierte Einordnung gefunden
                Fahrzeuge.add(new Fahrzeug(Depot, Fahrzeuge.size()));
                Fahrzeuge.get(Fahrzeuge.size()-1).setKapazitätsMaximum(fahrzeugKapaPuffer);
                aktuell = Fahrzeuge.get(Fahrzeuge.size()-1);
                sweep(neu, aktuell);
            }
        }else{
            System.out.println("Kapazitär nicht okay");
            //Keine Kapazitäre Einordnung
            Fahrzeuge.add(new Fahrzeug(Depot, Fahrzeuge.size()));
            Fahrzeuge.get(Fahrzeuge.size()-1).setKapazitätsMaximum(fahrzeugKapaPuffer);
            aktuell = Fahrzeuge.get(Fahrzeuge.size()-1);
            sweep(neu, aktuell);
        }
    }

    private void insertion(Knoten neu, Fahrzeug aktuell, double[] temp) {
        for(int postion = 1; postion < aktuell.getKnoten().size(); postion++){
            aktuell.getKnoten().add(postion, neu);
            boolean legalität = true;
            //Check ob Zeitlich i O ist
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
            double länge = 0d;
            if(legalität) {
                //länge der Strecke berechnen
                 länge = aktuell.längeBerechnen();
            }else{
                 länge = Double.MAX_VALUE;
            }
            temp[postion] = länge;
            aktuell.getKnoten().remove(postion);
        }
    }
}
