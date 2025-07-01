
package Model;


public enum OrderSpeed {
    HIGH("Alta",50),
    MEDIUM("Media",200),
    LOW("Baja", 500);
    
    private final String description;
    private final int delayMiliSeconds;

    private OrderSpeed(String description, int delayMiliSeconds) {
        this.description = description;
        this.delayMiliSeconds = delayMiliSeconds;
    }

    public String getDescription() {
        return description;
    }

    public int getDelayMiliSeconds() {
        return delayMiliSeconds;
    }
    
    @Override
    public String toString(){
        return description;
    }
    
    public static OrderSpeed fromString(String text){
        if(text != null){
            for(OrderSpeed orderSpeed : OrderSpeed.values()){
                if(orderSpeed.description.equalsIgnoreCase(text) || orderSpeed.name().equalsIgnoreCase(text)){
                    return orderSpeed;
                }
            }
        }
        return MEDIUM;
    }
}
