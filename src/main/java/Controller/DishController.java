
package Controller;

import Model.Dishes;
import Model.DishesDao;
import Model.IngredientDao;
import Model.Ingredients;
import java.util.ArrayList;


public class DishController {
    private final DishesDao dishesDao;
    private final IngredientDao ingredientDao;
    
    public DishController(DishesDao dishesDao, IngredientDao ingredientDao) {
        this.dishesDao = dishesDao;
        this.ingredientDao = ingredientDao;
    }
    
    public boolean addDish(Dishes dish) {
        return dishesDao.create(dish);
    }
    
    public boolean updateDish(Dishes dish) {
        return dishesDao.update(dish);
    }
    
    public boolean deleteDish(int id) {
        return dishesDao.delete(id);
    }
    
    public Dishes getDishById(int id) {
        return dishesDao.getById(id);
    }
    
    public ArrayList<Dishes> getAllDishes() {
        return dishesDao.getAll();
    }
    
    public ArrayList<Dishes> getDishesByName(String name) {
        return dishesDao.getbyName(name);
    }
    
    public ArrayList<Dishes> getDishesByIngredient(int ingredientId) {
        return dishesDao.getByIngredient(ingredientId);
    }
    
    public ArrayList<Dishes> getTop10MostOrderedDishes() {
        return dishesDao.getTop10MostOrdered();
    }
    
    public ArrayList<Ingredients> getIngredientsForDish(int dishId) {
        Dishes dish = dishesDao.getById(dishId);
        if (dish != null) {
            return ingredientDao.getByIds(dish.getIngredientsId());
        }
        return new ArrayList<>();
    }
    
    public void calculateDishesPrices() {
        dishesDao.estimateOverallPrice(ingredientDao.getAll());
    }
}
