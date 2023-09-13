import java.util.Comparator;

class KnotenComparator implements Comparator<Knoten> {
    @Override
    public int compare(Knoten knoten1, Knoten knoten2) {
        // Sortiere in aufsteigender Reihenfolge nach dem Winkel (Double-Attribut angle)
        return Double.compare(knoten1.getWinkel(), knoten2.getWinkel());
    }
}