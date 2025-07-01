package Model;
import java.io.Serializable;

public class Employee implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private EmployeeType type;
    private boolean availability;
    private WorkOrder currentOrder;
    private transient Object lock;
    
    public Employee() {
        this.availability = true;
        this.currentOrder = null;
        initializeLock();
    }

    public Employee(int id, String name, EmployeeType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.availability = true;
        this.currentOrder = null;
        initializeLock();
    }
    
    // Initialize the lock object - called from constructors and after deserialization
    private void initializeLock() {
        if (this.lock == null) {
            this.lock = new Object();
        }
    }
    
    // This method is called automatically after deserialization
    private Object readResolve() {
        initializeLock();
        return this;
    }
    
    public boolean isChef(){
        return EmployeeType.CHEF.equals(type);
    }
   
    public boolean isAdministrador(){
        return EmployeeType.ADMIN.equals(type);
    }
    
    public boolean assignOrder(WorkOrder order) {
        initializeLock(); // Safety check
        synchronized(lock) {
            if (this.currentOrder != null) {
                return false;
            }
            this.currentOrder = order;
            this.availability = false;
            return true;
        }
    }
    
    public void endOrder() {
        initializeLock(); // Safety check
        synchronized(lock) {
            this.currentOrder = null;
            this.availability = true;
        }
    }
    
    public boolean canTakeOrder() {
        initializeLock(); // Safety check
        synchronized(lock) {
            return this.availability && this.isChef();
        }
    }
    
    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", tipo='" + type + '\'' +
                ", disponible=" + availability +
                ", tieneOrdenActual=" + (currentOrder != null) +
                '}';
    }
    
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public EmployeeType getType() {return type;}
    public void setType(EmployeeType type) {this.type = type;}
    public boolean isAvailability() {return availability;}
    public void setAvailability(boolean availability) {this.availability = availability;}
    public WorkOrder getCurrentOrder() {return currentOrder;}
    public void setCurrentOrder(WorkOrder currentOrder) {this.currentOrder = currentOrder;}
}