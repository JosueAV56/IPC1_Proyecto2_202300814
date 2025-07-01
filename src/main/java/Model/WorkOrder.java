
package Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderNumber;
    private Dishes dishes;
    private Client client;
    private LocalDateTime dateTime;
    private Employee employee;
    private OrderStatus orderStatus;
    private long startTime;
    private long endTime;
    private boolean notified = false;
    private float progress = 0;


    public WorkOrder() {
        this.dateTime = LocalDateTime.now();
        this.orderStatus= OrderStatus.QUEUE_WAIT;
        this.startTime = 0;
        this.endTime = 0; 
    }

    public WorkOrder(Dishes dishes, Client client) {
        this.dishes = dishes;
        this.client = client;
        this.dateTime = LocalDateTime.now();
        this.orderStatus = OrderStatus.QUEUE_WAIT;
        this.employee = null; 
        
    }
    
    public synchronized boolean isOnHold(){
        return this.orderStatus == OrderStatus.QUEUE_WAIT;
    }
    public synchronized void startProcess(){
        this.orderStatus = OrderStatus.IN_KITCHEN;
        this.startTime = System.currentTimeMillis();
    }
    public synchronized boolean isInKitchen(){
        return this.orderStatus == OrderStatus.IN_KITCHEN; 
    }
    public synchronized void completeOrder(){
        this.orderStatus = OrderStatus.READY;
        this.endTime = System.currentTimeMillis();
    }
    public synchronized long getProcessTime(){
        if(startTime>0 && endTime> 0){
            return endTime - startTime;
        }
        return 0;
    }
    public synchronized boolean isReady(){
        return this.orderStatus == OrderStatus.READY;
    }
    public synchronized boolean hasGoldClient(){
        return client !=null && client.isGoldClient();
    }
    
    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }   
    
    public String getFormattedDate (){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
    
    @Override
    public String toString() {
        return "OrdenTrabajo{" +
                "numeroOrden=" + orderNumber +
                ", platillo=" + (dishes != null ? dishes.getName() : "null") +
                ", cliente=" + (client != null ? client.getCompleteName(): "null") +
                ", fechaHora=" + getFormattedDate() +
                ", empleado=" + (employee != null ? employee.getName() : "null") +
                ", estado=" + orderStatus +
                '}';
    }
    

    public int getOrderNumber() {return orderNumber;}
    public void setOrderNumber(int orderNumber) {this.orderNumber = orderNumber;}
    public Dishes getDishes() {return dishes;}
    public void setDishes(Dishes dishes) {this.dishes = dishes;}
    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}
    public LocalDateTime getDateTime() {return dateTime;}
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}
    public Employee getEmployee() {return employee;}
    public void setEmployee(Employee employee) {this.employee = employee;}
    public OrderStatus getOrderStatus() {return orderStatus;}
    public void setOrderStatus(OrderStatus orderStatus) {this.orderStatus = orderStatus;}
    public long getStartTime() {return startTime;}
    public void setStartTime(long startTime) {this.startTime = startTime;}
    public long getEndTime() {return endTime;}
    public void setEndTime(long endTime) {this.endTime = endTime;}
}
