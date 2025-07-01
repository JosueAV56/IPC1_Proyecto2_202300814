package Controller;
import Model.Client;
import Model.ClientDao;
import Model.DishesDao;
import Model.Employee;
import Model.EmployeeDao;
import Model.IngredientDao;
import Model.Managers.BillManager;
import Model.Managers.SerializableManager;
import Model.OrderDao;
import Model.Threads.ThreadManager;
import Model.TypeClient;
import java.io.IOException;
import java.util.ArrayList;

public class AdminController {
    private final ClientDao clientDao;
    private final ThreadManager threadManager;

    public AdminController(ClientDao clientDao, ThreadManager threadManager) {
        this.clientDao = clientDao;
        this.threadManager = threadManager;
    }
    
    public boolean authenticateAdmin(String user,String pass){
        return "admin".equals(user) && "admin".equals(pass);
    }
    public ArrayList<Client> getAll(){
        return clientDao.getAll();
    }
    
    public ArrayList<Client> getGoldClients(){
        return clientDao.getByType(TypeClient.GOLD);
    }
    
    public ArrayList<Client> getNomalClients(){
        return clientDao.getByType(TypeClient.COMMUN);
    }
    
    public ClientDao getClientDao() {
    return this.clientDao;
    }
    
    
    public void stopSystem(){
        threadManager.stopSystem();
    }
    
    public void saveSystemData(String basePath,
                             IngredientDao ingredientDao,
                             DishesDao dishesDao,
                             ClientDao clientDao,
                             OrderDao orderDao,
                             EmployeeDao employeeDao,
                             BillManager billManager) throws IOException {
        SerializableManager.saveAllData(basePath, ingredientDao, dishesDao, clientDao, orderDao, employeeDao);
        SerializableManager.saveBillData(basePath, billManager);
    }
    
    public void loadSystemData(String basePath,
                             IngredientDao ingredientDao,
                             DishesDao dishesDao,
                             ClientDao clientDao,
                             OrderDao orderDao,
                             EmployeeDao employeeDao,
                             BillManager billManager) throws IOException {
        SerializableManager.loadAllData(basePath, ingredientDao, dishesDao, clientDao, orderDao, employeeDao);
        SerializableManager.loadBillData(basePath, billManager);
    }
}