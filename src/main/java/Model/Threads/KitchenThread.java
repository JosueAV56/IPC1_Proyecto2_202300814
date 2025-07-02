package Model.Threads;
import Model.Employee;
import Model.OrderDao;
import Model.OrderStatus;
import Model.WorkOrder;

public class KitchenThread extends Thread {
    
    private static final int VERIFICATION_TIME = 2000; 
    
    private final Employee chef;
    private final OrderDao orderDao;
    private volatile boolean running = true;
    private volatile WorkOrder currentOrder = null;
    private ProgressThread.ProgressUpdateListener listener;
    private final Object orderLock = new Object();

    public KitchenThread(Employee chef, OrderDao orderDao) {
        this.chef = chef;
        this.orderDao = orderDao;
        this.setName("Cocina Thread-" + chef.getName());
        System.out.println("[KitchenThread]  Cocinero " + chef.getName() + " inicializado (ID: " + chef.getId() + ")");
    }
    
    public void setProgressListener(ProgressThread.ProgressUpdateListener listener) {
        this.listener = listener;
        System.out.println("[KitchenThread] Listener configurado para chef: " + chef.getName());
    }
    
    @Override
    public void run(){
        System.out.println("[KitchenThread]  INICIANDO hilo para chef: " + chef.getName() + " (ID: " + chef.getId() + ")");

        while(running && !Thread.currentThread().isInterrupted()) {
            try {
                // Debug
                synchronized(orderLock) {
                    System.out.println("[KitchenThread] === DEBUG ESTADO CHEF " + chef.getName() + " ===");
                    System.out.println("[KitchenThread] Chef ID: " + chef.getId());
                    System.out.println("[KitchenThread] Chef disponible: " + chef.isAvailability());
                    System.out.println("[KitchenThread] Orden actual en chef: " + 
                                     (chef.getCurrentOrder() != null ? "#" + chef.getCurrentOrder().getOrderNumber() : "NINGUNA"));
                    System.out.println("[KitchenThread] Orden actual en thread: " + 
                                     (currentOrder != null ? "#" + currentOrder.getOrderNumber() : "NINGUNA"));
                    System.out.println("[KitchenThread] === FIN DEBUG ===");
                }
                
                System.out.println("[KitchenThread] " + chef.getName() + " verificando órdenes...");
                
                if (chef == null) {
                    System.err.println("[KitchenThread] ❌ ERROR CRÍTICO: Chef es null");
                    break;
                }
                
                boolean canTake;
                try {
                    canTake = chef.canTakeOrder();
                    System.out.println("[KitchenThread] " + chef.getName() + " puede tomar orden: " + canTake);
                } catch (Exception e) {
                    System.err.println("[KitchenThread]  ERROR en canTakeOrder() para chef " + 
                                     chef.getName() + ": " + e.getMessage());
                    e.printStackTrace();
                    Thread.sleep(VERIFICATION_TIME);
                    continue;
                }
                
                if (!canTake) {
                    WorkOrder chefCurrentOrder = chef.getCurrentOrder();
                    synchronized(orderLock) {
                        if (chefCurrentOrder != null) {
                            System.out.println("[KitchenThread] " + chef.getName() + " ocupado con orden #" + 
                                             chefCurrentOrder.getOrderNumber() + 
                                             " (Chef asignado: " + (chefCurrentOrder.getEmployee() != null ? 
                                             chefCurrentOrder.getEmployee().getName() : "NINGUNO") + ")");
                            
                            if (currentOrder == null || currentOrder.getOrderNumber() != chefCurrentOrder.getOrderNumber()) {
                                System.out.println("[KitchenThread]️ Sincronizando estado local con chef");
                                currentOrder = chefCurrentOrder;
                            }
                        } else {
                            System.out.println("[KitchenThread] " + chef.getName() + " dice no poder tomar orden pero no tiene orden actual - INCONSISTENCIA");
                            chef.endOrder();
                            currentOrder = null;
                        }
                    }
                    Thread.sleep(VERIFICATION_TIME);
                    continue;
                }
                
                if (orderDao == null) {
                    System.err.println("[KitchenThread] ERROR CRÍTICO: OrderDao es null");
                    break;
                }
                
                // Debug
                boolean hasWaiting = orderDao.hasWaitingOrders();
                System.out.println("[KitchenThread] " + chef.getName() + " - Hay órdenes esperando: " + hasWaiting);
                
                if (hasWaiting) {
                    System.out.println("[KitchenThread] " + chef.getName() + " buscando siguiente orden...");
                    
                    synchronized(orderLock) {
                        if (!chef.canTakeOrder()) {
                            System.out.println("[KitchenThread] " + chef.getName() + " ya no puede tomar órdenes después de sincronización");
                            continue;
                        }
                        
                        System.out.println("[KitchenThread] " + chef.getName() + " obteniendo siguiente orden...");
                        WorkOrder nextOrder = orderDao.getNextWaitingOrder();
                        
                        if (nextOrder != null) {
                            System.out.println("[KitchenThread] " + chef.getName() + " encontró orden #" + nextOrder.getOrderNumber());
                            System.out.println("[KitchenThread] Estado de orden #" + nextOrder.getOrderNumber() + ": " + nextOrder.getOrderStatus());
                            System.out.println("[KitchenThread] Chef actual en orden: " + 
                                             (nextOrder.getEmployee() != null ? nextOrder.getEmployee().getName() : "NINGUNO"));
                            
                            System.out.println("[KitchenThread] " + chef.getName() + " intentando asignar orden #" + nextOrder.getOrderNumber());
                            boolean assigned = orderDao.assignOrderToChef(nextOrder, chef);
                            
                            if (assigned) {
                                currentOrder = nextOrder;
                                System.out.println("[KitchenThread] ÉXITO: Orden #" + nextOrder.getOrderNumber() + 
                                                 " asignada a " + chef.getName() + " (ID: " + chef.getId() + ")");
                                
                                if (chef.getCurrentOrder() != null && chef.getCurrentOrder().getOrderNumber() == nextOrder.getOrderNumber()) {
                                    System.out.println("[KitchenThread] ✅ Verificación: Chef tiene la orden correcta");
                                } else {
                                    System.err.println("[KitchenThread] ❌ ERROR: Asignación no se reflejó en chef");
                                }
                                
                                try {
                                    processOrder(nextOrder);
                                } catch (Exception e) {
                                    System.err.println("[KitchenThread] ❌ Error procesando orden: " + e.getMessage());
                                    e.printStackTrace();
                                } finally {
                                    synchronized(orderLock) {
                                        System.out.println("[KitchenThread] Limpiando estado después de procesar orden #" + 
                                                         (nextOrder != null ? nextOrder.getOrderNumber() : "NULL"));
                                        currentOrder = null;
                                    }
                                }
                            } else {
                                System.out.println("[KitchenThread] ❌ FALLO: No se pudo asignar orden #" + nextOrder.getOrderNumber() + 
                                                 " a " + chef.getName() + " - podría estar ya asignada");
                                
                                if (nextOrder.getEmployee() != null) {
                                    System.out.println("[KitchenThread] Orden #" + nextOrder.getOrderNumber() + 
                                                     " está asignada a: " + nextOrder.getEmployee().getName() + 
                                                     " (ID: " + nextOrder.getEmployee().getId() + ")");
                                }
                            }
                        } else {
                            System.out.println("[KitchenThread] " + chef.getName() + " no encontró órdenes disponibles");
                        }
                    }
                } else {
                    System.out.println("[KitchenThread] " + chef.getName() + " - No hay órdenes esperando");
                    Thread.sleep(VERIFICATION_TIME);
                }
            } catch (InterruptedException e) {
                System.out.println("[KitchenThread] ⚠️ Hilo interrumpido para chef: " + chef.getName());
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("[KitchenThread] ❌ ERROR GENERAL en chef " + 
                                 (chef != null ? chef.getName() : "NULL") + ": " + e.getMessage());
                e.printStackTrace();
                
                synchronized(orderLock) {
                    currentOrder = null;
                }
                if (chef != null) {
                    chef.endOrder();
                }
                
                try {
                    Thread.sleep(VERIFICATION_TIME);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        
        synchronized(orderLock) {
            currentOrder = null;
        }
        if (chef != null) {
            chef.endOrder();
        }
        
        System.out.println("[KitchenThread] Cocinero " + 
                         (chef != null ? chef.getName() : "NULL") + " terminado");
    }

    public void processOrder(WorkOrder order) throws InterruptedException {
        System.out.println("[KitchenThread] === PROCESANDO ORDEN #" + 
                         (order != null ? order.getOrderNumber() : "NULL") + " CON CHEF " + chef.getName() + " ===");
        
        if (order == null) {
            System.err.println("[KitchenThread] ERROR: Orden nula");
            return;
        }
        
        if (!running) {
            System.out.println("[KitchenThread] Hilo detenido, cancelando procesamiento");
            return;
        }
        
        if (chef == null) {
            System.err.println("[KitchenThread] ERROR: Chef nulo");
            return;
        }

        try {
            System.out.println("[KitchenThread] Verificando asignación de orden #" + order.getOrderNumber() + " a " + chef.getName());
            if (!orderDao.isOrderAssignedToChef(order, chef)) {
                System.out.println("[KitchenThread]️ Orden #" + order.getOrderNumber() + " ya no está asignada a " + chef.getName());
                return;
            }
            System.out.println("[KitchenThread] Orden #" + order.getOrderNumber() + " confirmada para " + chef.getName());

            // 1. Fase de espera en cola (solo si es cliente normal)
            if (!order.hasGoldClient()) {
                System.out.println("[KitchenThread] Orden #" + order.getOrderNumber() + " en cola de espera (cliente normal)");
                int queueTimeMs = OrderStatus.QUEUE_WAIT.getMillisecondsTime();
                int updateInterval = Math.max(queueTimeMs / 100, 10); // Mínimo 10ms
                
                for (int i = 0; i <= 100; i++) {
                    if (!running) return;
                    
                    if (!orderDao.isOrderAssignedToChef(order, chef)) {
                        System.out.println("[KitchenThread]️ Orden reassignada durante procesamiento - abortando");
                        return;
                    }
                    
                    updateProgressBar(order, OrderStatus.QUEUE_WAIT, i);
                    if (i < 100) Thread.sleep(updateInterval);
                }
            } else {
                System.out.println("[KitchenThread]  Cliente Oro detectado - Prioridad a orden #" + order.getOrderNumber());
                updateProgressBar(order, OrderStatus.QUEUE_WAIT, 100);
                Thread.sleep(100);
            }

            // Verificar que la orden sigue asignada a este chef
            if (!orderDao.isOrderAssignedToChef(order, chef)) {
                System.out.println("[KitchenThread]️ Orden ya no está asignada a este chef");
                return;
            }

            // Mover la orden a cocina
            System.out.println("[KitchenThread] Moviendo orden #" + order.getOrderNumber() + " a cocina");
            if (!orderDao.moveToKitchen(order, chef)) {
                System.err.println("[KitchenThread] Error al mover orden #" + order.getOrderNumber() + " a cocina");
                return;
            }

            // Fase de preparación en cocina
            System.out.println("[KitchenThread] Orden #" + order.getOrderNumber() + " siendo preparada por " + chef.getName());
            int cookingTimeMs = OrderStatus.IN_KITCHEN.getMillisecondsTime();
            int updateInterval = Math.max(cookingTimeMs / 100, 10);
            
            for (int i = 0; i <= 100; i++) {
                if (!running) return;
                
                if (!orderDao.isOrderAssignedToChef(order, chef)) {
                    System.out.println("[KitchenThread] ️ Orden reassignada durante cocción - abortando");
                    return;
                }
                
                updateProgressBar(order, OrderStatus.IN_KITCHEN, i);
                if (i < 100) Thread.sleep(updateInterval);
            }

            System.out.println("[KitchenThread] Finalizando orden #" + order.getOrderNumber());
            if (!orderDao.moveToReady(order, chef)) {
                System.err.println("[KitchenThread] Error al marcar orden #" + order.getOrderNumber() + " como lista");
                return;
            }

            System.out.println("[KitchenThread] Mostrando progreso de listo para orden #" + order.getOrderNumber());
            int readyTimeMs = OrderStatus.READY.getMillisecondsTime();
            updateInterval = Math.max(readyTimeMs / 100, 10);
            
            for (int i = 0; i <= 100; i++) {
                if (!running) break;
                updateProgressBar(order, OrderStatus.READY, i);
                if (i < 100) Thread.sleep(updateInterval);
            }

            if (listener != null) {
                System.out.println("[KitchenThread] Notificando factura pendiente para orden #" + order.getOrderNumber());
                listener.onInvoicePending(order);
            }

            System.out.println("[KitchenThread] === ORDEN #" + order.getOrderNumber() + " COMPLETADA POR " + chef.getName() + " ===");
            
        } catch (Exception e) {
            System.err.println("[KitchenThread]  ERROR procesando orden #" + order.getOrderNumber() + ": " + e.getMessage());
            e.printStackTrace();
            
            if (chef != null) {
                chef.endOrder();
            }
        }
    }
    
    private void updateProgressBar(WorkOrder order, OrderStatus phase, int progress) {
        if (listener != null) {
            listener.onPhaseProgressUpdate(order.getOrderNumber(), phase, progress);
        }
    }
    
    public synchronized void stopThread() {
        System.out.println("[KitchenThread] Deteniendo cocinero " + chef.getName());
        running = false;
        this.interrupt();
    }
    
    public boolean isWorking() {
        synchronized(orderLock) {
            boolean working = currentOrder != null;
            return working;
        }
    }
    
    public WorkOrder getCurrentOrder() {
        synchronized(orderLock) {
            return currentOrder;
        }
    }
    
    public Employee getChef() {
        return chef;
    }
    
    public boolean isRunning() {
        return running;
    }
}