import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Setup {
    private List<Knoten> knotenList = new ArrayList<>();
    private List<Knoten> dynamikList = new ArrayList<>();
    private Knoten Depot;

    public Setup() throws IOException {
        Depot = new Knoten(0d,0d,0,0,0,0,12);
        SetUp();
    }

    public void  SetUp() throws IOException {
        Knoten knoten1 = new Knoten(10.0, 20.0, 1, 70.5, 50, 2, 8);
        Knoten knoten2 = new Knoten(-30.0, 40.0, 2, 45.0, 20, 3, 9);
        Knoten knoten3 = new Knoten(50.0, -60.0, 3, 80.2, 80, 1, 7);
        Knoten knoten4 = new Knoten(-70.0, -10.0, 4, 60.7, 30, 4, 10);
        Knoten knoten5 = new Knoten(80.0, 50.0, 5, 95.1, 90, 1, 6);
        Knoten knoten6 = new Knoten(-90.0, -80.0, 6, 40.3, 10, 5, 11);
        Knoten knoten7 = new Knoten(30.0, -70.0, 7, 55.6, 40, 2, 8);
        Knoten knoten8 = new Knoten(-50.0, 60.0, 8, 75.8, 60, 3, 9);
        Knoten knoten9 = new Knoten(-10.0, 30.0, 9, 85.0, 70, 1, 7);
        Knoten knoten10 = new Knoten(40.0, -20.0, 10, 50.9, 30, 4, 10);
        Knoten knoten11 = new Knoten(-60.0, 0.0, 11, 90.4, 80, 1, 6);
        Knoten knoten12 = new Knoten(70.0, -40.0, 12, 35.2, 20, 5, 11);
        Knoten knoten13 = new Knoten(0.0, -50.0, 13, 65.7, 40, 2, 8);
        Knoten knoten14 = new Knoten(-20.0, 70.0, 14, 80.0, 60, 3, 9);
        Knoten knoten15 = new Knoten(-40.0, -30.0, 15, 95.3, 90, 1, 7);
        Knoten knoten16 = new Knoten(60.0, 10.0, 16, 40.9, 10, 4, 10);
        Knoten knoten17 = new Knoten(-80.0, 80.0, 17, 55.2, 30, 1, 6);
        Knoten knoten18 = new Knoten(90.0, -90.0, 18, 75.5, 50, 5, 11);
        Knoten knoten19 = new Knoten(20.0, 0.0, 19, 85.8, 70, 2, 8);
        Knoten knoten20 = new Knoten(-30.0, -20.0, 20, 50.0, 40, 3, 9);

        knotenList.add(knoten1);
        knotenList.add(knoten2);
        knotenList.add(knoten3);
        knotenList.add(knoten4);
        knotenList.add(knoten5);
        knotenList.add(knoten6);
        knotenList.add(knoten7);
        knotenList.add(knoten8);
        knotenList.add(knoten9);
        knotenList.add(knoten10);
        knotenList.add(knoten11);
        knotenList.add(knoten12);
        knotenList.add(knoten13);
        knotenList.add(knoten14);
        knotenList.add(knoten15);
        knotenList.add(knoten16);
        knotenList.add(knoten17);
        knotenList.add(knoten18);
        knotenList.add(knoten19);
        knotenList.add(knoten20);

        for(Knoten k: knotenList){
            k.setWinkel(WinkelBerechnen(k.getX_Koordinate(),k.getY_Koordinate()));
            System.out.println(toAlphabetic(k.getID()-1) + " - " + k.getWinkel()  );
        }

        System.out.println();
        Collections.sort(knotenList, new KnotenComparator());

        for(Knoten k: knotenList){
            System.out.println(toAlphabetic(k.getID()-1) + " - " + k.getWinkel());
        }
        dynamikList.add(knotenList.get(3));
        dynamikList.add(knotenList.get(8));
        dynamikList.add(knotenList.get(13));
        dynamikList.add(knotenList.get(18));
        dynamikList.add(knotenList.get(6));
        dynamikList.add(knotenList.get(12));

        knotenList.remove(18);
        knotenList.remove(13);
        knotenList.remove(12);
        knotenList.remove(8);
        knotenList.remove(6);
        knotenList.remove(3);


        File fiel = new File("D:\\Users\\Jonas\\Desktop\\Uni\\Master\\Auswertung\\auswertung.xlsx");

        FileInputStream file = new FileInputStream(fiel);
        Workbook workbook = new XSSFWorkbook(file);
        try {
            Sheet sheet = workbook.createSheet("Insertion");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Gesammt Länge");
            row.createCell(1).setCellValue("Nicht eingefügte Knoten");
            row.createCell(2).setCellValue("Nicht eingefügt wegen Zeit");
            row.createCell(3).setCellValue("Funktionswert");

            sheet = workbook.createSheet("Tabu");
            row = sheet.createRow(0);
            row.createCell(0).setCellValue("Gesammt Länge");
            row.createCell(1).setCellValue("Nicht eingefügte Knoten");
            row.createCell(2).setCellValue("Nicht eingefügt wegen Zeit");
            row.createCell(3).setCellValue("Funktionswert");
            sheet = workbook.createSheet("Waiting");
            row = sheet.createRow(0);
            row.createCell(0).setCellValue("Gesammt Länge");
            row.createCell(1).setCellValue("Nicht eingefügte Knoten");
            row.createCell(2).setCellValue("Nicht eingefügt wegen Zeit");
            row.createCell(3).setCellValue("Funktionswert");

            FileOutputStream outputStream = new FileOutputStream(fiel);
            workbook.write(outputStream);
            workbook.close();
        }catch(IllegalArgumentException ignored){

        }


    }

    public static String toAlphabetic(int i) {
        if( i<0 ) {
            return "-"+toAlphabetic(-i-1);
        }

        int quot = i/26;
        int rem = i%26;
        char letter = (char)((int)'A' + rem);
        if( quot == 0 ) {
            return ""+letter;
        } else {
            return toAlphabetic(quot-1) + letter;
        }
    }
    public static double WinkelBerechnen(double xC, double yC) {
        // Berechne den Winkel mithilfe der trigonometrischen Funktion atan2
        double angle_rad = Math.atan2(yC, xC);

        // Konvertiere den Winkel von Bogenmaß in Grad
        double angle_deg = Math.toDegrees(angle_rad);

        // Stelle sicher, dass der Winkel im Uhrzeigersinn von 0° bis 360° liegt
        if (angle_deg < 0) {
            angle_deg += 360;
        }

        // Korrigiere den Winkel im Uhrzeigersinn, indem 90° subtrahiert werden
        angle_deg = 90 - angle_deg;

        // Stelle sicher, dass der Winkel immer positiv ist
        if (angle_deg < 0) {
            angle_deg += 360;
        }

        return angle_deg;
    }

    public List<Knoten> getKnotenList() {
        return knotenList;
    }

    public List<Knoten> getDynamikList() {
        return dynamikList;
    }

    public Knoten getDepot() {
        return Depot;
    }
}
