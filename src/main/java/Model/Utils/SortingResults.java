
package Model.Utils;

import Model.Ingredients;
import Model.OrderSpeed;
import java.util.ArrayList;

public class SortingResults<T> {
    private ArrayList<T> sortedList;
    private long elapsedTime;
    private SortingAlgorithm algorithmUsed;
    private OrderSpeed algorithmSpeed;

    public SortingResults(ArrayList<T> sortedList, long elapsedTime, 
                         SortingAlgorithm algorithmUsed, OrderSpeed algorithmSpeed) {
        this.sortedList = new ArrayList<>(sortedList); // Copia defensiva
        this.elapsedTime = elapsedTime;
        this.algorithmUsed = algorithmUsed;
        this.algorithmSpeed = algorithmSpeed;
    }

    // Getters
    public ArrayList<T> getSortedList() {
        return new ArrayList<>(sortedList); // Copia defensiva
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public SortingAlgorithm getAlgorithmUsed() {
        return algorithmUsed;
    }

    public OrderSpeed getAlgorithmSpeed() {
        return algorithmSpeed;
    }
    
}
    