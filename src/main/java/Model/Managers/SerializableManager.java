package Model.Managers;
import Model.Bill;
import Model.Client;
import Model.ClientDao;
import Model.Dishes;
import Model.DishesDao;
import Model.Employee;
import Model.EmployeeDao;
import Model.IngredientDao;
import Model.Ingredients;
import Model.OrderDao;
import Model.WorkOrder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SerializableManager {
    public static void saveAllData(String basePath,
                                 IngredientDao ingredientDao,
                                 DishesDao dishesDao,
                                 ClientDao clientDao,
                                 OrderDao orderDao,
                                 EmployeeDao employeeDao) throws IOException {
        // Asegurar que el directorio existe
        new File(basePath).mkdirs();
        
        // Guardar cada componente por separado
        saveObject(basePath + "ingredients.dat", ingredientDao.getListaInterna());
        saveObject(basePath + "dishes.dat", dishesDao.getInternList());
        saveObject(basePath + "clients.dat", clientDao.getInterList());
        saveObject(basePath + "orders.dat", orderDao.getInternalList());
        saveObject(basePath + "employees.dat", employeeDao.getInternalList());
        saveObject(basePath + "nextOrder.dat", orderDao.getNextOrderNumber());
    }
    
    public static void saveBillData(String basePath, BillManager billManager) throws IOException {
        new File(basePath).mkdirs();
        saveObject(basePath + "bills.dat", billManager.getBillsMap());
        saveObject(basePath + "nextBillNumber.dat", billManager.getNextBillNumber());
    }
    
    public static void loadAllData(String basePath,
                                 IngredientDao ingredientDao,
                                 DishesDao dishesDao,
                                 ClientDao clientDao,
                                 OrderDao orderDao,
                                 EmployeeDao employeeDao) {
        loadIfExists(basePath + "ingredients.dat", ingredientDao);
        loadIfExists(basePath + "dishes.dat", dishesDao);
        loadIfExists(basePath + "clients.dat", clientDao);
        loadIfExists(basePath + "orders.dat", orderDao);
        loadIfExists(basePath + "employees.dat", employeeDao);
        loadIfExists(basePath + "nextOrder.dat", orderDao);
    }
    
    public static void loadBillData(String basePath, BillManager billManager) {
        loadIfExists(basePath + "bills.dat", billManager);
        loadIfExists(basePath + "nextBillNumber.dat", billManager);
    }
    
    // Métodos auxiliares privados
    
    private static void saveObject(String filePath, Object obj) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(obj);
        }
    }
    
    private static void loadIfExists(String filePath, IngredientDao dao) {
        try {
            Object obj = loadObject(filePath);
            if (obj != null) {
                dao.setListaInterna((ArrayList<Ingredients>) obj);
            }
        } catch (Exception e) {
            System.err.println("Error loading ingredients: " + e.getMessage());
        }
    }
    
    private static void loadIfExists(String filePath, DishesDao dao) {
        try {
            Object obj = loadObject(filePath);
            if (obj != null) {
                dao.setInternList((ArrayList<Dishes>) obj);
            }
        } catch (Exception e) {
            System.err.println("Error loading dishes: " + e.getMessage());
        }
    }
    
    private static void loadIfExists(String filePath, ClientDao dao) {
        try {
            Object obj = loadObject(filePath);
            if (obj != null) {
                dao.setListaInterna((ArrayList<Client>) obj);
            }
        } catch (Exception e) {
            System.err.println("Error loading clients: " + e.getMessage());
        }
    }
    
    private static void loadIfExists(String filePath, OrderDao dao) {
        try {
            Object obj = loadObject(filePath);
            if (obj != null) {
                if (filePath.contains("nextOrder")) {
                    dao.setNextOrderNumber((Integer) obj);
                } else {
                    dao.setInternalList((ArrayList<WorkOrder>) obj);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading orders: " + e.getMessage());
        }
    }
    
    // NUEVO: Método para cargar empleados
    private static void loadIfExists(String filePath, EmployeeDao dao) {
        try {
            Object obj = loadObject(filePath);
            if (obj != null) {
                dao.setInternalList((ArrayList<Employee>) obj);
            }
        } catch (Exception e) {
            System.err.println("Error loading employees: " + e.getMessage());
        }
    }
    
    private static void loadIfExists(String filePath, BillManager billManager) {
        try {
            Object obj = loadObject(filePath);
            if (obj != null) {
                if (filePath.contains("nextBillNumber")) {
                    billManager.setNextBillNumber((Integer) obj);
                } else {
                    billManager.setBillsMap((HashMap<Integer, Bill>) obj);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading bills: " + e.getMessage());
        }
    }
    
    private static Object loadObject(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        } catch (Exception e) {
            System.err.println("Error loading " + filePath + ": " + e.getMessage());
            return null;
        }
    }
}