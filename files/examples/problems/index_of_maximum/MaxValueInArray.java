package examples.problems.index_of_maximum;

/**
 * Created by admin on 07.03.2016.
 */
public class MaxValueInArray {
    public static void main(String[] args) {
        int[] ar = {1,2,3,4,5,6,7,8,9};
        int max = indexOfMax(ar);
        System.out.println(max);
    }

    private static int indexOfMax(int[] ar) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] > max) {
                max = ar[i];
            }
        }
        return max;
    }
}
