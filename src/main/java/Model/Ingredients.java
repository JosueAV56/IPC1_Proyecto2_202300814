
package Model;
import java.io.Serializable;

public class Ingredients implements Serializable,Comparable<Ingredients> {
    private int identifier;
    private String name;
    private String brand;
    private int stock;
    private String units;
    private double price;

    public Ingredients() {
        
    }

    public Ingredients(int identifier, String name, String brand, int stock, String units, double price) {
        this.identifier = identifier;
        this.name = name;
        this.brand = brand;
        this.stock = stock;
        this.units = units;
        this.price = price;
    }
    
    public boolean hasStock(){
        return stock > 0; 
    }
    
    public boolean reduceStock(int amount){
        if(stock>= amount){
            stock -= amount;
            return true;
        }
        return false;
    }
    public void increaseStock(int amount){
         stock += amount;
    }

    @Override
    public String toString(){
        return "Ingrediente{" +
                ",identificador='" + identifier + 
                ", nombre='" + name + '\'' +
                ", marca='" + brand + '\'' +
                ", existencias=" + stock + 
                ", unidades='" + units + '\'' +
                ", precio=" + price +
                '}';
    }
    
    
    public int compareTo(Ingredients other) {
        return Double.compare(other.getPrice(), this.getPrice());
    }

   

    public int getIdentifier() {return identifier;}
    public void setIdentifier(int identifier) {this.identifier = identifier;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}
    public String getUnits() {return units;}
    public void setUnits(String units) {this.units = units;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    
    
    
}
