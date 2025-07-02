package Model;

public enum OrderStatus {
    QUEUE_WAIT("Cola de Espera", 15),  // 15 segundos
    IN_KITCHEN("En Cocina", 10),       // 10 segundos
    READY("Listo", 4),                // 4 segundos 
    PAID("Pagada",0);
    
    private final String description;
    private final int secondsTime;

    private OrderStatus(String description, int secondsTime) {
        this.description = description;
        this.secondsTime = secondsTime;
    }
    
    public OrderStatus next(){
        switch(this){
            case QUEUE_WAIT:
                return IN_KITCHEN;
            case IN_KITCHEN:
                return READY;
            case READY:
                return READY;
            default:
                return QUEUE_WAIT;
        }
    }
    
    public boolean isReady(){
        return this == READY;
    }
    
    public String toString(){
        return description;
    }
    
    public static OrderStatus fromString(String text){
        if(text != null){
            for(OrderStatus os : OrderStatus.values()){
                if(os.description.equalsIgnoreCase(text) || os.name().equalsIgnoreCase(text)){
                    return os;
                }
            }
        }
        return QUEUE_WAIT;
    }

    public String getDescription() { 
        return description;
    }
    
    public int getSecondsTime() { 
        return secondsTime;
    }
    
    public int getMillisecondsTime() {
        return secondsTime * 1000;
    }
}