public class utils {

    public static int minimum(int a, int b){
        int ret;
        if(a > b){
            ret = b;
        }else{
            ret = a;
        }
        return ret;
    }

    public static int maximum(int a, int b){
        int ret;
        if(a < b){
            ret = b;
        }else{
            ret = a;
        }
        return ret;
    }

    public static int findMinIndex(double[] array) {
        int minIndex = 0;
        double minValue = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minIndex = i;
            }
        }

        return minIndex;
    }
}
