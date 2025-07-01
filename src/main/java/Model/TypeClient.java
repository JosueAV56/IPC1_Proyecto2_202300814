
package Model;

public enum TypeClient {
    COMMUN("Normal"),
    GOLD("ORO");
    
    private String description;

    private TypeClient(String description) {
        this.description = description;
    }

    public String getDescription() {return description;}
    
    @Override
    public String toString(){
        return description;
    }
    
    public static TypeClient fromString(String text){
        
        if(text != null){
            for(TypeClient type: TypeClient.values()){
                if(type.description.equals(text) ||
                        type.name().equalsIgnoreCase(text)){
                    return type;
                
                }
            }
        
        }
        
        return COMMUN;
    
    }
    
}
