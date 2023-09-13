import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class tabuSearch {
    int ITERATION = 100;
    int NACHBARSCHAFTSGRÖßE = 100;
    int MAXILLEGALELÖSUNGENINFOLGE = 10;
    int illegalleLösungenInfFolge = 0;
    int TABULISTSIZE = 10;
    List<Fahrzeug> fahrzeuge;
    List<Knoten> dynamicList;
    List<Knoten> einzufügendeKonten = new ArrayList<>();
    List<tabuLösung> Lösungen = new ArrayList<>();
    List<tabuLösung> Tabu = new ArrayList<>();
    List<Knoten> AußerhalDerZeit = new ArrayList<>();

    public tabuSearch(List<Fahrzeug> fahrzeuge, List<Knoten> dynamicList) {
        this.fahrzeuge = fahrzeuge;
        this.dynamicList = dynamicList;
    }

    public List<Fahrzeug> tabuSearching() throws IOException {
        long startTime = System.nanoTime();

        File fiel = new File("D:\\Users\\Jonas\\Desktop\\Uni\\Master\\Auswertung\\auswertung.xlsx");
        FileInputStream file = new FileInputStream(fiel);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet("Tabu");
        System.out.println(sheet.getLastRowNum());
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        Random rnd = new Random();
        //tausch intern
        //tausch extern
        //insert
        System.out.println("Start");
        tabuLösung besteLösung = null;
        tabuLösung aktuelleLösung = null;
        for(int Stunde =0; Stunde < 12; Stunde++) {

            if (rnd.nextInt(100) < 60 && !dynamicList.isEmpty()) {
                Knoten KnotenNeu = dynamicList.get(rnd.nextInt(dynamicList.size()));
                dynamicList.remove(KnotenNeu);
                einzufügendeKonten.add(KnotenNeu);
                System.out.println("Knoten " + KnotenNeu.getID() + " wurde hinzugefügt");
                if (KnotenNeu.getEndZeit() >= Stunde + 1) {
                    for(Knoten k: einzufügendeKonten) {
                        k.setStartZeit(utils.maximum(Stunde + 1, k.getStartZeit()));
                    }
                } else {
                    System.out.println("Die Anfrage bezieht sich auf ein bereits überschrittenes Zeitfenster");
                    System.out.println("Es ist: " + Stunde);
                    System.out.println("Anfrage geht von " + KnotenNeu.getStartZeit() + " bis " + KnotenNeu.getEndZeit());
                    AußerhalDerZeit.add(KnotenNeu);

                }
            }

            List<Knoten> saveKnoten;
            List<Fahrzeug> saveFahrzeuge;
            for(int i = 0; i < ITERATION; i++) {
                //aktuelle Lösung
                if(aktuelleLösung == null) {
                    saveKnoten = einzufügendeKonten;
                    saveFahrzeuge = fahrzeuge;
                }else{
                    saveKnoten = aktuelleLösung.getEinzufügendeKnoten();
                    saveFahrzeuge = aktuelleLösung.getFahrzeuge();
                }
                for(int a = 0; a < NACHBARSCHAFTSGRÖßE; a++) {
                    fahrzeuge = saveFahrzeuge;
                    einzufügendeKonten = saveKnoten;
                    Lösungen.add(new tabuLösung());
                    int cases;
                    if (!einzufügendeKonten.isEmpty()) {
                        cases = rnd.nextInt(3);
                    }else{
                        cases = rnd.nextInt(2);
                    }
                    switch (cases) {
                        case 0:
                            internalSwap();
                            break;

                        case 1:
                            externalSwap();
                            break;

                        case 2:
                            int Knoten = rnd.nextInt(einzufügendeKonten.size());
                            insert(einzufügendeKonten.get(Knoten));
                            einzufügendeKonten.remove(Knoten);
                            break;
                    }
                    //berechne funktionswert
                    //prüfe auf legalität
                    Lösungen.get(Lösungen.size() - 1).setFahrzeuge(fahrzeuge);
                    Lösungen.get(Lösungen.size() - 1).setEinzufügendeKnoten(einzufügendeKonten);
                    double gesammtLänge = 0d;
                    List<Boolean> legalitäten = new ArrayList<Boolean>();
                    for (Fahrzeug f : fahrzeuge) {
                        legalitäten.add(prüfeAufLegalität(f));
                        gesammtLänge += f.längeBerechnen();
                    }
                    double funktionswert = gesammtLänge + ((einzufügendeKonten.size() - AußerhalDerZeit.size()) * 1000);
                    Lösungen.get(Lösungen.size() - 1).setFunktionswert(funktionswert);
                    if (legalitäten.contains(false)) {
                        Lösungen.get(Lösungen.size() - 1).setLegalität(false);
                    } else {
                        Lösungen.get(Lösungen.size() - 1).setLegalität(true);
                    }
                }
                //wähle Lösung
                List<tabuLösung> tabuwiederholungen = new ArrayList<>();
                for(tabuLösung Lösung: Lösungen) {
                    //check for Tabuschritt
                    for (tabuLösung tabus : Tabu) {
                        if (Lösung.toString().equals(tabus.toString())) {
                            tabuwiederholungen.add(Lösung);
                        }
                    }
                }
                Lösungen.removeAll(tabuwiederholungen);

                //sort nach funktionswert
                Collections.sort(Lösungen, new LösungComparator());

                if(Lösungen.get(0).isLegalität()){
                    aktuelleLösung = Lösungen.get(0);
                    illegalleLösungenInfFolge = 0;
                    if(besteLösung != null){
                        if(aktuelleLösung.getFunktionswert() < besteLösung.getFunktionswert()){
                            besteLösung = aktuelleLösung;
                        }
                    }else{
                        besteLösung = aktuelleLösung;
                    }
                }else{
                    boolean legaleLösungVorhanden = false;
                    tabuLösung legaleLösung = null;
                    for(tabuLösung l : Lösungen){
                        if(l.isLegalität()){
                            legaleLösungVorhanden = true;
                            legaleLösung = l;
                        }
                    }
                    //checke nach legalitätswiederholung
                    if(illegalleLösungenInfFolge < MAXILLEGALELÖSUNGENINFOLGE){
                        aktuelleLösung = Lösungen.get(0);
                        if(Lösungen.get(0).isLegalität()){
                            illegalleLösungenInfFolge = 0;
                        }else{
                            illegalleLösungenInfFolge++;
                        }
                    }else if (legaleLösungVorhanden){
                        aktuelleLösung = legaleLösung;
                        illegalleLösungenInfFolge = 0;
                    }else{
                        aktuelleLösung = Lösungen.get(0);
                        illegalleLösungenInfFolge++;
                    }

                }

                //update tabuliste
                Tabu.add(0, aktuelleLösung);
                if(Tabu.size() > TABULISTSIZE){
                    Tabu.remove(TABULISTSIZE);
                }

                Lösungen = new ArrayList<>();
            }



        }
        System.out.println("Tabu Lösung: ");
        if(besteLösung == null){
            System.out.println("Keine Legale Lösung gefunden");
            row.createCell(0).setCellValue("KEINE LÖSUNG GEFUNDEN");
        }else {
            double Gesammtlänge = 0;
            for (Fahrzeug f : besteLösung.getFahrzeuge()) {
                f.printKnoten();
                Gesammtlänge += f.längeBerechnen();
            }

            System.out.println("Gesammtlänge der Lösung: " + Gesammtlänge);
            System.out.println("Anzahl nicht eingefügter Knoten: " + einzufügendeKonten.size());
            System.out.println("Anzahl Knoten die Außerhalb der Zeit aufgetaucht sind: " + AußerhalDerZeit.size());
            System.out.println("Funktionswert der Lösung: " + (Gesammtlänge + (einzufügendeKonten.size() - AußerhalDerZeit.size()) * 1000));
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;

            row.createCell(0).setCellValue(Gesammtlänge);
            row.createCell(1).setCellValue(einzufügendeKonten.size());
            row.createCell(2).setCellValue( AußerhalDerZeit.size());
            row.createCell(3).setCellValue((Gesammtlänge + (einzufügendeKonten.size() - AußerhalDerZeit.size()) * 1000));
            row.createCell(4).setCellValue(totalTime);

            for(int i = 0; i < fahrzeuge.size(); i++){
                row.createCell(i + 5).setCellValue(fahrzeuge.get(i).toString());
            }

        }
        FileOutputStream outputStream = new FileOutputStream(fiel);
        workbook.write(outputStream);
        workbook.close();
    return fahrzeuge;
    }

    private boolean prüfeAufLegalität(Fahrzeug f) {
        boolean legal = true;
        //Kapa
        int kapa = 0;
        for(int i = 0; i < f.getKnoten().size();i++){
            kapa += f.getKnoten().get(i).getBedarf();
        }
        if(kapa > f.getKapazitätsMaximum()){
            legal = false;
            //System.out.println("Kapazitär nicht okay");
        }
        //Zeit
        int start =  f.getKnoten().get(1).getStartZeit();
        for(int i = 2; i < f.getKnoten().size()-1;i++){
            if(start + 1 <= f.getKnoten().get(i).getEndZeit()){
                start = utils.maximum(start + 1, f.getKnoten().get(i).getStartZeit());
            }else if(start + 1 >   f.getKnoten().get(i).getEndZeit()){
                legal = false;
            }else{
                System.out.println("Unbetrachteter Fall");
            }
        }

        return legal;
    }

    private void internalSwap(){
        Lösungen.get(Lösungen.size() - 1).setVeränderung(1);
        Random rnd = new Random();
        Fahrzeug fahrzeug = fahrzeuge.get(rnd.nextInt(fahrzeuge.size()));
        Lösungen.get(Lösungen.size() - 1).setFahrzeug1(fahrzeug.getID());
        Lösungen.get(Lösungen.size() - 1).setFahrzeug2(fahrzeug.getID());
        if(fahrzeug.getKnoten().size() > 3){
            int eins = rnd.nextInt(fahrzeug.getKnoten().size()-2)+1;
            Lösungen.get(Lösungen.size() - 1).setIndex1(eins);

            int zwei;
            do{
                zwei = rnd.nextInt(fahrzeug.getKnoten().size()-2)+1;
            } while( eins == zwei);

            Lösungen.get(Lösungen.size() - 1).setIndex2(zwei);

            Knoten erster = fahrzeug.getKnoten().get(eins);
            Knoten zweiter = fahrzeug.getKnoten().get(zwei);
            fahrzeug.getKnoten().remove(eins);
            fahrzeug.getKnoten().add(eins,zweiter);
            fahrzeug.getKnoten().remove(zwei);
            fahrzeug.getKnoten().add(zwei, erster);
        }

    }

    private void externalSwap(){
        Random rnd = new Random();
        Lösungen.get(Lösungen.size() - 1).setVeränderung(2);
        Fahrzeug fahrzeug1 = fahrzeuge.get(rnd.nextInt(fahrzeuge.size()));
        Lösungen.get(Lösungen.size() - 1).setFahrzeug1(fahrzeug1.getID());
        Fahrzeug fahrzeug2;
        do {
            fahrzeug2 = fahrzeuge.get(rnd.nextInt(fahrzeuge.size()));
        }while ((!fahrzeug1.equals(fahrzeug2)));
        Lösungen.get(Lösungen.size() - 1).setFahrzeug2(fahrzeug2.getID());
         int eins = rnd.nextInt(fahrzeug1.getKnoten().size()-2)+1;
         int zwei = rnd.nextInt(fahrzeug2.getKnoten().size()-2)+1;
        Lösungen.get(Lösungen.size() - 1).setIndex1(eins);
        Lösungen.get(Lösungen.size() - 1).setIndex2(zwei);

        Knoten erster = fahrzeug1.getKnoten().get(eins);
        Knoten zweiter = fahrzeug2.getKnoten().get(zwei);

        fahrzeug1.getKnoten().remove(eins);
        fahrzeug1.getKnoten().add(eins,zweiter);
        fahrzeug2.getKnoten().remove(zwei);
        fahrzeug2.getKnoten().add(zwei, erster);
    }

    private void insert(Knoten insert){
        Random rnd = new Random();
        Lösungen.get(Lösungen.size() - 1).setVeränderung(3);
        Fahrzeug fahrzeug = fahrzeuge.get(rnd.nextInt(fahrzeuge.size()));
        Lösungen.get(Lösungen.size() - 1).setFahrzeug1(fahrzeug.getID());
        int index = 0;
        if(fahrzeug.getKnoten().size()-2 <= 0){
            index = 1;
        }else {
            index = rnd.nextInt(fahrzeug.getKnoten().size() - 2) + 1;
        }
        Lösungen.get(Lösungen.size() - 1).setIndex1(index);
        Lösungen.get(Lösungen.size() - 1).setKnoten(insert.getID());
        fahrzeug.getKnoten().add(index, insert);

    }

    public List<Knoten> getEinzufügendeKonten() {
        return einzufügendeKonten;
    }
}
