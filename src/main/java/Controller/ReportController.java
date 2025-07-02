package Controller;

import Model.Client;
import Model.ClientDao;
import Model.Dishes;
import Model.DishesDao;
import Model.IngredientDao;
import Model.Ingredients;
import Model.Managers.PDFManager;
import Model.OrderSpeed;
import Model.Utils.SortingAlgorithm;
import Model.Utils.SortingManager;
import Model.Utils.SortingResults;
import java.util.ArrayList;

public class ReportController {
    private SortingManager sortingManager;
    private final MainController mainController;
    private IngredientDao ingredientsDao;
    private DishesDao dishesDao;
    private ClientDao clientDao;
    private long lastSortingTime;
    
    public ReportController(MainController mainController, IngredientDao ingredientsDao,DishesDao dishesDao,ClientDao clientDao) {
        this.ingredientsDao = ingredientsDao;
        this.dishesDao = dishesDao;
        this.sortingManager = new SortingManager();
        this.mainController = mainController;
    }
    
    public void generateClientReport(ArrayList<Client> clients, String filePath, 
                                   SortingAlgorithm algorithm, OrderSpeed speed) {
        SortingResults<Client> results = sortingManager.sort(clients, algorithm, speed, true);
        
        PDFManager.generateClientReport(results.getSortedList(), filePath);
    }
    
    public void generateMostUsedIngredientsReport(ArrayList<Ingredients> ingredients, String filePath, 
                                                SortingAlgorithm algorithm, OrderSpeed speed) {
        SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
        
        ArrayList<Ingredients> top10 = new ArrayList<>();
        int limit = Math.min(10, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top10.add(results.getSortedList().get(i));
        }
        
        PDFManager.generateIngredientsReport(top10, filePath, false);
    }
    
    public void generateMostExpensiveIngredientsReport(ArrayList<Ingredients> ingredients, String filePath, 
                                                     SortingAlgorithm algorithm, OrderSpeed speed) {
        SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
        
        ArrayList<Ingredients> top10 = new ArrayList<>();
        int limit = Math.min(10, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top10.add(results.getSortedList().get(i));
        }
        
        PDFManager.generateIngredientsReport(top10, filePath, true);
    }
    
    public void generateMostOrderedDishesReport(ArrayList<Dishes> dishes, String filePath, 
                                              SortingAlgorithm algorithm, OrderSpeed speed) {
        SortingResults<Dishes> results = sortingManager.sort(dishes, algorithm, speed, true);
        
        ArrayList<Dishes> top10 = new ArrayList<>();
        int limit = Math.min(10, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top10.add(results.getSortedList().get(i));
        }
        
        PDFManager.generateDishesReport(top10, filePath);
    }
    
    public void generateTop5ClientsReport(ArrayList<Client> clients, String filePath, 
                                        SortingAlgorithm algorithm, OrderSpeed speed) {
        SortingResults<Client> results = sortingManager.sort(clients, algorithm, speed, true);
        
        ArrayList<Client> top5 = new ArrayList<>();
        int limit = Math.min(5, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top5.add(results.getSortedList().get(i));
        }
        
        PDFManager.generateClientReport(top5, filePath);
    }
    public ArrayList<Client> getClientsByType(SortingAlgorithm algorithm, OrderSpeed speed) {
        ArrayList<Client> clients = new ArrayList<>(mainController.getClientController().getAllClients());
        return sortingManager.sort(clients, algorithm, speed, false).getSortedList();
    }
    
    public ArrayList<Ingredients> getMostUsedIngredients(SortingAlgorithm algorithm, OrderSpeed speed) {
        ArrayList<Ingredients> ingredients = new ArrayList<>(mainController.getIngredientController().getAllIngredients());
        SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
        
        ArrayList<Ingredients> top10 = new ArrayList<>();
        int limit = Math.min(10, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top10.add(results.getSortedList().get(i));
        }
        return top10;
    }
    
    public ArrayList<Ingredients> getMostExpensiveIngredients(SortingAlgorithm algorithm, OrderSpeed speed) {
        ArrayList<Ingredients> ingredients = ingredientsDao.getTop10MostExpensive();
        SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
        
        ArrayList<Ingredients> top10 = new ArrayList<>();
        int limit = Math.min(10, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top10.add(results.getSortedList().get(i));
        }
        return top10;
    }
    
    public ArrayList<Dishes> getMostOrderedDishes(SortingAlgorithm algorithm, OrderSpeed speed) {
        ArrayList<Dishes> dishes = new ArrayList<>(mainController.getDishController().getAllDishes());
        SortingResults<Dishes> results = sortingManager.sort(dishes, algorithm, speed, true);
        
        ArrayList<Dishes> top10 = new ArrayList<>();
        int limit = Math.min(10, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top10.add(results.getSortedList().get(i));
        }
        return top10;
    }
    
    public ArrayList<Client> getTop5Clients(SortingAlgorithm algorithm, OrderSpeed speed) {
        ArrayList<Client> clients = new ArrayList<>(mainController.getClientController().getAllClients());
        SortingResults<Client> results = sortingManager.sort(clients, algorithm, speed, true);
        
        ArrayList<Client> top5 = new ArrayList<>();
        int limit = Math.min(5, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top5.add(results.getSortedList().get(i));
        }
        return top5;
    }
    public void generateClientReportWithChart(ArrayList<Client> clients, String filePath, 
                                        SortingAlgorithm algorithm, OrderSpeed speed, 
                                        org.jfree.chart.JFreeChart chart, long sortingTime) {
    SortingResults<Client> results = sortingManager.sort(clients, algorithm, speed, true);
    
    PDFManager.generateClientReportWithChart(results.getSortedList(), filePath, chart, sortingTime);
}

public void generateMostUsedIngredientsReportWithChart(ArrayList<Ingredients> ingredients, String filePath, 
                                                     SortingAlgorithm algorithm, OrderSpeed speed,
                                                     org.jfree.chart.JFreeChart chart, long sortingTime) {
    SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
    
    ArrayList<Ingredients> top10 = new ArrayList<>();
    int limit = Math.min(10, results.getSortedList().size());
    for (int i = 0; i < limit; i++) {
        top10.add(results.getSortedList().get(i));
    }
    
    PDFManager.generateIngredientsReportWithChart(top10, filePath, false, chart, sortingTime);
}

public void generateMostExpensiveIngredientsReportWithChart(ArrayList<Ingredients> ingredients, String filePath, 
                                                          SortingAlgorithm algorithm, OrderSpeed speed,
                                                          org.jfree.chart.JFreeChart chart, long sortingTime) {
    SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
    
    ArrayList<Ingredients> top10 = new ArrayList<>();
    int limit = Math.min(10, results.getSortedList().size());
    for (int i = 0; i < limit; i++) {
        top10.add(results.getSortedList().get(i));
    }
    
    PDFManager.generateIngredientsReportWithChart(top10, filePath, true, chart, sortingTime);
}

public void generateMostOrderedDishesReportWithChart(ArrayList<Dishes> dishes, String filePath, 
                                                   SortingAlgorithm algorithm, OrderSpeed speed,
                                                   org.jfree.chart.JFreeChart chart, long sortingTime) {
    SortingResults<Dishes> results = sortingManager.sort(dishes, algorithm, speed, true);
    
    ArrayList<Dishes> top10 = new ArrayList<>();
    int limit = Math.min(10, results.getSortedList().size());
    for (int i = 0; i < limit; i++) {
        top10.add(results.getSortedList().get(i));
    }
    
    PDFManager.generateDishesReportWithChart(top10, filePath, chart, sortingTime);
}

public void generateTop5ClientsReportWithChart(ArrayList<Client> clients, String filePath, 
                                             SortingAlgorithm algorithm, OrderSpeed speed,
                                             org.jfree.chart.JFreeChart chart, long sortingTime) {
    SortingResults<Client> results = sortingManager.sort(clients, algorithm, speed, true);
    
    ArrayList<Client> top5 = new ArrayList<>();
    int limit = Math.min(5, results.getSortedList().size());
    for (int i = 0; i < limit; i++) {
        top5.add(results.getSortedList().get(i));
    }
    
    PDFManager.generateClientReportWithChart(top5, filePath, chart, sortingTime);
}
}
