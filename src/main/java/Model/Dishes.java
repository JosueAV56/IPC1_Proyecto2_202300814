
package Model;
import java.io.Serializable;
import java.util.ArrayList;

public class Dishes implements Serializable,Comparable<Dishes>{
    private int identifier;
    private String name;
    private ArrayList<Integer> ingredientsId;
    private double laborPrice;
    private double totalPrice;
    private int orderCounter;

    public Dishes() {
        this.ingredientsId = new ArrayList<>();
        this.orderCounter = 0;
    }

     public Dishes(int identifier, String name, ArrayList<Ingredients> ingredients, double laborPrice) {
        this.identifier = identifier;
        this.name = name;
        this.ingredientsId = new ArrayList<>();
        
        // Convertir Ingredients a IDs
        if (ingredients != null) {
            for (Ingredients ingredient : ingredients) {
                this.ingredientsId.add(ingredient.getIdentifier());
            }
        }
        
        this.laborPrice = laborPrice;
        this.totalPrice = 0.0;
        this.orderCounter = 0;
    }
     
    
    public synchronized void addIngredient(int ingredientId){
        if(!ingredientsId.contains(ingredientId)){
            ingredientsId.add(ingredientId);
        }
    }
    public synchronized void removeIngrediente(int ingredientId){
        ingredientsId.remove(ingredientId);
    }
    public synchronized boolean containsIngredient(int ingredientId){
        return ingredientsId.contains(ingredientId);
    }
    public synchronized void calculateTotalPrice(ArrayList<Ingredients> ingredients){
        double ingredientsPrice = 0.0; 
            for(Integer ingredientId : ingredientsId){
                for(Ingredients ingredient : ingredients){
                    if(ingredient.getIdentifier() == ingredientId){
                        ingredientsPrice += ingredient.getPrice();
                        break;
                    }
                }
            }
            this.totalPrice = this.laborPrice + ingredientsPrice ; 
    }
    public synchronized void increaseOrderCounter(){
        orderCounter ++;
    }
    
    @Override
    public String toString() {
        return "Platillo{" +
                "identificador=" + identifier +
                ", nombre='" + name + '\'' +
                ", ingredientesIds=" + ingredientsId +
                ", precioManoObra=" + laborPrice +
                ", precioTotal=" + totalPrice +
                ", vecesOrdenado=" + orderCounter +
                '}';
    }
    
     public int compareTo(Dishes other) {
        // Ordenar por veces pedido (descendente)
        return Integer.compare(other.getOrderCounter(), this.getOrderCounter());
    }
   

    public int getIdentifier() {return identifier;}
    public void setIdentifier(int identifier) {this.identifier = identifier;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public ArrayList<Integer> getIngredientsId() {return ingredientsId;}
    public void setIngredientsId(ArrayList<Integer> ingredientsId) {this.ingredientsId = ingredientsId;}
    public double getLaborPrice() {return laborPrice;}
    public void setLaborPrice(double laborPrice) {this.laborPrice = laborPrice;}
    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}
    public int getOrderCounter() {return orderCounter;}
    public void setOrderCounter(int orderCounter) {this.orderCounter = orderCounter;}

} 
