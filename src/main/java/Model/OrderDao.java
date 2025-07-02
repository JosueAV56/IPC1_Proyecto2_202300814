package Model;
import java.util.ArrayList;
import java.util.LinkedList;

public class OrderDao {
    private ArrayList<WorkOrder> orders;
    private LinkedList<WorkOrder> waitingQueue;
    private LinkedList<WorkOrder> kitchenQueue;
    private LinkedList<WorkOrder> readyQueue;
    private int nextOrder;
    private IngredientDao ingredientDao;

    public OrderDao(IngredientDao ingredientDao) {
        this.orders = new ArrayList<>();
        this.waitingQueue = new LinkedList<>();
        this.kitchenQueue = new LinkedList<>();
        this.readyQueue = new LinkedList<>();
        this.nextOrder = 1;
        this.ingredientDao = ingredientDao; 
        System.out.println("[OrderDao] Inicializado correctamente con IngredientDao");
    }

    public OrderDao() {
        this.orders = new ArrayList<>();
        this.waitingQueue = new LinkedList<>();
        this.kitchenQueue = new LinkedList<>();
        this.readyQueue = new LinkedList<>();
        this.nextOrder = 1;
        this.ingredientDao = null;
        System.out.println("[OrderDao] Inicializado SIN IngredientDao - DEBE ser configurado");
    }
    
    
    public synchronized boolean create(WorkOrder order) {
        if (order != null) {
            order.setOrderNumber(nextOrder++);
            order.setOrderStatus(OrderStatus.QUEUE_WAIT);
            if (order.getDishes() != null) {
            order.getDishes().increaseOrderCounter();
            }
            orders.add(order);
            
            if (order.hasGoldClient()) {
                waitingQueue.addFirst(order);
                System.out.println("[OrderDao]  Orden oro #" + order.getOrderNumber() + " creada (prioridad)");
            } else {
                waitingQueue.addLast(order);
                System.out.println("[OrderDao]  Orden #" + order.getOrderNumber() + " creada");
            }
            return true;
        }
        System.out.println("[OrderDao]  Error: Orden nula");
        return false;
    }

    public WorkOrder getByOrderNumber(int orderNumber) {
        for (WorkOrder order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }

    public ArrayList<WorkOrder> getAll() {
        return new ArrayList<>(orders);
    }

    public synchronized boolean update(WorkOrder newOrder) {
        if (newOrder != null) {
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getOrderNumber() == newOrder.getOrderNumber()) {
                    orders.set(i, newOrder);
                    System.out.println("[OrderDao] Orden #" + newOrder.getOrderNumber() + " actualizada");
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean delete(int orderNumber) {
        WorkOrder order = getByOrderNumber(orderNumber);
        if (order != null) {
            orders.remove(order);
            waitingQueue.remove(order);
            kitchenQueue.remove(order);
            readyQueue.remove(order);
            System.out.println("[OrderDao] Orden #" + orderNumber + " eliminada");
            return true;
        }
        return false;
    }

    // ===== MÉTODOS DE CONSULTA =====
    
    public ArrayList<WorkOrder> getOrdersByDish(Dishes dish) {
        ArrayList<WorkOrder> dishOrders = new ArrayList<>();
        if (dish != null) {
            for (WorkOrder order : orders) {
                if (order.getDishes() != null && 
                    order.getDishes().getIdentifier() == dish.getIdentifier()) {
                    dishOrders.add(order);
                }
            }
        }
        return dishOrders;
    }

    public ArrayList<WorkOrder> getOrdersByEmployee(Employee employee) {
        ArrayList<WorkOrder> employeeOrders = new ArrayList<>();
        if (employee != null) {
            for (WorkOrder order : orders) {
                if (order.getEmployee() != null && 
                    order.getEmployee().getId() == employee.getId()) {
                    employeeOrders.add(order);
                }
            }
        }
        return employeeOrders;
    }

    public ArrayList<WorkOrder> getOrdersByClient(Client client) {
        ArrayList<WorkOrder> clientOrders = new ArrayList<>();
        if (client != null) {
            for (WorkOrder order : orders) {
                if (order.getClient() != null && 
                    order.getClient().getDpi().equals(client.getDpi())) {
                    clientOrders.add(order);
                }
            }
        }
        System.out.println("[OrderDao] Órdenes para cliente " + 
                         (client != null ? client.getUser() : "null") + ": " + clientOrders.size());
        return clientOrders;
    }

    public ArrayList<WorkOrder> getOrdersByClientAndStatus(Client client, OrderStatus status) {
        ArrayList<WorkOrder> clientOrders = new ArrayList<>();
        if (client != null) {
            for (WorkOrder order : orders) {
                if (order.getClient() != null && 
                    order.getClient().getDpi().equals(client.getDpi()) &&
                    order.getOrderStatus() == status) {
                    clientOrders.add(order);
                }
            }
        }
        return clientOrders;
    }

    public ArrayList<WorkOrder> getOrdersByStatus(OrderStatus status) {
        ArrayList<WorkOrder> statusOrders = new ArrayList<>();
        for (WorkOrder order : orders) {
            if (order.getOrderStatus() == status) {
                statusOrders.add(order);
            }
        }
        return statusOrders;
    }

    public synchronized boolean hasWaitingOrders() {
        boolean hasWaiting = !waitingQueue.isEmpty();
        System.out.println("[OrderDao] Órdenes en espera: " + waitingQueue.size() + " (hay: " + hasWaiting + ")");
        return hasWaiting;
    }
    
    public synchronized WorkOrder getNextWaitingOrder() {
    if (!waitingQueue.isEmpty()) {

        for (WorkOrder order : waitingQueue) {
            if (order.hasGoldClient()) {
                System.out.println("[OrderDao] Priorizando orden oro #" + order.getOrderNumber());
                return order;
            }
        }

        WorkOrder order = waitingQueue.getFirst();
        System.out.println("[OrderDao] Siguiente orden normal #" + order.getOrderNumber());
        return order;
    }
    System.out.println("[OrderDao] No hay órdenes en espera");
    return null;
}

public synchronized boolean assignOrderToChef(WorkOrder order, Employee chef) {
    if (order == null || chef == null) {
        System.out.println("[OrderDao] Error: Orden o chef nulo");
        return false;
    }


    if (chef.getCurrentOrder() != null) {
        System.out.println("[OrderDao]  Chef " + chef.getName() + " ya tiene una orden asignada");
        return false;
    }

 
    if (!waitingQueue.contains(order)) {
        System.out.println("[OrderDao]  Orden #" + order.getOrderNumber() + " no está en cola de espera");
        return false;
    }


    if (order.getEmployee() != null) {
        System.out.println("[OrderDao]  Orden #" + order.getOrderNumber() + " ya está asignada a " + order.getEmployee().getName());
        return false;
    }


    boolean chefAccepted = chef.assignOrder(order);
    if (chefAccepted) {
        order.setEmployee(chef);
        System.out.println("[OrderDao] ✅ Orden #" + order.getOrderNumber() + 
                         " asignada a chef " + chef.getName() + " (ID: " + chef.getId() + ")");
        return true;
    } else {
        System.out.println("[OrderDao] ❌ Chef " + chef.getName() + " no pudo aceptar la orden");
        return false;
    }
}


public synchronized boolean isOrderAssignedToChef(WorkOrder order, Employee chef) {
    if (order == null || chef == null) {
        return false;
    }
    

    Employee assignedChef = order.getEmployee();
    boolean isAssigned = assignedChef != null && assignedChef.getId() == chef.getId();
    
    if (isAssigned) {
        System.out.println("[OrderDao] ✅ Orden #" + order.getOrderNumber() + " confirmada para chef " + chef.getName());
    } else {
        System.out.println("[OrderDao] ❌ Orden #" + order.getOrderNumber() + " NO asignada a chef " + chef.getName());
        if (assignedChef != null) {
            System.out.println("[OrderDao] Orden asignada a: " + assignedChef.getName());
        }
    }
    
    return isAssigned;
}

    public synchronized boolean moveToKitchen(WorkOrder order, Employee chef) {
        if (order == null || chef == null) {
        System.out.println("[OrderDao] ❌ Error: Orden o chef nulo");
        return false;
    }


    Dishes dish = order.getDishes();
        if (!hasEnoughStock(dish)) {
            System.out.println("[OrderDao] ❌ No hay suficiente stock para preparar " + dish.getName());
            return false;
        }


        if (!reduceIngredientsStock(dish)) {
            System.out.println("[OrderDao] ❌ Error al reducir el stock");
            return false;
        }


        if (waitingQueue.remove(order)) {
            kitchenQueue.add(order);
            order.setOrderStatus(OrderStatus.IN_KITCHEN);
            updateOrderInList(order);
        
                System.out.println("[OrderDao] ✅ Orden #" + order.getOrderNumber() + 
                         " movida a cocina por " + chef.getName());
            return true;
        }

    System.out.println("[OrderDao] ❌ Error al mover orden #" + order.getOrderNumber() + " a cocina");
    return false;
    }
    
        public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
        System.out.println("[OrderDao] IngredientDao configurado");
    }

 
    private synchronized boolean hasEnoughStock(Dishes dish) {
        if (ingredientDao == null) {
            System.err.println("[OrderDao] ❌ ERROR: IngredientDao no inicializado");
            return false;
        }
        
        if (dish == null || dish.getIngredientsId() == null) {
            System.err.println("[OrderDao] ❌ ERROR: Platillo o ingredientes nulos");
            return false;
        }
        
        for (Integer ingredientId : dish.getIngredientsId()) {
            if (ingredientId == null) {
                System.err.println("[OrderDao] ❌ ERROR: ID de ingrediente nulo");
                return false;
            }
            
            Ingredients ingredient = ingredientDao.getById(ingredientId);
            if (ingredient == null) {
                System.err.println("[OrderDao] ❌ ERROR: Ingrediente no encontrado: " + ingredientId);
                return false;
            }
            
            if (!ingredient.hasStock()) {
                System.out.println("[OrderDao] ❌ Sin stock: " + ingredient.getName());
                return false;
            }
        }
        return true;
    }

    private synchronized boolean reduceIngredientsStock(Dishes dish) {
    if (ingredientDao == null) {
        System.err.println("[OrderDao]  ERROR: IngredientDao no inicializado");
        return false;
    }
    
    if (dish == null || dish.getIngredientsId() == null) {
        System.err.println("[OrderDao]  ERROR: Platillo o ingredientes nulos");
        return false;
    }
    
    ArrayList<Integer> modifiedIngredients = new ArrayList<>();
    
    try {
        for (Integer ingredientId : dish.getIngredientsId()) {
            if (ingredientId == null) continue;
            
            Ingredients ingredient = ingredientDao.getById(ingredientId);
            if (ingredient == null) {
                System.err.println("[OrderDao] ❌ ERROR: Ingrediente no encontrado: " + ingredientId);
                rollbackStockChanges(modifiedIngredients);
                return false;
            }
            
            if (!ingredient.reduceStock(1)) {
                System.err.println("[OrderDao] ❌ ERROR: No se pudo reducir stock del ingrediente: " + ingredient.getName());
                rollbackStockChanges(modifiedIngredients);
                return false;
            }
            
            modifiedIngredients.add(ingredientId);
        }
        
        for (Integer ingredientId : dish.getIngredientsId()) {
            if (ingredientId == null) continue;
            
            ingredientDao.incrementUsageCount(ingredientId);
            System.out.println("[OrderDao]  Ingrediente " + ingredientId + " usado, contador incrementado");
        }
        
        System.out.println("[OrderDao]  Stock reducido correctamente para platillo: " + dish.getName());
        return true;
        
    } catch (Exception e) {
        System.err.println("[OrderDao]  ERROR en reduceIngredientsStock: " + e.getMessage());
        rollbackStockChanges(modifiedIngredients);
        return false;
    }
    }

    private void rollbackStockChanges(ArrayList<Integer> modifiedIngredients) {
    System.out.println("[OrderDao] Iniciando rollback de cambios de stock...");
    for (Integer ingredientId : modifiedIngredients) {
        if (ingredientId == null) continue;
        
        Ingredients ingredient = ingredientDao.getById(ingredientId);
        if (ingredient != null) {
            ingredient.increaseStock(1);
            System.out.println("[OrderDao] Stock restaurado para ingrediente: " + ingredient.getName());
        }
    }
}
    public synchronized boolean moveToReady(WorkOrder order, Employee chef) {
        if (order == null) {
            System.out.println("[OrderDao] Error: Orden nula");
            return false;
        }
        
        if (chef == null) {
            System.out.println("[OrderDao] Error: Chef nulo");
            return false;
        }

        if (!kitchenQueue.contains(order)) {
            System.out.println("[OrderDao]  Orden #" + order.getOrderNumber() + " no está en cocina");
            return false;
        }

        if (order.getEmployee() == null || order.getEmployee().getId() != chef.getId()) {
            System.out.println("[OrderDao]  Orden no asignada al chef correcto");
            return false;
        }

        if (kitchenQueue.remove(order)) {
            readyQueue.add(order);
            order.setOrderStatus(OrderStatus.READY);
            updateOrderInList(order);
            
            chef.endOrder();
            
            System.out.println("[OrderDao]  Orden #" + order.getOrderNumber() + 
                             " lista para servir por " + chef.getName());
            return true;
        }

        System.out.println("[OrderDao]  Error al mover orden #" + order.getOrderNumber() + " a ready");
        return false;
    }

    private void updateOrderInList(WorkOrder order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderNumber() == order.getOrderNumber()) {
                orders.set(i, order);
                break;
            }
        }
    }
    
    public synchronized boolean markOrderAsPaid(WorkOrder order) {
        if (order == null || !readyQueue.contains(order)) {
            System.out.println("[OrderDao]  Error: Orden no está lista para pagar");
            return false;
        }

        if (readyQueue.remove(order)) {
            Client client = order.getClient();
            if (client != null) {
                client.increasePaidDishes();
                if (client.getPaidDishes() >= 10 && client.getTypeClient() == TypeClient.COMMUN) {
                    client.setTypeClient(TypeClient.GOLD);
                    System.out.println("[OrderDao]  Cliente " + client.getUser() + " promovido a ORO");
                }
            }

            System.out.println("[OrderDao]  Orden #" + order.getOrderNumber() + " marcada como pagada");
            return true;
        }

        return false;
    }

    
    public synchronized boolean hasKitchenOrders() {
        return !kitchenQueue.isEmpty();
    }

    public synchronized boolean hasReadyOrders() {
        return !readyQueue.isEmpty();
    }

    public int getTotalOrdersCount() {
        return orders.size();
    }

    public synchronized int getWaitingOrdersCount() {
        return waitingQueue.size();
    }

    public synchronized int getKitchenOrdersCount() {
        return kitchenQueue.size();
    }

    public synchronized int getReadyOrdersCount() {
        return readyQueue.size();
    }

    public synchronized int getOrdersCountByStatus(OrderStatus status) {
        int count = 0;
        for (WorkOrder order : orders) {
            if (order.getOrderStatus() == status) {
                count++;
            }
        }
        return count;
    }

    // ===== MÉTODOS DE OBTENCIÓN DE LISTAS =====
    
    public synchronized ArrayList<WorkOrder> getOrdersInWaiting() {
        return new ArrayList<>(waitingQueue);
    }

    public synchronized ArrayList<WorkOrder> getOrdersInKitchen() {
        return new ArrayList<>(kitchenQueue);
    }

    public synchronized ArrayList<WorkOrder> getReadyOrders() {
        return new ArrayList<>(readyQueue);
    }

    public synchronized ArrayList<WorkOrder> getCompletedOrders() {
        ArrayList<WorkOrder> completedOrders = new ArrayList<>();
        for (WorkOrder order : orders) {
            if (order.isReady()) {
                completedOrders.add(order);
            }
        }
        return completedOrders;
    }

    public synchronized ArrayList<WorkOrder> getPendingOrders() {
        ArrayList<WorkOrder> pendingOrders = new ArrayList<>();
        for (WorkOrder order : orders) {
            if (!order.isReady()) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }

    public synchronized ArrayList<WorkOrder> getGoldClientOrders() {
        ArrayList<WorkOrder> goldOrders = new ArrayList<>();
        for (WorkOrder order : orders) {
            if (order.hasGoldClient()) {
                goldOrders.add(order);
            }
        }
        return goldOrders;
    }

    // ===== MÉTODOS DE VERIFICACIÓN =====
    
    public synchronized boolean orderExists(int orderNumber) {
        return getByOrderNumber(orderNumber) != null;
    }

    public synchronized boolean isOrderInProcess(int orderNumber) {
        WorkOrder order = getByOrderNumber(orderNumber);
        return order != null && order.isInKitchen();
    }

    public synchronized boolean isOrderReady(int orderNumber) {
        WorkOrder order = getByOrderNumber(orderNumber);
        return order != null && order.isReady();
    }

    // ===== MÉTODOS DE UTILIDAD =====
    
    public synchronized void clear() {
        orders.clear();
        waitingQueue.clear();
        kitchenQueue.clear();
        readyQueue.clear();
        nextOrder = 1;
        System.out.println("[OrderDao] Todas las órdenes eliminadas");
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public int size() {
        return orders.size();
    }

    public synchronized void reorganizeWaitingQueue() {
        LinkedList<WorkOrder> newQueue = new LinkedList<>();
        
        for (WorkOrder order : waitingQueue) {
            if (order.hasGoldClient()) {
                newQueue.add(order);
            }
        }
        
        for (WorkOrder order : waitingQueue) {
            if (!order.hasGoldClient()) {
                newQueue.add(order);
            }
        }
        
        waitingQueue = newQueue;
        System.out.println("[OrderDao] Cola de espera reorganizada (oro primero)");
    }

    // ===== GETTERS Y SETTERS =====
    
    public synchronized ArrayList<WorkOrder> getInternalList() {
        return new ArrayList<>(orders);
    }

    public synchronized void setInternalList(ArrayList<WorkOrder> orders) {
        this.orders = orders != null ? orders : new ArrayList<>();
        rebuildQueues();
        recalculateNextOrderNumber();
        System.out.println("[OrderDao] Lista interna actualizada");
    }

    public synchronized int getNextOrderNumber() {
        return nextOrder;
    }

    public synchronized void setNextOrderNumber(int nextOrderNumber) {
        this.nextOrder = nextOrderNumber;
    }

    public synchronized LinkedList<WorkOrder> getWaitingQueue() {
        return new LinkedList<>(waitingQueue);
    }

    public synchronized LinkedList<WorkOrder> getKitchenQueue() {
        return new LinkedList<>(kitchenQueue);
    }

    public synchronized LinkedList<WorkOrder> getReadyQueue() {
        return new LinkedList<>(readyQueue);
    }

    // ===== MÉTODOS PRIVADOS DE UTILIDAD =====
    
    private synchronized void rebuildQueues() {
        waitingQueue.clear();
        kitchenQueue.clear();
        readyQueue.clear();
        
        for (WorkOrder order : orders) {
            switch (order.getOrderStatus()) {
                case QUEUE_WAIT:
                    if (order.hasGoldClient()) {
                        waitingQueue.addFirst(order);
                    } else {
                        waitingQueue.addLast(order);
                    }
                    break;
                case IN_KITCHEN:
                    kitchenQueue.add(order);
                    break;
                case READY:
                    readyQueue.add(order);
                    break;
            }
        }
        System.out.println("[OrderDao] Colas reconstruidas");
    }

    private synchronized void recalculateNextOrderNumber() {
        int maxOrderNumber = 0;
        for (WorkOrder order : orders) {
            if (order.getOrderNumber() > maxOrderNumber) {
                maxOrderNumber = order.getOrderNumber();
            }
        }
        nextOrder = maxOrderNumber + 1;
        System.out.println("[OrderDao] Próximo número de orden: " + nextOrder);
    }

    // ===== MÉTODOS DE DEPURACIÓN =====
    
    public synchronized void printSystemStatus() {
        System.out.println("[OrderDao] === ESTADO DEL SISTEMA ===");
        System.out.println("[OrderDao] Total de órdenes: " + orders.size());
        System.out.println("[OrderDao] En espera: " + waitingQueue.size());
        System.out.println("[OrderDao] En cocina: " + kitchenQueue.size());
        System.out.println("[OrderDao] Listas: " + readyQueue.size());
        System.out.println("[OrderDao] Próximo número: " + nextOrder);
        System.out.println("[OrderDao] === FIN ESTADO ===");
    }
}