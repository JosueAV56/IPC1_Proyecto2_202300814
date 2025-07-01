
package Model.Utils;

import Model.OrderSpeed;
import java.util.ArrayList;

public class SortingManager {
    public <T extends Comparable<T>> SortingResults<T> sort(
            ArrayList<T> data, SortingAlgorithm algorithm, OrderSpeed speed, boolean descending) {
        
        return SortingUtils.sort(data, algorithm, speed, descending);
    }
}
