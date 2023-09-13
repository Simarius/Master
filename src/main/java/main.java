import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class main {



    public static void main(String[] args) throws IOException {
        for(int i = 0; i < 100; i++) {
            Setup setup = new Setup();
            sweepInsert sweep = new sweepInsert(setup.getDepot());
            sweep.main(setup.getKnotenList());
            sweep.Insertion(setup.getDynamikList());
            randomBeginning random = new randomBeginning(setup.getDepot());
            List<Fahrzeug> fahrzeuge = random.beginn(setup.getKnotenList());
            tabuSearch tabu = new tabuSearch(fahrzeuge, setup.getDynamikList());
            fahrzeuge = tabu.tabuSearching();
            Waiting w = new Waiting(setup.getDepot());
            w.waiting(setup.getKnotenList(), setup.getDynamikList());
        }

    }


}
