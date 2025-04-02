package v04;

public class BubbleSort {
    public static void sort(int[] array) {
        boolean swapped;
        int passIndex = 0;
        do {
            swapped = false;
            for (int j = 0; j < array.length - 1 - passIndex; j++) {
                if (array[j] > array[j + 1]) {
             
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true; 
                }
            }
            passIndex++;
        } while (swapped);
    }
}
