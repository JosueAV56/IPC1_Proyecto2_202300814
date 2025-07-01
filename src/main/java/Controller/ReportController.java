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
    
    // Método para generar reporte de clientes
    public void generateClientReport(ArrayList<Client> clients, String filePath, 
                                   SortingAlgorithm algorithm, OrderSpeed speed) {
        // Ordenar clientes por cantidad de platillos pagados (descendente)
        SortingResults<Client> results = sortingManager.sort(clients, algorithm, speed, true);
        
        // Generar PDF con los resultados ordenados
        PDFManager.generateClientReport(results.getSortedList(), filePath);
    }
    
    // Método para generar reporte de ingredientes más usados
    public void generateMostUsedIngredientsReport(ArrayList<Ingredients> ingredients, String filePath, 
                                                SortingAlgorithm algorithm, OrderSpeed speed) {
        // Ordenar ingredientes por frecuencia de uso (descendente)
        SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
        
        // Tomar solo los top 10
        ArrayList<Ingredients> top10 = new ArrayList<>();
        int limit = Math.min(10, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top10.add(results.getSortedList().get(i));
        }
        
        // Generar PDF
        PDFManager.generateIngredientsReport(top10, filePath, false);
    }
    
    // Método para generar reporte de ingredientes más caros
    public void generateMostExpensiveIngredientsReport(ArrayList<Ingredients> ingredients, String filePath, 
                                                     SortingAlgorithm algorithm, OrderSpeed speed) {
        // Ordenar ingredientes por precio (descendente)
        SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
        
        // Tomar solo los top 10
        ArrayList<Ingredients> top10 = new ArrayList<>();
        int limit = Math.min(10, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top10.add(results.getSortedList().get(i));
        }
        
        // Generar PDF
        PDFManager.generateIngredientsReport(top10, filePath, true);
    }
    
    // Método para generar reporte de platillos más pedidos
    public void generateMostOrderedDishesReport(ArrayList<Dishes> dishes, String filePath, 
                                              SortingAlgorithm algorithm, OrderSpeed speed) {
        // Ordenar platillos por contador de pedidos (descendente)
        SortingResults<Dishes> results = sortingManager.sort(dishes, algorithm, speed, true);
        
        // Tomar solo los top 10
        ArrayList<Dishes> top10 = new ArrayList<>();
        int limit = Math.min(10, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top10.add(results.getSortedList().get(i));
        }
        
        // Generar PDF
        PDFManager.generateDishesReport(top10, filePath);
    }
    
    // Método para generar reporte de top 5 clientes con más pedidos
    public void generateTop5ClientsReport(ArrayList<Client> clients, String filePath, 
                                        SortingAlgorithm algorithm, OrderSpeed speed) {
        // Ordenar clientes por cantidad de platillos pagados (descendente)
        SortingResults<Client> results = sortingManager.sort(clients, algorithm, speed, true);
        
        // Tomar solo los top 5
        ArrayList<Client> top5 = new ArrayList<>();
        int limit = Math.min(5, results.getSortedList().size());
        for (int i = 0; i < limit; i++) {
            top5.add(results.getSortedList().get(i));
        }
        
        // Generar PDF (usamos el mismo método de clientes pero con solo 5)
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
    // Ordenar clientes por cantidad de platillos pagados (descendente)
    SortingResults<Client> results = sortingManager.sort(clients, algorithm, speed, true);
    
    // Generar PDF con los resultados ordenados, incluyendo la gráfica y tiempo de ordenamiento
    PDFManager.generateClientReportWithChart(results.getSortedList(), filePath, chart, sortingTime);
}

// Método para generar reporte de ingredientes más usados con gráfica
public void generateMostUsedIngredientsReportWithChart(ArrayList<Ingredients> ingredients, String filePath, 
                                                     SortingAlgorithm algorithm, OrderSpeed speed,
                                                     org.jfree.chart.JFreeChart chart, long sortingTime) {
    // Ordenar ingredientes por frecuencia de uso (descendente)
    SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
    
    // Tomar solo los top 10
    ArrayList<Ingredients> top10 = new ArrayList<>();
    int limit = Math.min(10, results.getSortedList().size());
    for (int i = 0; i < limit; i++) {
        top10.add(results.getSortedList().get(i));
    }
    
    // Generar PDF con gráfica
    PDFManager.generateIngredientsReportWithChart(top10, filePath, false, chart, sortingTime);
}

// Método para generar reporte de ingredientes más caros con gráfica
public void generateMostExpensiveIngredientsReportWithChart(ArrayList<Ingredients> ingredients, String filePath, 
                                                          SortingAlgorithm algorithm, OrderSpeed speed,
                                                          org.jfree.chart.JFreeChart chart, long sortingTime) {
    // Ordenar ingredientes por precio (descendente)
    SortingResults<Ingredients> results = sortingManager.sort(ingredients, algorithm, speed, true);
    
    // Tomar solo los top 10
    ArrayList<Ingredients> top10 = new ArrayList<>();
    int limit = Math.min(10, results.getSortedList().size());
    for (int i = 0; i < limit; i++) {
        top10.add(results.getSortedList().get(i));
    }
    
    // Generar PDF con gráfica
    PDFManager.generateIngredientsReportWithChart(top10, filePath, true, chart, sortingTime);
}

// Método para generar reporte de platillos más pedidos con gráfica
public void generateMostOrderedDishesReportWithChart(ArrayList<Dishes> dishes, String filePath, 
                                                   SortingAlgorithm algorithm, OrderSpeed speed,
                                                   org.jfree.chart.JFreeChart chart, long sortingTime) {
    // Ordenar platillos por contador de pedidos (descendente)
    SortingResults<Dishes> results = sortingManager.sort(dishes, algorithm, speed, true);
    
    // Tomar solo los top 10
    ArrayList<Dishes> top10 = new ArrayList<>();
    int limit = Math.min(10, results.getSortedList().size());
    for (int i = 0; i < limit; i++) {
        top10.add(results.getSortedList().get(i));
    }
    
    // Generar PDF con gráfica
    PDFManager.generateDishesReportWithChart(top10, filePath, chart, sortingTime);
}

// Método para generar reporte de top 5 clientes con gráfica
public void generateTop5ClientsReportWithChart(ArrayList<Client> clients, String filePath, 
                                             SortingAlgorithm algorithm, OrderSpeed speed,
                                             org.jfree.chart.JFreeChart chart, long sortingTime) {
    // Ordenar clientes por cantidad de platillos pagados (descendente)
    SortingResults<Client> results = sortingManager.sort(clients, algorithm, speed, true);
    
    // Tomar solo los top 5
    ArrayList<Client> top5 = new ArrayList<>();
    int limit = Math.min(5, results.getSortedList().size());
    for (int i = 0; i < limit; i++) {
        top5.add(results.getSortedList().get(i));
    }
    
    // Generar PDF con gráfica (usamos el mismo método de clientes pero con solo 5)
    PDFManager.generateClientReportWithChart(top5, filePath, chart, sortingTime);
}
}
