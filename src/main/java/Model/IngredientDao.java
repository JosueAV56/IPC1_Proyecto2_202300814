
package Model;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IngredientDao {
    private ArrayList<Ingredients> ingredients; 
    private final DishesDao dishesDao;
    private Map<Integer, Integer> ingredientUsageCount = new ConcurrentHashMap<>();

    public IngredientDao() {
        this.ingredients = new ArrayList<>();
        this.dishesDao = new DishesDao();
    }
    public Ingredients getByid(int id){
        for(Ingredients ig : ingredients){
            if(ig.getIdentifier()==id){
                return ig;
            }
        }
        return null;
    }
    public synchronized ArrayList<Ingredients> getAll(){
        return new ArrayList<>(ingredients);
    }
    public synchronized ArrayList<Ingredients> getByName(String name){
        ArrayList<Ingredients> findIt = new ArrayList<>();
        for(Ingredients ig: ingredients){
            if(ig.getName().toLowerCase().contains(name.toLowerCase())){
                findIt.add(ig);
            }
        }
        return findIt;
    }
    
    public synchronized int getUsageCount(int ingredientId) {
    return ingredientUsageCount.getOrDefault(ingredientId, 0);
    }
    
    public synchronized void incrementUsageCount(int ingredientId) {
    ingredientUsageCount.put(ingredientId, 
        ingredientUsageCount.getOrDefault(ingredientId, 0) + 1);
    }

    public synchronized Ingredients getById(int id){
        for(Ingredients ingredient: ingredients){
            if(ingredient.getIdentifier() == id){
                return ingredient;
            }
        }
        return null;
    }
    public synchronized boolean create(Ingredients ingredient){
        if(ingredient != null && !ingredientExists(ingredient.getIdentifier())){
            return ingredients.add(ingredient);
        }
        return false;
    }
    
    public synchronized boolean ingredientExists(int id){
            return getById(id) != null;        
    }
    public synchronized ArrayList<Ingredients> getByBrand(String brand){
        ArrayList<Ingredients> findIt = new ArrayList<>();
        for(Ingredients ig: ingredients){
            if(ig.getBrand().toLowerCase().contains(brand.toLowerCase())){
                findIt.add(ig);
            }
        }
        return findIt;
    } 
    
    public synchronized boolean uptade(Ingredients updateIngredient){
        for(int i=0; i<ingredients.size(); i++){
            if(ingredients.get(i).getIdentifier() == updateIngredient.getIdentifier()){
                ingredients.set(i, updateIngredient);
                return true;
            }
        }
        return false;
    }
    public synchronized boolean delete(int id){
        for(int i=0; i<ingredients.size(); i++){
            if(ingredients.get(i).getIdentifier() == id){
                ingredients.remove(i);
                return true;
            }
        }
        return false;
    }
    public synchronized ArrayList<Ingredients> getWStock() {
        ArrayList<Ingredients> wStock = new ArrayList<>();
        for (Ingredients ig : ingredients) {
            if (ig.hasStock()) {
                wStock.add(ig);
            }
        }
        return wStock;
    }
    public synchronized ArrayList<Ingredients> getWNoStock() {
        ArrayList<Ingredients> noStock = new ArrayList<>();
        for (Ingredients ig : ingredients) {
            if (!ig.hasStock()) {
                noStock.add(ig);
            }
        }
        return noStock;
    }
    public synchronized boolean uptadeStock(int id, int newAmount){
        Ingredients ingredient = getById(id);
        if(ingredient != null){
            ingredient.setStock(newAmount);
            return true;
        }
        return false;
    }
    public synchronized boolean reduceStock(int id, int newAmount){
        Ingredients ingredient = getById(id);
        if(ingredient != null){
            ingredient.reduceStock(newAmount);
            return true;
        }
        return false;
    }
    public synchronized boolean increaseStock(int id, int newAmount){
        Ingredients ingredient = getById(id);
        if(ingredient != null){
            ingredient.increaseStock(newAmount);
            return true;
        }
        return false;
    }
    
    
     // Métodos para reportes
    public synchronized ArrayList<Ingredients> getTop10MostExpensive() {
        ArrayList<Ingredients> ingredientesOrdenados = new ArrayList<>(ingredients);
        
        // Ordenamiento burbuja por precio (descendente)
        for (int i = 0; i < ingredientesOrdenados.size() - 1; i++) {
            for (int j = 0; j < ingredientesOrdenados.size() - i - 1; j++) {
                if (ingredientesOrdenados.get(j).getPrice() < 
                    ingredientesOrdenados.get(j + 1).getPrice()) {
                    Ingredients temp = ingredientesOrdenados.get(j);
                    ingredientesOrdenados.set(j, ingredientesOrdenados.get(j + 1));
                    ingredientesOrdenados.set(j + 1, temp);
                }
            }
        }
        
        // Retornar solo los primeros 10
        ArrayList<Ingredients> top10 = new ArrayList<>();
        int limite = Math.min(10, ingredientesOrdenados.size());
        for (int i = 0; i < limite; i++) {
            top10.add(ingredientesOrdenados.get(i));
        }
        return top10;
    }
    public synchronized ArrayList<Ingredients> getByIds(ArrayList<Integer> ids){
        ArrayList<Ingredients> findIt = new ArrayList<>();
        for(Integer id: ids){
            Ingredients ig = getById(id);
            if(ig != null){
                findIt.add(ig);
            }
        }
        return findIt;
    }
    public synchronized double calculateOverallPrice(ArrayList<Integer> ingredientsIds) {
        double overall = 0.0;
        for (Integer id : ingredientsIds) {
            Ingredients ig = getById(id);
            if (ig != null) {
                overall += ig.getPrice();
            }
        }
        return overall;
    }
    
    public synchronized int getProximoId() {
        int maxId = 0;
        for (Ingredients ingrediente : ingredients) {
            if (ingrediente.getIdentifier() > maxId) {
                maxId = ingrediente.getIdentifier();
            }
        }
        return maxId + 1;
    }
    
    public synchronized int size() {
        return ingredients.size();
    }
    
    public synchronized boolean isEmpty() {
        return ingredients.isEmpty();
    }
    
    public synchronized void limpiar() {
        ingredients.clear();
    }
    public synchronized void loadIngredient(ArrayList<Ingredients> ingredientesNuevos) {
        if (ingredientesNuevos != null) {
            for (Ingredients ingrediente : ingredientesNuevos) {
                create(ingrediente);
            }
        }
    }
    
    // Método para obtener la lista interna (para serialización)
    public synchronized ArrayList<Ingredients> getListaInterna() {
        return ingredients;
    }
    
    // Método para establecer la lista interna (para deserialización)
    public synchronized void setListaInterna(ArrayList<Ingredients> ingredientes) {
        this.ingredients = ingredientes != null ? ingredientes : new ArrayList<>();
    }
}
