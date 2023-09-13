import java.util.Comparator;

public class LösungComparator implements Comparator<tabuLösung> {
        @Override
        public int compare(tabuLösung p1, tabuLösung p2) {
            return Double.compare(p1.getFunktionswert(), p2.getFunktionswert());
        }
    }

