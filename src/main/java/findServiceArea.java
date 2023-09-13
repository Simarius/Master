import java.util.ArrayList;
import java.util.List;

public class findServiceArea {
    static double RADIUS = 40d;

    public static List<ServiceArea> find(List<Knoten> KnotenList, List<Knoten> dynamicList){
        List<Knoten> gesammtKnotenList = new ArrayList<>();
        gesammtKnotenList.addAll(KnotenList);
        gesammtKnotenList.addAll(dynamicList);
        List<ServiceArea> Areas = new ArrayList<>();
        Areas.add(new ServiceArea(new ArrayList<Knoten>(), gesammtKnotenList.get(0)));
        gesammtKnotenList.remove(0);
        for(Knoten k : gesammtKnotenList){
            List<Double> distanzen = new ArrayList<>();
            for(ServiceArea area : Areas){
                distanzen.add(calculateDistance(area.getUrsprung(),k));
            }
            double[] arr = distanzen.stream().mapToDouble(d -> d).toArray();
            int a = utils.findMinIndex(arr);
            if(distanzen.get(a) < RADIUS){
                k.setServiceArea(Areas.get(a));
                Areas.get(a).getAreaKnoten().add(k);
            }else{
                k.setServiceArea(Areas.get(a));
                Areas.add(new ServiceArea(new ArrayList<Knoten>(), k));
            }
        }


        for(int i = 0; i< Areas.size(); i++){
            //sort(Areas.get(i));
            System.out.println("Service Area Nr:" + i);
            for(Knoten a : Areas.get(i).getAreaKnoten()){
                System.out.print(a.getID() + " - ");
            }
            System.out.println();
        }
        return Areas;
    }

    public static double calculateDistance(Knoten knoten1, Knoten knoten2) {
        double deltaX = knoten2.getX_Koordinate() - knoten1.getX_Koordinate();
        double deltaY = knoten2.getY_Koordinate() - knoten1.getY_Koordinate();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }


    public static void sort (ServiceArea area){
        double[] distanzen = new double[area.getAreaKnoten().size()];
        List<Knoten>[] routen = new ArrayList[area.getAreaKnoten().size()];

        for(int j = 0; j < area.getAreaKnoten().size(); j++){
            Knoten start = area.getAreaKnoten().get(j);
            List<Knoten> Lösung = findShortestRoute(area.getAreaKnoten(),start);
            double dist = 0d;
            if(area.getAreaKnoten().size() > 1) {
                for (int i = 1; i < area.getAreaKnoten().size(); i++) {
                    dist += calculateDistance(area.getAreaKnoten().get(i-1), area.getAreaKnoten().get(i));
                }
            }
            distanzen[j] = dist;
            Lösung.remove(Lösung.size()-1);
            routen[j] = Lösung;
        }
        int index = utils.findMinIndex(distanzen);
        area.setAreaKnoten(routen[index]);


    }
    public static List<Knoten> findShortestRoute(List<Knoten> knotenList, Knoten startKnoten) {
        List<Knoten> route = new ArrayList<>();
        List<Knoten> unbesuchteKnoten = new ArrayList<>(knotenList);

        // Wähle den Startknoten beliebig
        unbesuchteKnoten.remove(startKnoten);
        route.add(startKnoten);

        while (!unbesuchteKnoten.isEmpty()) {
            Knoten aktuellerKnoten = route.get(route.size() - 1);
            Knoten nächsterKnoten = findNearestNeighbor(aktuellerKnoten, unbesuchteKnoten);
            route.add(nächsterKnoten);
            unbesuchteKnoten.remove(nächsterKnoten);
        }

        // Füge die Rückkehr zum Startknoten hinzu
        route.add(startKnoten);

        return route;
    }

    public static Knoten findNearestNeighbor(Knoten aktuellerKnoten, List<Knoten> knotenList) {
        Knoten nächsterKnoten = null;
        double kürzesteEntfernung = Double.MAX_VALUE;

        for (Knoten knoten : knotenList) {
            double entfernung = calculateDistance(aktuellerKnoten, knoten);
            if (entfernung < kürzesteEntfernung) {
                kürzesteEntfernung = entfernung;
                nächsterKnoten = knoten;
            }
        }

        return nächsterKnoten;
    }
}
