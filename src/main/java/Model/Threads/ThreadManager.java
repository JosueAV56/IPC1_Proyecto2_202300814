package Model.Threads;
import Model.Client;
import Model.Employee;
import Model.OrderDao;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadManager {
    private final OrderDao orderDao;
    private final ConcurrentHashMap<Integer,KitchenThread> chefsThread;
    private final ConcurrentHashMap<String,ProgressThread> progressThreads;
    private volatile boolean running = false;

    public ThreadManager(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.chefsThread = new ConcurrentHashMap<>();
        this.progressThreads = new ConcurrentHashMap<>();
        System.out.println("[ThreadManager] Inicializado");
    }

    public synchronized void initializeWithChefs(ArrayList<Employee> existingChefs) {
        System.out.println("[ThreadManager] Inicializando con " + existingChefs.size() + " chefs existentes");
        
        stopAllKitchenThreads();
        
        for (Employee chef : existingChefs) {
            if (chef != null && chef.isChef()) {
                System.out.println("[ThreadManager] Creando hilo para chef: " + chef.getName() + " (ID: " + chef.getId() + ")");
                KitchenThread kitchenThread = new KitchenThread(chef, orderDao);
                chefsThread.put(chef.getId(), kitchenThread);
            } else {
                System.out.println("[ThreadManager]️ Empleado no es chef o es null: " + 
                                 (chef != null ? chef.getName() : "NULL"));
            }
        }
        
        System.out.println("[ThreadManager] Hilos creados: " + chefsThread.size());
    }
    
    public synchronized void startKitchenThreads() throws InterruptedException {
        if (chefsThread.isEmpty()) {
            System.out.println("[ThreadManager] ️ No hay hilos de chef para iniciar. Use initializeWithChefs() primero.");
            return;
        }
        
        System.out.println("[ThreadManager] Iniciando " + chefsThread.size() + " hilos de cocina...");
        running = true;
        
        for (KitchenThread kitchenThread : chefsThread.values()) {
            if (!kitchenThread.isAlive()) {
                kitchenThread.start();
                System.out.println("[ThreadManager] Iniciado hilo para: " + kitchenThread.getChef().getName());
                Thread.sleep(2000); 
            }
        }
        
        System.out.println("[ThreadManager] Todos los hilos de cocina iniciados");
    }

    private void stopAllKitchenThreads() {
        System.out.println("[ThreadManager] Deteniendo " + chefsThread.size() + " hilos de cocina...");
        
        for (KitchenThread thread : chefsThread.values()) {
            if (thread.isAlive()) {
                thread.stopThread();
                try {
                    thread.join(2000); // Esperar máximo 2 segundos
                } catch (InterruptedException e) {
                    System.out.println("[ThreadManager] Interrumpido esperando a " + thread.getChef().getName());
                    Thread.currentThread().interrupt();
                }
            }
        }
        chefsThread.clear();
        System.out.println("[ThreadManager] Hilos de cocina detenidos");
    }
    
    public synchronized void stopSystem(){
        System.out.println("[ThreadManager] Deteniendo sistema...");
        if(running){
            running = false;
            
            stopAllKitchenThreads();
            
            System.out.println("[ThreadManager] Deteniendo " + progressThreads.size() + " ProgressThreads");
            for(ProgressThread pt: progressThreads.values()){
                System.out.println("[ThreadManager] Deteniendo ProgressThread: " + pt.getClient().getUser());
                pt.stopThread();
                try {
                    pt.join(1000);
                } catch (InterruptedException e) {
                    System.out.println("[ThreadManager] Interrumpido esperando a " + pt.getClient().getUser());
                    Thread.currentThread().interrupt();
                }
            }
            
            progressThreads.clear();
            System.out.println("[ThreadManager] Todos los hilos detenidos");
        } else {
            System.out.println("[ThreadManager] Sistema ya estaba detenido");
        }
    }

    public void removeChef(int chefId) {
        System.out.println("[ThreadManager] Removiendo chef ID: " + chefId);
        KitchenThread thread = chefsThread.remove(chefId);
        if (thread != null) {
            thread.stopThread();
            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[ThreadManager] ✅ Chef removido: " + chefId);
        } else {
            System.out.println("[ThreadManager] Chef no encontrado: " + chefId);
        }
    }
    
    public void addClientProgress(Client client, ProgressThread.ProgressUpdateListener listener) {
        System.out.println("[ThreadManager] Agregando monitor de progreso para: " + client.getUser());
        
        if (client != null && !progressThreads.containsKey(client.getDpi())) {
            ProgressThread progressThread = new ProgressThread(client, orderDao, listener);
            progressThreads.put(client.getDpi(), progressThread);
            
            System.out.println("[ThreadManager] Configurando listener en " + chefsThread.size() + " KitchenThreads");
            for (KitchenThread kitchenThread : chefsThread.values()) {
                kitchenThread.setProgressListener(listener);
                System.out.println("[ThreadManager] Listener configurado en KitchenThread: " + kitchenThread.getChef().getName());
            }
            
            if (running) {
                progressThread.start();
                System.out.println("[ThreadManager] ✅ ProgressThread iniciado para: " + client.getUser());
            } else {
                System.out.println("[ThreadManager] ADVERTENCIA: Sistema no está ejecutándose, ProgressThread no iniciado");
            }
        } else {
            System.out.println("[ThreadManager] Cliente ya tiene monitor o es null: " + (client != null ? client.getUser() : "null"));
        }
    }
    
    public void removeClientProgress(String clientDpi) {
        System.out.println("[ThreadManager] Removiendo monitor de progreso para DPI: " + clientDpi);
        ProgressThread thread = progressThreads.remove(clientDpi);
        if (thread != null) {
            thread.stopThread();
            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[ThreadManager] ✅ Monitor removido para DPI: " + clientDpi);
        } else {
            System.out.println("[ThreadManager] Monitor no encontrado para DPI: " + clientDpi);
        }
    }
    
    public boolean isChefWorking(int chefId) {
        KitchenThread thread = chefsThread.get(chefId);
        boolean working = thread != null && thread.isWorking();
        System.out.println("[ThreadManager] Chef " + chefId + " trabajando: " + working);
        return working;
    }
    
    public ArrayList<Employee> getAvailableChefs() {
        ArrayList<Employee> available = new ArrayList<>();
        for (KitchenThread thread : chefsThread.values()) {
            if (!thread.isWorking()) {
                available.add(thread.getChef());
            }
        }
        System.out.println("[ThreadManager] Chefs disponibles: " + available.size() + "/" + chefsThread.size());
        return available;
    }
    
    public void restartWithChefs(ArrayList<Employee> chefs) throws InterruptedException {
        System.out.println("[ThreadManager] Reiniciando sistema con chefs específicos...");
        stopSystem();
        Thread.sleep(1000);
        initializeWithChefs(chefs);
        startKitchenThreads();
    }
    
    public void printSystemStatus() {
        System.out.println("[ThreadManager] === ESTADO DEL SISTEMA ===");
        System.out.println("[ThreadManager] Sistema ejecutándose: " + running);
        System.out.println("[ThreadManager] Hilos de cocina registrados: " + chefsThread.size());
        System.out.println("[ThreadManager] Hilos de progreso activos: " + progressThreads.size());
        
        for (KitchenThread thread : chefsThread.values()) {
            System.out.println("[ThreadManager] - " + thread.getChef().getName() + 
                             " (ID: " + thread.getChef().getId() + ")" +
                             " | Vivo: " + thread.isAlive() + 
                             " | Trabajando: " + thread.isWorking() +
                             " | Estado: " + thread.getState());
        }
        System.out.println("[ThreadManager] === FIN ESTADO ===");
    }
    
    public int getActiveChefCount() {
        int count = chefsThread.size();
        System.out.println("[ThreadManager] Hilos de chef registrados: " + count);
        return count;
    }
    
    public int getActiveProgressCount() {
        int count = progressThreads.size();
        System.out.println("[ThreadManager] Hilos de progreso activos: " + count);
        return count;
    }
    
    public boolean isRunning() {
        System.out.println("[ThreadManager] Sistema ejecutándose: " + running);
        return running;
    }
    
    public boolean hasThreadsInitialized() {
        boolean initialized = !chefsThread.isEmpty();
        System.out.println("[ThreadManager] Hilos inicializados: " + initialized);
        return initialized;
    }
    
    public Employee getChefById(int chefId) {
        KitchenThread thread = chefsThread.get(chefId);
        return thread != null ? thread.getChef() : null;
    }
}