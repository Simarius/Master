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

    public Setup(String path) throws IOException {
        Depot = new Knoten(0d,0d,0,0,0,0,12);
        SetUp(path);
    }

    public void  SetUp( String path) throws IOException {
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
        /*
        Knoten knoten21 = new Knoten(-50.5, 30.7, 21, 42.3, 75, 2, 7);
        Knoten knoten22 = new Knoten(10.2, -20.8, 22, 68.5, 48, 3, 10);
        Knoten knoten23 = new Knoten(0.0, 0.0, 23, 15.8, 25, 4, 9);
        Knoten knoten24 = new Knoten(-80.3, -90.5, 24, 91.2, 90, 1, 5);
        Knoten knoten25 = new Knoten(25.1, -75.9, 25, 37.6, 60, 0, 12);
        Knoten knoten26 = new Knoten(45.6, 60.8, 26, 62.0, 32, 6, 11);
        Knoten knoten27 = new Knoten(80.0, -10.0, 27, 78.9, 18, 7, 8);
        Knoten knoten28 = new Knoten(-15.4, 40.2, 28, 55.7, 55, 3, 10);
        Knoten knoten29 = new Knoten(-90.0, -70.0, 29, 10.1, 10, 2, 6);
        Knoten knoten30 = new Knoten(35.7, 15.3, 30, 38.6, 42, 5, 12);
        Knoten knoten31 = new Knoten(-70.0, 50.0, 31, 5.4, 20, 0, 11);
        Knoten knoten32 = new Knoten(75.6, -85.2, 32, 83.7, 80, 4, 9);
        Knoten knoten33 = new Knoten(65.2, 30.1, 33, 49.8, 22, 8, 12);
        Knoten knoten34 = new Knoten(-35.0, -5.0, 34, 31.7, 28, 1, 11);
        Knoten knoten35 = new Knoten(15.9, -50.0, 35, 24.3, 38, 7, 9);
        Knoten knoten36 = new Knoten(-10.8, 90.7, 36, 88.4, 92, 2, 8);
        Knoten knoten37 = new Knoten(90.0, 70.0, 37, 62.8, 72, 1, 6);
        Knoten knoten38 = new Knoten(-55.3, -15.6, 38, 45.0, 50, 3, 8);
        Knoten knoten39 = new Knoten(30.0, 5.0, 39, 35.2, 35, 5, 10);
        Knoten knoten40 = new Knoten(5.7, 10.4, 40, 18.9, 28, 0, 12);
        */
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
        /*
        knotenList.add(knoten21);
        knotenList.add(knoten22);
        knotenList.add(knoten23);
        knotenList.add(knoten24);
        knotenList.add(knoten25);
        knotenList.add(knoten26);
        knotenList.add(knoten27);
        knotenList.add(knoten28);
        knotenList.add(knoten29);
        knotenList.add(knoten30);
        knotenList.add(knoten31);
        knotenList.add(knoten32);
        knotenList.add(knoten33);
        knotenList.add(knoten34);
        knotenList.add(knoten35);
        knotenList.add(knoten36);
        knotenList.add(knoten37);
        knotenList.add(knoten38);
        knotenList.add(knoten39);
        knotenList.add(knoten40);
        */
        for(Knoten k: knotenList){
            k.setWinkel(WinkelBerechnen(k.getX_Koordinate(),k.getY_Koordinate()));
            //System.out.println(toAlphabetic(k.getID()-1) + " - " + k.getWinkel()  );
        }

        System.out.println();
        Collections.sort(knotenList, new KnotenComparator());

        for(Knoten k: knotenList){
            //System.out.println(toAlphabetic(k.getID()-1) + " - " + k.getWinkel());
        }
        dynamikList.add(knotenList.get(3));
        dynamikList.add(knotenList.get(5));
        dynamikList.add(knotenList.get(6));
        dynamikList.add(knotenList.get(7));
        dynamikList.add(knotenList.get(8));
        dynamikList.add(knotenList.get(12));
        dynamikList.add(knotenList.get(13));
        dynamikList.add(knotenList.get(16));
        dynamikList.add(knotenList.get(18));

        /*
        dynamikList.add(knotenList.get(23));
        dynamikList.add(knotenList.get(27));
        dynamikList.add(knotenList.get(29));
        dynamikList.add(knotenList.get(31));
        dynamikList.add(knotenList.get(35));
        dynamikList.add(knotenList.get(38));

        knotenList.remove(38);
        knotenList.remove(35);
        knotenList.remove(31);
        knotenList.remove(29);
        knotenList.remove(27);
        knotenList.remove(23);
        */
        knotenList.remove(18);
        knotenList.remove(16);
        knotenList.remove(13);
        knotenList.remove(12);
        knotenList.remove(8);
        knotenList.remove(7);
        knotenList.remove(6);
        knotenList.remove(5);
        knotenList.remove(3);


        File fiel = new File(path);

        FileInputStream file = new FileInputStream(fiel);
        Workbook workbook = new XSSFWorkbook(file);
        try {
            Sheet sheet = workbook.createSheet("Insertion");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Gesammt Länge");
            row.createCell(1).setCellValue("Nicht eingefügte Knoten");
            row.createCell(2).setCellValue("Nicht eingefügt wegen Zeit");
            row.createCell(3).setCellValue("Nicht aufgetreten");
            row.createCell(4).setCellValue("Funktionswert");

            sheet = workbook.createSheet("Tabu");
            row = sheet.createRow(0);
            row.createCell(0).setCellValue("Gesammt Länge");
            row.createCell(1).setCellValue("Nicht eingefügte Knoten");
            row.createCell(2).setCellValue("Nicht eingefügt wegen Zeit");
            row.createCell(3).setCellValue("Nicht aufgetreten");
            row.createCell(4).setCellValue("Funktionswert");

            sheet = workbook.createSheet("Waiting");
            row = sheet.createRow(0);
            row.createCell(0).setCellValue("Gesammt Länge");
            row.createCell(1).setCellValue("Nicht eingefügte Knoten");
            row.createCell(2).setCellValue("Nicht eingefügt wegen Zeit");
            row.createCell(3).setCellValue("Nicht aufgetreten");
            row.createCell(4).setCellValue("Funktionswert");

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
