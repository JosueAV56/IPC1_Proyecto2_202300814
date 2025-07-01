
package Model;
import java.util.ArrayList;

public class DishesDao {
    private ArrayList<Dishes> dishes;
    private int nextId;
    
    public DishesDao() {
        this.dishes = new ArrayList<>();
        this.nextId = 1;
    }
    
    public synchronized boolean create(Dishes dish) {
        if (dish != null) {
            dish.setIdentifier(nextId);
            return dishes.add(dish);
        }
        return false;
    }
    public synchronized boolean createById(Dishes dish) {
        if (dish != null && !dishExist(dish.getIdentifier())) {
            // Actualizar proximoId si es necesario
            if (dish.getIdentifier() >= nextId) {
                nextId = dish.getIdentifier() + 1;
            }
            return dishes.add(dish);
        }
        return false;
    }
    public synchronized Dishes getById(int id) {
        for (Dishes dish : dishes) {
            if (dish.getIdentifier()== id) {
                return dish;
            }
        }
        return null;
    }
    public synchronized ArrayList<Dishes> getAll() {
        return new ArrayList<>(dishes);
    }
    public synchronized ArrayList<Dishes> getbyName(String name) {
        ArrayList<Dishes> findIt = new ArrayList<>();
        for (Dishes dish : dishes) {
            if (dish.getName().toLowerCase().contains(name.toLowerCase())) {
                findIt.add(dish);
            }
        }
        return findIt;
    }
    public synchronized ArrayList<Dishes> getByIngredient(int ingredienteId) {
        ArrayList<Dishes> findIt = new ArrayList<>();
        for (Dishes dish : dishes) {
            if (dish.containsIngredient(ingredienteId)) {
                findIt.add(dish);
            }
        }
        return findIt;
    }
    public synchronized boolean update(Dishes updateDish) {
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getIdentifier() == updateDish.getIdentifier()) {
                dishes.set(i, updateDish);
                return true;
            }
        }
        return false;
    }
    public synchronized boolean delete(int id) {
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getIdentifier() == id) {
                dishes.remove(i);
                return true;
            }
        }
        return false;
    }
    public synchronized boolean dishExist(int id) {
        return getById(id) != null;
    }
    
    public synchronized void estimateOverallPrice(ArrayList<Ingredients> ingredientes) {
        for (Dishes dish : dishes) {
            dish.calculateTotalPrice(ingredientes);
        }
    }
    
    public synchronized boolean increaseOrdersTimes(int id) {
        Dishes dish = getById(id);
        if (dish != null) {
            dish.increaseOrderCounter();
            return true;
        }
        return false;
    }
    
    public synchronized ArrayList<Dishes> getByRangePrice(double minPrice, double maxPrice) {
        ArrayList<Dishes> findIt = new ArrayList<>();
        for (Dishes dish : dishes) {
            if (dish.getTotalPrice() >= minPrice && dish.getTotalPrice() <= maxPrice) {
                findIt.add(dish);
            }
        }
        return findIt;
    }
    
    // Métodos para reportes
    public synchronized ArrayList<Dishes> getTop10MostOrdered() {
        ArrayList<Dishes> orderedDishes = new ArrayList<>(dishes);
        
        // Ordenamiento burbuja por veces ordenado (descendente)
        for (int i = 0; i < orderedDishes.size() - 1; i++) {
            for (int j = 0; j < orderedDishes.size() - i - 1; j++) {
                if (orderedDishes.get(j).getOrderCounter() < 
                    orderedDishes.get(j + 1).getOrderCounter()) {
                    Dishes temp = orderedDishes.get(j);
                    orderedDishes.set(j, orderedDishes.get(j + 1));
                    orderedDishes.set(j + 1, temp);
                }
            }
        }
        
        // Retornar solo los primeros 10
        ArrayList<Dishes> top10 = new ArrayList<>();
        int limite = Math.min(10, orderedDishes.size());
        for (int i = 0; i < limite; i++) {
            top10.add(orderedDishes.get(i));
        }
        return top10;
    }
    
    public synchronized ArrayList<Integer> getMostUsedIngredients() {
        // Contar frecuencia de cada ingrediente
        ArrayList<Integer> ingredientsIds = new ArrayList<>();
        ArrayList<Integer> frecuencias = new ArrayList<>();
        
        for (Dishes dish : dishes) {
            for (Integer ingredientId : dish.getIngredientsId()) {
                int indice = ingredientsIds.indexOf(ingredientId);
                if (indice == -1) {
                    ingredientsIds.add(ingredientId);
                    frecuencias.add(1);
                } else {
                    frecuencias.set(indice, frecuencias.get(indice) + 1);
                }
            }
        }
        
        // Ordenar por frecuencia (descendente)
        for (int i = 0; i < frecuencias.size() - 1; i++) {
            for (int j = 0; j < frecuencias.size() - i - 1; j++) {
                if (frecuencias.get(j) < frecuencias.get(j + 1)) {
                    // Intercambiar frecuencias
                    Integer tempFrec = frecuencias.get(j);
                    frecuencias.set(j, frecuencias.get(j + 1));
                    frecuencias.set(j + 1, tempFrec);
                    
                    // Intercambiar IDs
                    Integer tempId = ingredientsIds.get(j);
                    ingredientsIds.set(j, ingredientsIds.get(j + 1));
                    ingredientsIds.set(j + 1, tempId);
                }
            }
        }
        
        // Retornar solo los primeros 10
        ArrayList<Integer> top10 = new ArrayList<>();
        int limite = Math.min(10, ingredientsIds.size());
        for (int i = 0; i < limite; i++) {
            top10.add(ingredientsIds.get(i));
        }
        return top10;
    }
    
    public synchronized boolean validateAvailableIngredients(int platilloId, ArrayList<Ingredients> ingredients) {
        Dishes dish = getById(platilloId);
        if (dish == null) return false;
        
        for (Integer ingredientId : dish.getIngredientsId()) {
            boolean findIt = false;
            for (Ingredients ingredient : ingredients) {
                if (ingredient.getIdentifier() == ingredientId && ingredient.hasStock()) {
                    findIt = true;
                    break;
                }
            }
            if (!findIt) return false;
        }
        return true;
    }
    
    public synchronized int getNextId() {
        return nextId;
    }
    
    public synchronized void setnextId(int proximoId) {
        this.nextId = proximoId;
    }
    
    public synchronized int size() {
        return dishes.size();
    }
    
    public synchronized boolean isEmpty() {
        return dishes.isEmpty();
    }
    
    public synchronized void clear() {
        dishes.clear();
        nextId = 1;
    }
    
    // Método para cargar datos masivamente
    public synchronized void loadDishes(ArrayList<Dishes> newDishes) {
        if (newDishes != null) {
            for (Dishes dish : newDishes) {
                createById(dish);
            }
        }
    }
    
    // Método para obtener la lista interna (para serialización)
    public synchronized ArrayList<Dishes> getInternList() {
        return dishes;
    }
    
    // Método para establecer la lista interna (para deserialización)
    public synchronized void setInternList(ArrayList<Dishes> dishes) {
        this.dishes = dishes != null ? dishes : new ArrayList<>();
        // Recalcular próximo ID
        int maxId = 0;
        for (Dishes dish : this.dishes) {
            if (dish.getIdentifier() > maxId) {
                maxId = dish.getIdentifier();
            }
        }
        this.nextId = maxId + 1;
    }
}
