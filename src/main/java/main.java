import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class main {



    public static void main(String[] args) throws IOException {
        for(int i = 0; i < 100; i++) {
            String path = "D:\\Users\\Jonas\\Desktop\\Uni\\Master\\Auswertung\\Dynamisch-80%-30\\auswertung.xlsx";
            Setup setup = new Setup(path);
            sweepInsert sweep = new sweepInsert(setup.getDepot(),80, path);
            sweep.main(setup.getKnotenList());
            sweep.Insertion(setup.getDynamikList());

            setup = new Setup(path);
            randomBeginning random = new randomBeginning(setup.getDepot());
            List<Fahrzeug> fahrzeuge = random.beginn(setup.getKnotenList());
            tabuSearch tabu = new tabuSearch(fahrzeuge, setup.getDynamikList(), 80,path);
            fahrzeuge = tabu.tabuSearching();

            setup = new Setup(path);
            Waiting w = new Waiting(setup.getDepot(),30,path);
            w.waiting(setup.getKnotenList(), setup.getDynamikList());
        }
        for(int i = 0; i < 100; i++) {
            String path = "D:\\Users\\Jonas\\Desktop\\Uni\\Master\\Auswertung\\Dynamisch-60%-25\\auswertung.xlsx";
            Setup setup = new Setup(path);
            sweepInsert sweep = new sweepInsert(setup.getDepot(),60,path);
            sweep.main(setup.getKnotenList());
            sweep.Insertion(setup.getDynamikList());

            setup = new Setup(path);
            randomBeginning random = new randomBeginning(setup.getDepot());
            List<Fahrzeug> fahrzeuge = random.beginn(setup.getKnotenList());
            tabuSearch tabu = new tabuSearch(fahrzeuge, setup.getDynamikList(), 60,path);
            fahrzeuge = tabu.tabuSearching();

            setup = new Setup(path);
            Waiting w = new Waiting(setup.getDepot(),25,path);
            w.waiting(setup.getKnotenList(), setup.getDynamikList());
        }
        for(int i = 0; i < 100; i++) {
            String path = "D:\\Users\\Jonas\\Desktop\\Uni\\Master\\Auswertung\\Dynamisch-40%-20\\auswertung.xlsx";
            Setup setup = new Setup(path);
            sweepInsert sweep = new sweepInsert(setup.getDepot(),40, path);
            sweep.main(setup.getKnotenList());
            sweep.Insertion(setup.getDynamikList());

            setup = new Setup(path);
            randomBeginning random = new randomBeginning(setup.getDepot());
            List<Fahrzeug> fahrzeuge = random.beginn(setup.getKnotenList());
            tabuSearch tabu = new tabuSearch(fahrzeuge, setup.getDynamikList(), 40,path);
            fahrzeuge = tabu.tabuSearching();

            setup = new Setup(path);
            Waiting w = new Waiting(setup.getDepot(),20,path);
            w.waiting(setup.getKnotenList(), setup.getDynamikList());
        }
        for(int i = 0; i < 100; i++) {
            String path = "D:\\Users\\Jonas\\Desktop\\Uni\\Master\\Auswertung\\Dynamisch-40%-20\\auswertung.xlsx";
            Setup setup = new Setup(path);
            sweepInsert sweep = new sweepInsert(setup.getDepot(),20, path);
            sweep.main(setup.getKnotenList());
            sweep.Insertion(setup.getDynamikList());

            setup = new Setup(path);
            randomBeginning random = new randomBeginning(setup.getDepot());
            List<Fahrzeug> fahrzeuge = random.beginn(setup.getKnotenList());
            tabuSearch tabu = new tabuSearch(fahrzeuge, setup.getDynamikList(), 20,path);
            fahrzeuge = tabu.tabuSearching();

            setup = new Setup(path);
            Waiting w = new Waiting(setup.getDepot(),15,path);
            w.waiting(setup.getKnotenList(), setup.getDynamikList());
        }

    }


}
