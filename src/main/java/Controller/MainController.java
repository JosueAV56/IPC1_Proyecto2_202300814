package Controller;

import Model.Client;
import Model.ClientDao;
import Model.DishesDao;
import Model.Employee;
import Model.EmployeeDao;
import Model.EmployeeType;
import Model.IngredientDao;
import Model.Managers.BillManager;
import Model.Managers.SerializableManager;
import Model.OrderDao;
import Model.OrderSpeed;
import Model.Threads.ThreadManager;
import Model.TypeClient;
import Model.Utils.SortingAlgorithm;
import View.LoginView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainController {
    private final IngredientDao ingredientDao;
    private final DishesDao dishesDao;
    private final ClientDao clientDao;
    private final OrderDao orderDao;
    private final EmployeeDao employeeDao;
    private final BillManager billManager;
    private final ThreadManager threadManager; 
    
    
    private final AdminController adminController;
    private final ClientController clientController;
    private final DishController dishController;
    private final IngredientController ingredientController;
    private final OrderController orderController;
    private final FileController fileController;
    private final ReportController reportController;
    
    public MainController() throws InterruptedException {
        System.out.println("[MainController] Inicializando MainController...");
        
        
        this.ingredientDao = new IngredientDao();
        this.dishesDao = new DishesDao();
        this.clientDao = new ClientDao();
        this.orderDao = new OrderDao(ingredientDao);
        this.employeeDao = new EmployeeDao();
        this.billManager = new BillManager();
        this.threadManager = new ThreadManager(orderDao);
        System.out.println("[MainController] ThreadManager creado");
        this.adminController = new AdminController(clientDao, threadManager);
        this.clientController = new ClientController(clientDao, orderDao, dishesDao, billManager, threadManager);
        this.dishController = new DishController(dishesDao, ingredientDao);
        this.ingredientController = new IngredientController(ingredientDao);
        this.orderController = new OrderController(orderDao, threadManager);
        System.out.println("[MainController] OrderController creado con ThreadManager compartido");
        this.fileController = new FileController();
        this.reportController = new ReportController(this,ingredientDao,dishesDao,clientDao);
        System.out.println("[MainController] Todos los controladores inicializados");
        initializeSystem();
    }
    
    private void initializeSystem() throws InterruptedException {
        System.out.println("[MainController] Iniciando sistema...");
        
        
        new File("data/").mkdirs();
        
        
        loadPersistentData();
        
        
        if (isFirstExecution()) {
            initializeDefaultData();
        }
        
        
        dishController.calculateDishesPrices();
        

        loadChefsIntoThreadManager();
        

        System.out.println("[MainController] Iniciando hilos del sistema...");
        threadManager.startKitchenThreads();
        

        threadManager.printSystemStatus();
        
        System.out.println("[MainController] ✅ Sistema inicializado correctamente");
    }
    
    private void loadPersistentData() {
        try {
            SerializableManager.loadAllData("data/", ingredientDao, dishesDao, clientDao, orderDao, employeeDao);
            SerializableManager.loadBillData("data/", billManager);
            System.out.println("[MainController] Datos cargados - Empleados: " + employeeDao.size());
        } catch (Exception e) {
            System.err.println("[MainController] Error al cargar datos: " + e.getMessage());
            showError("Error al cargar datos. Se iniciará con datos nuevos.\n" + e.getMessage());
        }
    }
    
    private boolean isFirstExecution() {
        boolean isEmpty = clientDao.getAll().isEmpty() || 
                         orderDao.getInternalList().isEmpty() ||
                         ingredientDao.getAll().isEmpty() ||
                         employeeDao.isEmpty();
        
        System.out.println("[MainController] Primera ejecución: " + isEmpty);
        return isEmpty;
    }
    
    private void initializeDefaultData() {
        try {
            System.out.println("[MainController] Inicializando datos por defecto...");
            
            Client adminClient = new Client("0000000000000", "Administrador", 
                                          "admin", "admin", "", TypeClient.COMMUN);
            clientDao.create(adminClient);
            

            Employee chef1 = new Employee(1, "Dulce Martínez", EmployeeType.CHEF);
            Employee chef2 = new Employee(2, "Álvaro Morales", EmployeeType.CHEF);
            
            employeeDao.create(chef1);
            employeeDao.create(chef2);
            
            System.out.println("[MainController] Chefs creados y guardados en EmployeeDao");
            

            savePersistentData();
            
        } catch (Exception e) {
            System.err.println("[MainController] Error al inicializar datos: " + e.getMessage());
            showError("Error al inicializar datos por defecto: " + e.getMessage());
        }
    }
    
    private void loadChefsIntoThreadManager() throws InterruptedException {
    System.out.println("[MainController] Cargando chefs en ThreadManager...");
    
    ArrayList<Employee> chefs = employeeDao.getChefs();
    System.out.println("[MainController] Chefs encontrados en EmployeeDao: " + chefs.size());
    
    if (chefs.isEmpty()) {
        System.out.println("[MainController] ️ No se encontraron chefs, creando chefs por defecto");
        
    
        Employee chef1 = new Employee(1, "Dulce Martínez", EmployeeType.CHEF);
        Employee chef2 = new Employee(2, "Álvaro Morales", EmployeeType.CHEF);
        
        employeeDao.create(chef1);
        employeeDao.create(chef2);
        
        chefs = employeeDao.getChefs();
        System.out.println("[MainController] Chefs por defecto creados: " + chefs.size());
    }
    

    threadManager.initializeWithChefs(chefs);
    System.out.println("[MainController]  ThreadManager inicializado con " + chefs.size() + " chefs");
}
    
    public void savePersistentData() {
        try {
            SerializableManager.saveAllData("data/", ingredientDao, dishesDao, clientDao, orderDao, employeeDao);
            SerializableManager.saveBillData("data/", billManager);
            System.out.println("[MainController] Datos guardados correctamente");
        } catch (IOException e) {
            System.err.println("[MainController] Error al guardar datos: " + e.getMessage());
            showError("Error al guardar datos: " + e.getMessage());
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // NUEVO: Método para obtener el ThreadManager directamente
    public ThreadManager getThreadManager() {
        if (threadManager == null) {
            System.err.println("[MainController] ️ ThreadManager es null!");
        }
        return threadManager;
    }
    

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }
    

    public AdminController getAdminController() { return adminController; }
    public ClientController getClientController() { return clientController; }
    public DishController getDishController() { return dishController; }
    public IngredientController getIngredientController() { return ingredientController; }
    public OrderController getOrderController() { return orderController; }
    public FileController getFileController() { return fileController; }
    public ReportController getReportController() { return reportController; }
    
  
    public void startApplication() {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView(this);
            loginView.setVisible(true);
        });
    }
    

    public void shutdown() {
        System.out.println("[MainController] Cerrando aplicación...");
        

        if (threadManager != null) {
            threadManager.stopSystem();
        }
        

        savePersistentData();
        

        System.out.println("[MainController] ✅ Aplicación cerrada correctamente");
        System.exit(0);
    }
    
    public static void main(String[] args) throws InterruptedException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            MainController controller = new MainController();
            controller.startApplication();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al iniciar la aplicación: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}