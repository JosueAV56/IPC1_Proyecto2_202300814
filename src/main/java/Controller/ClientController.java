
package Controller;

import Model.Bill;
import Model.Client;
import Model.ClientDao;
import Model.Dishes;
import Model.DishesDao;
import Model.IngredientDao;
import Model.Ingredients;
import Model.Managers.BillManager;
import Model.OrderDao;
import Model.Threads.ProgressThread;
import Model.Threads.ThreadManager;
import Model.TypeClient;
import Model.WorkOrder;
import java.util.ArrayList;

public class ClientController {
    private final ClientDao clientDao;
    private final OrderDao orderDao;
    private final DishesDao dishesDao;
    private final BillManager billManager;
    private final ThreadManager threadManager;

    public ClientController(ClientDao clientDao, OrderDao orderDao, DishesDao dishesDao, BillManager billManager, ThreadManager threadManager) {
        this.clientDao = clientDao;
        this.orderDao = orderDao;
        this.dishesDao = dishesDao;
        this.billManager = billManager;
        this.threadManager = threadManager;
    }
    
    public Client authentificateClient(String user, String pass){
        return clientDao.authenticate(user, pass);
    }
    public boolean registerClient(Client client){
        return clientDao.create(client);
    }
    public ArrayList<Dishes> getAvailableDishes(){
        return dishesDao.getAll();
    }
    public WorkOrder createOrder(Dishes dishes, Client client){
        if(dishes != null && client != null){
            WorkOrder order = new WorkOrder(dishes,client);
            if(orderDao.create(order)){
                threadManager.addClientProgress(client,null);
                return order;
            }
        }
        return null;
    }
    public ArrayList<WorkOrder> getClientOrders(Client client){
        return orderDao.getOrdersByClient(client);
    }
    public ArrayList<Bill> getClientBills(Client client){
        return billManager.getClientBills(client);
    }
    public Bill paidBill(int billNumber){
        Bill bill = billManager.getBill(billNumber);
        if(bill != null && !bill.isPaid()){
            billManager.payBill(billNumber);
            return bill;
        }
        return null;
    }
     public void setProgressListener(Client client, ProgressThread.ProgressUpdateListener listener) {
        threadManager.addClientProgress(client, listener);
    }
    public ArrayList<Client> getAllClients(){
        return clientDao.getAll();
    }
    public int countClientsByType(TypeClient type) {
        return clientDao.countByType(type);
    }
    
     
}
