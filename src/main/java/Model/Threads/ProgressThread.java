package Model.Threads;
import Model.Client;
import Model.OrderDao;
import Model.OrderStatus;
import Model.WorkOrder;
import java.util.ArrayList;

public class ProgressThread extends Thread{
    private static final int UPDATE_TIME = 6000; 
    private final Client client;
    private final OrderDao orderDao;
    private final ProgressUpdateListener listener;
    private volatile boolean running = false; 
    private ArrayList<WorkOrder> lastOrders = null;

    public ProgressThread(Client client, OrderDao orderDao, ProgressUpdateListener listener) {
        this.client = client;
        this.orderDao = orderDao;
        this.listener = listener;
        this.setName("Progreso Thread-" + client.getCompleteName());
        System.out.println("[ProgressThread] Creado para cliente: " + client.getUser());
    }
    
    @Override
    public void run() {
        running = true; 
        System.out.println("[ProgressThread] INICIANDO monitoreo para cliente: " + client.getUser());
        
        while(running && !Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("[ProgressThread] Verificando órdenes para: " + client.getUser());
                ArrayList<WorkOrder> currentOrders = orderDao.getOrdersByClient(client);
                System.out.println("[ProgressThread] Órdenes encontradas: " + (currentOrders != null ? currentOrders.size() : 0));
                
                // loggear el estado actual
                if (currentOrders != null && !currentOrders.isEmpty()) {
                    logOrdersStatus(currentOrders);
                }
                
                // procesar si hay cambios reales
                if (hasOrdersChanged(currentOrders)) {
                    System.out.println("[ProgressThread]  CAMBIOS DETECTADOS en órdenes");
                    lastOrders = currentOrders != null ? new ArrayList<>(currentOrders) : new ArrayList<>();
                    
                    if (listener != null) {
                        System.out.println("[ProgressThread] Notificando listener...");
                        listener.onProgressUpdated(currentOrders);
                        System.out.println("[ProgressThread] Listener notificado");
                    } else {
                        System.out.println("[ProgressThread]  LISTENER ES NULL");
                    }
                    
                    // Verificar si todas las órdenes están completas
                    if (allOrdersCompleted(currentOrders)) {
                        System.out.println("[ProgressThread]  Todas las órdenes completadas");
                        break;
                    }
                } else {
                    System.out.println("[ProgressThread] Sin cambios en órdenes");
                }
                
                System.out.println("[ProgressThread] Esperando " + UPDATE_TIME + "ms...");
                Thread.sleep(UPDATE_TIME);
                
            } catch (InterruptedException e) {
                System.out.println("[ProgressThread]️ Interrumpido correctamente");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("[ProgressThread]  ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        running = false;
        System.out.println("[ProgressThread]  FINALIZADO para cliente: " + client.getUser());
    }

    private boolean allOrdersCompleted(ArrayList<WorkOrder> orders) {
        if (orders == null || orders.isEmpty()) {
            return false;
        }
        
        for (WorkOrder order : orders) {
            if (order.getOrderStatus() != OrderStatus.READY) {
                System.out.println("[ProgressThread] Orden #" + order.getOrderNumber() + " aún no completada: " + order.getOrderStatus());
                return false;
            }
        }
        System.out.println("[ProgressThread] Todas las órdenes están en estado READY");
        return true;
    }
    
    private boolean hasOrdersChanged(ArrayList<WorkOrder> currentOrders){
        System.out.println("[ProgressThread] Comparando órdenes...");
        System.out.println("[ProgressThread] Órdenes anteriores: " + (lastOrders != null ? lastOrders.size() : 0));
        System.out.println("[ProgressThread] Órdenes actuales: " + (currentOrders != null ? currentOrders.size() : 0));
        
        if(lastOrders == null && currentOrders != null && !currentOrders.isEmpty()) {
            System.out.println("[ProgressThread] Primera vez detectando órdenes");
            return true;
        }
        
        if(lastOrders != null && !lastOrders.isEmpty() && (currentOrders == null || currentOrders.isEmpty())) {
            System.out.println("[ProgressThread] Órdenes eliminadas");
            return true;
        }
        
        if((lastOrders == null || lastOrders.isEmpty()) && (currentOrders == null || currentOrders.isEmpty())) {
            System.out.println("[ProgressThread] No hay órdenes en ambos casos");
            return false;
        }
        
        if (lastOrders.size() != currentOrders.size()) {
            System.out.println("[ProgressThread] Cambio en cantidad de órdenes");
            return true;
        }
        
        // Verificar cambios en el estado de las órdenes
        for (WorkOrder current : currentOrders) {
            boolean found = false;
            for (WorkOrder last : lastOrders) {
                if (current.getOrderNumber() == last.getOrderNumber()) {
                    if (current.getOrderStatus() != last.getOrderStatus()) {
                        System.out.println("[ProgressThread] Orden #" + current.getOrderNumber() + 
                                         " cambió de " + last.getOrderStatus() + " a " + current.getOrderStatus());
                        return true;
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("[ProgressThread] Nueva orden detectada: #" + current.getOrderNumber());
                return true;
            }
        }
        
        return false;
    }
    
    private void logOrdersStatus(ArrayList<WorkOrder> orders) {
        System.out.println("[ProgressThread] === ESTADO DE ÓRDENES ===");
        if (orders != null && !orders.isEmpty()) {
            for (WorkOrder order : orders) {
                System.out.println("[ProgressThread] Cliente: " + client.getUser() + 
                                 " | Orden #" + order.getOrderNumber() + 
                                 " | Estado: " + order.getOrderStatus().getDescription() +
                                 " | Chef: " + (order.getEmployee() != null ? order.getEmployee().getName() : "Sin asignar"));
            }
        } else {
            System.out.println("[ProgressThread] No hay órdenes para mostrar");
        }
        System.out.println("[ProgressThread] === FIN ESTADO ===");
    }
    
    public void stopThread() {
        System.out.println("[ProgressThread] Deteniendo hilo para: " + client.getUser());
        running = false;  
        this.interrupt();
    }
    
    public Client getClient() {
        return client;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public interface ProgressUpdateListener {
        void onProgressUpdated(ArrayList<WorkOrder> orders);
        void onPhaseProgressUpdate(int orderNumber, OrderStatus phase, int progress);
        default void onInvoicePending(WorkOrder order) {
            System.out.println("[ProgressUpdateListener] Factura pendiente para orden #" + order.getOrderNumber());
        }
    }
}