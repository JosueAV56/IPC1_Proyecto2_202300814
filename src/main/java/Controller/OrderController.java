package Controller;

import Model.Client;
import Model.Employee;
import Model.OrderDao;
import Model.OrderStatus;
import Model.WorkOrder;
import Model.Threads.ThreadManager;
import java.util.ArrayList;

public class OrderController {
    private final OrderDao orderDao;
    private ThreadManager threadManager;
    
    public OrderController(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.threadManager = new ThreadManager(orderDao); // Inicializar automáticamente
    }
    
    // Constructor alternativo si ya tienes un ThreadManager
    public OrderController(OrderDao orderDao, ThreadManager threadManager) {
        this.orderDao = orderDao;
        this.threadManager = threadManager;
    }
    
    public ArrayList<WorkOrder> getWaitingOrders() {
        return orderDao.getOrdersInWaiting();
    }
    
    public ArrayList<WorkOrder> getKitchenOrders() {
        return orderDao.getOrdersInKitchen();
    }
    
    public ArrayList<WorkOrder> getReadyOrders() {
        return orderDao.getReadyOrders();
    }
    
    public boolean assignOrderToChef(WorkOrder order, Employee chef) {
        return orderDao.assignOrderToChef(order, chef);
    }
    
    public boolean markOrderAsReady(WorkOrder order) {
        return orderDao.moveToReady(order, order.getEmployee());
    }
    
    public boolean markOrderAsPaid(WorkOrder order) {
        return orderDao.markOrderAsPaid(order);
    }
    
    public ArrayList<WorkOrder> getOrdersByStatus(OrderStatus status) {
        return orderDao.getOrdersByStatus(status);
    }
    
    public int getWaitingOrdersCount() {
        return orderDao.getWaitingOrdersCount();
    }
    
    public int getKitchenOrdersCount() {
        return orderDao.getKitchenOrdersCount();
    }
    
    public int getReadyOrdersCount() {
        return orderDao.getReadyOrdersCount();
    }
    
    public boolean createOrder(WorkOrder order) {
        if (order != null) {
            return orderDao.create(order);
        }
        return false;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ArrayList<WorkOrder> getClientOrders(Client client) {
        return orderDao.getOrdersByClient(client);
    }
    
    public ThreadManager getThreadManager() {
        // Lazy initialization como respaldo
        if (threadManager == null) {
            threadManager = new ThreadManager(orderDao);
        }
        return threadManager;
    }
    
    public void setThreadManager(ThreadManager threadManager) {
        this.threadManager = threadManager;
    }
}