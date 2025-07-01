
package Model.Utils;

public enum SortingAlgorithm {
    BUBBLE_SORT("Bubble Sort"),
    QUICK_SORT("Quick Sort");
    
    private final String name;
    
    private SortingAlgorithm(String name){
        this.name = name;
    }
    public String getName(){return name;}
    
    @Override
    public String toString(){
        return name;
    }
}
