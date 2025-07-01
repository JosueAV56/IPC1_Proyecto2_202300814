
package Controller;

import Model.IngredientDao;
import Model.Ingredients;
import java.util.ArrayList;

public class IngredientController {
    private final IngredientDao ingredientDao;

    public IngredientController(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }
    
    public boolean addIngredient(Ingredients ingredient) {
        return ingredientDao.create(ingredient);
    }
    
    public boolean updateIngredient(Ingredients ingredient) {
        return ingredientDao.uptade(ingredient);
    }
    
    public boolean deleteIngredient(int id) {
        return ingredientDao.delete(id);
    }
    
    public Ingredients getIngredientById(int id) {
        return ingredientDao.getById(id);
    }
    
    public ArrayList<Ingredients> getAllIngredients() {
        return ingredientDao.getAll();
    }
    
    public ArrayList<Ingredients> getIngredientsByName(String name) {
        return ingredientDao.getByName(name);
    }
    
    public ArrayList<Ingredients> getIngredientsByBrand(String brand) {
        return ingredientDao.getByBrand(brand);
    }
    public ArrayList<Ingredients> getIngredientsByIds(ArrayList<Integer> ids) {
    return ingredientDao.getByIds(new ArrayList<>(ids));
}
    
    public boolean updateStock(int id, int newAmount) {
        return ingredientDao.uptadeStock(id, newAmount);
    }
    
    public ArrayList<Ingredients> getTop10MostExpensiveIngredients() {
        return ingredientDao.getTop10MostExpensive();
    }
    public IngredientDao getIngredientDao(){
        return ingredientDao;
    }
}
