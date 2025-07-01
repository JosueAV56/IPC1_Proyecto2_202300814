
package Model.Utils;

import Model.OrderSpeed;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SortingUtils {
    public static <T extends Comparable<T>> SortingResults<T> sort(
            ArrayList<T> list, 
            SortingAlgorithm algorithm, 
            OrderSpeed speed, 
            boolean descending) {
        
        if (list == null || list.isEmpty()) {
            return new SortingResults<>(new ArrayList<>(), 0, algorithm, speed);
        }

        // Hacer una copia para no modificar la lista original
        ArrayList<T> listToSort = new ArrayList<>(list);
        
        long startTime = System.nanoTime();
        
        switch (algorithm) {
            case BUBBLE_SORT:
                bubbleSort(listToSort, speed, descending);
                break;
            case QUICK_SORT:
                quickSort(listToSort, 0, listToSort.size() - 1, speed, descending);
                break;
        }
        
        long elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        
        return new SortingResults<>(listToSort, elapsedTime, algorithm, speed);
    }

    // Bubble Sort optimizado con chequeo de intercambios
    private static <T extends Comparable<T>> void bubbleSort(
            ArrayList<T> list, 
            OrderSpeed speed, 
            boolean descending) {
        
        boolean swapped;
        int n = list.size();
        
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                int comparison = list.get(i - 1).compareTo(list.get(i));
                
                // Determinar si se debe intercambiar según el orden (ascendente/descendente)
                boolean shouldSwap = descending ? 
                    comparison < 0 : 
                    comparison > 0;
                
                if (shouldSwap) {
                    swap(list, i - 1, i);
                    swapped = true;
                    
                    // Aplicar delay según velocidad seleccionada
                    applyDelay(speed);
                }
            }
            n--;
        } while (swapped);
    }

    // Quick Sort recursivo
    private static <T extends Comparable<T>> void quickSort(
            ArrayList<T> list, 
            int low, 
            int high, 
            OrderSpeed speed, 
            boolean descending) {
        
        if (low < high) {
            int pi = partition(list, low, high, speed, descending);
            
            quickSort(list, low, pi - 1, speed, descending);
            quickSort(list, pi + 1, high, speed, descending);
        }
    }

    private static <T extends Comparable<T>> int partition(
            ArrayList<T> list, 
            int low, 
            int high, 
            OrderSpeed speed, 
            boolean descending) {
        
        T pivot = list.get(high);
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            int comparison = list.get(j).compareTo(pivot);
            
            // Determinar si el elemento actual debe estar antes del pivote
            boolean shouldPlaceBefore = descending ? 
                comparison >= 0 : 
                comparison <= 0;
            
            if (shouldPlaceBefore) {
                i++;
                swap(list, i, j);
                applyDelay(speed);
            }
        }
        
        swap(list, i + 1, high);
        return i + 1;
    }

    // Método thread-safe para intercambiar elementos
    private static synchronized <T> void swap(ArrayList<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // Aplicar delay según velocidad seleccionada
    private static void applyDelay(OrderSpeed speed) {
        try {
            Thread.sleep(speed.getDelayMiliSeconds());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
