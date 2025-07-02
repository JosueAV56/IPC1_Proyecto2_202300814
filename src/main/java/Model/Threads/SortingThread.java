package Model.Threads;

import java.util.ArrayList;
import java.util.List;

public class SortingThread extends Thread {
    
    private final List<Integer> data;
    private final String sortingMethod;
    private final SortingCallback callback;
    private volatile boolean isRunning = false;

    public interface SortingCallback {
        void onSortingComplete(List<Integer> sortedData, long executionTime);
        void onSortingError(Exception error);
    }

    public SortingThread(List<Integer> data, String sortingMethod, SortingCallback callback) {
        this.data = new ArrayList<>(data); 
        this.sortingMethod = sortingMethod.toLowerCase();
        this.callback = callback;
        this.setName("SortingThread-" + sortingMethod);
    }
    
    @Override
    public void run() {
        isRunning = true;
        long startTime = System.nanoTime();
        
        try {
            switch (sortingMethod) {
                case "Bubble Sort":
                    bubbleSort();
                    System.out.println("[ReporView] realizando con bubble sort");
                    break;
                case "Quick sort":
                    
                    System.out.println("[ReporView] realizando con quick sort");
                    break;
                default:
                    quickSort(0, data.size() - 1);
                    
                    System.out.println("[ReportView] generando por default");
            }
            
            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime) / 1_000_000; // Convertir a milisegundos
            
            if (callback != null) {
                callback.onSortingComplete(new ArrayList<>(data), executionTime);
            }
            
        } catch (Exception e) {
            if (callback != null) {
                callback.onSortingError(e);
            }
        } finally {
            isRunning = false;
        }
    }
    
    private void bubbleSort() {
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data.get(j) < data.get(j + 1)) {
                    swap(j, j + 1);
                }
            }
        }
    }
    
    private void quickSort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }
    
    private int partition(int low, int high) {
        Integer pivot = data.get(high);
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (data.get(j) > pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }
    
    private void swap(int i, int j) {
        Integer temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getSortingMethod() {
        return sortingMethod;
    }

    public int getDataSize() {
        return data.size();
    }
}