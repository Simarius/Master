import java.util.Comparator;

public class KnotenStartzeitComparator implements Comparator<Knoten> {
        @Override
        public int compare(Knoten knoten1, Knoten knoten2) {
            return Integer.compare(knoten1.getStartZeit(), knoten2.getStartZeit());
        }
    }

